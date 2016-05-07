package com.example.xwan.View.GlassAction;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Utils {
    public static Bitmap drawViewToBitmap(Bitmap dest, View view, int width, int height, int downSampling, Drawable drawable) {
        float scale = 1f / downSampling;
        int heightCopy = view.getHeight();
        view.layout(0, 0, width, height);
        int bmpWidth = (int)(width * scale);
        int bmpHeight = (int)(height * scale);
        if (dest == null || dest.getWidth() != bmpWidth || dest.getHeight() != bmpHeight) {
            dest = Bitmap.createBitmap(bmpWidth, bmpHeight, Bitmap.Config.ARGB_8888);
        }
        Canvas c = new Canvas(dest);
        drawable.setBounds(new Rect(0, 0, width, height));
        drawable.draw(c);
        if (downSampling > 1) {
            c.scale(scale, scale);
        }
        view.draw(c);
        view.layout(0, 0, width, heightCopy);
        // saveToSdCard(original, "original.png");
        return dest;
    }

    public static void saveToSdCard(Bitmap bmp, String fileName) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

            //you can create a new file name "test.jpg" in sdcard folder.
            File f = new File(Environment.getExternalStorageDirectory() + File.separator + fileName);
            f.createNewFile();
            //write the bytes in file
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());

            // remember close de FileOutput
            fo.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
