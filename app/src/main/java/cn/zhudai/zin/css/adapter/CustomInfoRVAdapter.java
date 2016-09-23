package cn.zhudai.zin.css.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.zhudai.zin.css.R;
import cn.zhudai.zin.css.bean.CustomResp;
import cn.zhudai.zin.css.ui.loadmoreRView.BaseLoadMoreRecyclerAdapter;

/**
 * Created by admin on 2016/8/28.
 */
public class CustomInfoRVAdapter extends BaseLoadMoreRecyclerAdapter<CustomResp.Custom, CustomInfoRVAdapter.ItemViewHolder> {

    /**
     * 为RecyclerView添加item的点击事件
     * 1.在MyAdapter中定义接口,模拟ListView的OnItemClickListener：
     * 2. 声明一个这个接口的变量( OnItemClickListener mListener;)，并注册监听（ itemView.setOnClickListener）
     * 3.接口的onItemClick()中的v.getTag()方法，这需要在onBindViewHolder()方法中设置和item相关的数据
     * 4.最后暴露给外面的调用者，定义一个设置Listener的方法（setOnItemClickListener）：
     */
    OnItemClickListener mListener;

    /*public CustomInfoRVAdapter(List<CustomResp.Custom> datas) {
        appendToList(datas);
    }*/
    public CustomInfoRVAdapter() {

    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_custominfo_recyclerview_item, parent, false);
        ItemViewHolder vh = new ItemViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.OnItemClick(v, (CustomResp.Custom) view.getTag());//注意这里使用getTag方法获取数据
                }
            }
        });
        return vh;
    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder holder, int position) {
        CustomResp.Custom info = getItem(position);
        //1：已读，0：未读
        Boolean hasRead = info.getHasRead()==1?true:false;
        String name = info.getTruename();
        String phone = info.getMobile();
        String status = info.getStatus();
        String remark = info.getRemark();
        String time = info.getTime();
        String sex = info.getSex();
        if (hasRead!=null){
            if (!hasRead){
                (holder.ivReddot).setVisibility(View.VISIBLE);


            }else {
                ( holder.ivReddot).setVisibility(View.INVISIBLE);

            }
        }
        if (!TextUtils.isEmpty(name)){
            (holder.tvName).setText(name);

        }
        if (!TextUtils.isEmpty(sex)){
            (holder.tvSex).setText(sex);
        }else {
            (holder.tvSex).setText("男");
        }
        if (!TextUtils.isEmpty(phone)){
            (holder.tvPhone).setText(phone);
        }
        if (!TextUtils.isEmpty(status)){

            (holder.status).setText(position+1+"");
            //(holder.status).setText((CharSequence) Html.fromHtml(status));

        }
        if (!TextUtils.isEmpty(remark)){
            (holder.tvRemark).setText(remark);
        }
        if (!TextUtils.isEmpty(time)){
            (holder.tvTime).setText(time);
        }

    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_reddot)
        ImageView ivReddot;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_sex)
        TextView tvSex;
        @Bind(R.id.tv_phone)
        TextView tvPhone;
        @Bind(R.id.status)
        TextView status;
        @Bind(R.id.tv_remark)
        TextView tvRemark;
        @Bind(R.id.tv_time)
        TextView tvTime;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
       /* public  void  setTvTitle(String t){
            tvTitle.setText(t);
        }*/

    }

    public interface OnItemClickListener {
        void OnItemClick(View view, CustomResp.Custom data);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
}
