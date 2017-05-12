package com.suyou.media.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.suyou.media.bean.AudioBean;
import com.suyou.media.config.ConstantSet;
import com.suyou.media.util.UncheckedUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MusicService extends Service implements MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, OnAudioFocusChangeListener {

    private ArrayList<AudioBean> mAudioBeen;
    private MediaPlayer          mPlayer;
    private MusicBroadReceiver   receiver;
    private int                  mCurrentPosition;
    private boolean isFirst = true;
    private int mPosition;
    private int mPlayMode = 2;//1.单曲循环 2.列表循环 0.随机播放

    @Override
    public void onCreate() {
        initVariables();
        registerReceiver();
        super.onCreate();
    }

    private void initVariables() {
        mAudioBeen = new ArrayList<>();
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnErrorListener(this);//资源出错
            mPlayer.setOnPreparedListener(this);//资源准备好的时候
            mPlayer.setOnCompletionListener(this);//播放完成的时候
        }
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConstantSet.MusicAction.ACTION_LIST_ITEM);
        intentFilter.addAction(ConstantSet.MusicAction.ACTION_PAUSE);
        intentFilter.addAction(ConstantSet.MusicAction.ACTION_PLAY);
        intentFilter.addAction(ConstantSet.MusicAction.ACTION_NEXT);
        intentFilter.addAction(ConstantSet.MusicAction.ACTION_PRV);
        intentFilter.addAction(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        intentFilter.setPriority(1000);
        if (receiver == null) {
            receiver = new MusicBroadReceiver();
        }
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            mAudioBeen = UncheckedUtil.cast(intent.getSerializableExtra("music_list"));
            mPosition = 0;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        Intent intent = new Intent(ConstantSet.MusicAction.ACTION_NEXT);
        sendBroadcast(intent);
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mPlayer.start();//开始播放
        //TODO把准备播放的歌曲信息通知到界面，并且开启定时器
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        //播放下一曲
        Intent intent = new Intent(ConstantSet.MusicAction.ACTION_NEXT);
        sendBroadcast(intent);
    }

    private void play(int position) {
        if (mPlayer == null || mAudioBeen.isEmpty()) {
            return;
        }
        mPlayer.reset();
        try {
            mPlayer.setDataSource(mAudioBeen.get(position).getSrc());
            mPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pause() {
        if (mPlayer == null || !mPlayer.isPlaying()) {
            return;
        }
        mCurrentPosition = mPlayer.getCurrentPosition();
        mPlayer.pause();
    }

    private class MusicBroadReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case ConstantSet.MusicAction.ACTION_LIST_ITEM:
                    isFirst = false;
                    mPosition = intent.getIntExtra("position", 0);
                    play(mPosition);
                    break;
                case ConstantSet.MusicAction.ACTION_PAUSE:
                    pause();
                    break;
                case ConstantSet.MusicAction.ACTION_PLAY:
                    if (isFirst) {
                        isFirst = false;
                        play(mPosition);
                    } else {
                        mPlayer.seekTo(mCurrentPosition);
                        mPlayer.start();
                    }
                    break;
                case ConstantSet.MusicAction.ACTION_NEXT:
                    if (mPlayMode % 3 == 2) {//2.列表播放
                        mPosition++;
                    } else if (mPlayMode % 3 == 0) {// 0.随机播放
                        mPosition = getRandom();
                    }
                    play(mPosition % mAudioBeen.size());
                    break;
                case ConstantSet.MusicAction.ACTION_PRV:
                    if (mPlayMode % 3 == 2) {//2.列表播放
                        mPosition--;
                    } else if (mPlayMode % 3 == 0) {// 0.随机播放
                        mPosition = getRandom();
                    }
                    play(mPosition % mAudioBeen.size());
                    break;
                case AudioManager.ACTION_AUDIO_BECOMING_NOISY:
                    Intent intent_pause = new Intent(ConstantSet.MusicAction.ACTION_PAUSE);
                    sendBroadcast(intent_pause);
                    break;
                default:
                    break;
            }
        }
    }

    private int getRandom() {
        Random mRandom = new Random();
        mPosition = mRandom.nextInt(mAudioBeen.size());
        return mPosition;
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                //你已经得到了音频焦点。
                mPlayer.start();
                mPlayer.setVolume(1.0f, 1.0f);
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                //你已经失去了音频焦点很长时间了。你必须停止所有的音频播放
                if (mPlayer.isPlaying())
                    mPlayer.stop();
                mPlayer.release();
                mPlayer = null;
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                //你暂时失去了音频焦点
                if (mPlayer.isPlaying())
                    mPlayer.pause();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                //你暂时失去了音频焦点，但你可以小声地继续播放音频（低音量）而不是完全扼杀音频。
                if (mPlayer.isPlaying())
                    mPlayer.setVolume(0.1f, 0.1f);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
        if (receiver != null) {
            unregisterReceiver(receiver); // 服务终止时解绑
        }
    }
}
