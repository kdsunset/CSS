package cn.zhudai.zin.css.utils.SpannableUtils;

/**
 * Created by admin on 2016/8/26.
 */
public class SpanClickableSpan extends ClickableSpanNoUnderline {

    private String urlString;

    public String getUrlString() {
        return urlString;
    }

    public void setUrlString(String urlString) {
        this.urlString = urlString;
    }

    public SpanClickableSpan(int color, OnClickListener onClickListener) {
        super(color, onClickListener);
    }

    public SpanClickableSpan(OnClickListener onClickListener) {
        super(onClickListener);
    }

}