package com.lanou.radiostation.bean;

import java.util.List;

/**
 * Created by user on 2016/7/22.
 */
public class DingYueTingRecommended {
    public Data data;

    public class Data {
     public List<RecommendedData> list;
    }

    public class RecommendedData {

        public int albumId;
        public int basedRelativeAlbumId;
        public String coverLarge;
        public String coverMiddle;
        public String info;
        public long lastUptrackAt;
        public int lastUptrackId;
        public String lastUptrackTitle;
        public String nickname;
        public int playsCounts;
        public String recReason;
        public String recSrc;
        public String recTrack;
        public int serialState;
        public String tags;
        public String title;
        public int trackId;
        public String trackTitle;
        public int tracks;
        public int uid;

    }
}
