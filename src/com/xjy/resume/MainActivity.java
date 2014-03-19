package com.xjy.resume;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.*;
import android.widget.ImageView;

import java.io.InputStream;

public class MainActivity extends Activity {

    private Context context = this;
    //TODO 我的电话号码，由于要托管，所以托管在网上的时候电话号码就改为假的，编译的时候需要改回来
    public final String MY_PHONE_NUMBER = "12345678910";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView myPhoto = (ImageView) this.findViewById(R.id.myPhoto);
        //my_photo.setImageResource(R.drawable.ic_launcher);
        //得到Resources对象
        Resources r = getResources();
        WindowManager manage=getWindowManager();
        Display display=manage.getDefaultDisplay();
        int screenWidth=display.getWidth();
        //以数据流的方式读取资源
        InputStream is = null;
        if(screenWidth > 480) {
            is = r.openRawResource(R.drawable.my_photo);
        } else {
            is = r.openRawResource(R.drawable.my_photo1);
        }
        //获取到BitmapDrawable对象
        BitmapDrawable  bmpDraw = new BitmapDrawable(is);
        //获得Bitmap
        Bitmap bmp = bmpDraw.getBitmap();
        myPhoto.setImageBitmap(circlePic(bmp));
        myPhoto.setOnClickListener(new PhotoClickListener());
    }

    //将Bitmap处理为圆形的图片
    private Bitmap circlePic(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int r = width < height ? width/2:height/2;//圆的半径，取宽和高中较小的，以便于显示没有空白
        Bitmap outBitmap = Bitmap.createBitmap(r*2, r*2, Bitmap.Config.ARGB_8888);//创建一个刚好2r大小的Bitmap
        Canvas canvas = new Canvas(outBitmap);
        final int color =0xff424242;
        final Paint paint = new Paint();
        /**
         * 截取图像的中心的一个正方形,用于在原图中截取
         * 坐标如下：
         * 1.如果 w < h , 左上坐标(0, (h-w)/2) , 右上坐标(w, (h+w)/2)
         * 2.如果 w > h , 左上坐标((w-h)/2, 0) , 右上坐标((w+h)/2, h)
         */
        final Rect rect = new Rect( width < height ? 0 : (width-height)/2, width < height ? (height-width)/2 : 0,
                width < height ? width : (width+height)/2, (width < height ? (height+width)/2 : height));
        //创建一个直径大小的正方形，用于设置canvas的显示与设置画布截取
        final Rect rect2 = new Rect( 0, 0, r*2, r*2);
        //提高精度，用于消除锯齿
        final RectF rectF = new RectF(rect2);
        //下面是设置画笔和canvas
        paint.setAntiAlias(true);
        canvas.drawARGB(0,0,0,0);
        paint.setColor(color);
        //设置圆角，半径都为r,大小为rect2
        canvas.drawRoundRect(rectF, r, r, paint);
        //设置图像重叠时的显示方式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //绘制图像到canvas
        canvas.drawBitmap(bitmap, rect, rect2, paint);
        return outBitmap;
    }

    private class PhotoClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //TODO 此处未指定跳转的activity
            Intent i = new Intent(context, PagerActivity.class);
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 1, "电话联系我");
        menu.add(0, 2, 2, "短信通知我");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == 1) {
            //电话联系
            Intent intent = new Intent(Intent.ACTION_CALL, Uri
                    .parse("tel:"+ MY_PHONE_NUMBER));
            startActivity(intent);
        } else if(item.getItemId() == 2) {
            //短信通知
            Uri uri = Uri.parse("smsto:"+ MY_PHONE_NUMBER);
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra("sms_body", "你好，谢景扬同学。");
            startActivity(intent);
        }
        return true;
    }
}
