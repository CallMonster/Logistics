package com.qxsx.chaersi.driverclient.entry.result;

import java.util.List;

/**
 * Created by Chaersi on 16/10/24.
 */
public class Order_StateResult {
    /**
     * code : 1
     * data : {"command":"orderList","orderList":[{"id":"4","orderNum":"2","deliverAddr":"河北区","receiptAddr":"和平区","callTime":""},{"id":"4","orderNum":"2","deliverAddr":"河北区","receiptAddr":"和平区","callTime":""}]}
     * msg : 操作正常
     */

    private String code;
    /**
     * command : orderList
     * orderList : [{"id":"4","orderNum":"2","deliverAddr":"河北区","receiptAddr":"和平区","callTime":""},{"id":"4","orderNum":"2","deliverAddr":"河北区","receiptAddr":"和平区","callTime":""}]
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
         * id : 4
         * orderNum : 2
         * deliverAddr : 河北区
         * receiptAddr : 和平区
         * callTime :
         */

        private List<OrderListBean> orderList;

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }

        public List<OrderListBean> getOrderList() {
            return orderList;
        }

        public void setOrderList(List<OrderListBean> orderList) {
            this.orderList = orderList;
        }

        public static class OrderListBean {
            private String id;
            private String orderNum;
            private String deliverAddr;
            private String receiptAddr;
            private String callTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(String orderNum) {
                this.orderNum = orderNum;
            }

            public String getDeliverAddr() {
                return deliverAddr;
            }

            public void setDeliverAddr(String deliverAddr) {
                this.deliverAddr = deliverAddr;
            }

            public String getReceiptAddr() {
                return receiptAddr;
            }

            public void setReceiptAddr(String receiptAddr) {
                this.receiptAddr = receiptAddr;
            }

            public String getCallTime() {
                return callTime;
            }

            public void setCallTime(String callTime) {
                this.callTime = callTime;
            }
        }
    }
}
