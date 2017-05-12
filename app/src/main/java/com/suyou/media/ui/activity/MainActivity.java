package com.suyou.media.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.suyou.media.R;
import com.suyou.media.app.BaseActivity;
import com.suyou.media.presenter.MainPre;
import com.suyou.media.ui.fragment.AudioFragment;
import com.suyou.media.ui.fragment.VideoFragment;
import com.suyou.media.ui.view.MainView;
import com.suyou.media.util.ToastUtil;
import com.suyou.media.util.ViewUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MainPre> implements OnClickListener, MainView {

    private int             mCurrentTabIndex;
    private long            mExitTime;
    private FragmentManager mFragmentManager;

    @Bind(R.id.view_root)
    View         mViewRoot;
    @Bind(R.id.view_foot)
    LinearLayout mViewFoot;
    @Bind(R.id.iv_video)
    ImageView    mIvVideo;
    @Bind(R.id.iv_audio)
    ImageView    mIvAudio;
    @Bind(R.id.iv_mine)
    ImageView    mIvMine;
    @Bind(R.id.tv_video)
    TextView     mTvVideo;
    @Bind(R.id.tv_audio)
    TextView     mTvAudio;
    @Bind(R.id.tv_mine)
    TextView     mTvMine;

    private VideoFragment mVideoFragment;
    private AudioFragment mAudioFragment;

    public static void intentTo(Activity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
        ViewUtil.right2LeftIn(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVariables();
        initViews();
    }

    private void initVariables() {
        ButterKnife.bind(this);
        mFragmentManager = getSupportFragmentManager();
    }

    private void initViews() {
        measure(mViewFoot, 0, 160);
        measure(mIvVideo, 70, 70);
        measure(mIvAudio, 70, 70);
        measure(mIvMine, 70, 70);
        setTabSelection(TabHomeIndex.HOME_TAB_VIDEO);
    }

    @Override
    protected MainPre getPresenter() {
        return new MainPre(this, this);
    }

    public void setTabSelection(int tabHomeIndex) {
        if (mCurrentTabIndex == tabHomeIndex) {
            return;
        }
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideFragments(transaction);// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        switch (tabHomeIndex) {
            case TabHomeIndex.HOME_TAB_VIDEO:
                if (mVideoFragment == null) {
                    mVideoFragment = VideoFragment.newInstance();
                    transaction.add(R.id.fl_content, mVideoFragment);
                } else {
                    transaction.show(mVideoFragment);
                }
                setVideoTabChecked(true);
                break;
            case TabHomeIndex.HOME_TAB_AUDIO:
                if (mAudioFragment == null) {
                    mAudioFragment = AudioFragment.newInstance();
                    transaction.add(R.id.fl_content, mAudioFragment);
                } else {
                    transaction.show(mAudioFragment);
                }
                setAudioTabChecked(true);
                break;
            case TabHomeIndex.HOME_TAB_MINE:
                //                if (mMineFragment == null) {
                //                    mMineFragment = MineFragment.newInstance();
                //                    transaction.add(R.id.fl_content, mMineFragment);
                //                } else {
                //                    transaction.show(mMineFragment);
                //                }
                setMineTabChecked(true);
                break;
            default:
                break;
        }
        mCurrentTabIndex = tabHomeIndex;
        transaction.commitAllowingStateLoss();
    }

    private void hideFragments(FragmentTransaction transaction) {
        List<Fragment> list = mFragmentManager.getFragments();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                transaction.hide(list.get(i));
            }
        }
        setVideoTabChecked(false);
        setAudioTabChecked(false);
        setMineTabChecked(false);
    }

    private void setVideoTabChecked(boolean isChecked) {
        if (isChecked) {
            mIvVideo.setImageResource(R.mipmap.tab_1_checked);
            mTvVideo.setTextColor(Color.YELLOW);
        } else {
            mIvVideo.setImageResource(R.mipmap.tab_1_unchecked);
            mTvVideo.setTextColor(Color.WHITE);
        }
    }

    private void setAudioTabChecked(boolean isChecked) {
        if (isChecked) {
            mIvAudio.setImageResource(R.mipmap.tab_2_checked);
            mTvAudio.setTextColor(Color.YELLOW);
        } else {
            mIvAudio.setImageResource(R.mipmap.tab_2_unchecked);
            mTvAudio.setTextColor(Color.WHITE);
        }
    }

    private void setMineTabChecked(boolean isChecked) {
        if (isChecked) {
            mIvMine.setImageResource(R.mipmap.tab_3_checked);
            mTvMine.setTextColor(Color.YELLOW);
        } else {
            mIvMine.setImageResource(R.mipmap.tab_3_unchecked);
            mTvMine.setTextColor(Color.WHITE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_video:
                setTabSelection(TabHomeIndex.HOME_TAB_VIDEO);
                break;
            case R.id.view_audio:
                setTabSelection(TabHomeIndex.HOME_TAB_AUDIO);
                break;
            case R.id.view_mine:
                setTabSelection(TabHomeIndex.HOME_TAB_MINE);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtil.show(mViewRoot, R.string.exit_app_toast);
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    private static class TabHomeIndex {
        static final int HOME_TAB_VIDEO = 1;
        static final int HOME_TAB_AUDIO = 2;
        static final int HOME_TAB_MINE  = 3;
    }
}
