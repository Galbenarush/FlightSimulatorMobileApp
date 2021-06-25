package com.example.flightsimulatormobileapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import androidx.annotation.Nullable;

import com.example.flightsimulatormobileapp.attributes.OnChange;

public class Joystick extends View {
    private Paint paint;
    private int outerCX;
    private int outerCY;
    private double outerCR;
    private int joystickCircleX;
    private int joystickCircleY;
    private double joystickCircleRadius;
    private int h;
    private int w;
    public OnChange onChange;

    public Joystick(Context context) {
        super(context);
        this.paint = new Paint();
    }

    public Joystick(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.paint = new Paint();
    }

    public Joystick(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.paint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int old_w, int old_h) {
        // super constructor
        super.onSizeChanged(w, h, old_w, old_h);
        this.w = w;
        this.h = h;

        // evaluate inner x,y,r
        this.joystickCircleRadius = 0.15f * Math.min(w, h);
        this.joystickCircleX = this.w / 2;
        this.joystickCircleY = this.h / 2;

        // evaluate outer x,y,r
        this.outerCR = 0.4 * Math.min(w, h);
        this.outerCX = this.w / 2;
        this.outerCY = this.h / 2;
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        //Getting the X and Y coordinates of the touch input.
        int touchX = (int) event.getX();
        int touchY = (int) event.getY();
        int eventaction = event.getAction();

        switch (eventaction) {
            //The user moved the joystick.
            case MotionEvent.ACTION_MOVE:
                moveOnTouch(touchX, touchY);
                this.invalidate();
                break;

            //The user let go of the joystick
            case MotionEvent.ACTION_UP:
                this.joystickCircleY = this.outerCY;
                this.joystickCircleX = this.outerCX;
                try {
                    onChange.onChange(0,0);
                } catch (Exception e) {

                }
                this.invalidate();
                break;
        }
        return true;
    }

    /*** SIN function ***/
    private double sin(double angle) {
        return Math.sin(Math.toRadians(angle));
    }

    /*** MOVE action - update aileron and elevator values***/
    private void moveOnTouch(int x, int y) {

        double dis = Math.sqrt(Math.pow(x - this.outerCX, 2) + Math.pow(y - this.outerCY, 2));
        if (dis <= this.outerCR) { // inside
            this.joystickCircleX = x;
            this.joystickCircleY = y;
        } else { // outside
            if (x == this.outerCX) {
                if (y > this.outerCY) {
                    this.joystickCircleY = this.outerCY + (int)this.outerCR;
                    this.joystickCircleX = this.outerCX;
                } else {
                    this.joystickCircleY = this.outerCY - (int)this.outerCR;
                    this.joystickCircleX = this.outerCX;
                }
            } else {
                double angle = calcDeg(x, y);
                if (angle <= 90) {
                    this.joystickCircleY = (int)(this.outerCY + this.outerCR * sin(angle));
                    this.joystickCircleX = findX(this.joystickCircleY, true);
                } else if (angle <= 180) {
                    this.joystickCircleY = (int)(this.outerCY + this.outerCR * sin(angle));
                    this.joystickCircleX = findX(this.joystickCircleY, false);
                } else if (angle <= 270) {
                    this.joystickCircleY = (int)(this.outerCY - this.outerCR * sin(angle-180));
                    this.joystickCircleX = findX(this.joystickCircleY, false);
                } else {
                    this.joystickCircleY = (int)(this.outerCY - this.outerCR * sin(360-angle));
                    this.joystickCircleX = findX(this.joystickCircleY, true);
                }
            }
        }

        //Activating the onChange method to notify that the values have changed.
        try {
            double a = (this.joystickCircleX - this.outerCX) / (this.outerCR);
            double e = (this.joystickCircleY - this.outerCY) / (this.outerCR);
            e = -1 * e;
            onChange.onChange(a,e);
        } catch (Exception e) {
            System.out.println("error on change");
        }
    }

    /*** FIND the correct value of X ***/
    private int findX(int y, boolean isPos) {
        double x = Math.sqrt(Math.abs(Math.pow(this.outerCR, 2) - Math.pow(y - this.outerCY, 2)));
        if (isPos) {
            x += this.outerCX;
        } else {
            x = this.outerCX - x;
        }
        return (int)x;
    }

    /*** CALCULATE degree for movement ***/
    private double calcDeg(int x, int y) {
        double incline, d;
        incline = (double)(this.outerCY - y) / (double)(this.outerCX - x);
        d = Math.toDegrees(Math.atan(Math.abs(incline)));
        if (x < this.outerCX && y > this.outerCY) {
            return 180 - d;
        }
        if (x < this.outerCX && y < this.outerCY) {
            return 180 + d;
        }
        if (x > this.outerCX && y < this.outerCY) {
            return 360 - d;
        }
        return d;
    }

    /*** DRAW joystick***/
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        // Draw inner circle
        paint.setColor(Color.parseColor("#FFFFFF"));
        canvas.drawCircle(this.joystickCircleX, this.joystickCircleY,
                (float)this.joystickCircleRadius, paint);

        // Draw outer circle
        paint.setColor(Color.parseColor("#000000"));
        canvas.drawCircle(this.outerCX, this.outerCY,
                (float)this.outerCR, paint);

    }

}