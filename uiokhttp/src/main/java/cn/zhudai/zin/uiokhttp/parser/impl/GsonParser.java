package cn.zhudai.zin.uiokhttp.parser.impl;


import com.google.gson.Gson;

import java.io.IOException;

import cn.zhudai.zin.uiokhttp.parser.Parser;
import okhttp3.Response;

/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-10-08
 * Time: 11:55
 */
public class GsonParser<T> implements Parser<T> {
    private Class<T> mClass=null;
    public GsonParser(Class<T> clazz){
        if (clazz==null){
            throw new IllegalArgumentException("Class can't be null");
        }
        this.mClass=clazz;
    }
    @Override
    public T parse(Response response) {
        try {
            Gson gson=new Gson();
            String str=response.body().string();

            T t=gson.fromJson(str,mClass);
            return t;
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }
}
