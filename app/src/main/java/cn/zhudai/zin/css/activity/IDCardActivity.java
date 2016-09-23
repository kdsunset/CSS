package cn.zhudai.zin.css.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.zhudai.zin.css.R;
import cn.zhudai.zin.css.application.MyApplication;
import cn.zhudai.zin.css.base.BaseActivity;
import cn.zhudai.zin.css.utils.ImageUtils;
import cn.zhudai.zin.css.utils.LogUtils;
import cn.zhudai.zin.css.utils.SpannableUtils.SpannableHelper;
import cn.zhudai.zin.css.utils.ToastUtils;

public class IDCardActivity extends BaseActivity {

    @Bind(R.id.tv_tip)
    TextView tvTip;
    @Bind(R.id.iv_IDimg_face)
    ImageView ivIDimgFace;
    @Bind(R.id.iv_IDimg_back)
    ImageView ivIDimgBack;
    @Bind(R.id.bt_next)
    Button btNext;
    private String idCardImgPathFace;
    private String idCardImgPathBack;
    private Context mContext;
    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idcard);
        ButterKnife.bind(this);
        mContext=this;

        initView();
    }

    private void initView() {
        setTip();
    }


    private void setTip() {
        String content = "您的图片仅用于审核，助贷网将为您严格保密\n请上传身份证照片";
        SpannableString spannableString = SpannableHelper
                .create(content)
                .setTextAbsoluteSize("您的图片仅用于审核，助贷网将为您严格保密\n", 16, true)
                .setTextAbsoluteSize("请上传身份证照片", 18, true)
                .setTextStyleSpan("请上传身份证照片", Typeface.BOLD_ITALIC)
                .getmSpannableString();

        tvTip.setText(spannableString);
    }

    @Override
    public void setToolbar(Toolbar toolbar) {

    }

    @Override
    public String setTitleInCenter() {
        return "实名认证";
    }
    @OnClick(R.id.bt_next)
    void nextAction(){
        if (TextUtils.isEmpty(idCardImgPathFace)||TextUtils.isEmpty(idCardImgPathBack)){
            ToastUtils.showToast(mContext,"请上传身份证照片");
        }else {
            Intent lastIntent=getIntent();
            String name = lastIntent.getExtras().getString("NAME");
            String city = lastIntent.getExtras().getString("CITY");
            String headimgpath = lastIntent.getExtras().getString("HEADIMGPATH");
            Intent intent = new Intent(IDCardActivity.this, UplaodQualificationsActivity.class);
            intent.putExtra("NAME",name);
            intent.putExtra("CITY",city);
            intent.putExtra("HEADIMGPATH",headimgpath);
            intent.putExtra("IMGFACE",idCardImgPathFace);
            intent.putExtra("IMGBACK",idCardImgPathBack);
            startActivity(intent);
        }
    }
    @OnClick(R.id.iv_IDimg_face)
    void selectIdImgFaceAction(){
        selectIdCardFaceImg();
    }
    @OnClick(R.id.iv_IDimg_back)
    void selectIdImgBackAction(){
        selectIdCardBackImg();
    }
    private void selectIdCardFaceImg(){
        final GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                if (resultList != null) {
                    idCardImgPathFace=resultList.get(0).getPhotoPath();
                    ImageUtils.setImg4ViewFromLocal(ivIDimgFace,idCardImgPathFace);

                    LogUtils.i("图片路径="+idCardImgPathFace);
                }

            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
            }
        };
        showSelectImgDialog(mOnHanlderResultCallback);
    }
    private void selectIdCardBackImg(){
        final GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                if (resultList != null) {
                    idCardImgPathBack=resultList.get(0).getPhotoPath();
                    ImageUtils.setImg4ViewFromLocal(ivIDimgBack,idCardImgPathBack);

                    LogUtils.i("图片路径="+idCardImgPathBack);
                }

            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
            }
        };
        showSelectImgDialog(mOnHanlderResultCallback);
    }
    private void showSelectImgDialog(final GalleryFinal.OnHanlderResultCallback callback){

        String[] items = new String[]{"打开相册","拍照","取消"};
        ListAdapter itemlist = new ArrayAdapter(this, R.layout.dialog_item, items);
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setAdapter(itemlist,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //which为从0开始的
                switch (which){
                    case 0:
                        GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, MyApplication.gFunctionConfig, callback);
                        break;
                    case 1:
                        GalleryFinal.openCamera(REQUEST_CODE_CAMERA, MyApplication.gFunctionConfig, callback);
                        break;
                    case 2:
                        break;
                }
            }
        })
        .show();

    }

}
