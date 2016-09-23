package cn.zhudai.zin.css.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import cn.zhudai.zin.css.R;
import cn.zhudai.zin.css.ui.ToolBarHelper;

public abstract class BaseActivity extends AppCompatActivity {
    private ToolBarHelper mToolBarHelper ;
    public Toolbar toolbar ;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        context=this;
    }



    @Override
    public void setContentView(int layoutResID) {

        mToolBarHelper = new ToolBarHelper(this, layoutResID);
        toolbar = mToolBarHelper.getToolBar();
        setContentView(mToolBarHelper.getContentView()); /*把 toolbar 设置到Activity 中*/
        toolbar.setTitle("");
        setToolbar(toolbar);
        mToolBarHelper.setTitle(setTitleInCenter());
        setSupportActionBar(toolbar); /*自定义的一些操作*/
        onCreateCustomToolBar(toolbar);
    }

    public abstract void setToolbar(Toolbar toolbar) ;
    public abstract String setTitleInCenter();
    public void showNavigationIcon(boolean b){
        if (b){
            toolbar.setNavigationIcon(R.drawable.ic_back_white);
        }
    }



    public void onCreateCustomToolBar(Toolbar toolbar) {
        toolbar.setContentInsetsRelative(0, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
