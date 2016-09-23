package cn.zhudai.zin.css.ui.loadmoreRView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import java.util.LinkedList;
import java.util.List;

import cn.zhudai.zin.css.R;

/**
 * Created on 15/8/23.
 */
public abstract class BaseLoadMoreRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {
    public static final int TYPE_FOOTER = Integer.MIN_VALUE;
    public static final int TYPE_ITEM = 0;
    private boolean hasFooter;//设置是否显示Footer
    private boolean hasMoreData;//设置是否可以继续加载数据
    private  static final String LOADING_MORE_INFO="加载中...";
    private  static final String NO_MORE_DATA_INFO="没有更多内容";
    private  static final String NET_ERROR="无法连接网络...";
    private  boolean hasNet=true;
    private final List<T> mList = new LinkedList<T>();
    public void setHasNet(boolean hasNet) {
        this.hasNet = hasNet;
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
       // public final MaterialProgressBarSupport mProgressView;
        public final CircleProgressBar mProgressView;
        public final TextView mTextView;
        public FooterViewHolder(View view) {
            super(view);
            mProgressView = (CircleProgressBar) view.findViewById(R.id.progress_view);
            mTextView = (TextView) view.findViewById(R.id.tv_content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText();
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_FOOTER){//底部 加载view
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_recyler_load_more, parent, false);
            return new FooterViewHolder(view);
        } else {
            //数据itemViewHolder
            return onCreateItemViewHolder(parent, viewType);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof FooterViewHolder) {
            ((FooterViewHolder) holder).mProgressView.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light, android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
            ((FooterViewHolder) holder).mProgressView.setVisibility(View.VISIBLE);
            ((FooterViewHolder) holder).mTextView.setText(LOADING_MORE_INFO);
         //有更多数据
            if(hasMoreData){
                ((FooterViewHolder) holder).mProgressView.setVisibility(View.VISIBLE);
               // ((FooterViewHolder) holder).mProgressView.startProgress();
                //((FooterViewHolder) holder).mProgressView.setIndeterminate(true);
                ((FooterViewHolder) holder).mTextView.setText(LOADING_MORE_INFO);
            } else {
               // ((FooterViewHolder) holder).mProgressView.stopProgress();
                ((FooterViewHolder) holder).mProgressView.setVisibility(View.GONE);
                //((FooterViewHolder) holder).mProgressView.st;
                ((FooterViewHolder) holder).mTextView.setText(NO_MORE_DATA_INFO);
            }
            if (hasNet==false){
                ((FooterViewHolder) holder).mProgressView.setVisibility(View.GONE);
                //((FooterViewHolder) holder).mProgressView.st;
                ((FooterViewHolder) holder).mTextView.setText(NET_ERROR);
            }
        } else {
            onBindItemViewHolder((VH)holder, position);
        }
    }

    //正常数据itemViewHolder 实现
    public abstract void onBindItemViewHolder(final VH holder, int position);
    //数据itemViewHolder 实现
    public abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);

    @Override
    public int getItemViewType(int position) {

        if (position == getBasicItemCount() && hasFooter) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;//0
    }

   	public List<T> getList(){
   		return mList;
   	}

   	public void appendToList(List<T> list) {
   		if (list == null) {
   			return;
   		}
   		mList.addAll(list);
   	}
   	public void append(T t) {
   		if (t == null) {
   			return;
   		}
   		mList.add(t);
   	}

   	public void appendToTop(T item) {
   		if (item == null) {
   			return;
   		}
   		mList.add(0, item);
   	}
   	public void appendToTopList(List<T> list) {
   		if (list == null) {
   			return;
   		}
   		mList.addAll(0, list);
   	}


	public void remove(int position) {
		if (position < mList.size() - 1 && position >= 0) {
			mList.remove(position);
		}
	}

   	public void clear() {
   		mList.clear();
   	}

    private int getBasicItemCount() {
        return mList.size();
    }

   	@Override
   	public int getItemCount() {
   		return getBasicItemCount() + (hasFooter ? 1 : 0);
   	}

    public T getItem(int position) {
        if (position > mList.size() - 1) {
            return null;
        }
        return mList.get(position);
    }

   	@Override
   	public long getItemId(int position) {
   		return position;
   	}


    public boolean hasFooter() {
        return hasFooter;
    }

    public void setHasFooter(boolean hasFooter) {
        if(this.hasFooter != hasFooter) {
            this.hasFooter = hasFooter;
            notifyDataSetChanged();
        }
    }


    public boolean hasMoreData() {
        return hasMoreData;
    }

    public void setHasMoreData(boolean isMoreData) {
        if(this.hasMoreData != isMoreData) {
            this.hasMoreData = isMoreData;
            notifyDataSetChanged();
        }
    }
    public void setHasMoreDataAndFooter(boolean hasMoreData, boolean hasFooter) {
        if(this.hasMoreData != hasMoreData || this.hasFooter != hasFooter) {
            this.hasMoreData = hasMoreData;
            this.hasFooter = hasFooter;
            notifyDataSetChanged();
        }
    }

    public void addData(List<T> data)
    {
        if (data == null) return;
        //setHasMoreData(true);
        this.hasMoreData = true;
        appendToList(data);
        this.notifyDataSetChanged();
    }
    public void reflashData(List<T> data)
    {
        clear();
        addData(data);
    }

}
