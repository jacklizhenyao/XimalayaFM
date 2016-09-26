package com.lanou.radiostation.bean;

import java.util.List;

/**
 * Created by user on 2016/7/25.
 */
public class AlbumJup {

    public Data data;

    public class Data {
       public Tracks tracks;
       public Album album;
    }
    public class Album{
        public String categoryName;
        public int albumId;
        public String nickname;
       public String coverLarge;
    }

   public class Tracks {


     public List<News> list;
      public int pageSize;

   }
   public class News {
       public String title;
       public String coverSmall;
       public String coverLarge;
       public int comments;
       public int duration;
       public int playtimes;
       public String playUrl32;
       public  boolean onclck = false;

   }
}
