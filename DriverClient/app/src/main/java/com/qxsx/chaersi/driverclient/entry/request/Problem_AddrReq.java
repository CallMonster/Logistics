package com.qxsx.chaersi.driverclient.entry.request;

/**
 * Created by Chaersi on 16/10/25.
 */
public class Problem_AddrReq {
    /**
     * command : reSendOrder
     * sid : uajndv9kead8lf0u45e2pbnrg7
     * orderID : 1
     * newPrice : 120
     * newDeliverAddr : aaaaaa
     * newReceiptAddr : bbbbbb
     * exceptionReasonId : 1
     * describe : 1111
     */

    private String command;
    private String sid;
    private String orderID;
    private String newPrice;
    private String newDeliverAddr;
    private String newReceiptAddr;
    private String exceptionReasonId;
    private String describe;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }

    public String getNewDeliverAddr() {
        return newDeliverAddr;
    }

    public void setNewDeliverAddr(String newDeliverAddr) {
        this.newDeliverAddr = newDeliverAddr;
    }

    public String getNewReceiptAddr() {
        return newReceiptAddr;
    }

    public void setNewReceiptAddr(String newReceiptAddr) {
        this.newReceiptAddr = newReceiptAddr;
    }

    public String getExceptionReasonId() {
        return exceptionReasonId;
    }

    public void setExceptionReasonId(String exceptionReasonId) {
        this.exceptionReasonId = exceptionReasonId;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
