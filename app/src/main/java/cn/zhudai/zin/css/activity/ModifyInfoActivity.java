package cn.zhudai.zin.css.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhudai.zin.css.R;
import cn.zhudai.zin.css.adapter.ExListViewAdapter;
import cn.zhudai.zin.css.base.BaseActivity;
import cn.zhudai.zin.css.controler.QuesMarkControler;
import cn.zhudai.zin.css.utils.ToastUtils;

public class ModifyInfoActivity extends BaseActivity {
    @Bind(R.id.sp_starlv)
    Spinner spStarlv;
    @Bind(R.id.iv_ques_mark_1)
    ImageView ivQuesMark1;
    @Bind(R.id.sp_process_state)
    Spinner spProcessState;
    @Bind(R.id.iv_ques_mark_2)
    ImageView ivQuesMark2;
    @Bind(R.id.expandablelv)
    ExpandableListView expandablelv;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_info);
        ButterKnife.bind(this);
        mContext = this;
        initView();
    }

    private void initView() {
        setSpinner(spStarlv, R.array.starlevel);
        ToastUtils.showToast(mContext,"555您选择了");
        setSpinner(spProcessState, R.array.processstate);
        ExListViewAdapter adapter=new ExListViewAdapter(mContext);
        expandablelv.setAdapter(adapter);

    }

    @Override
    public void setToolbar(Toolbar toolbar) {
        showNavigationIcon(true);
    }

    @Override
    public String setTitleInCenter() {
        return "修改信息";
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
                Toast.makeText(this, "mi_edit", Toast.LENGTH_LONG).show();
                break;

        }


        return super.onOptionsItemSelected(item);
    }
    private void setSpinner(Spinner spinner, int arrayid) {
        // 初始化控件
        // 建立数据源
        final List<String> sel=new ArrayList<>();
        final String[] mItems = getResources().getStringArray(arrayid);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, arrayid, R.layout.spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (pos > 0) {
                    sel.add(mItems[pos]);

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }

        });


    }

}
