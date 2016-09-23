package cn.zhudai.zin.css.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.zhudai.zin.css.R;
import cn.zhudai.zin.css.base.BaseActivity;
import cn.zhudai.zin.css.ui.CustomDialog;
import cn.zhudai.zin.css.utils.SpannableUtils.ClickableSpanNoUnderline;
import cn.zhudai.zin.css.utils.SpannableUtils.SpanClickableSpan;
import cn.zhudai.zin.css.utils.SpannableUtils.SpannableHelper;
import cn.zhudai.zin.css.utils.UIUtils;

public class AuthenticationResultActivity extends BaseActivity {



    @Bind(R.id.tv_tip)
    TextView tvTip;
    @Bind(R.id.tv_custom_info)
    TextView tvCustomInfo;
    @Bind(R.id.tv_account_info)
    TextView tvAccountInfo;
    private int reviewState;
    private static final int REVIEW_PASS = 0x01;
    private static final int REVIEW_ING = 0x02;
    private static final int REVIEW_FAIL = 0x03;
    //余额
    private String balance = 0 + "";
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_result);
        ButterKnife.bind(this);
        mContext=this;
        reviewState=REVIEW_PASS;
        intView();
    }

    @Override
    public void setToolbar(Toolbar toolbar) {

    }


    private void intView() {

        setTipAndDialogByState();


    }



    @Override
    public String setTitleInCenter() {
        return "全部客户";
    }



    private void setTipAndDialogByState() {
        //根据审核的状态打开对象的对话框

        switch (reviewState) {
            case REVIEW_PASS:
                //审核通过
                showReviewPassDialog();
                String content = "您当前的余额为" + balance + "，" + "完成充值后系统会自动为您分配数据。立即充值>>";
                SpannableString spannableString = SpannableHelper
                        .create(content)
                        .setTextColor(balance, UIUtils.getColorFromRes(R.color.red))
                        .setTextClicable("立即充值>>", tvTip, UIUtils.getColorFromRes(R.color.red), new ClickableSpanNoUnderline.OnClickListener<SpanClickableSpan>() {
                            @Override
                            public void onClick(View widget, SpanClickableSpan span) {
                                startActivity(new Intent(mContext, RechargeActivity.class));
                            }
                        })
                        .getmSpannableString();

                tvTip.setText(spannableString);
                tvCustomInfo.setEnabled(true);
                tvCustomInfo.performClick();//默认选中（手动调用点击事件）
                break;
            case REVIEW_ING:
                showWaitResultDialog();
                tvTip.setText("您已提交实名认证资料，请等待审核通过！");
                tvCustomInfo.setEnabled(false);
                break;
            case REVIEW_FAIL:
                showPassFailDialog();
                tvTip.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    private void showWaitResultDialog() {
        final CustomDialog.Builder builder = new CustomDialog.Builder(mContext)
                .setTitle("提示")
                .setMessage("您已提交实名认证资料，请等待审核通过！")
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();


    }

    private void showPassFailDialog() {
        String content = "您提交的资料没有通过审核，请重新提交资料！（温馨提示：提交清晰正确的图片能更快通过审核哦）";
        Drawable drawable = getResources().getDrawable(R.drawable.ic_warning_icon);
        drawable.setBounds(0, 0, 40, 40);
        final CustomDialog.Builder builder = new CustomDialog.Builder(mContext)
                .hideHeader(true)
                .setIconAtBeginning(drawable)
                .setMessage(content)
                .setNegativeButton("重新提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(mContext, UploadInfoActivity.class));
                        dialog.dismiss();
                    }
                });

        builder.create().show();


    }

    private void showReviewPassDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(mContext)
                .setTitle("提示")
                .setMessage("恭喜您通过审核，完成充值即可获取客户！")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("马上充值", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();

    }
}

