package cn.zhudai.zin.uiokhttp.parser.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;

import cn.zhudai.zin.uiokhttp.callback.FileCallback;
import cn.zhudai.zin.uiokhttp.parser.Parser;
import okhttp3.Response;

/**
 * Created by admin on 2016/9/14.
 */
public class FileParser implements Parser<File>{


    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir;
    /**
     * 目标文件存储的文件名
     */
    private String destFileName;
    private FileCallback callback;

    @Override
    public File parse(Response response)   throws FileNotFoundException {
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

                        //inProgress(finalSum * 1.0f / total,total);
                    }
                });
            }
            fos.flush();

            return file;

        }catch (IOException e){
            e.printStackTrace();
        }
        finally
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
        return null;
    }

    public FileParser(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;

    }
}
