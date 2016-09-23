package cn.zhudai.zin.css.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhudai.zin.css.R;
import cn.zhudai.zin.css.base.BaseActivity;
import cn.zhudai.zin.css.ui.CustomDialog;

public class AccountOperationActivity extends BaseActivity {

    @Bind(R.id.cb_switch)
    CheckBox cbSwitch;
    @Bind(R.id.bt_login_out)
    Button btLoginOut;
    @Bind(R.id.sp_max_sum)
    Spinner spMaxSum;
    @Bind(R.id.rl_modify_pwd)
    RelativeLayout rlModifyPwd;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_operation);
        ButterKnife.bind(this);
        mContext=this;
        initView();
    }

    private void initView() {

        setSpinner();
        spMaxSum.setSelection(6,true);

        cbSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //因为有对话框，点击后并不能马上改变checkbox的状态
                //这里使点击之后保持原来的状态
                cbSwitch.setChecked(!cbSwitch.isChecked());
                if (cbSwitch.isChecked()){
                    showSuspendDialog();
                }else {
                    showRecoveryDialog();
                }
            }
        });
    }

    @Override
    public void setToolbar(Toolbar toolbar) {
        showNavigationIcon(true);

    }

    @Override
    public String setTitleInCenter() {
        return "账户操作";
    }

    @OnClick(R.id.rl_modify_pwd)
    void modifyPwdAction(){
        startActivity(new Intent(mContext,ModifyPwdActivity.class));
    }
    @OnClick(R.id.bt_login_out)
    void loginOutAction(){
       startActivity(new Intent(mContext,LoginActivity.class));
    }
    private void showSuspendDialog(){
        CustomDialog.Builder builder=new CustomDialog.Builder(mContext)
                .hideHeader(true)
                .setMessage("确定暂停受理？（暂停受理后，系统将不再分配客户）")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cbSwitch.setChecked(false);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();

    }
   private void showRecoveryDialog(){
        CustomDialog.Builder builder=new CustomDialog.Builder(mContext)
                .hideHeader(true)
                .setMessage("确定恢复受理？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cbSwitch.setChecked(true);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();

    }
    private void setSpinner(){

            final List<String> sel=new ArrayList<>();
            final String[] mItems = getResources().getStringArray(R.array.max_sum);
            // 建立Adapter并且绑定数据源
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext, R.array.max_sum, R.layout.spinner);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //绑定 Adapter到控件
            spMaxSum.setAdapter(adapter);
            spMaxSum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



    }
}
