package com.qxsx.chaersi.driverclient.entry.request;

/**
 * Created by Chaersi on 16/10/23.
 */
public class ResetPassReq {


    /**
     * command : Resetpassword
     * tel : 18330219027
     * password : 183302
     * confirmpassword : 183302
     */

    private String command;
    private String tel;
    private String password;
    private String confirmpassword;

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

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }
}
