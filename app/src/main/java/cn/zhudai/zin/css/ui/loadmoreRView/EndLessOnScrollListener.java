package cn.zhudai.zin.css.ui.loadmoreRView;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by admin on 2016/9/9.
 */
public abstract class EndLessOnScrollListener extends RecyclerView.OnScrollListener {
    //声明一个LinearLayoutManager
    private LinearLayoutManager mLinearLayoutManager;

    //当前页，从0开始
    //private int currentPage = 0;
    private int currentPage ;

    //已经加载出来的Item的数量
    private int totalItemCount;

    //主要用来存储上一个totalItemCount
   // private int previousTotal = 0;
    private int previousTotal ;

    //在屏幕上可见的item数量
    private int visibleItemCount;

    //在屏幕可见的Item中的第一个
    //在当前屏幕显示的第一个listItem在整个listView里面的位置（下标从0开始）
    private int firstVisibleItem;

    //是否正在上拉数据
    private boolean loading = true;

    public EndLessOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        Log.d("1","firstVisibleItem: " +firstVisibleItem);
        Log.d("1","totalPageCount:" +totalItemCount);
        Log.d("1", "visibleItemCount:" + visibleItemCount);
        Log.d("1", "previousTotal:" + previousTotal);

        if (firstVisibleItem==0){
            previousTotal=0;
            currentPage=0;
            totalItemCount= mLinearLayoutManager.getItemCount();
        }
        if(loading){

            Log.d("数据加载中...","firstVisibleItem: " +firstVisibleItem);
            Log.d("数据加载中...","totalPageCount:" +totalItemCount);
            Log.d("数据加载中...", "visibleItemCount:" + visibleItemCount);
            Log.d("数据加载中...", "previousTotal:" + previousTotal);

            //最开始totalItemCount=10，previousTotal=0，然后（10>0，loading=false，即非加载数据状态），并在在第一页滑动过程中保持这种状态，
            if(totalItemCount > previousTotal){
                //说明数据已经加载结束
                loading = false;
                previousTotal = totalItemCount;
                Log.d("数据加载完毕..."," " );
            }
        }

        //这里需要好好理解
        //目前已加载出来的条目个数-当前屏幕看到的条目的个数<=当前屏幕上第一条条目的position
        //比如目前已加载第一页，总共10条，屏幕可容纳7条，第一条position为0（0开始），
        // 则firstVisibleItem为第三条时已显示了第一页的10条数据，再上拉则应加载更多
        if (!loading && totalItemCount-visibleItemCount <= firstVisibleItem){
            currentPage ++;
            onLoadMore(currentPage);
            loading = true;
        }
    }

    /**
     * 提供一个抽闲方法，在Activity中监听到这个EndLessOnScrollListener
     * 并且实现这个方法
     * */
    public abstract void onLoadMore(int currentPage);

    /*
    * CLASSPATH .;%JAVA_HOME%\lib;%JAVA_HOME%\lib\tools.jar
    * JAVA_HOME D:\Java\jdk1.8.0_101
    * Path %JAVA_HOME%\bin;%JAVA_HOME%\jre\bin;
    * */

}
