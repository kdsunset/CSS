package cn.zhudai.zin.css.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.zhudai.zin.css.R;
import cn.zhudai.zin.css.utils.AppManager;

public class SplashActivity extends AppCompatActivity {

    @Bind(R.id.ll_root)
    LinearLayout llRoot;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        mContext = this;
        AppManager.getAppManager().addActivity(this);
        initAnim();
    }

    private void initAnim() {
        // 添加界面的渐变动画 效果
        Animation animation = new AlphaAnimation(0.3f, 1f);
        animation.setDuration(2000);
        // 启动动画
        llRoot.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // checkVesion();
                // getVersion();
                //  checkUpdate();
                getUpdateInfoFromNet();
            }
        });
    }
    private void getUpdateInfoFromNet() {
        redirectTo();

    }
    // 进入
    private void redirectTo() {
        startActivity(new Intent(getApplicationContext(),
                LoginActivity.class));
        AppManager.getAppManager().finishActivity();
    }
}
