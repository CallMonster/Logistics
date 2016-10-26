package com.qxsx.chaersi.driverclient.entry.request;

/**
 * Created by Chaersi on 16/10/23.
 */
public class CertifyStep1 {

    /**
     * command : driverMessage
     * dName : 张尼玛
     * idCardNum : 120106198901091023
     * cooperationId : 123456
     * sid : 0qmlinai4jmt7rhsag5elhn585
     */

    private String command;
    private String dName;
    private String idCardNum;
    private String cooperationId;
    private String sid;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getDName() {
        return dName;
    }

    public void setDName(String dName) {
        this.dName = dName;
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    public String getCooperationId() {
        return cooperationId;
    }

    public void setCooperationId(String cooperationId) {
        this.cooperationId = cooperationId;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
