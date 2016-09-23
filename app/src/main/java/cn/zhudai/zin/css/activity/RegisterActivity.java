package cn.zhudai.zin.css.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.zhudai.zin.css.R;
import cn.zhudai.zin.css.api.CSSApi;
import cn.zhudai.zin.css.base.BaseActivity;
import cn.zhudai.zin.css.bean.SimpleResp;
import cn.zhudai.zin.css.bean.VfCodeResp;
import cn.zhudai.zin.css.ui.CountDownEffect;
import cn.zhudai.zin.css.utils.AssetsUtils;
import cn.zhudai.zin.css.utils.ToastUtils;
import cn.zhudai.zin.css.utils.UIUtils;
import cn.zhudai.zin.uiokhttp.callback.Callback;
import cn.zhudai.zin.uiokhttp.parser.impl.GsonParser;

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.et_city)
    EditText etCity;
    @Bind(R.id.et_name)
    EditText etName;
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
    @Bind(R.id.tv_tip)
    TextView tvTip;
    @Bind(R.id.bt_sumit)
    Button btSumit;
    @Bind(R.id.rb_agree_check)
    CheckBox rbAgreeCheck;
    private Context mcontext;

    private String mCity;
    private String mName;
    private String mPhone;
    private String mVfCode;
    private String mPwd;
    private String mCfPwd;
    private boolean isAgree;
    private static final int MSGCODE_FAIL=400;
    private static final int MSGCODE_OK_VFCODE=2002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mcontext = this;
        initView();
    }

    private void initView() {
        String tip = "同意<u>《助贷网客服系统用户协议》</u>";
        tvTip.setText(Html.fromHtml(tip));


    }

    @OnClick(R.id.tv_tip)
    void showProtocol() {
        startActivity(new Intent(mcontext, ShowProtocolActivity.class));
    }

    @OnClick(R.id.et_city)
    void selectCity() {
        cityPicker();
    }

    @OnClick(R.id.bt_sumit)
    void sumitAction() {
        getInputInfo();
        attemptAction();
    }

    @OnClick(R.id.bt_get_vfcode)
    void getVfCodeAction() {
        mPhone = UIUtils.getEditTextContent(etPhone);
        if (!TextUtils.isEmpty(mPhone)) {
            CountDownEffect builder = new
                    CountDownEffect(btGetVfcode)
                    .setDurationSecond(60)
                    .setIntervalSecond(1)
                    .setFinishText("获取验证码")
                    .create();
            builder.start();
            CSSApi.getVfCode(mPhone,new Callback<VfCodeResp>(new GsonParser<>(VfCodeResp.class)){
                @Override
                public void onResponse(VfCodeResp vfCodeResp) {
                    super.onResponse(vfCodeResp);
                    //获取验证码成功的操作（用户手动输入验证码）
                }

                @Override
                public void onFailure(IOException e) {
                    super.onFailure(e);
                }
            });
        } else {
            etPhone.setError("手机号码不能为空");
            etPhone.requestFocus();
        }
    }

    private void attemptAction() {
        etCity.setError(null);
        etName.setError(null);
        etCfpwd.setError(null);
        etPhone.setError(null);
        etPwd.setError(null);
        etVerificationCode.setError(null);
        boolean cancel = false;
        View focusView = null;


        // Check for a valid email address.
        if (TextUtils.isEmpty(mVfCode)) {            //如果验证码为空
            etVerificationCode.setError("验证码不能为空！");
            focusView = etVerificationCode;
            cancel = true;
        }
        if (TextUtils.isEmpty(mCity)) {            //如果工作城市为空
            etCity.setError("工作城市不能为空！");
            focusView = etCity;
            cancel = true;
        }

        if (TextUtils.isEmpty(mName)) {            //如果姓名为空
            etName.setError("姓名不能为空！");
            focusView = etName;
            cancel = true;
        }
        if (TextUtils.isEmpty(mPhone)) {            //如果手机号码为空
            etPhone.setError("手机号码不能为空！");
            focusView = etPhone;
            cancel = true;
        }else if (!mPhone.matches("^1[3|4|5|8]\\d{9}$")) {        //账号非空但是不合法
            etPhone.setError("不合法的手机号码！");
            focusView = etPhone;
            cancel = true;
        }

        if (TextUtils.isEmpty(mPwd)) {            //如果密码为空
            etPwd.setError("密码不能为空！");
            focusView = etPwd;
            cancel = true;
        }
        if (TextUtils.isEmpty(mCfPwd)) {            //如果账号为空
            etCfpwd.setError("确认密码不能为空！");
            focusView = etCfpwd;
            cancel = true;
        }
        if (!mPwd.equals(mCfPwd)) {            //如果账号为空
            etCfpwd.setError("两次密码输入不一致！");
            focusView = etCfpwd;
            cancel = true;
        }

        if (cancel) {

            focusView.requestFocus();
        } else {

            register();
        }
    }

    private void register() {
        CSSApi.registerAccount(mCity,mName,mPhone,mVfCode,mPwd,
                new Callback<SimpleResp>(new GsonParser<SimpleResp>(SimpleResp.class)){
                    @Override
                    public void onFailure(IOException e) {
                        super.onFailure(e);

                    }

                    @Override
                    public void onResponse(SimpleResp simpleResp) {
                        super.onResponse(simpleResp);
                        //注册成功后的主线程的操作
                        String kefuname = simpleResp.getUser().getKefuname();
                        ToastUtils.showToast(mcontext,kefuname);
                        /*boolean hasAuthentication=false;//判断是否已通过实名认证，未则转到认证页面，已认证则进入主界面
                        if (!hasAuthentication){
                            startActivity(new Intent(mcontext,UploadInfoActivity.class));
                        }else {
                            startActivity(new Intent(mcontext,MainActivity.class));
                        }*/
                    }
                });
    }

    private void getInputInfo() {
        mCity = UIUtils.getEditTextContent(etCity);
        mName = UIUtils.getEditTextContent(etName);
        mPhone = UIUtils.getEditTextContent(etPhone);
        mPwd = UIUtils.getEditTextContent(etPwd);
        mCfPwd = UIUtils.getEditTextContent(etCfpwd);
        mVfCode=UIUtils.getEditTextContent(etVerificationCode);
        isAgree =rbAgreeCheck.isChecked();
    }

    @Override
    public void setToolbar(Toolbar toolbar) {
        showNavigationIcon(true);
    }

    @Override
    public String setTitleInCenter() {
        return "快速注册";
    }

    private void cityPicker() {
        ArrayList<AddressPicker.Province> data = new ArrayList<AddressPicker.Province>();
        String json = AssetsUtils.readText(this, "city.json");
        data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
        AddressPicker picker = new AddressPicker(this, data);
        // picker.setSelectedItem("贵州", "贵阳", "花溪");
        //picker.setHideProvince(true);//加上此句举将只显示地级及县级
        picker.setHideCounty(true);//加上此句举将只显示省级及地级
        picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
            @Override
            public void onAddressPicked(String province, String city, String county) {
                //showToast(province + city + county);
                if ("上海市".equals(city) || "天津市".equals(city) || "重庆市".equals(city) || "北京市".equals(city) ||
                        "上海".equals(city) || "天津".equals(city) || "重庆".equals(city) || "北京".equals(city)) {
                    etCity.setText(city);
                    mCity = city;
                } else {

                    etCity.setText(province + city);
                    mCity = province + city;
                }
            }
        });
        picker.show();
    }

}
