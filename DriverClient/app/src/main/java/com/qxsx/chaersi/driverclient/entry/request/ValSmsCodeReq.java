package com.qxsx.chaersi.driverclient.entry.request;

/**
 * Created by Chaersi on 16/10/23.
 */
public class ValSmsCodeReq {

    /**
     * command : sendMessageForResetPWD
     * tel : 18330219027
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
