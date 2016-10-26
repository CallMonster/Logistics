package com.qxsx.chaersi.driverclient.entry.request;

/**
 * Created by Chaersi on 16/10/24.
 */
public class OrderCodeReq {
    /**
     * command : changeQRCode
     * vehicleId : 3
     * sid : oabpuph1sob7jbepafktn23q53
     * page : 1
     * limit : 3
     */

    private String command;
    private String vehicleId;
    private String sid;
    private String page;
    private String limit;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }
}
