package com.xjy.resume.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.xjy.resume.R;

public class Ring extends View {
    public String skillText;
    public int skillTextColor;
    public float ringWidth;
    public int skillColor;
    @Deprecated
    public int noneColor;
    public int skillPoint;
    public static final int TEXT_SIZE = 25;

    public Paint paint;

    public Ring(Context context) {
        super(context);
    }

    @Deprecated
    public void init(String skillText, int skillTextColor, float ringWidth, int skillColor, int noneColor, int skillPoint) {
//        Log.i("Ring","init");
        this.skillText = skillText;
        this.skillTextColor = skillTextColor;
        this.ringWidth = ringWidth;
        this.skillColor = skillColor;
        this.noneColor = noneColor;
        this.skillPoint = skillPoint;
        this.paint = new Paint();
        this.paint.setAntiAlias(true); //消除锯齿
        this.paint.setStyle(Paint.Style.STROKE); //绘制空心圆
    }

    public void init(String skillText, int skillTextColor, float ringWidth, int skillColor, int skillPoint) {
        this.skillText = skillText;
        this.skillTextColor = skillTextColor;
        this.ringWidth = ringWidth;
        this.skillColor = skillColor;
        this.skillPoint = skillPoint;
        this.paint = new Paint();
        this.paint.setAntiAlias(true); //消除锯齿
        this.paint.setStyle(Paint.Style.STROKE); //绘制空心圆
    }

    public Ring(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Ring);
        if (a != null) {
            skillText = a.getString(R.styleable.Ring_skillText);
            skillColor = a.getColor(R.styleable.Ring_skillColor, Color.BLUE);
            noneColor = a.getColor(R.styleable.Ring_noneColor, Color.WHITE);
            ringWidth = a.getDimension(R.styleable.Ring_ringWidth, 10);
            skillPoint = a.getInteger(R.styleable.Ring_skillPoint, 100);
            skillTextColor = a.getColor(R.styleable.Ring_skillTextColor, Color.BLACK);
            this.paint = new Paint();
            this.paint.setAntiAlias(true); //消除锯齿
            this.paint.setStyle(Paint.Style.STROKE); //绘制空心圆
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        Log.i("Ring","onDraw");
        super.onDraw(canvas);
        int width = canvas.getClipBounds().width();//获取高度
        int center = width / 2;//计算中心点
        int innerCircle = Math.round(center - this.ringWidth - 4); //设置内圆半径
        int ringWidth = Math.round(this.ringWidth); //设置圆环宽度

        //绘制内圆
        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
        p.setColor(skillColor);
        p.setStrokeWidth(1);
        canvas.drawCircle(center, center, innerCircle-20, p);

        //绘制圆环
        this.paint.setColor(skillColor);
        //this.paint.setARGB(255, 212 ,225, 233);
        this.paint.setStrokeWidth(ringWidth);
        //创建绘制圆环的范围
        RectF oval = new RectF();
        oval.top = this.ringWidth / 2 + 2;
        oval.left = this.ringWidth / 2 + 2;
        oval.right = width - this.ringWidth / 2 - 2;
        oval.bottom = width - this.ringWidth / 2 - 2;
        //扫过的角度，也就是能力值，注意一定要用float计算，不然会算出0
        float sweepAngle = (skillPoint / 100f) * 360f;
        //绘制能力值
        canvas.drawArc(oval, 270, sweepAngle, false, this.paint);

        p.setTextSize(TEXT_SIZE);
        p.setColor(skillTextColor);
        p.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(skillText, 0, skillText.length(), center, center, p);
    }
}
