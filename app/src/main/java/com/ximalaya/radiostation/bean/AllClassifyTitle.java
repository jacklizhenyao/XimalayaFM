package com.lanou.radiostation.bean;

import java.util.List;

/**
 * Created by user on 2016/7/27.
 */
public class AllClassifyTitle {

    public CategoryInfo categoryInfo;
    public CategoryInfo focusImages;
    public Keywords keywords;


    public class CategoryInfo{
        public String title;
        public List<News> list;
    }

    public class Keywords{
        public List<Data> list;
    }
    public class Data{
        public String keywordName;
        public int  keywordId;
    }
    public class News{
        public String pic;
    }
}
