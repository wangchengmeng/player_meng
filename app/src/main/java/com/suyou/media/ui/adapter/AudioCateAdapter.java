package com.suyou.media.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maogu.htclibrary.util.DensityUtils;
import com.maogu.htclibrary.util.StringUtil;
import com.maogu.htclibrary.util.UIUtil;
import com.suyou.media.R;
import com.suyou.media.bean.AudioCateBean;
import com.suyou.media.ui.activity.AudioListActivity;
import com.suyou.media.util.glid.GlideUtil;

import java.util.List;

public class AudioCateAdapter extends RecyclerView.Adapter<AudioCateAdapter.ViewHolder> {

    private Activity            mActivity;
    private List<AudioCateBean> mAudioBeen;

    public AudioCateAdapter(Activity activity, List<AudioCateBean> videoBeen) {
        this.mActivity = activity;
        this.mAudioBeen = videoBeen;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final AudioCateBean bean = mAudioBeen.get(holder.getAdapterPosition());
        holder.mViewRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioListActivity.intentTo(mActivity);
            }
        });
        String imgUrl = (String) holder.mIvCover.getTag(R.id.iv_thumb);
        if (StringUtil.isNullOrEmpty(imgUrl) || !imgUrl.equals(bean.getCover())) {
            GlideUtil.show(bean.getCover(), holder.mIvCover, R.mipmap.placeholder);
    }
    holder.mIvCover.setTag(R.id.iv_thumb, bean.getCover());
        holder.mTvName.setText(bean.getName());
        holder.mTvDesc.setText(bean.getDesc());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mActivity, R.layout.item_audio_cate, null);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mAudioBeen.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        int size = (DensityUtils.getScreenW() - DensityUtils.getMeasureValue(10) * 4) / 3;
        private ImageView mIvCover;
        private View      mViewRoot;
        private TextView  mTvName;
        private TextView  mTvDesc;

        ViewHolder(View view) {
            super(view);

            mViewRoot = view.findViewById(R.id.view_root);
            mIvCover = (ImageView) view.findViewById(R.id.iv_cover);
            mTvName = (TextView) view.findViewById(R.id.tv_name);
            mTvDesc = (TextView) view.findViewById(R.id.tv_desc);

            UIUtil.setViewWidth(mIvCover, size);
            UIUtil.setViewHeight(mIvCover, size);
        }
    }
}
