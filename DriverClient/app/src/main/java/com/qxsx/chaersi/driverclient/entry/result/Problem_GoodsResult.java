package com.qxsx.chaersi.driverclient.entry.result;

/**
 * Created by Chaersi on 16/10/25.
 */
public class Problem_GoodsResult {
    /**
     * code : 112
     * data : {"command":"pickupProblem"}
     * msg : 提交异常信息成功
     */

    private String code;
    /**
     * command : pickupProblem
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
