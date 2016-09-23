package cn.zhudai.zin.css.utils.SpannableUtils;

/**
 * Created by admin on 2016/8/22.
 */

import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import cn.zhudai.zin.css.utils.LogUtils;

/**
 * Spannable工具类，用于设置文字的前景色、背景色、Typeface、粗体、斜体、字号、超链接、删除线、下划线、上下标等
 *
 * */
public class SpannableHelper {
    private static SpannableHelper mSpannableHelper;
    private  SpannableString mSpannableString;
    private String parentStr;

   private SpannableHelper(String parentStr ){


        mSpannableString=new SpannableString(parentStr);

        this.parentStr=parentStr;

    }
    public static SpannableHelper create( String parentStr){
         mSpannableHelper=null;
         mSpannableHelper=new SpannableHelper(parentStr);
        return mSpannableHelper;
    }

    public static class Builder{
        private SpannableString spannableString;
        private String parentStr;


        public Builder(String parentStr) {

            this.parentStr = parentStr;
        }
    }


    public SpannableString getmSpannableString() {
        return mSpannableString;
    }


    /**
    * 设置文字绝对大小，默认px，
    *size：默认单位为px。
    * dip：true为size的单位是dip，false为px。
    */
    public SpannableHelper setTextAbsoluteSize(String sub, int size, boolean isDp){
        int[] indexs = this.getIndexFromParentStr(parentStr, sub);
        LogUtils.i(indexs[0]+"\t"+indexs[1]);
        new AbsoluteSizeSpan(size, isDp);
        mSpannableString.setSpan(new AbsoluteSizeSpan(size, isDp), indexs[0], indexs[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return this;
    }
    /**
    * 加粗 Typeface.BOLD_ITALIC
    *
    */
    public SpannableHelper setTextStyleSpan(String sub,int typeface){
        int[] indexs = this.getIndexFromParentStr(parentStr, sub);
        mSpannableString.setSpan(new StyleSpan(typeface), indexs[0], indexs[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }


    private  int[] getIndexFromParentStr(String parent,String sub){
        int[] startAndEndIndex=new int[2];
        int start = parent.indexOf(sub);
        int end=start+sub.length();
        startAndEndIndex[0]=start;
        startAndEndIndex[1]=end;
        return startAndEndIndex;
    }

    public SpannableHelper setTextClicable(String sub, TextView tv,int color, ClickableSpanNoUnderline.OnClickListener<SpanClickableSpan> listener){

        int[] indexs = getIndexFromParentStr(parentStr, sub);
        SpanClickableSpan spanClickableSpan = new SpanClickableSpan(color, listener);
        //spanClickableSpan.setUrlString("https://github.com/CaMnter");
        mSpannableString.setSpan(spanClickableSpan, indexs[0], indexs[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
       // tv.setText(mSpannableString);
        // 在单击链接时凡是有要执行的动作，都必须设置MovementMethod对象
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        // 设置点击后的颜色，这里涉及到ClickableSpan的点击背景
        tv.setHighlightColor(0x00000000);
        return this;
    }
    public SpannableHelper setTextColor(String sub,int color){
        int[] indexs = getIndexFromParentStr(parentStr, sub);

        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(color);
        mSpannableString.setSpan(foregroundColorSpan,indexs[0],indexs[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }



}