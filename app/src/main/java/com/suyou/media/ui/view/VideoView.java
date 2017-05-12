package com.suyou.media.ui.view;


import com.suyou.media.app.IView;
import com.suyou.media.bean.VideoBean;

import java.util.List;

public interface VideoView extends IView {

    void update(int status, List<VideoBean> list);

    void dismissLoading();
}
