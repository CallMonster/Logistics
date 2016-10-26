package com.qxsx.chaersi.driverclient.entry.result;

/**
 * Created by Chaersi on 16/10/25.
 */
public class OrderDetailResult {
    /**
     * code : 110
     * data : {"command":"orderDetail","orderdetail":{"id":"63","orderNum":"22","orderStatus":"4","driverId":"DR20161024100002","vehicleId":"VE0201610231000","customerId":"23","deliver":"测试人员1","deliverTel":"18555655456","sCompany":"天津工业大学","sProvance":"天津","deliverAddr":"宾水西道延长线399号","receipt":"开发人员1","receiptTel":"18555644789","rCompany":"武汉大学","rProvance":"武汉","receiptAddr":"珞珈山","rounteId":"23","volume":"27.0","weight":"3.0","length":"3","wide":"3","tall":"3","goodsNum":"3","description":"货物良好","payMethod":"1","createTime":"2016-10-24 18:35:22","takersTime":"2016-10-24 18:35:22","callTime":"2016-10-24 18:35:22","leadTime":"2016-10-24 18:35:22","exceptionReasonId":"23","exceptionReason":"数量问题","addRequir":"","callImg":"","leadImg":"","exceptionImg":"","totalMoney":"","unfinshesOrder":"待送达","driverOnline":"2"}}
     * msg : 订单列表返回成功
     */

    private String code;
    /**
     * command : orderDetail
     * orderdetail : {"id":"63","orderNum":"22","orderStatus":"4","driverId":"DR20161024100002","vehicleId":"VE0201610231000","customerId":"23","deliver":"测试人员1","deliverTel":"18555655456","sCompany":"天津工业大学","sProvance":"天津","deliverAddr":"宾水西道延长线399号","receipt":"开发人员1","receiptTel":"18555644789","rCompany":"武汉大学","rProvance":"武汉","receiptAddr":"珞珈山","rounteId":"23","volume":"27.0","weight":"3.0","length":"3","wide":"3","tall":"3","goodsNum":"3","description":"货物良好","payMethod":"1","createTime":"2016-10-24 18:35:22","takersTime":"2016-10-24 18:35:22","callTime":"2016-10-24 18:35:22","leadTime":"2016-10-24 18:35:22","exceptionReasonId":"23","exceptionReason":"数量问题","addRequir":"","callImg":"","leadImg":"","exceptionImg":"","totalMoney":"","unfinshesOrder":"待送达","driverOnline":"2"}
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
         * id : 63
         * orderNum : 22
         * orderStatus : 4
         * driverId : DR20161024100002
         * vehicleId : VE0201610231000
         * customerId : 23
         * deliver : 测试人员1
         * deliverTel : 18555655456
         * sCompany : 天津工业大学
         * sProvance : 天津
         * deliverAddr : 宾水西道延长线399号
         * receipt : 开发人员1
         * receiptTel : 18555644789
         * rCompany : 武汉大学
         * rProvance : 武汉
         * receiptAddr : 珞珈山
         * rounteId : 23
         * volume : 27.0
         * weight : 3.0
         * length : 3
         * wide : 3
         * tall : 3
         * goodsNum : 3
         * description : 货物良好
         * payMethod : 1
         * createTime : 2016-10-24 18:35:22
         * takersTime : 2016-10-24 18:35:22
         * callTime : 2016-10-24 18:35:22
         * leadTime : 2016-10-24 18:35:22
         * exceptionReasonId : 23
         * exceptionReason : 数量问题
         * addRequir :
         * callImg :
         * leadImg :
         * exceptionImg :
         * totalMoney :
         * unfinshesOrder : 待送达
         * driverOnline : 2
         */

