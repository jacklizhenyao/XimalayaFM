package com.lanou.radiostation.util;

import android.graphics.Bitmap;

import com.lanou.radiostation.activity.AlbumActivity;
import com.lanou.radiostation.bean.AlbumJup;
import com.lanou.radiostation.bean.PlayData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/7/27.
 */
public class MusicConstant {


    //判断当前是否是播放状态,给playbackactivity界面的chekbok赋值
    public static boolean ISPlay ;
    public static boolean count;
    public static int pre_albumID=0 ;
    public static int pre_history_albumID=0 ;
    public static int history_albumID=0;
    public static int radio_id=0;
    public static int getRadio_id_pre=0;
    public static PlayData playData;
    public static boolean IS_PLAYACRIVITY_RUNNING=false;
    /**
     * 当前播放音乐的总长度
     */
    public static int DURATION = 0;
    /**
     * 当前播放音乐的进度值
     */
    public static int PROGRESS = 0;
    /**
     * 保存当前音乐的播放状态
     */
    public static boolean IS_PLAYING = false;
    /**
     * 确定当前播放音乐列表中的第几个音频
     */
    public static int PLAYING_POSITION = -1;

    /**
     * 继续播放
     */
    public static final int PLAY_GO_ON = 1;
    /**
     * 暂停播放
     */
    public static final int PAUSE = 2;
    /**
     * 下一曲
     */
    public static final int NEXT_MUSIC = 3;
    /**
     * 上一曲
     */
    public static final int PRE_MUSIC = 4;
    /**
     * 改变音乐播放的位置（快进或者快退）
     */
    public static final int SWITCH_PROGRESS = 5;

    /**
     * 音乐播放模式 1:循环
     */
    public static final int LOOPING = 0;
    /**
     * 音乐播放模式 2：随机
     */
    public static final int RANDOM = 1;
    /**
     * 音乐播放模式 3：单曲
     */
    public static final int SINGLE = 2;
    /**
     * 当前的播放模式
     */
    public static int CURRENT_MODE = LOOPING;
}
