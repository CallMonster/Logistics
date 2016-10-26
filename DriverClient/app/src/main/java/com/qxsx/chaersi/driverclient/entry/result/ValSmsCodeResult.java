package com.qxsx.chaersi.driverclient.entry.result;

/**
 * Created by Chaersi on 16/10/23.
 */
public class ValSmsCodeResult {

    /**
     * code : -105
     * data : {"command":"sendMessageForResetPWD"}
     * msg : 手机号尚未注册
     */

    private String code;
    /**
     * command : sendMessageForResetPWD
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
