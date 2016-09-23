package cn.zhudai.zin.css.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhudai.zin.css.R;
import cn.zhudai.zin.css.activity.AboutAppActivity;
import cn.zhudai.zin.css.activity.AccountOperationActivity;
import cn.zhudai.zin.css.activity.ConsumeInfoActivity;
import cn.zhudai.zin.css.activity.FeedbackActivity;
import cn.zhudai.zin.css.activity.NoticeListActivity;
import cn.zhudai.zin.css.activity.PersonalInfoActivity;
import cn.zhudai.zin.css.activity.RechargeActivity;
import cn.zhudai.zin.css.activity.RedistributionActivity;
import cn.zhudai.zin.css.utils.UIUtils;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2016/8/29.
 */
public class AccountInfoFrag extends Fragment {

    @Bind(R.id.iv_head_img)
    CircleImageView ivHeadImg;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.rl_recharge)
    RelativeLayout rlRecharge;
    @Bind(R.id.rl_consumer)
    RelativeLayout rlConsumer;
    @Bind(R.id.rl_operation)
    RelativeLayout rlOperation;
    @Bind(R.id.rl_distribute)
    RelativeLayout rlDistribute;
    @Bind(R.id.rl_authentication)
    RelativeLayout rlAuthentication;
    @Bind(R.id.rl_feedback)
    RelativeLayout rlFeedback;
    @Bind(R.id.rl_about)
    RelativeLayout rlAbout;
    @Bind(R.id.rl_notice)
    RelativeLayout rlNotice;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = UIUtils.inflate(R.layout.frag_account_info);
        ButterKnife.bind(this, root);
        mContext=getActivity();
        initView();
        return root;
    }

    private void initView() {
        ivHeadImg.setImageResource(R.drawable.ic_ha);
        tvName.setText("蔡小八");
        tvPhone.setText("13570294587");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.rl_recharge)
    void reChargeAction() {
        startActivity(new Intent(mContext, RechargeActivity.class));
    }

    @OnClick(R.id.rl_consumer)
    void consumerAction() {
        startActivity(new Intent(mContext, ConsumeInfoActivity.class));
    }

    @OnClick(R.id.rl_operation)
    void operationAction() {
        startActivity(new Intent(mContext, AccountOperationActivity.class));
    }

    @OnClick(R.id.rl_distribute)
    void distributeAction() {
        startActivity(new Intent(mContext, RedistributionActivity.class));
    }

    @OnClick(R.id.rl_authentication)
    void authenticationAction() {
        startActivity(new Intent(mContext, PersonalInfoActivity.class));
    }

    @OnClick(R.id.rl_feedback)
    void feedbackAction() {
        startActivity(new Intent(mContext, FeedbackActivity.class));
    }

    @OnClick(R.id.rl_about)
    void aboutAction() {
        startActivity(new Intent(mContext, AboutAppActivity.class));
    }

    @OnClick(R.id.rl_notice)
    void noticeAction() {
        startActivity(new Intent(mContext, NoticeListActivity.class));
    }
}
