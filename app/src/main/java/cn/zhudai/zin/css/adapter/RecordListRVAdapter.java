package cn.zhudai.zin.css.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.zhudai.zin.css.R;
import cn.zhudai.zin.css.bean.RechargeRecordBean;
import cn.zhudai.zin.css.utils.LogUtils;
import cn.zhudai.zin.css.utils.UIUtils;

/**
 * Created by admin on 2016/9/5.
 */
public class RecordListRVAdapter extends RecyclerView.Adapter<RecordListRVAdapter.CustomViewHolder> {


    private List<RechargeRecordBean> datas;
    private OnItemClickListener mListener;
    public static final int TYPE_HEADER = Integer.MIN_VALUE;
    public static final int TYPE_ITEM = 0;
    private Context mContext;

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview_record, parent, false);
        CustomViewHolder vh = new CustomViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.OnItemClick(v, (String) view.getTag());//注意这里使用getTag方法获取数据
                }
            }
        });
        return vh;


    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        if (position == 0) {
            holder.itemRoot.setBackgroundColor(UIUtils.getColorFromRes(R.color.gray_light2));
            holder.tvItemTime.setText("充值时间");
            holder.tvItemAmount.setText("充值金额");
            holder.tvItemStatus.setText("充值状态");
            holder.tvItemBalance.setText("充值后余额");

        } else {

            RechargeRecordBean data = getDataItem(position);
            String time = data.getTime()+"";
            String amount = data.getAmount()+"";
            String status=convertStatus(data.getState());
            String balance=data.getBalance()+"";
            holder.tvItemTime.setText(time);
            holder.tvItemAmount.setText(amount);
            holder.tvItemStatus.setText(status);
            holder.tvItemBalance.setText(balance);
            //将数据保存在itemView的Tag中，以便点击时进行获取
            holder.itemView.setTag(getDataItem(position));
        }


    }

    public RechargeRecordBean getDataItem(int positon) {
        return datas.get(positon - 1);
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;//0
        }

    }

    private int getNoamlItemCount() {
        return datas.size();
    }

    @Override
    public int getItemCount() {
        return getNoamlItemCount() + 1;
    }

    /**
     * @param datas 展示的数据
     */
    public RecordListRVAdapter(Context context, List<RechargeRecordBean> datas) {
        this.datas = datas;
        this.mContext = context;
        LogUtils.i("HeaderRecyclerViewAdapter");
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_item_time)
        TextView tvItemTime;
        @Bind(R.id.tv_item_amount)
        TextView tvItemAmount;
        @Bind(R.id.tv_item_status)
        TextView tvItemStatus;
        @Bind(R.id.tv_item_balance)
        TextView tvItemBalance;
        @Bind(R.id.item_root)
        LinearLayout itemRoot;
        public CustomViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


    }


    /**
     * RecyclerView的Item的监听器接口
     */
    public interface OnItemClickListener {
        void OnItemClick(View view, String data);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    private String convertStatus(int staustInt) {
        String s = "";
        switch (staustInt) {
            case 1:
                s = "成功";
                break;
            case 2:
                s = "失败";
                break;

        }
        return s;
    }
}
