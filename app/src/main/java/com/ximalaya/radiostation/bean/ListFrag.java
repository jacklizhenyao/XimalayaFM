package com.lanou.radiostation.bean;

import java.util.List;

/**
 * Created by user on 2016/8/1.
 */
public class ListFrag {

    public FocusImages focusImages;

    public List<Datas> datas;

    public class Datas{
        public List<News> list;
        public String title;
    }
    public class News{
        public List<FirstKResults> firstKResults;
        public String title;
        public String coverPath;

    }
    public class FirstKResults{
        public String contentType;
        public int id;
        public String title;
    }


    public class FocusImages{
        public List<JDT> list;
    }
    public class JDT{
        public String pic;
        public String url;
    }


}
