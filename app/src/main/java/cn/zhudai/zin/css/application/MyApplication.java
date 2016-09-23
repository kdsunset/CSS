package cn.zhudai.zin.css.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.zhudai.zin.css.R;
import cn.zhudai.zin.css.api.HttpsUtils;
import cn.zhudai.zin.css.api.OkHttpUtils;
import cn.zhudai.zin.css.utils.galleryfinal.UILImageLoader;
import okhttp3.OkHttpClient;
import okio.Buffer;

public class MyApplication extends Application {
    //全局上下文环境
    private static Context mContext;
    //全局的handler
    private static Handler mHandler;
    //主线程
    private static Thread mMainThread;
    //主线程id
    private static int mMainThreadId;
    private static boolean loginStatus;
    public  CoreConfig gCoreConfig;
    public static FunctionConfig gFunctionConfig;
    private String CER_ZHUDAI="-----BEGIN CERTIFICATE-----\n" +
            "MIIGDTCCBPWgAwIBAgIQKWaptPe2ReLxnTuxEdZ40TANBgkqhkiG9w0BAQsFADBE\n" +
            "MQswCQYDVQQGEwJVUzEWMBQGA1UEChMNR2VvVHJ1c3QgSW5jLjEdMBsGA1UEAxMU\n" +
            "R2VvVHJ1c3QgU1NMIENBIC0gRzMwHhcNMTYwNzE0MDAwMDAwWhcNMTcwNzE0MjM1\n" +
            "OTU5WjCBiTELMAkGA1UEBhMCQ04xDzANBgNVBAgMBuW5v+S4nDEPMA0GA1UEBwwG\n" +
            "5bm/5beeMS0wKwYDVQQKDCTlub/lt57lsI/lvq7mipXotYTlkqjor6LmnInpmZDl\n" +
            "hazlj7gxEjAQBgNVBAsMCeW8gOWPkemDqDEVMBMGA1UEAwwMKi56aHVkYWkuY29t\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA9NHlK6Sz42dZGOLJnNzv\n" +
            "WBbbtrF1YXTR/JLQnE6qTBrRfgofpSIYR9wray3NOXhgi7JyT8K+MeyFgiXuQSwR\n" +
            "B8UitGDX84OhP1/gSuWiYCHIjoTSjRZGDpdvimuGnL1jQbc29aYp7CbdnM0dU4fh\n" +
            "+JCJ3m92b2n41gAgrqmE60ycC05zVHCRIStCwDzS+W3tZcGXbRc9r35tZ8KSRAHw\n" +
            "GTQaFWHUgi6JMDf+qeizwvTitL7BUCJv8sQGlG/JtpJ55vIw+0S3JjNZBlIDUbMM\n" +
            "hA7bfiGcs0TQM7RHEjY23lPYfLjf2XOVQNU/oi/t6dddzitnOBEDQDD16vvV2uks\n" +
            "/QIDAQABo4ICszCCAq8wIwYDVR0RBBwwGoIMKi56aHVkYWkuY29tggp6aHVkYWku\n" +
            "Y29tMAkGA1UdEwQCMAAwDgYDVR0PAQH/BAQDAgWgMCsGA1UdHwQkMCIwIKAeoByG\n" +
            "Gmh0dHA6Ly9nbi5zeW1jYi5jb20vZ24uY3JsMIGdBgNVHSAEgZUwgZIwgY8GBmeB\n" +
            "DAECAjCBhDA/BggrBgEFBQcCARYzaHR0cHM6Ly93d3cuZ2VvdHJ1c3QuY29tL3Jl\n" +
            "c291cmNlcy9yZXBvc2l0b3J5L2xlZ2FsMEEGCCsGAQUFBwICMDUMM2h0dHBzOi8v\n" +
            "d3d3Lmdlb3RydXN0LmNvbS9yZXNvdXJjZXMvcmVwb3NpdG9yeS9sZWdhbDAdBgNV\n" +
            "HSUEFjAUBggrBgEFBQcDAQYIKwYBBQUHAwIwHwYDVR0jBBgwFoAU0m/3lvSFP3I8\n" +
            "MH0j2oV4m6N8WnwwVwYIKwYBBQUHAQEESzBJMB8GCCsGAQUFBzABhhNodHRwOi8v\n" +
            "Z24uc3ltY2QuY29tMCYGCCsGAQUFBzAChhpodHRwOi8vZ24uc3ltY2IuY29tL2du\n" +
            "LmNydDCCAQUGCisGAQQB1nkCBAIEgfYEgfMA8QB3AN3rHSt6DU+mIIuBrYFocH4u\n" +
            "jp0B1VyIjT0RxM227L7MAAABVegdmfYAAAQDAEgwRgIhAJHLsKBX39RJ4hsp646y\n" +
            "Fj15ip1hcNfLdLg+r/sOFAo0AiEArQvLAXNQTeq9j9W+XB4aE0oHewCuw46W1qbR\n" +
            "a9HMSQUAdgCkuQmQtBhYFIe7E6LMZ3AKPDWYBPkb37jjd80OyA3cEAAAAVXoHZoY\n" +
            "AAAEAwBHMEUCIQDrw10pFM2f05KCTtuZIXJEJyyP7ej9ttOy5wAqWm4BPgIgYugS\n" +
            "m884mK9Gs1BzMWC8NYltxgur/k2v29dd6oWDoGswDQYJKoZIhvcNAQELBQADggEB\n" +
            "AG6c2bDe42n6vBRKlbsn6sat/BW8bTjqJdGMRDGBwwvbjINVJ3sByL1BW5R27SP0\n" +
            "7krkDVNtx90t8yFXmkra9BsQBloh953azQKlFznGk3/XZho9fzyz5n+G4ti5gxb/\n" +
            "SjDsET7TXL586k7orzrywQiumXondCgeHuJoj0F1L27p/6B0R5/jloUUfU3izSym\n" +
            "ap+vKwKWucHohZPAgdo7DNaR78PMXXv/+TX6S+9Tum1iIF91PdyyvP3+YhZ/eK8X\n" +
            "OnEtjRZr+woJjTHmi1Dab12ZlPiQ9TArqqtjMVtVywMiINfzT7GK2GrRZQhumwl3\n" +
            "L0ikEYqS0h4t+oCS5mXVBRE=\n" +
            "-----END CERTIFICATE-----";


