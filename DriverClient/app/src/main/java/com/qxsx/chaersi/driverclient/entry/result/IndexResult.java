package com.qxsx.chaersi.driverclient.entry.result;

import java.util.List;

/**
 * Created by Chaersi on 16/10/24.
 */
public class IndexResult {
    /**
     * code : 1
     * data : {"command":"homePage","banner":[{"id":"1","bigImg":"http://192.168.3.23580d7aba2df57.jpg"},{"id":"9","bigImg":"http://192.168.3.23580d7aba2df57.jpg"},{"id":"10","bigImg":"http://192.168.3.23NULL"}],"driverThisWeek":[],"driverLastWeek":[],"latestInfo":[{"id":"10","smallImg":"http://192.168.3.23/pic/thumbnail/driverInfo/10.jpg","title":"ces","content":""},{"id":"9","smallImg":"http://192.168.3.23/pic/thumbnail/driverInfo/9.jpg","title":"广州公交司机月均6700","content":"./pic/thumbnail/9.png"},{"id":"8","smallImg":"http://192.168.3.23/pic/thumbnail/driverInfo/8.jpg","title":"直招带车司机5500","content":"./pic/thumbnail/8.png"}],"newList":[{"title":"紫金山路明起施工 交管部门：途经车辆减速慢行"},{"title":"交通：24日起海河大桥断交施工 驾驶员注意绕行"}],"isRead":"1","realtimemessage":[{"messageContent":"fgvdgdsfg"},{"messageContent":"sgdfsgdsfgdsfgdsfg"}]}
     * msg : 操作正常
     */

    private String code;
    /**
     * command : homePage
     * banner : [{"id":"1","bigImg":"http://192.168.3.23580d7aba2df57.jpg"},{"id":"9","bigImg":"http://192.168.3.23580d7aba2df57.jpg"},{"id":"10","bigImg":"http://192.168.3.23NULL"}]
     * driverThisWeek : []
     * driverLastWeek : []
     * latestInfo : [{"id":"10","smallImg":"http://192.168.3.23/pic/thumbnail/driverInfo/10.jpg","title":"ces","content":""},{"id":"9","smallImg":"http://192.168.3.23/pic/thumbnail/driverInfo/9.jpg","title":"广州公交司机月均6700","content":"./pic/thumbnail/9.png"},{"id":"8","smallImg":"http://192.168.3.23/pic/thumbnail/driverInfo/8.jpg","title":"直招带车司机5500","content":"./pic/thumbnail/8.png"}]
     * newList : [{"title":"紫金山路明起施工 交管部门：途经车辆减速慢行"},{"title":"交通：24日起海河大桥断交施工 驾驶员注意绕行"}]
     * isRead : 1
     * realtimemessage : [{"messageContent":"fgvdgdsfg"},{"messageContent":"sgdfsgdsfgdsfgdsfg"}]
     */

    private DataBean data;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        private String command;
        private String isRead;
        /**
         * id : 1
         * bigImg : http://192.168.3.23580d7aba2df57.jpg
         */

        private List<BannerBean> banner;
        private List<?> driverThisWeek;
        private List<?> driverLastWeek;
        /**
         * id : 10
         * smallImg : http://192.168.3.23/pic/thumbnail/driverInfo/10.jpg
         * title : ces
         * content :
         */

        private List<LatestInfoBean> latestInfo;
        /**
         * title : 紫金山路明起施工 交管部门：途经车辆减速慢行
         */

        private List<NewListBean> newList;
        /**
         * messageContent : fgvdgdsfg
         */

        private List<RealtimemessageBean> realtimemessage;

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }

        public String getIsRead() {
            return isRead;
        }

        public void setIsRead(String isRead) {
            this.isRead = isRead;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<?> getDriverThisWeek() {
            return driverThisWeek;
        }

        public void setDriverThisWeek(List<?> driverThisWeek) {
            this.driverThisWeek = driverThisWeek;
        }

        public List<?> getDriverLastWeek() {
            return driverLastWeek;
        }

        public void setDriverLastWeek(List<?> driverLastWeek) {
            this.driverLastWeek = driverLastWeek;
        }

        public List<LatestInfoBean> getLatestInfo() {
            return latestInfo;
        }

        public void setLatestInfo(List<LatestInfoBean> latestInfo) {
            this.latestInfo = latestInfo;
        }

        public List<NewListBean> getNewList() {
            return newList;
        }

        public void setNewList(List<NewListBean> newList) {
            this.newList = newList;
        }

        public List<RealtimemessageBean> getRealtimemessage() {
            return realtimemessage;
        }

        public void setRealtimemessage(List<RealtimemessageBean> realtimemessage) {
            this.realtimemessage = realtimemessage;
        }

        public static class BannerBean {
            private String id;
            private String bigImg;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getBigImg() {
                return bigImg;
            }

            public void setBigImg(String bigImg) {
                this.bigImg = bigImg;
            }
        }

        public static class LatestInfoBean {
            private String id;
            private String smallImg;
            private String title;
            private String content;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSmallImg() {
                return smallImg;
            }

            public void setSmallImg(String smallImg) {
                this.smallImg = smallImg;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }

        public static class NewListBean {
            private String title;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class RealtimemessageBean {
            private String messageContent;

            public String getMessageContent() {
                return messageContent;
            }

            public void setMessageContent(String messageContent) {
                this.messageContent = messageContent;
            }
        }
    }
}
