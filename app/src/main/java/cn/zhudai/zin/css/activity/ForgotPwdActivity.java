package cn.zhudai.zin.css.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhudai.zin.css.R;
import cn.zhudai.zin.css.base.BaseActivity;
import cn.zhudai.zin.css.ui.CountDownEffect;
import cn.zhudai.zin.css.utils.SMSContentObserver;
import cn.zhudai.zin.css.utils.UIUtils;

public class ForgotPwdActivity extends BaseActivity {
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_verification_code)
    EditText etVerificationCode;
    @Bind(R.id.bt_get_vfcode)
    Button btGetVfcode;
    @Bind(R.id.et_pwd)
    EditText etPwd;
    @Bind(R.id.et_cfpwd)
    EditText etCfpwd;
    @Bind(R.id.bt_sumit)
    Button btSumit;

    private String mPhone;
    private String mPwd;
    private String mCfpwd;
    private String mVfcode;
    private Context mContext;
    Handler smshandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == 1)
            {
                etVerificationCode.setText(msg.obj.toString());
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwd);
        ButterKnife.bind(this);
        mContext=this;
        initView();
        SMSContentObserver smsContentObserver = new SMSContentObserver(
                mContext, smshandler);

        mContext.getContentResolver().registerContentObserver(
                Uri.parse("content://sms/"), true, smsContentObserver);
    }

    @Override
    public void setToolbar(Toolbar toolbar) {

        showNavigationIcon(true);
    }

    @Override
    public String setTitleInCenter() {
        return "找回密码";
    }

    private void initView() {

    }
    //点击获取验证按钮，网络访问，验证码平台发送验证码短信
    @OnClick(R.id.bt_get_vfcode)
    void getVfCodeAction(){
        mPhone=UIUtils.getEditTextContent(etPhone);
        if (!TextUtils.isEmpty(mPhone)){
            CountDownEffect builder=new
                    CountDownEffect(btGetVfcode)
                    .setDurationSecond(60)
                    .setIntervalSecond(1)
                    .setFinishText("获取验证码")
                    .create();
            builder.start();
/*
           CSSApi.getVfCode(mVfcode,new Callback<VfCodeResp>(new GsonParser<>(VfCodeResp.class)){
                @Override
                public void onResponse(VfCodeResp vfCodeResp) {
                    super.onResponse(vfCodeResp);
                    //获取验证码成功的操作（用户手动输入验证码）
                }

                @Override
                public void onFailure(IOException e) {
                    super.onFailure(e);
                }
            })*/
        }else {
            etPhone.setError("手机号码不能为空!");
            etPhone.requestFocus();
        }



    }
    //重设密码的请求
    @OnClick(R.id.bt_sumit)
    void sumintAction(){

        getInputInfo();//点击确定按钮,获取所有的输入信息
        attemptLogin();

    }

    private void attemptLogin() {
        // Reset errors.
        etPhone.setError(null);
        etVerificationCode.setError(null);
        etPwd.setError(null);
        etCfpwd.setError(null);
        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(mPhone)) {            //如果账号为空
            etPhone.setError("手机号码不能为空！");
            focusView = etPhone;
            cancel = true;
        } else if (!mPhone.matches("^1[3|4|5|8]\\d{9}$")) {        //账号非空但是不合法
            etPhone.setError("不合法的手机号码！");
            focusView = etPhone;
            cancel = true;
        }
        if (TextUtils.isEmpty(mVfcode)) {            //如果账号为空
            etVerificationCode.setError("验证码不能为空！");
            focusView = etVerificationCode;
            cancel = true;
        }
        if (TextUtils.isEmpty(mPwd)) {            //如果账号为空
            etPwd.setError("密码不能为空！");
            focusView = etPwd;
            cancel = true;
        } /*else if (!mCode.equals(mCodeFromNet)) {        //账号非空但是不合法
            etUserPwd.setError("密码包含非法字符！");
            focusView = etUserPwd;
            cancel = true;
        }*/
        if (TextUtils.isEmpty(mCfpwd)) {            //如果账号为空
            etCfpwd.setError("确认密码不能为空！");
            focusView = etCfpwd;
            cancel = true;
        }
        if (!mPwd.equals(mCfpwd)) {            //如果账号为空
            etCfpwd.setError("两次密码输入不一致！");
            focusView = etCfpwd;
            cancel = true;
        }
        if (cancel) {

            focusView.requestFocus();
        } else {

            reset();
        }
    }

    private void reset() {
       /* CSSApi.resetPwd(mPhone,mVfcode,mPwd,mCfpwd,
                new Callback<ResetPwdResp>(new GsonParser<ResetPwdResp>(ResetPwdResp.class)){
                    @Override
                    public void onResponse(ResetPwdResp resetPwdResp) {
                        super.onResponse(resetPwdResp);
                        //重设密码成功后的UI操作
                        boolean hasAuthentication=false;//判断是否已通过实名认证，未则转到认证页面，已认证则进入主界面
                        if (!hasAuthentication){
                            startActivity(new Intent(mContext,UploadInfoActivity.class));
                        }else {
                            startActivity(new Intent(mContext,MainActivity.class));
                        }
                    }

                    @Override
                    public void onFailure(IOException e) {
                        super.onFailure(e);
                    }
                });*/
    }

    private void getInputInfo(){
        mPhone= UIUtils.getEditTextContent(etPhone);
        mVfcode=UIUtils.getEditTextContent(etVerificationCode);
        mPwd=UIUtils.getEditTextContent(etPwd);
        mCfpwd=UIUtils.getEditTextContent(etCfpwd);
    }

}
