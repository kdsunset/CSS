package cn.zhudai.zin.css.activity;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhudai.zin.css.R;
import cn.zhudai.zin.css.adapter.CustomInfoRVAdapter;
import cn.zhudai.zin.css.controler.AuthenticationResultControler;
import cn.zhudai.zin.css.fragment.AccountInfoFrag;
import cn.zhudai.zin.css.fragment.CustomInfoListFrag;
import cn.zhudai.zin.css.utils.UIUtils;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.tv_custom_info)
    TextView tvCustomInfo;
    @Bind(R.id.tv_account_info)
    TextView tvAccountInfo;

    @Bind(R.id.fl_content)
    FrameLayout flContent;
    @Bind(R.id.tv_tip)
    TextView tvTip;
    @Bind(R.id.tb_title)
    TextView tbTitle;
    @Bind(R.id.v_toolbar)
    Toolbar vToolbar;


    private Context mContext;


    private CustomInfoRVAdapter mAdapter;
    private int page = 1;
    CustomInfoListFrag customInfoListFrag;
    AccountInfoFrag accountInfoFrag;
    private int reviewState;
    //余额
    private String balance = 0 + "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;

        reviewState = 0x03;
        intView();


    }

    private void intView() {
        initToolbar();
        //如果是认证
        if ((getIntent().getExtras() != null) && ((getIntent().getExtras().getInt("TAG") == 1))) {
            AuthenticationResultControler controler = new AuthenticationResultControler(mContext);
            controler.setTipAndDialogByState(reviewState, balance, tvTip, tvCustomInfo, tvAccountInfo);
        } else {
            tvCustomInfo.performClick();
        }




    }

    private void initToolbar() {
        vToolbar.setTitle("");
        setSupportActionBar(vToolbar);
    }


    @OnClick(R.id.tv_custom_info)
    void customInfoAction() {
        tbTitle.setText("全部客户");
        tvCustomInfo.setBackgroundColor(UIUtils.getColorFromRes(R.color.white));
        tvAccountInfo.setBackgroundColor(UIUtils.getColorFromRes(R.color.gray_244));
        tvCustomInfo.setTextColor(UIUtils.getColorFromRes(R.color.red_pink));
        tvAccountInfo.setTextColor(UIUtils.getColorFromRes(R.color.black));
        if (customInfoListFrag == null) {
            customInfoListFrag = new CustomInfoListFrag();
        }
        if (accountInfoFrag == null) {
            accountInfoFrag = new AccountInfoFrag();
        }
        //openFrag(customInfoListFrag);
        if (customInfoListFrag==null){
            customInfoListFrag=new CustomInfoListFrag();
        }
        switchFrag(customInfoListFrag);

    }

    @OnClick(R.id.tv_account_info)
    void accountInfoAction() {
        tbTitle.setText("账户信息");
        tvCustomInfo.setBackgroundColor(UIUtils.getColorFromRes(R.color.gray_244));
        tvCustomInfo.setTextColor(UIUtils.getColorFromRes(R.color.black));
        tvAccountInfo.setBackgroundColor(UIUtils.getColorFromRes(R.color.white));
        tvAccountInfo.setTextColor(UIUtils.getColorFromRes(R.color.red_pink));
        if (accountInfoFrag==null){
            accountInfoFrag=new AccountInfoFrag();
        }
        //openFrag(accountInfoFrag);
        switchFrag(accountInfoFrag);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.ab_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    private void openFrag(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();

        // 使用当前Fragment的布局替代id_content的控件
        transaction.replace(R.id.fl_content, fragment);
        transaction.commit();
    }
    private void switchFrag(Fragment show){
        Fragment cur=new Fragment();
        if (show instanceof CustomInfoListFrag){
            cur=accountInfoFrag;
        }else if (show instanceof AccountInfoFrag){
            cur=customInfoListFrag;
        }
        FragmentManager fm = getSupportFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();

        if (!show.isAdded()){
            transaction.add(R.id.fl_content,show);
        }
        if (cur!=null){
            transaction.hide(cur);
        }
        transaction.show(show).commit();
/*
            if (!show.isAdded()) {	// 先判断是否被add过
                transaction.hide(cur).add(R.id.fl_content, show).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(cur).show(show).commit(); // 隐藏当前的fragment，显示下一个

            }*/




    }


}
