package cn.zhudai.zin.uiokhttp.callback;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;
import java.lang.ref.WeakReference;

import cn.zhudai.zin.uiokhttp.parser.Parser;
import okhttp3.Call;
import okhttp3.Response;

/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-10-08
 * Time: 11:02
 */
public class Callback<T> implements okhttp3.Callback {
    private Parser<T> mParser;
    private static final int CALLBACK_SUCCESSFUL=0x01;
    private static final int CALLBACK_FAILED=0x02;



    static class UIHandler<T> extends Handler{
        private WeakReference mWeakReference;
        public UIHandler(cn.zhudai.zin.uiokhttp.callback.Callback<T> callback){
            super(Looper.getMainLooper());
            mWeakReference=new WeakReference(callback);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CALLBACK_SUCCESSFUL: {
                    T t = (T) msg.obj;
                    cn.zhudai.zin.uiokhttp.callback.Callback callback = (cn.zhudai.zin.uiokhttp.callback.Callback) mWeakReference.get();
                    if (callback != null) {
                        callback.onResponse(t);
                    }
                    break;
                }
                case CALLBACK_FAILED: {
                    IOException e = (IOException) msg.obj;
                    cn.zhudai.zin.uiokhttp.callback.Callback callback = (cn.zhudai.zin.uiokhttp.callback.Callback) mWeakReference.get();
                    if (callback != null) {
                        callback.onFailure(e);
                    }
                    break;
                }
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }
    private Handler mHandler=new UIHandler(this);

    public Callback(Parser<T> mParser) {
        if (mParser == null) {
            throw new IllegalArgumentException("Parser can't be null");
        }
        this.mParser = mParser;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Message message=Message.obtain();
        message.what=CALLBACK_FAILED;
        message.obj=e;
        mHandler.sendMessage(message);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful()) {
            T parseResult = mParser.parse(response);
            Message message=Message.obtain();
            message.what=CALLBACK_SUCCESSFUL;
            message.obj=parseResult;
            mHandler.sendMessage(message);
        } else {
            Message message=Message.obtain();
            message.what=CALLBACK_FAILED;
            mHandler.sendMessage(message);
        }
    }


    public void onResponse(T t){

    }
    public  void onFailure(IOException e){

    }
    public void inProgress(float progress, long total){

    }
}
