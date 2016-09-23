package cn.zhudai.zin.css.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.zhudai.zin.css.R;
import cn.zhudai.zin.css.base.BaseActivity;

public class NoticeListActivity extends BaseActivity {


    @Bind(R.id.rv_noticelist)
    RecyclerView recyclerView;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);
        ButterKnife.bind(this);
        mContext = this;
        initView();
    }

    @Override
    public void setToolbar(Toolbar toolbar) {
        showNavigationIcon(true);
    }

    @Override
    public String setTitleInCenter() {
        return "系统通知";
    }

    private void initView() {
        //initRecyclerView();


    }

   /* private void initRecyclerView() {
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        //分隔线
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayout.VERTICAL,
                UIUtils.dp2px(1), getResources().getColor(R.color.gray_light)));
        ///为每个item增加响应事件
    }*/

}
