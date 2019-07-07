    package com.example.piyush.flyingfish;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FlyingFishView extends View {

    //private Bitmap fish;
    private Bitmap background;
    private Bitmap life[] = new Bitmap[2];
    private Bitmap fish[] = new Bitmap[2];

    private int fishX = 10;
    private int fishY;
    private int fishSpeed;

    private int yellowX, yellowY, yellowSpeed = 16;
    private Paint yellowPaint = new Paint();

    private int score, lifeCounter;

    private int greenX, greenY, greenSpeed = 20;
    private Paint greenPaint = new Paint();

    private int redX, redY, redSpeed = 20;
    private Paint redPaint = new Paint();

    private Boolean touch = false;

    private int canvasWidth, canvasHeight;

    private Paint scorePaint = new Paint();

    public FlyingFishView(Context context) {
        super(context);

        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish2);

        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);


        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);

        fishY = 550;
        score = 0;
        lifeCounter = 3;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        canvas.drawBitmap(background, 0, 0, null);

        int minFishY = fish[0].getHeight();
        int maxFishY = canvasHeight - fish[0].getHeight() * 3;

        fishY = fishY + fishSpeed;

        if (fishY < minFishY) {
            fishY = minFishY;
        }

        if (fishY > maxFishY) {
            fishY = maxFishY;
        }
        fishSpeed = fishSpeed + 2;

        if (touch) {
            canvas.drawBitmap(fish[1], fishX, fishY, null);
            touch = false;
        } else {
            canvas.drawBitmap(fish[0], fishX, fishY, null);
        }

        yellowX = yellowX - yellowSpeed;

        if (hitBallChecker(yellowX, yellowY)) {
            score = score + 10;
            yellowX = -100;
        }

        if (yellowX < 0) {
            yellowX = canvasWidth + 21;
            yellowY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(yellowX, yellowY, 25, yellowPaint);


        greenX = greenX - greenSpeed;

        if (hitBallChecker(greenX, greenY)) {
            score = score + 20;
            greenX = -100;
        }

        if (greenX < 0) {
            greenX = canvasWidth + 21;
            greenY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(greenX, greenY, 25, greenPaint);


        redX = redX - redSpeed;

        if (hitBallChecker(redX, redY)) {
            score = score - 10;
            lifeCounter--;
            redX = -100;

            if (lifeCounter == 0) {
                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();

                Intent i=new Intent(getContext(),GameOverActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("score",score);
                getContext().startActivity(i);
            }
        }

        if (redX < 0) {
            redX = canvasWidth + 21;
            redY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(redX, redY, 25, redPaint);

        if(score>100 && score<200)
        {
            yellowSpeed=17;
            redSpeed=21;
            greenSpeed=21;
        }
        if(score>200 && score<300)
        {
            yellowSpeed=18;
            redSpeed=22;
            greenSpeed=22;
        }
        if(score>300 && score<400)
        {
            yellowSpeed=19;
            redSpeed=23;
            greenSpeed=23;
        }
        if(score>400 && score<500)
        {
            yellowSpeed=20;
            redSpeed=24;
            greenSpeed=24;
        }
        if(score>500)
        {
            yellowSpeed=21;
            redSpeed=25;
            greenSpeed=25;
        }
        canvas.drawText("Score: " + score, 20, 60, scorePaint);

        for (int i = 0; i <3;i++)
        {

            int x=(int) (580+life[0].getWidth()*1.5*i);
            int y=30;

            if(i<lifeCounter)
            {
                canvas.drawBitmap(life[0], x, y, null);
            }
            else
            {
                canvas.drawBitmap(life[1], x, y, null);
            }
        }
    }

    public boolean hitBallChecker(int x, int y) {
        if (fishX < x && x < (fishX + fish[0].getWidth()) && fishY < y && y < (fishY + fish[0].getHeight())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touch = true;

            fishSpeed = -22;
        }
        return true;
    }
}
