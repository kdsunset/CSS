package cn.zhudai.zin.css.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.zhudai.zin.css.R;
import cn.zhudai.zin.css.base.BaseActivity;
import cn.zhudai.zin.css.fragment.CustomInfoListFrag;

public class RedistributionActivity extends BaseActivity {

    @Bind(R.id.framelayout)
    FrameLayout framelayout;
    private Context mContext;
    private CustomInfoListFrag customInfoListFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redistribution);
        ButterKnife.bind(this);
        mContext=this;
        initView();
    }

    private void initView() {
        if (customInfoListFrag == null) {
            customInfoListFrag = new CustomInfoListFrag();
        }
        openFrag(customInfoListFrag);
    }

    @Override
    public void setToolbar(Toolbar toolbar) {

    }

    @Override
    public String setTitleInCenter() {
        return "再分配客户";
    }

    private void openFrag(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();

        // 使用当前Fragment的布局替代id_content的控件
        transaction.replace(R.id.fl_content, fragment);
        transaction.commit();
    }
}
