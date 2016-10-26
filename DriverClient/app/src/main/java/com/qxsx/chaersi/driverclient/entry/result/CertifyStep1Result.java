package com.qxsx.chaersi.driverclient.entry.result;

/**
 * Created by Chaersi on 16/10/23.
 */
public class CertifyStep1Result {
    /**
     * code : 1
     * data : {"command":"driverMessage"}
     * msg : 操作正常
     */

    private String code;
    /**
     * command : driverMessage
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

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }
    }
}
