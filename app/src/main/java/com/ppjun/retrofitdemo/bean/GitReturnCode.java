package com.ppjun.retrofitdemo.bean;

/**
 * Package :com.ppjun.retrofitdemo.bean
 * Description :
 * Author :Rc3
 * Created at :2016/10/21 15:18.
 */

public class GitReturnCode {

    /**
     * msg :
     * code : 105
     * data : null
     */

    private String msg;
    private String code;
    private Object data;
    private int status;
    private String desc;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
