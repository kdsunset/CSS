package cn.zhudai.zin.css.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import cn.zhudai.zin.css.base.BaseResp;

/**
 * Created by admin on 2016/9/7.
 */
public class CustomResp extends BaseResp{
    private Result result;
    public void setResult(Result result){
        this.result = result;
    }
    public Result getResult(){
        return this.result;
    }


    public class Result {
        @SerializedName("kefuclient")
        private List<Custom> customs ;

        private int new_num;

        public void setCustoms(List<Custom> customs){
            this.customs = customs;
        }
        public List<Custom> getCustoms(){
            return this.customs;
        }
        public void setNew_num(int new_num){
            this.new_num = new_num;
        }
        public int getNew_num(){
            return this.new_num;
        }

    }
    public class Custom {
        private String id;

        @Override
        public String toString() {
            return "Custom{" +
                    "id='" + id + '\'' +
                    ", truename='" + truename + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", status='" + status + '\'' +
                    ", remark='" + remark + '\'' +
                    ", time='" + time + '\'' +
                    ", huashu_type='" + huashu_type + '\'' +
                    ", sex='" + sex + '\'' +
                    ", hasRead=" + hasRead +
                    '}';
        }

        private String truename;

        private String mobile;

        private String status;

        private String remark;

        private String time;

        private String huashu_type;

        private String sex;

        public int getHasRead() {
            return hasRead;
        }

        public void setHasRead(int hasRead) {
            this.hasRead = hasRead;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        @SerializedName("is_move_read")

        private int hasRead;

        public void setId(String id){
            this.id = id;
        }
        public String getId(){
            return this.id;
        }
        public void setTruename(String truename){
            this.truename = truename;
        }
        public String getTruename(){
            return this.truename;
        }
        public void setMobile(String mobile){
            this.mobile = mobile;
        }
        public String getMobile(){
            return this.mobile;
        }
        public void setStatus(String status){
            this.status = status;
        }
        public String getStatus(){
            return this.status;
        }
        public void setRemark(String remark){
            this.remark = remark;
        }
        public String getRemark(){
            return this.remark;
        }
        public void setTime(String time){
            this.time = time;
        }
        public String getTime(){
            return this.time;
        }
        public void setHuashu_type(String huashu_type){
            this.huashu_type = huashu_type;
        }
        public String getHuashu_type(){
            return this.huashu_type;
        }

    }
}
