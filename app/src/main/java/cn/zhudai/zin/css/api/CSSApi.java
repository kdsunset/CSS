package cn.zhudai.zin.css.api;

import android.util.Log;

import java.io.File;
import java.util.List;

import cn.zhudai.zin.css.utils.LogUtils;
import cn.zhudai.zin.uiokhttp.callback.Callback;
import cn.zhudai.zin.uiokhttp.callback.FileCallback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
/**
 * Created by admin on 2016/8/21.
 */
public class CSSApi {

    // private static final String Base_URL="https://www.zhudai.com/app/api_kefu/%s";
    //测试环境
    private static final String Base_URL="http://test.zhudai.com/app/api_kefu/%s";

    public static String getAbsoluteApiUrl(String partUrl) {
        String url = partUrl;
        if (!partUrl.startsWith("http:") && !partUrl.startsWith("https:")) {
            url = String.format(Base_URL, partUrl);
        }
        Log.d("BASE_CLIENT", "request:" + url);



        return url;
    }
    /**
     * 注册时获取验证码
     *
     */
    public static void getVfCode(String phone , Callback callback){
        String url="";
        RequestBody formBody = new FormBody.Builder()
                .add("phone", phone)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        ApiHttpClient.post(request,callback);
    }
    /**
    *登陆
    */
    public static void login(String phone, String pwd ,Callback callback){
        //String url="https://www.zhudai.com/app/api_kefu/login.html";
        String path="login.html";
        RequestBody formBody = new FormBody.Builder()
                .add("account", phone)
                .add("password",pwd)
                .build();
        Request request = new Request.Builder()
                .url(getAbsoluteApiUrl(path))
                .post(formBody)
                .build();
        ApiHttpClient.post(request,callback);
    }


    /**
    * 重设密码
    *
    */
    public static void resetPwd(String phone,String code,String pwd,Callback callback){
        String url=Base_URL+"";
        RequestBody formBody = new FormBody.Builder()
                .add("mobile", phone)
                .add("setcode", code)
                .add("setpassword", pwd)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        ApiHttpClient.post(request,callback);

    }

    /**
    *注册账号
    *
    */
    public static void registerAccount(String city,String name,String phone
            ,String vfcode,String pwd,Callback callback){
        String url="https://www.zhudai.com/app/api_kefu/reg.html";
        RequestBody formBody = new FormBody.Builder()
                .add("city", city)
                .add("nickname",name)
                .add("mobile", phone)
                .add("phonecode", vfcode)
                .add("password", pwd)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        ApiHttpClient.post(request,callback);

    }
    public static void uploadAuthenticationInfo(String name, String city, List<String> picturePathList, Callback callback){
        String url=Base_URL+"";
        okhttp3.MediaType MEDIA_TYPE_PNG = okhttp3.MediaType.parse("image/jpg");
        okhttp3. OkHttpClient client = new okhttp3.OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("name", name);
        builder.addFormDataPart("city", city);
        for (int i = 0; i < picturePathList.size(); i++) {
            File f = new File(picturePathList.get(i));
            if (f.exists()) {
                builder.addFormDataPart("image[]", f.getName(), okhttp3.RequestBody.create(MEDIA_TYPE_PNG,f));
                LogUtils.i("imagefilepath="+f.getAbsolutePath());
            }
        }
        MultipartBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        ApiHttpClient.post(request,callback);
    }
    /**
    * 首页获取客户信息列表
    *
    */
    public static void getCustomInfoList(String kid,int page,Callback callback){
        String url="https://www.zhudai.com/app/api_kefu/index.html";
        RequestBody formBody = new FormBody.Builder()
                .add("kid", kid)
                .add("page", page+"")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        ApiHttpClient.post(request,callback);
    }
    /**
    *
    *检查版本更新
    */
    public static void checkVersion(Callback callback){
        String path="";
        RequestBody formBody = new FormBody.Builder()

                .build();
        Request request = new Request.Builder()
                .url(getAbsoluteApiUrl(path))
                .post(formBody)
                .build();
        ApiHttpClient.post(request,callback);

    }
    public static void downloadApk(FileCallback callback){
        String path="";
        RequestBody formBody = new FormBody.Builder()

                .build();
        Request request = new Request.Builder()
                .url(getAbsoluteApiUrl(path))
                .post(formBody)
                .build();
        ApiHttpClient.post(request,callback);
    }
    /*test*/
    public static void test(Callback callback){
        String url="https://kyfw.12306.cn/otn/";
        RequestBody formBody = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        ApiHttpClient.post(request,callback);

    }
}
