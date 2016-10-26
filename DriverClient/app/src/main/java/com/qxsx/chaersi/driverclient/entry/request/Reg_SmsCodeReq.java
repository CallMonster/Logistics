package com.qxsx.chaersi.driverclient.entry.request;

/**
 * Created by Chaersi on 16/10/23.
 */
public class Reg_SmsCodeReq {
    /**
     * command :  sendMessage
     * tel : 18222933753
     */

    private String command;
    private String tel;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
