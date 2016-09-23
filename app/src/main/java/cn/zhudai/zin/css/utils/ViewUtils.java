package cn.zhudai.zin.css.utils;

import android.content.Context;
import android.graphics.Paint;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.zhudai.zin.css.R;

/**
 * Created by admin on 2016/8/17.
 */
public class ViewUtils {
    public static void addUnderLine(TextView textView){
        textView.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
        textView.getPaint().setAntiAlias(true);//抗锯齿
    }
    public static void setSpinner(Spinner spinner,int arrayid,Context context,  AdapterView.OnItemSelectedListener listener) {
        // 初始化控件
        // 建立数据源
        final List<String> sel=new ArrayList<>();
        final String[] mItems = context.getResources().getStringArray(arrayid);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, arrayid, R.layout.spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(listener);


    }
}
