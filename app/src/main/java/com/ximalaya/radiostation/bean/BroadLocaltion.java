package com.lanou.radiostation.bean;

import java.util.List;

/**
 * Created by user on 2016/8/3.
 */
public class BroadLocaltion {

    public Data data;

    public class Data{

        public List<Datas> data;
    }
    public class Datas{
        public String coverSmall;
        public String name;
        public String programName;
        public int playCount;
        public PlayUrl playUrl;
    }
    public class PlayUrl{
        public String ts24;
    }
}
