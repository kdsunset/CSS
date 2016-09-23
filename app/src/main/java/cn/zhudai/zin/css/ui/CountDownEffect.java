package cn.zhudai.zin.css.ui;

import android.os.CountDownTimer;
import android.widget.Button;

/**
 * 为按钮添加倒数效果
 * Created by admin on 2016/8/24.
 */
public class CountDownEffect {


    private int durationSecond;
    private int intervalSecond;
    private String finishText;
    private Button button;
    private  CountDownTimer timer;

    public CountDownEffect(Button button) {
        this.button = button;
    }

    public CountDownEffect setDurationSecond(int durationSecond) {
        this.durationSecond = durationSecond;
        return this;
    }

    public CountDownEffect setIntervalSecond(int intervalSecond) {
        this.intervalSecond = intervalSecond;
        return this;
    }

    public CountDownEffect setFinishText(String finishText) {
        this.finishText = finishText;
        return this;
    }

    public CountDownEffect create() {
        timer = new CountDownTimer(durationSecond * 1000, intervalSecond * 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String result =  "重新发送("+millisUntilFinished / 1000+")";
                button.setText(result);
                button.setEnabled(false);
            }

            @Override
            public void onFinish() {
                button.setEnabled(true);
                button.setText(finishText);
            }
        };
        return this;
    }

    public  void start() {
        timer.start();
    }


}
