// Gal Ben Arush

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
    // height and width
    private int h;
    private int w;
    private Paint paint;
    // outer circle settings
    private int outerCX;
    private int outerCY;
    private double outerCR;
    // inner circle settings
    private int joystickX;
    private int joystickY;
    private double joystickR;
    // onChange object
    public OnChange onChange;


    /*** Joystick constructors ***/
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


    /*** Function that evaluates on size changes ***/
    @Override
    protected void onSizeChanged(int w, int h, int old_w, int old_h) {
        // super constructor
        super.onSizeChanged(w, h, old_w, old_h);
        // set X,Y
        this.w = w;
        this.h = h;

        // evaluate inner x,y,r
        this.joystickR = 0.14f * Math.min(w, h);
        this.outerCR = 0.42 * Math.min(w, h);
        this.joystickX = this.outerCX = this.w / 2;
        this.joystickY = this.outerCY = this.h / 2;

    }


    /*** Function that set motion events***/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int touchX, touchY, getAction;
        touchX = (int) event.getX();
        touchY = (int) event.getY();
        getAction = event.getAction();

        switch (getAction) {
            // Touch motion event
            case MotionEvent.ACTION_MOVE:
                moveOnTouch(touchX, touchY);
                this.invalidate();
                break;

            // Release motion event
            case MotionEvent.ACTION_UP:
                // initialize joystick position
                this.joystickY = this.outerCY;
                this.joystickX = this.outerCX;
                try {
                    onChange.onChange(0,0); // back to center
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
            this.joystickX = x;
            this.joystickY = y;
        } else { // outside
            if (x == this.outerCX) {
                if (y > this.outerCY) {
                    this.joystickY = this.outerCY + (int)this.outerCR;
                } else {
                    this.joystickY = this.outerCY - (int)this.outerCR;
                }
                this.joystickX = this.outerCX;
            } else {
                double angle = calcDeg(x, y);
                if (angle <= 90) {
                    this.joystickY = (int)(this.outerCY + this.outerCR * sin(angle));
                    this.joystickX = findX(this.joystickY, true);
                } else if (angle <= 180) {
                    this.joystickY = (int)(this.outerCY + this.outerCR * sin(angle));
                    this.joystickX = findX(this.joystickY, false);
                } else if (angle <= 270) {
                    this.joystickY = (int)(this.outerCY - this.outerCR * sin(angle-180));
                    this.joystickX = findX(this.joystickY, false);
                } else {
                    this.joystickY = (int)(this.outerCY - this.outerCR * sin(360-angle));
                    this.joystickX = findX(this.joystickY, true);
                }
            }
        }

        //Activating the onChange method to notify that the values have changed.
        try {
            double a, e;
            a = (this.joystickX - this.outerCX) / (this.outerCR);
            e = ((this.joystickY - this.outerCY) / (this.outerCR)) * -1;
            onChange.onChange(a,e);
        } catch (Exception e) {
            System.out.println("error on change");
        }
    }

    /*** FIND the correct value of X ***/
    private int findX(int y, boolean isPos) {
        double x = Math.sqrt(Math.abs(Math.pow(this.outerCR, 2) - Math.pow(y - this.outerCY, 2)));
        if (isPos) {
            x = x + this.outerCX;
        } else {
            x = this.outerCX - x;
        }
        return (int)x;
    }

    /*** CALCULATE degree for movement ***/
    private double calcDeg(int x, int y) {
        double d;
        d = Math.toDegrees(Math.atan(Math.abs((double)(this.outerCY - y) / (double)(this.outerCX - x))));
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
        // Draw outer circle
        paint.setColor(Color.parseColor("#000000"));
        canvas.drawCircle(this.outerCX, this.outerCY,
                (float)this.outerCR, paint);
        // Draw inner circle
        paint.setColor(Color.parseColor("#FFFFFF"));
        canvas.drawCircle(this.joystickX, this.joystickY,
                (float)this.joystickR, paint);


    }

}