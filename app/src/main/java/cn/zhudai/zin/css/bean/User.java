package cn.zhudai.zin.css.bean;

/**
 * Created by admin on 2016/9/12.
 */
public class User {

        private String kid;

        private int usertype;

        private String account;

        private String kefuname;

        public void setKid(String kid){
            this.kid = kid;
        }
        public String getKid(){
            return this.kid;
        }
        public void setUsertype(int usertype){
            this.usertype = usertype;
        }
        public int getUsertype(){
            return this.usertype;
        }
        public void setAccount(String account){
            this.account = account;
        }
        public String getAccount(){
            return this.account;
        }
        public void setKefuname(String kefuname){
            this.kefuname = kefuname;
        }
        public String getKefuname(){
            return this.kefuname;
        }
}
