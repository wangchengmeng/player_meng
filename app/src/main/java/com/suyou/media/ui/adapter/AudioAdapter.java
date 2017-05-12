package com.suyou.media.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.maogu.htclibrary.util.DensityUtils;
import com.maogu.htclibrary.util.StringUtil;
import com.suyou.media.R;
import com.suyou.media.bean.AudioBean;
import com.suyou.media.ui.activity.PlayerMusicActivity;
import com.suyou.media.util.DownloadUtil;
import com.suyou.media.util.glid.GlideUtil;

import java.util.List;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.ViewHolder> {

    private Activity        mActivity;
    private List<AudioBean> mAudioBeen;

    public AudioAdapter(Activity activity, List<AudioBean> videoBeen) {
        this.mActivity = activity;
        this.mAudioBeen = videoBeen;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final AudioBean bean = mAudioBeen.get(holder.getAdapterPosition());
        holder.mViewRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AudioBean.DOWNLOAD_OVER == bean.getStatus()) {
                    PlayerMusicActivity.startActivity(mActivity, bean);
                } else {
                    PlayerMusicActivity.startActivity(mActivity, bean);
                }
            }
        });
        String imgUrl = (String) holder.mIvThumb.getTag(R.id.iv_thumb);
        if (StringUtil.isNullOrEmpty(imgUrl) || !imgUrl.equals(bean.getCover())) {
            GlideUtil.show(bean.getCover(), holder.mIvThumb, R.mipmap.placeholder);
        }
        holder.mIvThumb.setTag(R.id.iv_thumb, bean.getCover());
        holder.mTvTitle.setText(bean.getTitle());
        holder.mTvSinger.setText(bean.getSinger());

        holder.mPbDownload.setProgress(bean.getProgress());
        switch (bean.getStatus()) {
            case AudioBean.NO_DOWNLOAD:
                holder.mPbDownload.setVisibility(View.GONE);
                holder.mIvFlag.setEnabled(true);
                holder.mIvFlag.setImageResource(R.mipmap.icon_no_download);
                holder.mIvFlag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DownloadUtil.startDownload(bean.getDownloadUrl(), bean.getPath());
                    }
                });
                Log.d("aaa", "下载进度NO_DOWNLOAD：" + bean.getProgress());
                break;
            case AudioBean.DOWNLOAD_STOP:
                holder.mPbDownload.setVisibility(View.VISIBLE);
                holder.mIvFlag.setEnabled(true);
                holder.mIvFlag.setImageResource(R.mipmap.icon_no_download);
                holder.mIvFlag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DownloadUtil.startDownload(bean.getDownloadUrl(), bean.getPath());
                    }
                });
                Log.d("aaa", "下载进度DOWNLOAD_STOP：" + bean.getProgress());
                break;
            case AudioBean.PENDING:
                holder.mPbDownload.setVisibility(View.VISIBLE);
                holder.mIvFlag.setEnabled(false);
                holder.mIvFlag.setImageResource(R.mipmap.icon_download_pend);
                Log.d("aaa", "下载进度PENDING：" + bean.getProgress());
                break;
            case AudioBean.DOWNLOAD_ING:
                holder.mPbDownload.setVisibility(View.VISIBLE);
                holder.mIvFlag.setEnabled(true);
                holder.mIvFlag.setImageResource(R.mipmap.icon_download_pause);
                holder.mIvFlag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DownloadUtil.pauseDownload(bean.getDownloadUrl(), bean.getPath());
                    }
                });
                Log.d("aaa", "下载进度DOWNLOAD_ING：" + bean.getProgress());
                break;
            case AudioBean.DOWNLOAD_OVER:
                holder.mPbDownload.setVisibility(View.GONE);
                holder.mIvFlag.setEnabled(true);
                holder.mIvFlag.setImageResource(R.mipmap.download_local);
                holder.mIvFlag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PlayerMusicActivity.startActivity(mActivity, bean);
                    }
                });
                Log.d("aaa", "下载进度DOWNLOAD_OVER：" + bean.getProgress());
                break;
            default:
                break;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mActivity, R.layout.item_audio, null);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mAudioBeen.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView     mIvThumb;
        private ImageView     mIvArrow;
        private ImageView     mIvFlag;
        private View          mViewRoot;
        private DonutProgress mPbDownload;
        private TextView      mTvTitle;
        private TextView      mTvSinger;

        ViewHolder(View view) {
            super(view);

            mViewRoot = view.findViewById(R.id.view_root);
            mIvThumb = (ImageView) view.findViewById(R.id.iv_thumb);
            mIvArrow = (ImageView) view.findViewById(R.id.iv_arrow);
            mIvFlag = (ImageView) view.findViewById(R.id.iv_flag);
            mPbDownload = (DonutProgress) view.findViewById(R.id.circle_progress);
            mTvTitle = (TextView) view.findViewById(R.id.tv_title);
            mTvSinger = (TextView) view.findViewById(R.id.tv_singer);

            DensityUtils.measure(mIvThumb, 200, 200);
            DensityUtils.measure(mIvArrow, 30, 60);
            DensityUtils.measure(mPbDownload, 180, 180);
            DensityUtils.measure(mIvFlag, 97, 83);
        }
    }

}
