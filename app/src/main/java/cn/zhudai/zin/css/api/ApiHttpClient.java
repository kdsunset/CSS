package cn.zhudai.zin.css.api;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by admin on 2016/8/21.
 */
public class ApiHttpClient {
   // private static OkHttpClient mOkHttpClient=new OkHttpClient();
    private static OkHttpClient mOkHttpClient=OkHttpUtils.getInstance().getOkHttpClient();
    public static void post(Request request, Callback callback){
        //OkHttpClient mOkHttpClient=new OkHttpClient();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }



}
