package com.qxsx.chaersi.driverclient.entry.result;

/**
 * Created by Chaersi on 16/10/24.
 */
public class OrderCodeResult {
    /**
     * code : -401
     * data : {"command":"changeQRCode"}
     * msg : 二维码验证失败
     */

    private String code;
    /**
     * command : changeQRCode
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
