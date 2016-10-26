package com.zt.hackman.response;

import java.util.List;

/**
 * Created by Administrator on 2016/9/14.
 */
public class HomeResponse {

    public List<LatestInfo> latestInfo;
    public String isRead;
    public List<Banner> banner;
    public List<News> newList;

    /**
     * 推送信息类
     */
    public static class Banner{
        public int id;
        public String bigImg;
    }

    public static class LatestInfo{
        public String id;
        public String smallImg;
        public String title;
        public String content;
        public String createTime;

    }

    public static class News{
        //红还是蓝0,1
        public String title;
    }


}
