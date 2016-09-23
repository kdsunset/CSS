package cn.zhudai.zin.css.bean;

import com.google.gson.annotations.SerializedName;

import cn.zhudai.zin.css.base.BaseResp;

/**
 * Created by admin on 2016/8/24.
 * 用于注册，重设密码，（登陆）的Response，
 */
public class SimpleResp extends BaseResp{
    @SerializedName("result")
    private User result;

    public User getUser() {
        return result;
    }

    public void setUser(User result) {
        this.result = result;
    }
}
