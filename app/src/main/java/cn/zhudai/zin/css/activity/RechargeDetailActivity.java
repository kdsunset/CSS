package cn.zhudai.zin.css.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.zhudai.zin.css.R;
import cn.zhudai.zin.css.base.BaseActivity;

public class RechargeDetailActivity extends BaseActivity {

    @Bind(R.id.tv_balance)
    TextView tvBalance;
    @Bind(R.id.tv_total_recharge)
    TextView tvTotalRecharge;
    @Bind(R.id.tv_total_consumption)
    TextView tvTotalConsumption;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_detail);
        ButterKnife.bind(this);
        mContext=this;
    }

    @Override
    public void setToolbar(Toolbar toolbar) {
        showNavigationIcon(true);
    }

    @Override
    public String setTitleInCenter() {
        return "充值详情";
    }
}
