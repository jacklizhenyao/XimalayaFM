package com.lanou.radiostation.bean;

import java.util.List;

/**
 * Created by user on 2016/7/30.
 */
public class DownLoad {

    public List<Data> list;

    public class Data{

        public String albumCoverUrl290;
        public String intro;
        public  String title;
        public int albumId;
        public String nickname;
        public int playsCounts;
        public String path;

    }

}
