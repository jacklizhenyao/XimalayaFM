package com.lanou.radiostation.bean;

import java.util.List;

/**
 * Created by user on 2016/7/28.
 */
public class Attention {
    public List<Data> list;

    public class Data{
   public  String nickname;
        public String coverLarge;
        public String coverMiddle;
        public String coverSmall;
       public int albumId;
        public int playtimes;
        public String  title;
        public int trackId;
        public int uid;
        public int  userSource;
        public String backgroundLogo;

    }
}
