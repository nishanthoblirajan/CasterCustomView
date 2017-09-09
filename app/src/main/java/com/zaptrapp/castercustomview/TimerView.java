package com.zaptrapp.castercustomview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;


public class TimerView extends View {
    private Paint backgroundPaint;
    private TextPaint numberPaint;

    //To track the startTime in the timer start function
    private long startTime;

    //add runnable to call the update timer function
    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            updateTimer();
        }
    };

    public TimerView(Context context) {
        super(context);
        //add the initialization for the views
        init();
    }

    public TimerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //add the initialization for the views
        init();
    }

    private void init(){
        backgroundPaint = new Paint();
        //setting color for the background paint
        backgroundPaint.setColor(Color.parseColor("#880E4F"));


        numberPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        //set text color
        numberPaint.setColor(ContextCompat.getColor(getContext(),android.R.color.white));
        //set text size to 64sp

        //to set it from dp use .density instead of scaled density
        numberPaint.setTextSize(64f * getResources().getDisplayMetrics().scaledDensity);
    }

    public void start(){
        //Get the current system time and set it to startTime
        startTime = System.currentTimeMillis();
        updateTimer();
    }

    public void stop(){
        //Reset the startTime to zero to indicate stop
        startTime = 0;
        removeCallbacks(updateRunnable);
    }

    private void updateTimer(){
        invalidate();
        //200ms
        postDelayed(updateRunnable,200L);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //get the width and height of the canvas into two variables
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        //Calculate the centre of the canvas
        float centerX = Math.round(canvasWidth*0.5f);
        float centerY = Math.round(canvasHeight*0.5f);

        //calculate the radius of the circle to be half the shorted dimension of the canvas
        //to make sure the circle stays within the bounds
        float radius = (canvasWidth<canvasHeight?canvasWidth:canvasHeight)*0.5f;



        //To center the text inside the circle
        //measure the text and
        // use both the measured width and the height to create offsets
        //so that we can move the bottom left position to middle the text

        //since the current time is in millis we need to multiply it by 0.001
        String number = String.valueOf((long)((System.currentTimeMillis()-startTime)*0.001));
        //the measureText returns the width of the text(here number)
        float textOffsetX = numberPaint.measureText(number)*0.5f;
        //.ascent -- the recommended distance above the baseline of single spaced text
        float textOffsetY = numberPaint.getFontMetrics().ascent * -0.4f;

        //draw the circle
        canvas.drawCircle(centerX,centerY,radius,backgroundPaint);
        //draw Text
        canvas.drawText(number,centerX - textOffsetX,centerY +textOffsetY,numberPaint);
    }


    
}
