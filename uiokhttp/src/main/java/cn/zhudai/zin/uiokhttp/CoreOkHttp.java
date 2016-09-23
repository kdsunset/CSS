package cn.zhudai.zin.uiokhttp;

import okhttp3.OkHttpClient;

/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-10-08
 * Time: 12:15
 */
public class CoreOkHttp extends OkHttpClient {
   /* private OkHttpClient mOkHttpClient;
    public CoreOkHttp(OkHttpClient client){

        OkHttpClient clone = client.clone();

        clone.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Log.e("TAG","intercept start");
                Request request = chain.request();
                Response response = chain.proceed(request);
                Log.e("TAG","end");
                return response;
            }
        });
        mOkHttpClient=clone;
    }
    public OkHttpClient getOkHttpClient(){
        return mOkHttpClient;
    }*/

}
