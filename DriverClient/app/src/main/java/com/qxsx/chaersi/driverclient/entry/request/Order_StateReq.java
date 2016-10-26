package com.qxsx.chaersi.driverclient.entry.request;

/**
 * Created by Chaersi on 16/10/24.
 */
public class Order_StateReq {
    /**
     * command : orderList
     * sid : sbk9gnh6na1eqdr26fu5aa9ne6
     * orderStatus : 3
     * limit : 2
     * page : 1
     */

    private String command;
    private String sid;
    private String orderStatus;
    private String limit;
    private String page;

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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
