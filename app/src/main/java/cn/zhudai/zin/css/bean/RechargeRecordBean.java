package cn.zhudai.zin.css.bean;

/**
 * Created by admin on 2016/9/5.
 */
public class RechargeRecordBean {
    private Long time;
    private double amount;
    private int state;
    private double balance;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public RechargeRecordBean(Long time, double amount, int state, double balance) {
        this.time = time;
        this.amount = amount;
        this.state = state;
        this.balance = balance;
    }
}
