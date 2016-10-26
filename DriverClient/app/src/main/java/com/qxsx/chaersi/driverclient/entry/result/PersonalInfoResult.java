package com.qxsx.chaersi.driverclient.entry.result;

/**
 * Created by Chaersi on 16/10/25.
 */
public class PersonalInfoResult {
    /**
     * code : 106
     * data : {"command":"driverPersonCenter","face":"","reviewStatus":"1","hasNewMsg":"0","dName":"未设置","driverId":"DR20161024100002","orderCount":"0","favorableRate":"100%"}
     * msg : 个人中心数据返回成功
     */

    private String code;
    /**
     * command : driverPersonCenter
     * face :
     * reviewStatus : 1
     * hasNewMsg : 0
     * dName : 未设置
     * driverId : DR20161024100002
     * orderCount : 0
     * favorableRate : 100%
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
        private String face;
        private String reviewStatus;
        private String hasNewMsg;
        private String dName;
        private String driverId;
        private String orderCount;
        private String favorableRate;

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getReviewStatus() {
            return reviewStatus;
        }

        public void setReviewStatus(String reviewStatus) {
            this.reviewStatus = reviewStatus;
        }

        public String getHasNewMsg() {
            return hasNewMsg;
        }

        public void setHasNewMsg(String hasNewMsg) {
            this.hasNewMsg = hasNewMsg;
        }

        public String getDName() {
            return dName;
        }

        public void setDName(String dName) {
            this.dName = dName;
        }

        public String getDriverId() {
            return driverId;
        }

        public void setDriverId(String driverId) {
            this.driverId = driverId;
        }

        public String getOrderCount() {
            return orderCount;
        }

        public void setOrderCount(String orderCount) {
            this.orderCount = orderCount;
        }

        public String getFavorableRate() {
            return favorableRate;
        }

        public void setFavorableRate(String favorableRate) {
            this.favorableRate = favorableRate;
        }
    }
}
