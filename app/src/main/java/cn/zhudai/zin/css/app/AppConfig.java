package cn.zhudai.zin.css.app;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * 应用程序配置类：用于保存用户相关信息及设置
 *

 */
public class AppConfig {

    private Context mContext;
    private static AppConfig appConfig;
    private final static String APP_CONFIG = "config";
    // 默认存放图片的路径
    public final static String DEFAULT_SAVE_IMAGE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "CSS"
            + File.separator + "css_img" + File.separator;

    // 默认存放文件下载的路径
    public final static String DEFAULT_SAVE_FILE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "CSS"
            + File.separator + "download" + File.separator;

    public static AppConfig getAppConfig(Context context) {
        if (appConfig == null) {
            appConfig = new AppConfig();
            appConfig.mContext = context;
        }
        return appConfig;
    }


}
