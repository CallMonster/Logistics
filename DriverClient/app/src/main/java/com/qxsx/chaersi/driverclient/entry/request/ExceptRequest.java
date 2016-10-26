package com.qxsx.chaersi.driverclient.entry.request;

/**
 * Created by Chaersi on 16/10/25.
 */
public class ExceptRequest {
    /**
     * command : exceptionReason
     * sid : uajndv9kead8lf0u45e2pbnrg7
     * type : 1
     */

    private String command;
    private String sid;
    private String type;

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
}
