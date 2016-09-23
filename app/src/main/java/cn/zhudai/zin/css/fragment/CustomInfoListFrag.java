package cn.zhudai.zin.css.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.zhudai.zin.css.R;
import cn.zhudai.zin.css.activity.CustomDetailActivity;
import cn.zhudai.zin.css.adapter.CustomInfoRVAdapter;
import cn.zhudai.zin.css.api.CSSApi;
import cn.zhudai.zin.css.bean.CustomResp;
import cn.zhudai.zin.css.ui.MyItemDecoration;
import cn.zhudai.zin.css.ui.loadmoreRView.EndLessOnScrollListener;
import cn.zhudai.zin.css.utils.LogUtils;
import cn.zhudai.zin.css.utils.UIUtils;
import cn.zhudai.zin.uiokhttp.callback.Callback;
import cn.zhudai.zin.uiokhttp.parser.impl.GsonParser;

/**
 * Created by admin on 2016/8/29.
 */
public class CustomInfoListFrag extends Fragment {
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout mSwipeContainer;
    @Bind(R.id.tv_msg)
    TextView tvMsg;

    private Context mContext;
    private int page = 1;
    private String mUid;
    private boolean isloading=false;
    private int lastVisibleItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragView = UIUtils.inflate(R.layout.frag_custom_info_list);
        ButterKnife.bind(this, fragView);

        return fragView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        initSwipeFlashLayout();
        setRecyclerView();
       // mUid =412016+"";119560
       // mUid =119882+"";
        mUid =119560+"";
        //mSwipeContainer.performClick();
       // mSwipeContainer.setRefreshing(true);
       getDataFromNet(1);


    }

    /**
     * 设置刷新头SwipeFlashLayout
     */
    private void initSwipeFlashLayout() {
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub
                //下拉刷新时要执行的操作
                page=1;
                getDataFromNet(page);
                LogUtils.i("initSwipeFlashLayout-page="+page);
            }
        });
    }

    /**
     * 设置RecyclerView的方法
     */
    private void setRecyclerView() {
        final LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);

        //每个item高度一致，可设置为true，提高性能
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        //分隔线
        mRecyclerView.addItemDecoration(new MyItemDecoration(mContext));
        ///为每个item增加响应事件
        if (mAdapter==null){
            mAdapter = new CustomInfoRVAdapter();
            mRecyclerView.setAdapter(mAdapter);
        }
        mAdapter.setOnItemClickListener(new CustomInfoRVAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, CustomResp.Custom data) {
                Intent intent=new Intent(mContext, CustomDetailActivity.class);
                startActivity(intent);

            }
        });
       /* mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {

              //  if (!isloading){//解决不停上拉的时候不要重复请求网络
                    mAdapter.setHasFooter(true);

                   // mAdapter.setHasMoreDataAndFooter(true,true);

                    page = page+1;
                   // SystemClock.sleep(2000);
                    getDataFromNet(current_page);
                    LogUtils.i("addOnScrollListener-page="+current_page);
                }



            //}
        });*/
        mRecyclerView.addOnScrollListener(new EndLessOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {

                //  if (!isloading){//解决不停上拉的时候不要重复请求网络

                int visibleItemCount = mRecyclerView.getChildCount();
                int totalItemCount = mLinearLayoutManager.getItemCount();
                //当屏幕上的条目数不足一屏时不显示footer,
                if (totalItemCount>visibleItemCount){
                    mAdapter.setHasFooter(true);
                }
                page = page+1;

                getDataFromNet(current_page);
                LogUtils.i("addOnScrollListener-page="+current_page);
            }



            //}
        });
        /*mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int currentScrollState = newState;
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();

                if ((visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE &&
                        (lastVisibleItem) >= totalItemCount - 1)) {


                    // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
                    if (!isloading){//解决不停上拉的时候不要重复请求网络
                        mAdapter.setHasFooter(true);

                        // mAdapter.setHasMoreDataAndFooter(true,true);

                        page = page+1;
                        // SystemClock.sleep(2000);
                        getDataFromNet(page);
                        LogUtils.i("addOnScrollListener-page="+page);
                    }



                    //}
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
            }
        });*/



    }

    private CustomInfoRVAdapter mAdapter;


    private void getDataFromNet(final int page) {
        isloading=true;
        CSSApi.getCustomInfoList(mUid, page, new Callback<CustomResp>(new GsonParser<>(CustomResp.class)) {
            @Override
            public void onResponse(CustomResp customResp) {
                super.onResponse(customResp);

                int newNum = customResp.getResult().getNew_num();
                if (newNum > 0) {
                    String msg = "您有<font color=\"#ff0000\">" + newNum + "</font>条新数据，请及时跟进！";
                    tvMsg.setText((CharSequence)Html.fromHtml(msg));


                }else {
                    tvMsg.setVisibility(View.GONE);
                }
                List<CustomResp.Custom> list = customResp.getResult().getCustoms();
                LogUtils.i("size="+list.size());
                for (int i=0;i<list.size();i++){
                    LogUtils.i(list.get(i).toString());
                }
                if(page>1){
                    if (list.size()!=0){

                        mAdapter.addData(list);
                    }else {
                        mAdapter.setHasMoreDataAndFooter(false,true);
                    }
                }else {
                    mSwipeContainer.setRefreshing(false);
                    mAdapter.reflashData(list);
                }


                /*if (page > 1&&list.size()>0) {
                    mAdapter.setHasMoreData(true);
                }


                if (page==1){
                    mSwipeContainer.setRefreshing(false);
                    mAdapter.clear();
                }
                mAdapter.appendToList(list);
                mAdapter.notifyDataSetChanged();

                isloading = false;*/

            }

            @Override
            public void onFailure(IOException e) {
                super.onFailure(e);

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
