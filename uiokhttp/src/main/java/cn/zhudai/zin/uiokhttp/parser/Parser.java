package cn.zhudai.zin.uiokhttp.parser;


import java.io.FileNotFoundException;

import okhttp3.Response;

/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-10-08
 * Time: 11:08
 */
public interface Parser<T> {
    T parse(Response response) throws FileNotFoundException;
}
