package com.qxsx.chaersi.driverclient.entry.result;

import java.util.List;

/**
 * Created by Chaersi on 16/10/25.
 */
public class ExceptResult {

    /**
     * code : 1
     * data : {"command":"exceptionReason","reason":[{"reasonId":"1","type":"1","title":"取货货物异常"},{"reasonId":"2","type":"1","title":"取货地址变更"}]}
     * msg : 操作正常
     */

    private String code;
    /**
     * command : exceptionReason
     * reason : [{"reasonId":"1","type":"1","title":"取货货物异常"},{"reasonId":"2","type":"1","title":"取货地址变更"}]
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
        /**
         * reasonId : 1
         * type : 1
         * title : 取货货物异常
         */

        private List<ReasonBean> reason;

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }

        public List<ReasonBean> getReason() {
            return reason;
        }

        public void setReason(List<ReasonBean> reason) {
            this.reason = reason;
        }

        public static class ReasonBean {
            private String reasonId;
            private String type;
            private String title;

            public String getReasonId() {
                return reasonId;
            }

            public void setReasonId(String reasonId) {
                this.reasonId = reasonId;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
