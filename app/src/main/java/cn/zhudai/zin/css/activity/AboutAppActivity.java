package cn.zhudai.zin.css.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.zhudai.zin.css.R;
import cn.zhudai.zin.css.base.BaseActivity;

public class AboutAppActivity extends BaseActivity {

    @Bind(R.id.tv_about)
    TextView tvAbout;
    private String about=
            "\u3000\u3000助贷网（www.zhudai.com）成立于2015年1月，是广州小微投资咨询有限公司旗下品牌，" +
                    "总部位于广州，公司人员规模达1000余人，在珠三角、长三角等多地设有分公司。\n" +
            "\u3000\u3000助贷网专注于银行无抵押贷款、信用贷款、抵押贷款等业务领域，旨在为小微企业和个人提供专业、" +
                    "便捷、划算、安全的信贷金融服务，是广州最大的信用贷款资讯及服务机构，即将成为中" +
                    "国领先的贷款服务O2O平台。\n" +
            "\u3000\u3000助贷网旗下品牌包括：小微金融、助贷网、贷贷猪、助贷直通车、助贷云平台等。\n";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        ButterKnife.bind(this);
        tvAbout.setText(about);
    }

    @Override
    public void setToolbar(Toolbar toolbar) {
        showNavigationIcon(true);
    }

    @Override
    public String setTitleInCenter() {
        return "关于我们";
    }
}
