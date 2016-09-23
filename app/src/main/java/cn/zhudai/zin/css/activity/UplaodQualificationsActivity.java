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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.zhudai.zin.css.R;
import cn.zhudai.zin.css.application.MyApplication;
import cn.zhudai.zin.css.base.BaseActivity;
import cn.zhudai.zin.css.ui.CustomDialog;
import cn.zhudai.zin.css.utils.ImageUtils;
import cn.zhudai.zin.css.utils.LogUtils;
import cn.zhudai.zin.css.utils.SpannableUtils.SpannableHelper;
import cn.zhudai.zin.css.utils.ToastUtils;

public class UplaodQualificationsActivity extends BaseActivity {

    @Bind(R.id.tv_tip)
    TextView tvTip;
    @Bind(R.id.iv_IDimg_face)
    TextView ivIDimgFace;
    @Bind(R.id.iv_QualificationImg)
    ImageView ivQualificationImg;
    @Bind(R.id.bt_next)
    Button btNext;
    private Context mContext;
    private String qualifyImgPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uplaod_qualifications);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void setToolbar(Toolbar toolbar) {

    }

    @Override
    public String setTitleInCenter() {
        return "实名认证";
    }
    private void initView() {
        setTip();

    }
    @OnClick(R.id.iv_QualificationImg)
    void selectImgAction() {
        selectImg();
    }

    @OnClick(R.id.bt_next)
    void nextAction(){
        if (TextUtils.isEmpty(qualifyImgPath)){
            ToastUtils.showToast(mContext,"请上传资质图片");
        }else {
            upLoadInfo();
        }


    }

    private void upLoadInfo() {
        Intent lastIntent=getIntent();
        String name = lastIntent.getExtras().getString("NAME");
        String city = lastIntent.getExtras().getString("CITY");
        String headimgpath = lastIntent.getExtras().getString("HEADIMGPATH");
        String imgface = lastIntent.getExtras().getString("IMGFACE");
        String imgback = lastIntent.getExtras().getString("IMGBACK");
        List<String> imgPathList=new ArrayList<>();
        imgPathList.add(headimgpath);
        imgPathList.add(imgface);
        imgPathList.add(imgback);
        Intent intent=new Intent(mContext, MainActivity.class);
        intent.putExtra("TAG",1);
        startActivity(intent);
        /*CSSApi.uploadAuthenticationInfo(name,city,imgPathList,
                new Callback<UploadQualifyInfoResp>(new GsonParser<>(UploadQualifyInfoResp.class)){
            @Override
            public void onResponse(UploadQualifyInfoResp uploadQualifyInfoResp) {
                super.onResponse(uploadQualifyInfoResp);
                //上传信息完成后的操作
                boolean b=true;
                if (b){
                    startActivity(new Intent(mContext, MainActivity.class));
                }

            }

            @Override
            public void onFailure(IOException e) {
                super.onFailure(e);
            }
        });*/
    }

    private void setTip() {
        String content = "您的图片仅用于审核，助贷网将为您严格保密\n请上传资质证明";
        SpannableString spannableString = SpannableHelper
                .create(content)
                .setTextAbsoluteSize("您的图片仅用于审核，助贷网将为您严格保密\n", 16, true)
                .setTextAbsoluteSize("请上传资质证明", 18, true)
                .setTextStyleSpan("请上传资质证明", Typeface.BOLD_ITALIC)
                .getmSpannableString();

        tvTip.setText(spannableString);
    }

    public void showDialog(){
        CustomDialog.Builder builder = new CustomDialog.Builder(this)
                .setMessage("这个就是自定义的提示框")
                .hideHeader(true)
               // .setTitle("提示")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //设置你的操作事项
                    }
                });

        /*builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });*/

        builder.create().show();

    }
    private void selectImg(){
        final GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                if (resultList != null) {
                    qualifyImgPath=resultList.get(0).getPhotoPath();
                    ImageUtils.setImg4ViewFromLocal(ivQualificationImg,qualifyImgPath);

                    LogUtils.i("图片路径="+qualifyImgPath);
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
        final int REQUEST_CODE_GALLERY=0x01;
        final int REQUEST_CODE_CAMERA=0x01;
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
