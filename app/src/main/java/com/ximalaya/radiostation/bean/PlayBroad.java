package com.lanou.radiostation.bean;

import java.util.List;

/**
 * Created by user on 2016/8/1.
 */
public class PlayBroad {

    public Result result;
    public class Result{
        public List<Today> todaySchedules;

    }

    public class Today{
        public  String listenBackUrl;
        public int programId;
        public String programName;
        public int programScheduleId;
        public String startTime;
        public String endTime;
        public List<Announcer> announcerList;
    }

    public class Announcer{
        public String announcerName;
    }
}