    //此方法在其余代码运行前,就会调用,所有在此处去构建开发过程中需要用到的常见对象,并且提供方法用于获取
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        mHandler = new Handler();

        //MyApplication运行在主线程中,所以拿当前线程对象即可
        mMainThread = Thread.currentThread();

        //主线程id,就是MyApplication(主线程)线程id,获取当前线程id
        mMainThreadId = android.os.Process.myTid();
        initGalleryFinal();
        initImageLoader();
        initOKhttp();


    }

    public static Context getContext() {
        return mContext;
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static int getMainThreadId() {
        return mMainThreadId;
    }

    public static boolean getLoginStatus() {

        return loginStatus;
    }

    private void initGalleryFinal() {
        //设置主题
        //ThemeConfig.CYAN
        /*ThemeConfig theme = new ThemeConfig.Builder()
                .build();*/

        ThemeConfig theme = new ThemeConfig.Builder()
               // .setTitleBarBgColor(R.color.red_nor)
                .setTitleBarBgColor(Color.rgb(0xC4, 0x31, 0x2A))//c4312a
                .setTitleBarTextColor(Color.BLACK)
                .setTitleBarIconColor(Color.BLACK)
                .setFabNornalColor(Color.RED)
                .setFabPressedColor(R.color.red_deep)
                .setCheckNornalColor(Color.WHITE)
                .setCheckSelectedColor(Color.BLACK)
                .setIconBack(R.mipmap.ic_action_previous_item)
                .setIconRotate(R.mipmap.ic_action_repeat)
                .setIconCrop(R.mipmap.ic_action_crop)
                .setIconCamera(R.mipmap.ic_action_camera)

                .build();
        //配置功能
        gFunctionConfig = new FunctionConfig.Builder()

                .setEnableCamera(true)
                .setEnableEdit(true)

                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();

        //配置imageloader
        cn.finalteam.galleryfinal.ImageLoader imageloader = new UILImageLoader();

        gCoreConfig = new CoreConfig.Builder(mContext, imageloader, theme)
                .setFunctionConfig(gFunctionConfig)

                //.setPauseOnScrollListener(new UILPauseOnScrollListener(false, true))
                .build();
        GalleryFinal.init(gCoreConfig);
    }
    private void initImageLoader(){

        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);

        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);
    }
    private void initOKhttp(){

        InputStream inputStream = new Buffer().writeUtf8(CER_ZHUDAI).inputStream();
        InputStream[] certificates=new InputStream[]{inputStream};

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(certificates, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .hostnameVerifier(new HostnameVerifier()
                {
                    @Override
                    public boolean verify(String hostname, SSLSession session)
                    {
                        return true;
                    }
                })
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }
}


