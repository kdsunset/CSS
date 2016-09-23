package cn.zhudai.zin.css.bean;

import com.google.gson.annotations.SerializedName;

import cn.zhudai.zin.css.base.BaseResp;

/**
 * Created by admin on 2016/9/14.
 */
public class UpdateResp extends BaseResp {
    @SerializedName("result")
    private UpdateInfo updateInfo;

    public UpdateInfo getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(UpdateInfo updateInfo) {
        this.updateInfo = updateInfo;
    }

    public class UpdateInfo{
        private int versionCode; // 服务器上的版本号
        private String VersionName;
        private String updateLog; // 升级信息
        private String url; // apk下载地址

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return VersionName;
        }

        public void setVersionName(String versionName) {
            VersionName = versionName;
        }

        public String getUpdateLog() {
            return updateLog;
        }

        public void setUpdateLog(String updateLog) {
            this.updateLog = updateLog;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
