package com.suyou.media.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.maogu.htclibrary.util.DensityUtils;
import com.maogu.htclibrary.util.StringUtil;
import com.maogu.htclibrary.util.UIUtil;
import com.suyou.media.R;
import com.suyou.media.bean.VideoCateBean;
import com.suyou.media.ui.activity.VideoListActivity;
import com.suyou.media.util.glid.GlideUtil;

import java.util.List;

public class VideoCateAdapter extends BaseQuickAdapter<VideoCateBean, BaseViewHolder> {

    private int mSize = (DensityUtils.getScreenW() - DensityUtils.getMeasureValue(10) * 4) / 3;
    private Activity mActivity;

    public VideoCateAdapter(Activity activity, List<VideoCateBean> videoBeen) {
        super(R.layout.item_audio_cate, videoBeen);
        this.mActivity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, final VideoCateBean bean) {

        ImageView ivCover = helper.getView(R.id.iv_cover);
        String imgUrl = (String) ivCover.getTag(R.id.iv_thumb);
        if (StringUtil.isNullOrEmpty(imgUrl) || !imgUrl.equals(bean.getCover())) {
            GlideUtil.show(bean.getCover(), ivCover, R.mipmap.placeholder);
            ivCover.setTag(R.id.iv_cover, bean.getCover());
        }

        helper.setText(R.id.tv_name, bean.getName());
        helper.setText(R.id.tv_desc, bean.getDesc());
        helper.getView(R.id.view_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoListActivity.startActivity(mActivity, bean.getCateId());
            }
        });

        UIUtil.setViewWidth(ivCover, mSize);
        UIUtil.setViewHeight(ivCover, mSize);
    }
}