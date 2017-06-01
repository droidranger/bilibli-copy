package com.ranger.xyg.xygapp.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by xyg on 2017/5/18.
 */

public class FileUtils {

    private static final String BASE_PATH = "/mnt/sdcard/xygCache";

    private void backupApp(String path, String outName) throws IOException {
        File in = new File(path);
        File basePath = new File(BASE_PATH);
        if (!basePath.exists()) {
            basePath.mkdir();
        }
        File out = new File(basePath, outName + ".apk");
        if (!out.exists()) {
            out.createNewFile();
        }
        FileInputStream fis = new FileInputStream(in);
        FileOutputStream fos = new FileOutputStream(out);

        int count;
        byte[] buffer = new byte[256 * 1024];
        while ((count = fis.read(buffer)) > 0) {
            fos.write(buffer, 0, count);
        }

        fis.close();
        fos.flush();
        fos.close();
    }
}
