package com.qxsx.chaersi.driverclient.entry.request;

/**
 * Created by Chaersi on 16/10/25.
 */
public class OrderDetailReq {
    /**
     * command : orderDetail
     * sid : tofv4terv8ksi12u5poo312hn2
     * status : 0
     * orderId : 1
     */

    private String command;
    private String sid;
    private String status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
