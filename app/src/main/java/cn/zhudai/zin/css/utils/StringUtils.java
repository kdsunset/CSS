package cn.zhudai.zin.css.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class StringUtils {
    /**
     *传入一个InputStream，返回其中的文本内容
     */
    public static String getString(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static int[] getIndexFromParentStr(String parent,String sub){
        int[] startAndEndIndex=new int[2];
        int start = parent.indexOf(sub);
        int end=start+sub.length();
        startAndEndIndex[0]=start;
        startAndEndIndex[1]=end;
        return startAndEndIndex;
    }

}
