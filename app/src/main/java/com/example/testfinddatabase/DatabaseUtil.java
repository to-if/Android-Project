package com.example.testfinddatabase;

import android.annotation.SuppressLint;
import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseUtil {
    @SuppressLint("SdCardPath")
    public static void packDataBase(Context context) {
        String DB_PATH = "/data/data/com.example.testfinddatabase/databases/";
        String DB_NAME = "test.db";

        //检测 sqlite 数据库文件是否存在
        if (!(new File(DB_PATH+DB_NAME)).exists()) {
            File f = new File(DB_PATH);
            if (!f.exists()) {
                f.mkdir();
            }
            try {
                InputStream is = context.getAssets().open(DB_NAME);
                OutputStream os = new FileOutputStream(DB_PATH+DB_NAME);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer))>0) {
                    os.write(buffer,0,length);
                }
                os.flush();
                os.close();
                is.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
