package com.qxsx.chaersi.driverclient.entry.request;

/**
 * Created by Chaersi on 16/10/25.
 */
public class Problem_GoodsReq {
    /**
     * command : pickupProblem
     * sid : daefhgherr45456
     * type : 3
     * describe : 12sadad12
     * orderId : 12sadad12
     */

    private String command;
    private String sid;
    private String type;
    private String describe;
    private String orderId;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
