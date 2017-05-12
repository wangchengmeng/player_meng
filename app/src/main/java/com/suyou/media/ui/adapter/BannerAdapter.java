package com.suyou.media.ui.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.maogu.htclibrary.util.DensityUtils;
import com.suyou.media.R;
import com.suyou.media.bean.BannerBean;
import com.suyou.media.util.glid.GlideUtil;

import java.util.List;

/**
 * 首页轮换Banner
 */
public class BannerAdapter extends PagerAdapter {
    private List<BannerBean> mBannerBeans;
    private Activity         mActivity;

    public BannerAdapter(Activity activity, List<BannerBean> BannerBeans) {
        this.mActivity = activity;
        this.mBannerBeans = BannerBeans;
    }

    @Override
    public int getCount() {
        if (null == mBannerBeans) {
            return 0;
        }
        if (mBannerBeans.size() > 1) {
            return Integer.MAX_VALUE;
        } else {
            return mBannerBeans.size();
        }
    }

    /**
     * @return 返回真实大小
     */
    public int getSize() {
        if (mBannerBeans != null) {
            return mBannerBeans.size();
        } else {
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(mActivity);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        final BannerBean model = mBannerBeans.get(position % getSize());
        GlideUtil.show(model.getCover(), imageView, R.mipmap.banner_default);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        DensityUtils.measure(imageView, 1080, 540);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
