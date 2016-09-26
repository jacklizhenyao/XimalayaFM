package com.lanou.radiostation.bean;

import java.util.List;

/**
 * Created by user on 2016/7/29.
 */
public class Allclassify_01 {

    public CategoryInfo categoryInfo;
    public FocusImages focusImages;
    public Keywords keywords;



    public class CategoryInfo{
        public String title;
        public List<News> list;
    }


    public class FocusImages{
        public List<Page> list;
    }

    public class Page{
        public String pic;
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
