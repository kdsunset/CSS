package cn.zhudai.zin.css.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.zhudai.zin.css.R;
import cn.zhudai.zin.css.application.MyApplication;
import cn.zhudai.zin.css.base.BaseActivity;
import cn.zhudai.zin.css.ui.CustomDialog;
import cn.zhudai.zin.css.utils.AssetsUtils;
import cn.zhudai.zin.css.utils.ImageUtils;
import cn.zhudai.zin.css.utils.LogUtils;
import cn.zhudai.zin.css.utils.SpannableUtils.SpannableHelper;
import cn.zhudai.zin.css.utils.UIUtils;
import de.hdodenhof.circleimageview.CircleImageView;

public class UploadInfoActivity extends BaseActivity {

    @Bind(R.id.tv_tip)
    TextView tvTip;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_city)
    EditText etCity;
    @Bind(R.id.iv_uplaodImg)
    CircleImageView ivUplaodImg;
    @Bind(R.id.bt_next)
    Button btNext;
    private String mName;
    private String mCity;
    private String mHeadImgPath;
    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_info);
        ButterKnife.bind(this);
        intView();
    }

    private void intView() {
        setTvTip();
        showDialog();


    }

    @Override
    public void setToolbar(Toolbar toolbar) {

    }

    @Override
    public String setTitleInCenter() {
        return "实名认证";
    }



    @OnClick(R.id.iv_uplaodImg)
    void upLoadAction() {
       selectImg();
    }

    @OnClick(R.id.et_city)
    void showCityAction() {
        cityPicker();
    }
    @OnClick(R.id.bt_next)
    void nextAction(){
        attemptNext();
    }
    private void selectImg(){
        final GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                if (resultList != null) {
                    mHeadImgPath=resultList.get(0).getPhotoPath();
                    ImageUtils.setImg4ViewFromLocal(ivUplaodImg,mHeadImgPath);

                    LogUtils.i("图片路径="+mHeadImgPath);
                }

            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                Toast.makeText(UploadInfoActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        };
        String[] items = new String[]{"打开相册","拍照","取消"};
        ListAdapter itemlist = new ArrayAdapter(this, R.layout.dialog_item, items);
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setAdapter(itemlist,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //which为从0开始的
                switch (which){
                    case 0:
                        GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, MyApplication.gFunctionConfig, mOnHanlderResultCallback);
                        break;
                    case 1:
                        GalleryFinal.openCamera(REQUEST_CODE_CAMERA, MyApplication.gFunctionConfig, mOnHanlderResultCallback);
                        break;
                    case 2:
                        break;
                }
            }
        })
                .show();
    }
    private void getInputInfo(){
        mName= UIUtils.getEditTextContent(etName);
        mCity=UIUtils.getEditTextContent(etCity);
    }
    private void attemptNext(){
        getInputInfo();
        boolean cancel = false;
        View focusView = null;
        etName.setError(null);
        etCity.setError(null);
        // Check for a valid email address.
        if (TextUtils.isEmpty(mName)) {            //如果账号为空
            etName.setError("请输入姓名！");
            focusView = etName;
            cancel = true;
        }
        if (TextUtils.isEmpty(mCity)) {            //如果账号为空
            etCity.setError("请选择工作城市！");
            focusView = etCity;
            cancel = true;
        }
        if (cancel) {

            focusView.requestFocus();
        } else {

           //当姓名和城市非空（图片未确定是否必须），跳转到下一步
            Intent intent = new Intent(UploadInfoActivity.this, IDCardActivity.class);
            intent.putExtra("NAME",mName);
            intent.putExtra("CITY",mCity);
            intent.putExtra("headimgpath",mHeadImgPath);
            startActivity(intent);
        }
    }
    private void setTvTip() {
        String content = "您的图片仅用于审核，助贷网将为您严格保密\n" +
                "请确认个人信息（提交后不可修改）";
        SpannableString spannableString = SpannableHelper
                .create(content)
                .setTextAbsoluteSize("您的图片仅用于审核，助贷网将为您严格保密\n", 16, true)
                .setTextAbsoluteSize("请确认个人信息（提交后不可修改）", 18, true)
                .setTextStyleSpan("请确认个人信息（提交后不可修改）", Typeface.BOLD_ITALIC)
                .getmSpannableString();

        tvTip.setText(spannableString);
    }

    private void cityPicker() {
        ArrayList<AddressPicker.Province> data = new ArrayList<AddressPicker.Province>();
        String json = AssetsUtils.readText(this, "city.json");
        data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
        AddressPicker picker = new AddressPicker(this, data);
        // picker.setSelectedItem("贵州", "贵阳", "花溪");
        //picker.setHideProvince(true);//加上此句举将只显示地级及县级
        picker.setHideCounty(true);//加上此句举将只显示省级及地级
        picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
            @Override
            public void onAddressPicked(String province, String city, String county) {
                //showToast(province + city + county);
                if ("上海市".equals(city) || "天津市".equals(city) || "重庆市".equals(city) || "北京市".equals(city) ||
                        "上海".equals(city) || "天津".equals(city) || "重庆".equals(city) || "北京".equals(city)) {
                    etCity.setText(city);
                    mCity = city;
                } else {

                    etCity.setText(province + city);
                    mCity = province + city;
                }
            }
        });
        picker.show();
    }
    public void showDialog(){
        CustomDialog.Builder builder = new CustomDialog.Builder(this)
                .setMessage("您尚未完成实名认证，请补充资料，" +
                        "通过审核并充值后方可接收客户数据！")
                .setTitle("提示")
                .setNegativeButton("现在去完善", new DialogInterface.OnClickListener() {
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
}
