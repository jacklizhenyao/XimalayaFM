package com.lanou.radiostation.bean;

import java.util.List;

/**
 * Created by user on 2016/7/26.
 */
public class Radio {

   public List<Result> result;


    public class Result{
        public String radioCoverSmall;
        public String rname;
        public String programName;
        public int radioPlayCount;

    }
}
