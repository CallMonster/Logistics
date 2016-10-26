package com.qxsx.chaersi.driverclient.entry.request;

/**
 * Created by Chaersi on 16/10/23.
 */
public class LoginReq {

    /**
     * command : login
     * tel : 18330219027
     * password : admin
     */

    private String command;
    private String tel;
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
