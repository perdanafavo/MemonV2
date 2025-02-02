package com.example.projectalpha.Helpers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.MediaStore;

import com.example.projectalpha.Config.ENVIRONMENT;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ImageHandle {

    private final String FILE_NAME_IMAGE = "image.jpeg";

    private Activity activity;

    public ImageHandle(Activity activity) {
        this.activity = activity;
    }

    public Bitmap bipmapCompress(Bitmap bitmap){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        String dateTime = sdf.format(Calendar.getInstance().getTime());
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas cs = new Canvas(mutableBitmap);
        Paint tPaint = new Paint();
        tPaint.setTextSize(10);
        tPaint.setColor(Color.RED);
        tPaint.setStyle(Paint.Style.FILL);
        cs.drawBitmap(mutableBitmap, 0f, 0f, null);
        float height = tPaint.measureText("yY");
        cs.drawText(dateTime, 85f, height, tPaint);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        return BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
    }

    public Bitmap bipmapCompress(Bitmap bitmap, int quality){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
        return BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
    }

    public File fileCreate(Bitmap bitmap){
        try {
            File file = new File(activity.getApplicationContext().getCacheDir(), FILE_NAME_IMAGE);
            file.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] bitmapdata = bos.toByteArray();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public File fileCreate(Bitmap bitmap, String name){
        try {
            File file = new File(activity.getApplicationContext().getCacheDir(), name);
            file.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] bitmapdata = bos.toByteArray();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public MultipartBody.Part convertMultiparFile(File file) {
        RequestBody reqFile = RequestBody.create(file, MediaType.parse("image/*"));
        return MultipartBody.Part.createFormData("photo", file.getName(), reqFile);
    }

    public void takeImage(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(takePictureIntent, ENVIRONMENT.REQUEST_IMAGE_CAPTURE);
        }
    }

    public void takeImage(int REQUEST_CODE){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(takePictureIntent, REQUEST_CODE);
        }
    }

    public void directoryImage(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.addCategory(Intent.CATEGORY_OPENABLE);
        photoPickerIntent.setType("image/*");
        activity.startActivityForResult(Intent.createChooser(photoPickerIntent, "PILIH FOTO"), ENVIRONMENT.REQUEST_LOAD_IMG);
    }
}
