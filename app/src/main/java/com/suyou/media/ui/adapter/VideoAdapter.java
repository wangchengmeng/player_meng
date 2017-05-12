package com.suyou.media.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.maogu.htclibrary.util.DensityUtils;
import com.maogu.htclibrary.util.NetUtil;
import com.maogu.htclibrary.util.StringUtil;
import com.maogu.htclibrary.util.UIUtil;
import com.suyou.media.R;
import com.suyou.media.bean.VideoBean;
import com.suyou.media.ui.activity.PlayerActivity;
import com.suyou.media.util.DownloadUtil;
import com.suyou.media.util.glid.GlideUtil;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private Activity        mActivity;
    private List<VideoBean> mVideoBeen;

    public VideoAdapter(Activity activity, List<VideoBean> videoBeen) {
        this.mActivity = activity;
        this.mVideoBeen = videoBeen;
    }

    @Override
    public void onBindViewHolder(VideoAdapter.ViewHolder holder, int position) {
        final VideoBean bean = mVideoBeen.get(holder.getAdapterPosition());
        holder.mViewRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (VideoBean.DOWNLOAD_OVER == bean.getStatus()) {
                    PlayerActivity.startActivity(mActivity, bean.getPath(), bean.getName());
                } else {
                    if (!NetUtil.isNetworkAvailable()) {
                        return;
                    }
                    PlayerActivity.startActivity(mActivity, bean.getOnlineUrl(), bean.getName());
                }
            }
        });
        String imgUrl = (String) holder.mIvThumb.getTag(R.id.iv_thumb);
        if (StringUtil.isNullOrEmpty(imgUrl) || !imgUrl.equals(bean.getBigImage())) {
            GlideUtil.show(bean.getBigImage(), holder.mIvThumb, R.mipmap.placeholder);
        }
        holder.mIvThumb.setTag(R.id.iv_thumb, bean.getBigImage());
        holder.mPbDownload.setProgress(bean.getProgress());
        switch (bean.getStatus()) {
            case VideoBean.NO_DOWNLOAD:
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
            case VideoBean.DOWNLOAD_STOP:
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
            case VideoBean.PENDING:
                holder.mPbDownload.setVisibility(View.VISIBLE);
                holder.mIvFlag.setEnabled(false);
                holder.mIvFlag.setImageResource(R.mipmap.icon_download_pend);
                Log.d("aaa", "下载进度PENDING：" + bean.getProgress());
                break;
            case VideoBean.DOWNLOAD_ING:
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
            case VideoBean.DOWNLOAD_OVER:
                holder.mPbDownload.setVisibility(View.GONE);
                holder.mIvFlag.setEnabled(true);
                holder.mIvFlag.setImageResource(R.mipmap.download_local);
                holder.mIvFlag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PlayerActivity.startActivity(mActivity, bean.getPath(), bean.getName());
                    }
                });
                Log.d("aaa", "下载进度DOWNLOAD_OVER：" + bean.getProgress());
                break;
            default:
                break;
        }
    }

    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mActivity, R.layout.item_video, null);
        return new VideoAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mVideoBeen.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView     mIvThumb;
        private ImageView     mIvFlag;
        private View          mViewRoot;
        private DonutProgress mPbDownload;

        ViewHolder(View view) {
            super(view);

            mViewRoot = view.findViewById(R.id.view_root);
            mIvThumb = (ImageView) view.findViewById(R.id.iv_thumb);
            mIvFlag = (ImageView) view.findViewById(R.id.iv_flag);
            mPbDownload = (DonutProgress) view.findViewById(R.id.circle_progress);

            int size = (DensityUtils.getScreenW() - DensityUtils.getMeasureValue(10) * 4) / 3;
            UIUtil.setViewWidth(mIvThumb, size);
            UIUtil.setViewWidth(mPbDownload, size * 2 / 3);
            UIUtil.setViewHeight(mPbDownload, size * 2 / 3);
            UIUtil.setViewHeight(mIvThumb, size * 480 / 450);
            DensityUtils.measure(mIvFlag, 194, 166);
        }
    }
}
