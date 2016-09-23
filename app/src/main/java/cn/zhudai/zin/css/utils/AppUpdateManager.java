package cn.zhudai.zin.css.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import cn.zhudai.zin.css.api.CSSApi;
import cn.zhudai.zin.css.bean.UpdateResp;
import cn.zhudai.zin.css.interf.ICallbackResult;
import cn.zhudai.zin.css.service.DownloadService;
import cn.zhudai.zin.uiokhttp.callback.Callback;
import cn.zhudai.zin.uiokhttp.parser.impl.StringParser;

/**
 * Created by admin on 2016/9/13.
 */
/*
* 1.网络请求，获取服务器上的版本信息*/
public class AppUpdateManager {
    private Context mContext;

    private boolean isShow = false;
    private UpdateResp.UpdateInfo mUpdate;
    private ProgressDialog waitDialog;
    /**
    *
    *检查更新
    */
    public void checkUpdate() {
        if (isShow) {
            showCheckingDialog();
        }
        CSSApi.checkVersion(new Callback<String>(new StringParser()){
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                hideCheckDialog();
                Gson gson=new Gson();
                try {
                    UpdateResp updateResp = gson.fromJson(response, UpdateResp.class);
                    mUpdate=updateResp.getUpdateInfo();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                onFinshCheck();
            }

            @Override
            public void onFailure(IOException e) {
                super.onFailure(e);
                hideCheckDialog();
                if (isShow) {
                    showFaileDialog();
                }
            }
        });
    }
    private void onFinshCheck() {
        if (haveNew()) {
            showUpdateInfoDialog();
        } else {
            if (isShow) {
                showLatestDialog();
            }
        }
    }
    public boolean haveNew() {
        if (this.mUpdate == null) {
            return false;
        }
        boolean haveNew = false;
        int curVersionCode = getLocalVerison(mContext);
        if (curVersionCode < mUpdate.getVersionCode()) {
            haveNew = true;
        }
        return haveNew;
    }
    /**
     * 取得应用的版本号（本地）
     *
     */
    public int getLocalVerison(Context context) {
        PackageManager pm = context.getPackageManager(); // 取得包管理器的对象，这样就可以拿到应用程序的管理对象
        try {
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0); // 得到应用程序的包信息对象
            LogUtils.i("versionName"+info.versionCode);
            return info.versionCode; // 取得应用程序的版本号
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            // 此异常不会发生
            return -1;
        }
    }

    private void showCheckingDialog() {
        if (waitDialog == null) {
            waitDialog = DialogHelper.getWaitDialog((Activity) mContext, "正在获取新版本信息...");
        }
        waitDialog.show();
    }

    private void hideCheckDialog() {
        if (waitDialog != null) {
            waitDialog.dismiss();
        }
    }

    private void showUpdateInfoDialog() {
        if (mUpdate == null) {
            return;
        }
        AlertDialog.Builder dialog = DialogHelper.getConfirmDialog(mContext, mUpdate.getUpdateLog(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //UIHelper.openDownLoadService(mContext, mUpdate.getUrl(), mUpdate.getVersionName());
                openDownLoadService(mContext, mUpdate.getUrl(), mUpdate.getVersionName());
            }
        });
        dialog.setTitle("发现新版本");
        dialog.show();
    }


    /*以bindService的方式启动service（需要ServiceConnection，Binder）*/

    private void openDownLoadService(Context context, String url, String versionName) {
        final ICallbackResult callback = new ICallbackResult() {

            @Override
            public void OnBackResult(Object s) {
            }
        };
        ServiceConnection conn = new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                DownloadService.DownloadBinder binder = (DownloadService.DownloadBinder) service;
                binder.addCallback(callback);
                binder.start();

            }
        };
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra(DownloadService.BUNDLE_KEY_DOWNLOAD_URL, url);
        intent.putExtra(DownloadService.BUNDLE_KEY_TITLE, versionName);
        context.startService(intent);
        context.bindService(intent, conn, Context.BIND_AUTO_CREATE);

    }

    private void showLatestDialog() {
        DialogHelper.getMessageDialog(mContext, "已经是新版本了").show();
    }

    private void showFaileDialog() {
        DialogHelper.getMessageDialog(mContext, "网络异常，无法获取新版本信息").show();
    }
}
