package cn.zhudai.zin.css.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhudai.zin.css.R;
import cn.zhudai.zin.css.base.BaseActivity;
import cn.zhudai.zin.css.controler.QuesMarkControler;
import cn.zhudai.zin.css.utils.ToastUtils;
import cn.zhudai.zin.css.utils.ViewUtils;

public class CustomDetailActivity extends BaseActivity {

    @Bind(R.id.sp_starlv)
    Spinner spStarlv;
    @Bind(R.id.iv_ques_mark_1)
    ImageView ivQuesMark1;
    @Bind(R.id.sp_process_state)
    Spinner spProcessState;
    @Bind(R.id.iv_ques_mark_2)
    ImageView ivQuesMark2;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_age)
    TextView tvAge;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.tv_amount)
    TextView tvAmount;
    @Bind(R.id.tv_profession)
    TextView tvProfession;
    @Bind(R.id.tv_House_property)
    TextView tvHouseProperty;
    @Bind(R.id.tv_car_property)
    TextView tvCarProperty;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_detail);
        ButterKnife.bind(this);
        mContext = this;
        initView();
    }

    private void initView() {
        ViewUtils.addUnderLine(tvPhone);
        setSpinner(spStarlv,R.array.starlevel);
        setSpinner(spProcessState,R.array.processstate);

    }

    @Override
    public void setToolbar(Toolbar toolbar) {
        showNavigationIcon(true);
        toolbar.inflateMenu(R.menu.custom_detail_menu);

    }

    @Override
    public String setTitleInCenter() {
        return "客户详情";
    }

    private void setSpinner(Spinner spinner,int arrayid) {
        // 初始化控件
        // 建立数据源
        final String selStr = "";

        final String[] mItems = getResources().getStringArray(arrayid);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, arrayid,R.layout.spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (pos > 0) {
                    //ToastUtils.showToast(mContext,"你点击的是:" + mItems[pos]);
                    String mItem = mItems[pos];
                    //selStr=mItem;
                    ToastUtils.showToast(mContext, "你点击的是:" + mItem);
                    //.makeText(mContext, "你点击的是:" + mItems[pos]).show();
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
      /*  String string = spinner.getSelectedItem().toString();
        ToastUtils.showToast(mContext,"你点击的是:" + string);*/
    }

    @OnClick(R.id.tv_phone)
    void call() {

        String phone = tvPhone.getText().toString();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        startActivity(intent);
    }

    @OnClick(R.id.iv_ques_mark_1)
    void setIvQuesMark1() {
        QuesMarkControler.showStarlevelQuesmarkTipDialog(mContext);
    }

    @OnClick(R.id.iv_ques_mark_2)
    void setIvQuesMark2() {
        QuesMarkControler.showstatelQuesmarkTipDialog(mContext);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.custom_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent();

        switch (item.getItemId()) {
            case R.id.mi_edit:
                startActivity(new Intent(mContext,ModifyInfoActivity.class));
                break;

        }


        return super.onOptionsItemSelected(item);
    }


}
