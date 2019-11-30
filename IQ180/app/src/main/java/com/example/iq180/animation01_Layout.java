package com.example.iq180;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.View;

public class animation01_Layout extends View {
    Paint b1,b2,b3,b4;
    int x1,y2,cx1,cy1,cx2,cy2,cx3,cy3;
    int y1=0;
    int sweep = 0;
    int sweep2=0;
    int sweep3=0;
    public animation01_Layout(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        b1 = new Paint();
        b1.setColor(Color.BLACK);
        b1.setStyle(Paint.Style.STROKE);
        b1.setStrokeWidth(40);

        b2 = new Paint();
        b2.setColor(Color.BLACK);
        b2.setStyle(Paint.Style.STROKE);
        b2.setStrokeWidth(50);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPaint(paint);
        paint.setColor(Color.parseColor("#790606"));
        paint.setTypeface(Typeface.SERIF);
        paint.setTextSize(300);

        x1=450;
        y2=500;
        cx1=x1+205;
        cy1=y2+75;
        cx2=cx1;
        cy2=y2+275;
        cx3=x1+490;
        cy3=y2+200;

        canvas.drawText("IQ",100,890,paint);

        if(y1<400) {
            Rect rect = new Rect();
            rect.set(x1, y2, x1+10, y2+y1);
            canvas.drawRect(rect, b1);
            y1=y1+6;
        }



        if(y1>395&&sweep<360){
            Rect rect = new Rect();
            rect.set(x1, y2, x1+10, y2+400);
            canvas.drawRect(rect, b1);

            RectF oval= new RectF();
            oval.set(cx1-75,cy1-75,cx1+75,cy1+75);
            Path myPath = new Path();
            myPath.arcTo(oval, 90, -(float) sweep, true);
            canvas.drawPath(myPath,b2);
            sweep+=7;

        }



        if(sweep>350&&sweep2<360){
            Rect rect = new Rect();
            rect.set(x1, y2, x1+10, y2+400);
            canvas.drawRect(rect, b1);

            RectF oval= new RectF();
            oval.set(cx1-75,cy1-75,cx1+75,cy1+75);
            Path myPath = new Path();
            myPath.arcTo(oval, 90, -(float) 359.999, true);
            canvas.drawPath(myPath,b2);

            RectF oval2= new RectF();
            oval2.set(cx2-95,cy2-125,cx2+95,cy2+125);
            Path myPath2 = new Path();
            myPath2.arcTo(oval2, 270, (float) sweep2, true);
            canvas.drawPath(myPath2,b2);
            sweep2+=5;

        }


        if(sweep2>350&&sweep3<360){
            Rect rect = new Rect();
            rect.set(x1, y2, x1+10, y2+400);
            canvas.drawRect(rect, b1);

            RectF oval= new RectF();
            oval.set(cx1-75,cy1-75,cx1+75,cy1+75);
            Path myPath = new Path();
            myPath.arcTo(oval, 90, -(float) 359.999, true);
            canvas.drawPath(myPath,b2);

            RectF oval2= new RectF();
            oval2.set(cx2-95,cy2-125,cx2+95,cy2+125);
            Path myPath2 = new Path();
            myPath2.arcTo(oval2, 270, (float) 359.969, true);
            canvas.drawPath(myPath2,b2);

            RectF oval3= new RectF();
            oval3.set(cx3-120,cy3-200,cx3+120,cy3+200);
            Path myPath3 = new Path();
            myPath3.arcTo(oval3, 180, (float) sweep3, true);
            canvas.drawPath(myPath3,b2);
            sweep3+=5;
        }

        if(sweep3>355) {
            Rect rect = new Rect();
            rect.set(x1, y2, x1+10, y2+400);
            canvas.drawRect(rect, b1);

            RectF oval= new RectF();
            oval.set(cx1-75,cy1-75,cx1+75,cy1+75);
            Path myPath = new Path();
            myPath.arcTo(oval, 90, -(float) 359.999, true);
            canvas.drawPath(myPath,b2);

            RectF oval2= new RectF();
            oval2.set(cx2-95,cy2-125,cx2+95,cy2+125);
            Path myPath2 = new Path();
            myPath2.arcTo(oval2, 270, (float) 359.969, true);
            canvas.drawPath(myPath2,b2);

            RectF oval3= new RectF();
            oval3.set(cx3-120,cy3-200,cx3+120,cy3+200);
            Path myPath3 = new Path();
            myPath3.arcTo(oval3, 180, (float) 359.999, true);
            canvas.drawPath(myPath3,b2);
        }




        invalidate();
    }
}
