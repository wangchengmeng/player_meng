package com.suyou.media.ui.view;


import com.suyou.media.app.IView;
import com.suyou.media.bean.AudioBean;

import java.util.List;

public interface AudioView extends IView {

    void update(int status, List<AudioBean> list);

    void dismissLoading();
}
