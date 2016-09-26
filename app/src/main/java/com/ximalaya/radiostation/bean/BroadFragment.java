package com.lanou.radiostation.bean;

import java.util.List;

/**
 * Created by user on 2016/8/3.
 */
public class BroadFragment {
    public Data data;

    public class Data{

        public List<LocalRadios> localRadios;
        public List<TopRadios> topRadios;
        public List<Categories> categories;

    }

    public class Categories{
        public int id;
        public String name;
    }

    public class LocalRadios{
        public String coverSmall;
        public String programName;
        public String name;
        public int playCount;
        public int id;
        public PlayUrl playUrl;
    }
    public class TopRadios{
        public String coverSmall;
        public String programName;
        public String name;
        public int playCount;
        public int id;
        public PlayUrl playUrl;
    }
    public class PlayUrl{
        public String ts24;
    }
}
