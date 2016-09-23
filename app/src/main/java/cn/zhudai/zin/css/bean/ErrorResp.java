package cn.zhudai.zin.css.bean;

import cn.zhudai.zin.css.base.BaseResp;

/**
 * Created by admin on 2016/9/13.
 */
public class ErrorResp extends BaseResp {
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ErrorResp{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
