package com.suyou.media.ui.view;


import com.suyou.media.app.IView;
import com.suyou.media.bean.AudioCateBean;

import java.util.List;

public interface AudioFragmentView extends IView {

    void update(int status, List<AudioCateBean> list);

    void dismissLoading();
}
