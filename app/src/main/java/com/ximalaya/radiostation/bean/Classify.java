package com.lanou.radiostation.bean;

import java.util.List;

/**
 * Created by user on 2016/7/24.
 */
public class Classify {

    public List<News> list;

    public class News{
        public String coverPath;
        public String title;
        public int id;
    }

}
