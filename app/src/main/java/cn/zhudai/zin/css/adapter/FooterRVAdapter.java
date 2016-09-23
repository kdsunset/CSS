package cn.zhudai.zin.css.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import java.util.List;

import cn.zhudai.zin.css.R;

/**
 * Created by admin on 2016/9/9.
 */
public abstract class FooterRVAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter{
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private Context context;
    private List data;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return data.size() == 0 ? 0 : data.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public FooterRVAdapter(Context context, List data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {

            return onCreateItemViewHolder( parent,  viewType);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_footer, parent,
                    false);
            return new FooterViewHolder(view);
        }
        return null;
    }



    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        /*if (holder instanceof ItemViewHolder) {
            //holder.tv.setText(data.get(position));
            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, position);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemLongClick(holder.itemView, position);
                        return false;
                    }
                });
            }
        }*/
        if (holder instanceof FooterViewHolder){
            ((FooterViewHolder) holder).mProgressView.setVisibility(View.VISIBLE);
        }else {
            onBindItemViewHolder((VH)holder,position);
        }
    }

    //正常数据itemViewHolder 实现
    public abstract void onBindItemViewHolder(final VH holder, int position);
    //数据itemViewHolder 实现
    public abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);
    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public ItemViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv_date);
        }
    }

     static class FooterViewHolder extends RecyclerView.ViewHolder {
        // public final MaterialProgressBarSupport mProgressView;
        public final CircleProgressBar mProgressView;
        public final TextView mTextView;
        public FooterViewHolder(View view) {
            super(view);
            mProgressView = (CircleProgressBar) view.findViewById(R.id.progressBar);
            mTextView = (TextView) view.findViewById(R.id.tv_content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText();
        }
    }
}
