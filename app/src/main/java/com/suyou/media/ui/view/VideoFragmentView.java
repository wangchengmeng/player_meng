package com.suyou.media.ui.view;


import com.suyou.media.app.IView;
import com.suyou.media.bean.BannerBean;
import com.suyou.media.bean.VideoCateBean;

import java.util.List;

public interface VideoFragmentView extends IView {

    void update(int status, List<VideoCateBean> list);

    void updateBanner(List<BannerBean> list);

    void dismissLoading();
}
