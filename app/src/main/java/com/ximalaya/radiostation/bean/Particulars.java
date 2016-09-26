package com.lanou.radiostation.bean;

import java.util.List;

/**
 * Created by user on 2016/8/5.
 */
public class Particulars {

    public Info info;
    public List<Data> list;

    public class Info{
        public String intro;
        public String nickname;
        public String title;
        public String smallLogo;
        public String personalSignature;
        public int contentType;
    }
    public class Data{
        public String intro;
        public String nickname;
        public String title;
        public String smallLogo;
        public String coverSmall;
        public String playPath32;
        public int commentsCounts;
        public int playsCounts;
        public int duration;
    }
}
