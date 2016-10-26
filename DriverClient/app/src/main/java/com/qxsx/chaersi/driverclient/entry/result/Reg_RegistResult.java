package com.qxsx.chaersi.driverclient.entry.result;

/**
 * Created by Chaersi on 16/10/23.
 */
public class Reg_RegistResult {
    /**
     * code : 102
     * data : {"command":"register","sid":"1itforleil65v6hs7rhm2n7l36"}
     * msg : 注册成功
     */

    private String code;
    /**
     * command : register
     * sid : 1itforleil65v6hs7rhm2n7l36
     */

    private DataBean data;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
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
}
