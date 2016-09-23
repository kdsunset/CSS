package cn.zhudai.zin.uiokhttp.callback;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executors;

import cn.zhudai.zin.uiokhttp.parser.Parser;
import okhttp3.Call;
import okhttp3.Response;

/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-10-08
 * Time: 11:02
 */
public class FileCallback<T> implements okhttp3.Callback {
    private Parser<T> mParser;
    private static final int CALLBACK_SUCCESSFUL=0x01;
    private static final int CALLBACK_FAILED=0x02;
    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir;
    /**
     * 目标文件存储的文件名
     */
    private String destFileName;


    static class UIHandler<T> extends Handler{
        private WeakReference mWeakReference;
        public UIHandler(FileCallback<T> callback){
            super(Looper.getMainLooper());
            mWeakReference=new WeakReference(callback);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CALLBACK_SUCCESSFUL: {
                    T t = (T) msg.obj;
                    FileCallback callback = (FileCallback) mWeakReference.get();
                    if (callback != null) {
                        callback.onResponse(t);
                    }
                    break;
                }
                case CALLBACK_FAILED: {
                    IOException e = (IOException) msg.obj;
                    FileCallback callback = (FileCallback) mWeakReference.get();
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

    public FileCallback(Parser<T> mParser) {
        if (mParser == null) {
            throw new IllegalArgumentException("Parser can't be null");
        }
        this.mParser = mParser;
    }
    public FileCallback() {

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
            File parseResult = saveFile(response);
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
    public File saveFile(Response response  ) throws IOException
    {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try
        {
            is = response.body().byteStream();
            final long total = response.body().contentLength();

            long sum = 0;

            File dir = new File(destFileDir);
            if (!dir.exists())
            {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1)
            {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;
                Executors.newCachedThreadPool().execute(new Runnable()
                {
                    @Override
                    public void run()
                    {

                        inProgress(finalSum * 1.0f / total,total);
                    }
                });
            }
            fos.flush();

            return file;

        } finally
        {
            try
            {
                response.body().close();
                if (is != null) is.close();
            } catch (IOException e)
            {
            }
            try
            {
                if (fos != null) fos.close();
            } catch (IOException e)
            {
            }

        }
    }
}
