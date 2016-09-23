package cn.zhudai.zin.css.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.zhudai.zin.css.R;

/**
 * Created by admin on 2016/8/31.
 */
public class ExListViewAdapter implements ExpandableListAdapter {

    private String[] groupList = new String[]{"基本信息", "需求信息", "身份信息",
            "车产信息","房产信息","保单信息","信用卡信息"};
    private Context context;
    private List<LinearLayout> childLayoutList;

    public ExListViewAdapter(Context context)
    {
        this.context = context;
       // childLayoutList=getChildData();
    }
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return groupList.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        /*LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ImageView imageView =new ImageView(context);
        AbsListView.LayoutParams plp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(plp);
        imageView.setPadding(UIUtils.dp2px(25), 0, UIUtils.dp2px(10), 0);
        imageView.setBackgroundResource(R.drawable.expand_list_indicator);
        ll.addView(imageView);
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, UIUtils.dp2px(50));
        TextView textView = new TextView(context);
        textView.setLayoutParams(lp);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        textView.setPadding(UIUtils.dp2px(50), 0, 0, 0);
        textView.setTextSize(20);
        textView.setText(getGroup(groupPosition).toString());*/
        LinearLayout root = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.exlv_group_layout, null);
        ImageView indicator = (ImageView) root.findViewById(R.id.iv_group_indicator);
        TextView tilte= (TextView) root.findViewById(R.id.tv_group_title);
        tilte.setText(getGroup(groupPosition).toString());
        if (!isExpanded){
            indicator.setImageResource(R.drawable.exlv_arrow_right);
        }else {
            indicator.setImageResource(R.drawable.exlv_arrow_down);
        }
        return root;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        switch (groupPosition){
            case 0:
                convertView=inflater.inflate(R.layout.item_child_base_info,null);
                break;
            case 1:
                convertView=inflater.inflate(R.layout.item_child_demand_info,null);
                break;
            case 2:
                convertView=inflater.inflate(R.layout.item_child_identity_info,null);
                break;
            case 3:
                convertView=inflater.inflate(R.layout.item_child_car_info,null);
                break;
            case 4:
                convertView=inflater.inflate(R.layout.item_child_house_info,null);
                break;
            case 5:
                convertView=inflater.inflate(R.layout.item_child_policy_info,null);
                break;
            case 6:
                convertView=inflater.inflate(R.layout.item_child_creditcard_info,null);
                break;

        }


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }


}
