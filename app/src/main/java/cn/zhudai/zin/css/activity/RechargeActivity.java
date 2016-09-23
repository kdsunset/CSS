package cn.zhudai.zin.css.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhudai.zin.css.R;
import cn.zhudai.zin.css.base.BaseActivity;

public class RechargeActivity extends BaseActivity {

    @Bind(R.id.tv_balance)
    TextView tvBalance;
    @Bind(R.id.et_recharge_account)
    EditText etRechargeAccount;
    @Bind(R.id.bt_next)
    Button btNext;
    @Bind(R.id.tv_tip)
    TextView tvTip;
    String per = 0.00 + "";
    @Bind(R.id.tv_detail)
    TextView tvDetail;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        ButterKnife.bind(this);
        mContext=this;
        initView();
    }


    @Override
    public void setToolbar(Toolbar toolbar) {
        showNavigationIcon(true);
    }

    @OnClick(R.id.tv_detail)
    void detailAction(){
        startActivity(new Intent(mContext,RechargeDetailActivity.class));
    }
    @OnClick(R.id.bt_next)
    void nextAction(){
        startActivity(new Intent(mContext,PaymentActivity.class));
    }
    @Override
    public String setTitleInCenter() {
        return "充值";
    }

    private void initView() {
        String tip = "客户充值成功后，助贷网将按照每条数据" + per + "元\n" +
                "向您分配客户数据，您可以随时暂停和开启客户的推送，每条数据分配\n" +
                "成功后，将从账户扣除" + per + "元！";
        tvTip.setText(tip);

    }
}
