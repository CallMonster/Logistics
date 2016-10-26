package com.qxsx.chaersi.driverclient.entry.request;

/**
 * Created by Chaersi on 16/10/25.
 */
public class PersonalInfoReq {
    /**
     * command : driverPersonCenter
     * sid : 5lcnbap5mda6uqhlalnhokkcf7
     */

    private String command;
    private String sid;

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
}
