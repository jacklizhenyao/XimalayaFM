package com.lanou.radiostation.bean;

import java.util.List;

/**
 * Created by user on 2016/7/30.
 */
public class Bean_frag_dingyueting_recom {
    public Data data;

    public class Data {
        public Album album;

        public Tracks tracks;
    }

    public class Album {
        public String nickname;
        public int albumId;
        public String title;
        public String coverLarge;
        public long updatedAt;
        public String updatedAtStr;
        public int playTimes;
    }

    public class Tracks {
        public int pageId;
        public int pageSize;
        public int maxPageId;
        public int totalCount;
        public byte[] imageBytes;
        public List<AlbumDetail> list;

    }
    public class AlbumDetail{
        public  String title;
    }
}