        private OrderdetailBean orderdetail;

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }

        public OrderdetailBean getOrderdetail() {
            return orderdetail;
        }

        public void setOrderdetail(OrderdetailBean orderdetail) {
            this.orderdetail = orderdetail;
        }

        public static class OrderdetailBean {
            private String id;
            private String orderNum;
            private String orderStatus;
            private String driverId;
            private String vehicleId;
            private String customerId;
            private String deliver;
            private String deliverTel;
            private String sCompany;
            private String sProvance;
            private String deliverAddr;
            private String receipt;
            private String receiptTel;
            private String rCompany;
            private String rProvance;
            private String receiptAddr;
            private String rounteId;
            private String volume;
            private String weight;
            private String length;
            private String wide;
            private String tall;
            private String goodsNum;
            private String description;
            private String payMethod;
            private String createTime;
            private String takersTime;
            private String callTime;
            private String leadTime;
            private String exceptionReasonId;
            private String exceptionReason;
            private String addRequir;
            private String callImg;
            private String leadImg;
            private String exceptionImg;
            private String totalMoney;
            private String unfinshesOrder;
            private String driverOnline;

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

            public String getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(String orderStatus) {
                this.orderStatus = orderStatus;
            }

            public String getDriverId() {
                return driverId;
            }

            public void setDriverId(String driverId) {
                this.driverId = driverId;
            }

            public String getVehicleId() {
                return vehicleId;
            }

            public void setVehicleId(String vehicleId) {
                this.vehicleId = vehicleId;
            }

            public String getCustomerId() {
                return customerId;
            }

            public void setCustomerId(String customerId) {
                this.customerId = customerId;
            }

            public String getDeliver() {
                return deliver;
            }

            public void setDeliver(String deliver) {
                this.deliver = deliver;
            }

            public String getDeliverTel() {
                return deliverTel;
            }

            public void setDeliverTel(String deliverTel) {
                this.deliverTel = deliverTel;
            }

            public String getSCompany() {
                return sCompany;
            }

            public void setSCompany(String sCompany) {
                this.sCompany = sCompany;
            }

            public String getSProvance() {
                return sProvance;
            }

            public void setSProvance(String sProvance) {
                this.sProvance = sProvance;
            }

            public String getDeliverAddr() {
                return deliverAddr;
            }

            public void setDeliverAddr(String deliverAddr) {
                this.deliverAddr = deliverAddr;
            }

            public String getReceipt() {
                return receipt;
            }

            public void setReceipt(String receipt) {
                this.receipt = receipt;
            }

            public String getReceiptTel() {
                return receiptTel;
            }

            public void setReceiptTel(String receiptTel) {
                this.receiptTel = receiptTel;
            }

            public String getRCompany() {
                return rCompany;
            }

            public void setRCompany(String rCompany) {
                this.rCompany = rCompany;
            }

            public String getRProvance() {
                return rProvance;
            }

            public void setRProvance(String rProvance) {
                this.rProvance = rProvance;
            }

            public String getReceiptAddr() {
                return receiptAddr;
            }

            public void setReceiptAddr(String receiptAddr) {
                this.receiptAddr = receiptAddr;
            }

            public String getRounteId() {
                return rounteId;
            }

            public void setRounteId(String rounteId) {
                this.rounteId = rounteId;
            }

            public String getVolume() {
                return volume;
            }

            public void setVolume(String volume) {
                this.volume = volume;
            }

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }

            public String getLength() {
                return length;
            }

            public void setLength(String length) {
                this.length = length;
            }

            public String getWide() {
                return wide;
            }

            public void setWide(String wide) {
                this.wide = wide;
            }

            public String getTall() {
                return tall;
            }

            public void setTall(String tall) {
                this.tall = tall;
            }

            public String getGoodsNum() {
                return goodsNum;
            }

            public void setGoodsNum(String goodsNum) {
                this.goodsNum = goodsNum;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getPayMethod() {
                return payMethod;
            }

            public void setPayMethod(String payMethod) {
                this.payMethod = payMethod;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getTakersTime() {
                return takersTime;
            }

            public void setTakersTime(String takersTime) {
                this.takersTime = takersTime;
            }

            public String getCallTime() {
                return callTime;
            }

            public void setCallTime(String callTime) {
                this.callTime = callTime;
            }

            public String getLeadTime() {
                return leadTime;
            }

            public void setLeadTime(String leadTime) {
                this.leadTime = leadTime;
            }

            public String getExceptionReasonId() {
                return exceptionReasonId;
            }

            public void setExceptionReasonId(String exceptionReasonId) {
                this.exceptionReasonId = exceptionReasonId;
            }

            public String getExceptionReason() {
                return exceptionReason;
            }

            public void setExceptionReason(String exceptionReason) {
                this.exceptionReason = exceptionReason;
            }

            public String getAddRequir() {
                return addRequir;
            }

            public void setAddRequir(String addRequir) {
                this.addRequir = addRequir;
            }

            public String getCallImg() {
                return callImg;
            }

            public void setCallImg(String callImg) {
                this.callImg = callImg;
            }

            public String getLeadImg() {
                return leadImg;
            }

            public void setLeadImg(String leadImg) {
                this.leadImg = leadImg;
            }

            public String getExceptionImg() {
                return exceptionImg;
            }

            public void setExceptionImg(String exceptionImg) {
                this.exceptionImg = exceptionImg;
            }

            public String getTotalMoney() {
                return totalMoney;
            }

            public void setTotalMoney(String totalMoney) {
                this.totalMoney = totalMoney;
            }

            public String getUnfinshesOrder() {
                return unfinshesOrder;
            }

            public void setUnfinshesOrder(String unfinshesOrder) {
                this.unfinshesOrder = unfinshesOrder;
            }

            public String getDriverOnline() {
                return driverOnline;
            }

            public void setDriverOnline(String driverOnline) {
                this.driverOnline = driverOnline;
            }
        }
    }
}
