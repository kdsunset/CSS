package cn.zhudai.zin.css.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import cn.zhudai.zin.css.R;
import cn.zhudai.zin.css.base.BaseActivity;

public class PersonalInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
    }

    @Override
    public void setToolbar(Toolbar toolbar) {
        showNavigationIcon(true);
    }

    @Override
    public String setTitleInCenter() {
        return "实名认证";
    }
}
