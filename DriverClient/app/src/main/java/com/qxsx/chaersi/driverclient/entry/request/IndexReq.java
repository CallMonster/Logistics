package com.qxsx.chaersi.driverclient.entry.request;

/**
 * Created by Chaersi on 16/10/24.
 */
public class IndexReq {
    /**
     * command : homePage
     * page : 1
     * limit : 10
     * sid : daefhgherr45456
     */

    private String command;
    private String page;
    private String limit;
    private String sid;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
