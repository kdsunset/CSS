package cn.zhudai.zin.uiokhttp.parser.impl;


import java.io.InputStream;

import cn.zhudai.zin.uiokhttp.parser.Parser;
import okhttp3.Response;


/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-10-08
 * Time: 11:51
 */
public class InputStreamParser implements Parser<InputStream> {
    @Override
    public InputStream parse(Response response) {
        InputStream result=null;
        try {
            result=response.body().byteStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
