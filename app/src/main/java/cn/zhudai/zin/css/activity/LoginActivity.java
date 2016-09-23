package cn.zhudai.zin.css.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhudai.zin.css.R;
import cn.zhudai.zin.css.api.CSSApi;
import cn.zhudai.zin.css.bean.ErrorResp;
import cn.zhudai.zin.css.bean.SimpleResp;
import cn.zhudai.zin.css.utils.ToastUtils;
import cn.zhudai.zin.css.utils.UIUtils;
import cn.zhudai.zin.css.utils.ViewUtils;
import cn.zhudai.zin.uiokhttp.callback.Callback;
import cn.zhudai.zin.uiokhttp.parser.impl.StringParser;


public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_pwd)
    EditText etPwd;
    @Bind(R.id.bt_login)
    Button btLogin;
    @Bind(R.id.tv_forget_pwd)
    TextView tvForgetPwd;
    @Bind(R.id.tv_register_)
    TextView tvRegister;
    Context mcontext;
    private String mPhone;
    private String mPwd;
    private static final int okMsgTag=0x01;
    private static final int failMsgTag=0x02;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        mcontext=this;
    }

    private void initView() {
        ViewUtils.addUnderLine(tvForgetPwd);
        ViewUtils.addUnderLine(tvRegister);

    }
    @OnClick(R.id.tv_forget_pwd)
    void reSetPwd(){
        startActivity(new Intent(mcontext,ForgotPwdActivity.class));
    }

    @OnClick(R.id.tv_register_)
    void  register(){

        startActivity(new Intent(mcontext,RegisterActivity.class));
    }
    @OnClick(R.id.bt_login)
    void loginAction(){
       // Intent intent=new Intent(this, MainActivity.class);
        //intent.putExtra("TAG",1);
        //startActivity(intent);
        getInputInfo();
        attemptToLogin();

    }

    private void attemptToLogin() {
        etPhone.setError(null);
        etPwd.setError(null);
        boolean cancel = false;
        View focusView = null;
        // Check for a valid email address.
        if (TextUtils.isEmpty(mPhone)) {
            etPhone.setError("手机号码不能为空！");
            focusView = etPhone;
            cancel = true;
        }
        if (TextUtils.isEmpty(mPwd)) {
            etPwd.setError("密码不能为空！");
            focusView = etPwd;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
           /* showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);*/
            login();
        }
    }
    /**
    *
    *登录的网络请求方法
    */
    private void login() {
        /*CSSApi.login(mPhone,mPwd,new Callback<ErrorResp>(new GsonParser<>(ErrorResp.class)) {
            @Override
            public void onResponse(ErrorResp loginResponse) {
                super.onResponse(loginResponse);
                //登录成功 的处理
                //String kefuname = loginResponse.getUser().getKefuname();

                //ToastUtils.showToast(mcontext,kefuname);
                String error=loginResponse.getMsg();
                ToastUtils.showToast(mcontext,loginResponse.getCode()+"");
            }

            @Override
            public void onFailure(IOException e) {
                super.onFailure(e);
            }

        });*/
        CSSApi.login(mPhone,mPwd,new Callback<String>(new StringParser()) {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                Gson gson=new Gson();
                try {
                    SimpleResp resp = gson.fromJson(response, SimpleResp.class);
                    ToastUtils.showToast(mcontext,resp.getUser().toString());
                }catch (Exception e){
                    ErrorResp errorResp = gson.fromJson(response, ErrorResp.class);
                    ToastUtils.showToast(mcontext,errorResp.getMsg()+"\n"+errorResp.getCode());
                }
            }

            @Override
            public void onFailure(IOException e) {
                super.onFailure(e);
            }

        });
    }



    private void getInputInfo(){
        mPhone= UIUtils.getEditTextContent(etPhone);
        mPwd=UIUtils.getEditTextContent(etPwd);
    }



}
