package cn.zhudai.zin.css.controler;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.view.View;
import android.widget.TextView;

import cn.zhudai.zin.css.R;
import cn.zhudai.zin.css.activity.RechargeActivity;
import cn.zhudai.zin.css.activity.UploadInfoActivity;
import cn.zhudai.zin.css.ui.CustomDialog;
import cn.zhudai.zin.css.utils.SpannableUtils.ClickableSpanNoUnderline;
import cn.zhudai.zin.css.utils.SpannableUtils.SpanClickableSpan;
import cn.zhudai.zin.css.utils.SpannableUtils.SpannableHelper;
import cn.zhudai.zin.css.utils.UIUtils;

/**
 * Created by admin on 2016/8/29.
 */
public class AuthenticationResultControler {
    private Context mContext;
    private static final int REVIEW_PASS = 0x01;
    private static final int REVIEW_ING = 0x02;
    private static final int REVIEW_FAIL = 0x03;
    public AuthenticationResultControler(Context mContext) {
        this.mContext = mContext;
    }

    public void setTipAndDialogByState(int reviewState, String balance, TextView tvTip,TextView tvCustomInfo,TextView tvAccountInfo) {
        //根据审核的状态打开对象的对话框
        tvTip.setVisibility(View.VISIBLE);
        switch (reviewState) {
            case REVIEW_PASS:
                //审核通过
                showReviewPassDialog();
                showTipTextView(tvTip,balance);
                tvCustomInfo.setEnabled(true);

                break;
            case REVIEW_ING:
                showWaitResultDialog();
                tvTip.setText("您已提交实名认证资料，请等待审核通过！");
                tvCustomInfo.setEnabled(false);
                break;
            case REVIEW_FAIL:
                showPassFailDialog();
                tvTip.setVisibility(View.GONE);
                tvCustomInfo.setEnabled(false);
                tvAccountInfo.setEnabled(false);
                break;
            default:
                break;
        }
    }
    public void showTipTextView(TextView tvTip,String balance){

        String content = "您当前的余额为" + balance + "，" + "完成充值后系统会自动为您分配数据。立即充值>>";
        SpannableString spannableString = SpannableHelper
                .create(content)
                .setTextColor(balance, UIUtils.getColorFromRes(R.color.red))
                .setTextClicable("立即充值>>", tvTip, UIUtils.getColorFromRes(R.color.red), new ClickableSpanNoUnderline.OnClickListener<SpanClickableSpan>() {
                    @Override
                    public void onClick(View widget, SpanClickableSpan span) {
                        mContext.startActivity(new Intent(mContext, RechargeActivity.class));
                    }
                })
                .getmSpannableString();

        tvTip.setText(spannableString);

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
        Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_warning_icon);
        drawable.setBounds(0, 0, 40, 40);
        final CustomDialog.Builder builder = new CustomDialog.Builder(mContext)
                .hideHeader(true)
                .setIconAtBeginning(drawable)
                .setMessage(content)
                .setNegativeButton("重新提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mContext.startActivity(new Intent(mContext, UploadInfoActivity.class));
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
