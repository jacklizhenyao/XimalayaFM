package com.lanou.radiostation.bean;

import java.util.List;

/**
 * Created by user on 2016/7/29.
 */
public class Search {



    public List<QueryResultList> queryResultList;
    public List<AlbumResultList> albumResultList;

    public class QueryResultList{

        public String keyword;
    }
    public class AlbumResultList{

        public String keyword;
        public String category;
        public String highlightKeyword;
        public String imgPath;
        public int id;
    }
}
