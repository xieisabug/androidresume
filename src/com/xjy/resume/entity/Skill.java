package com.xjy.resume.entity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.xjy.resume.R;
import com.xjy.resume.SkillActivity;
import com.xjy.resume.view.Ring;

public class Skill extends Ring {

    private final int ANIMATE_SPEED = 2;
    private final int START_ALPHA = 200;
    private final int START_RADIUS = 4;
    private static final int RING_WIDTH = 25;
    private static final int UI_WIDTH = 250;
    public static final int BLUE = 1;
    public static final int RED = 2;
    public static final int GREEN = 3;
    public static final int PINK = 4;
    public static final int YELLOW = 5;
    public static final int CLOUD = Color.parseColor("#ecf0f1");

    private int id = 0;
    private Context context = null;
    private boolean touched = false;
    private int temp;

    public int ringAlpha = START_ALPHA;
    public int translateRadius = START_RADIUS;

    public Skill(Context context, String name, int percent, int styleType) {
        super(context);
        this.context = context;
        switch (styleType) {
            case BLUE:
                init(name, Color.WHITE, RING_WIDTH, Color.parseColor("#004884"), percent);
                break;
            case GREEN:
                init(name, Color.WHITE, RING_WIDTH, Color.parseColor("#36B77D"), percent);
                break;
            case RED:
                init(name, Color.WHITE, RING_WIDTH, Color.parseColor("#ED1654"), percent);
                break;
            case PINK:
                init(name, Color.WHITE, RING_WIDTH, Color.parseColor("#EB9F9F"), percent);
                break;
            case YELLOW:
                init(name, Color.WHITE, RING_WIDTH, Color.parseColor("#E2A937"), percent);
                break;
        }

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(UI_WIDTH,UI_WIDTH);
        this.setLayoutParams(lp);
    }

    public Skill(Context context, int id, String name, int percent) {
        super(context);
        this.id = id;
        this.context = context;

        if(percent >= 90) {
            init(name, Color.WHITE, RING_WIDTH, Color.parseColor("#ED1654"), percent);
        } else if (percent >= 80) {
            init(name, Color.WHITE, RING_WIDTH, Color.parseColor("#EB9F9F"), percent);
        } else if (percent >= 75) {
            init(name, Color.WHITE, RING_WIDTH, Color.parseColor("#E2A937"), percent);
        } else if (percent >= 65) {
            init(name, Color.WHITE, RING_WIDTH, Color.parseColor("#36B77D"), percent);
        } else {
            init(name, Color.WHITE, RING_WIDTH, Color.parseColor("#004884"), percent);
        }
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(UI_WIDTH,UI_WIDTH);
        this.setLayoutParams(lp);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
//            Log.i("Ring", "Action Up : " + id);
            Log.i("Ring", "Action Up Speed: " + ANIMATE_SPEED);
            touched = true;
            ringAlpha = START_ALPHA;
            translateRadius = START_RADIUS;
            invalidate();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(touched) {
            Paint p = new Paint();
            p.setStyle(Paint.Style.FILL);
            p.setColor(skillColor);
            p.setStrokeWidth(1);
            p.setAlpha(ringAlpha);
            int w = canvas.getClipBounds().width();//获取高度
            int c = w / 2;//计算中心点
            int inner = Math.round(c - this.ringWidth - translateRadius); //设置内圆半径
            canvas.drawCircle(c,c, inner, p);

            translateRadius -= ANIMATE_SPEED;
            ringAlpha -= ANIMATE_SPEED * 5;

            if(inner >= UI_WIDTH/2 || ringAlpha <= 0) {
                touched = false;
                Intent toSkill = new Intent(context, SkillActivity.class);
                toSkill.putExtra("id", id);
                context.startActivity(toSkill);
            }

            postInvalidateDelayed(20);
        }
    }
}
