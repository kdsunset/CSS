package cn.zhudai.zin.uiokhttp.parser.impl;


import java.io.IOException;

import cn.zhudai.zin.uiokhttp.parser.Parser;
import okhttp3.Response;


/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-10-08
 * Time: 11:51
 */
public class StringParser implements Parser<String> {
    @Override
    public String parse(Response response) {
        String result=null;
        try {
            result=response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
