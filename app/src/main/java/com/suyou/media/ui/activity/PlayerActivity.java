package com.suyou.media.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.maogu.htclibrary.widget.LoadingUpView;
import com.suyou.media.R;
import com.suyou.media.app.BaseActivity;
import com.suyou.media.app.IPresenter;
import com.suyou.media.ui.view.EmptyView;
import com.suyou.media.ui.widget.media.AndroidMediaController;
import com.suyou.media.ui.widget.media.IjkVideoView;
import com.suyou.media.ui.widget.media.RecentMediaStorage;
import com.suyou.media.util.ToastUtil;
import com.suyou.media.util.ViewUtil;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class PlayerActivity extends BaseActivity implements EmptyView {

    private Uri           mVideoUri;
    private View          mViewRoot;
    private IjkVideoView  mVideoView;
    private String        mVideoPath;
    private LoadingUpView mLoadingUpView;

    public static void startActivity(Activity activity, String videoPath, String videoTitle) {
        Intent intent = new Intent(activity, PlayerActivity.class);
        intent.putExtra("videoPath", videoPath);
        intent.putExtra("videoTitle", videoTitle);
        activity.startActivity(intent);
        ViewUtil.right2LeftIn(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        initVariables();
        initViews();
    }

    private void initVariables() {
        mLoadingUpView = new LoadingUpView(this);
        Intent intent = getIntent();
        mVideoPath = intent.getStringExtra("videoPath");

        String intentAction = intent.getAction();
        if (!TextUtils.isEmpty(intentAction)) {
            if (intentAction.equals(Intent.ACTION_VIEW)) {
                mVideoPath = intent.getDataString();
            } else if (intentAction.equals(Intent.ACTION_SEND)) {
                mVideoUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
            }
        }

        if (!TextUtils.isEmpty(mVideoPath)) {
            new RecentMediaStorage(this).saveUrlAsync(mVideoPath);
        }

        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
    }

    private void initViews() {
        showLoadingUpView(mLoadingUpView);
        mViewRoot = findViewById(R.id.view_root);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        AndroidMediaController mMediaController = new AndroidMediaController(this, false);
        mMediaController.setSupportActionBar(actionBar);

        mVideoView = (IjkVideoView) findViewById(R.id.video_view);
        mVideoView.setMediaController(mMediaController);
        // prefer mVideoPath
        if (mVideoPath != null) {
            mVideoView.setVideoPath(mVideoPath);
        } else if (mVideoUri != null) {
            mVideoView.setVideoURI(mVideoUri);
        } else {
            Log.e(TAG, "Null Data Source\n");
            finish();
            return;
        }
        mVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer mp) {
                mVideoView.start();
            }
        });
        mVideoView.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
                ToastUtil.show(mViewRoot, "播放失败");
                finish();
                return false;
            }
        });
        mVideoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                finish();
            }
        });
        mVideoView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer iMediaPlayer, int what, int extra) {
                Log.d("aaa", "what:" + what);
                switch (what) {
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                        showLoadingUpView(mLoadingUpView);
                        break;
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                    case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                    case IMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START:
                        dismissLoadingUpView(mLoadingUpView);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mVideoView.isBackgroundPlayEnabled()) {
            mVideoView.enterBackground();
        }
    }

    @Override
    protected void onDestroy() {
        mVideoView.stopPlayback();
        mVideoView.release(true);
        mVideoView.stopBackgroundPlay();
        IjkMediaPlayer.native_profileEnd();
        super.onDestroy();
    }
}
