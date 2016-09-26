package com.lanou.radiostation.bean;

import java.util.List;

/**
 * Created by user on 2016/7/29.
 */
public class FragClassify {

    public List<Data> list;

    public class Data{
        public String albumCoverUrl290;
        public String intro;
        public String title;
        public int  albumId;
        public int  id;
        public int  playsCounts;
        public String  nickname;
        public int  commentsCount;
        public int  tracks;
    }
}
