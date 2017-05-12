package com.suyou.media.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.suyou.media.R;
import com.suyou.media.app.BaseActivity;
import com.suyou.media.app.IPresenter;
import com.suyou.media.bean.AudioBean;
import com.suyou.media.ui.view.EmptyView;
import com.suyou.media.util.ViewUtil;

import co.mobiwise.library.MusicPlayerView;

public class PlayerMusicActivity extends BaseActivity implements EmptyView {

    private AudioBean       mAudioBean;
    private MusicPlayerView mMusicPlayerView;

    public static void startActivity(Activity activity, AudioBean audioBean) {
        Intent intent = new Intent(activity, PlayerMusicActivity.class);
        intent.putExtra(AudioBean.class.getName(), audioBean);
        activity.startActivity(intent);
        ViewUtil.right2LeftIn(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        initVariables();
        initViews();
    }

    private void initVariables() {
        mAudioBean = (AudioBean) getIntent().getSerializableExtra(AudioBean.class.getName());
    }

    private void initViews() {
        if (null == mAudioBean) {
            finish();
            return;
        }
        TextView mTvSong = (TextView) findViewById(R.id.tv_song);
        TextView mTvSinger = (TextView) findViewById(R.id.tv_singer);
        mTvSong.setText(mAudioBean.getTitle());
        mTvSinger.setText(mAudioBean.getSinger());
        mMusicPlayerView = (MusicPlayerView) findViewById(R.id.mpv);
        mMusicPlayerView.setCoverURL(mAudioBean.getCover());
        mMusicPlayerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMusicPlayerView.isRotating()) {
                    mMusicPlayerView.stop();
                } else {
                    mMusicPlayerView.start();
                }
            }
        });
    }

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    public void finish() {
        super.finish();
        ViewUtil.left2RightOut(this);
    }
}
