package challengeandroid2018.iteam.com.challengeandroid_2018.activities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import challengeandroid2018.iteam.com.challengeandroid_2018.R;
import util.AlertMessage;
import util.GifAnimationDrawable;
import util.GifImageView;
import util.ShakeDetector;
import util.TiltDetector;
import util.Util;

public class GameActivity extends AppCompatActivity {

    private ImageView imageViewCharacter;

    private ConstraintLayout constraintLayoutGameActivity;

    private ConstraintLayout constraintLayoutObstacleBird;

    private ConstraintLayout constraintLayoutObstacleWall;

    private ConstraintLayout constraintLayoutObstacleBump;

    private Context context;

    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    private TiltDetector mTiltDetector;
    private Sensor magnetometer;

    private ArrayList<View> viewObstacleList = new ArrayList<>();

    private boolean gameOver = false;

    private Timer timer = new Timer();
    private boolean adding = false;
    private ConstraintLayout constraintLayoutFloor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        this.context = this;
        // we get the graphical elements
        this.imageViewCharacter = (ImageView) findViewById(R.id.imageViewCharacter);
        this.constraintLayoutObstacleBird = (ConstraintLayout) findViewById(R.id.constraintLayoutObstacleBird);
        this.constraintLayoutObstacleBump = (ConstraintLayout) findViewById(R.id.constraintLayoutObstacleBump);
        this.constraintLayoutObstacleWall = (ConstraintLayout) findViewById(R.id.constraintLayoutObstacleWall);
        this.constraintLayoutGameActivity = (ConstraintLayout) findViewById(R.id.constraintLayoutGameActivity);
        this.constraintLayoutFloor = (ConstraintLayout) findViewById(R.id.constraintLayoutFloor);

        // organizing the elements
        imageViewCharacter.bringToFront();
        /**imageViewCharacter.setBackgroundResource(R.drawable.run);
        AnimationDrawable anim = (AnimationDrawable) imageViewCharacter.getBackground();
        anim.start();*/


        constraintLayoutFloor.bringToFront();
        // we add the listeners
        constraintLayoutGameActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateObstacles();
            }
        });
        imageViewCharacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateCharacterCrouch();
            }
        });

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);


        if (mAccelerometer != null) {

            mShakeDetector = new ShakeDetector();
            mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

                @Override
                public void onShake(int count) {
                    handleShakeEvent(count);
                }
            });

            if (magnetometer != null) {

                mTiltDetector = new TiltDetector();
                mTiltDetector.setOnTiltListener(new TiltDetector.OnTiltListener() {
                    @Override
                    public void onTilt(int count) {
                        handleTiltEvent(count);
                    }
                } );

            }else{
                Util.displayErrorAlert(AlertMessage.SENSOR_ERROR_TYPE, AlertMessage.SENSOR_ERROR, this);
            }

        }else{
            Util.displayErrorAlert(AlertMessage.SENSOR_ERROR_TYPE, AlertMessage.SENSOR_ERROR, this);
        }
    }

    private void handleTiltEvent(int count) {
        Log.d("Tilt !", "Don't bend your knees !");

    }

    private void handleShakeEvent(int count) {
        Log.d("Shake ! Shake !", "Shake your milkshake !");
    }

    /**
     * check if two views are colliding
     * @param firstView
     * @param secondView
     * @return true if collision, else false
     */
    private boolean isViewOverlapping(View firstView, View secondView) {
        int[] firstPosition = new int[2];
        int[] secondPosition = new int[2];

        firstView.getLocationOnScreen(firstPosition);
        secondView.getLocationOnScreen(secondPosition);

        // Rect constructor parameters: left, top, right, bottom
        Rect rectFirstView = new Rect(firstPosition[0], firstPosition[1],
                firstPosition[0] + firstView.getMeasuredWidth(), firstPosition[1] + firstView.getMeasuredHeight());
        Rect rectSecondView = new Rect(secondPosition[0], secondPosition[1],
                secondPosition[0] + secondView.getMeasuredWidth(), secondPosition[1] + secondView.getMeasuredHeight());
        return rectFirstView.intersect(rectSecondView);
    }

    /**
     * make a jump animation on the view
     */
    public void animateCharacterJump(){
        Animation jumpAnimation = AnimationUtils.loadAnimation(context, R.anim.jump_up);
        jumpAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.jump_down);
                imageViewCharacter.startAnimation(hyperspaceJumpAnimation);
            }
        });
        this.imageViewCharacter.startAnimation(jumpAnimation);
    }

    /**
     * make a jump animation on the view
     */
    public void animateCharacterCrouch(){
        Animation jumpAnimation = AnimationUtils.loadAnimation(context, R.anim.crouch_down);
        jumpAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.crouch_up);
                imageViewCharacter.startAnimation(hyperspaceJumpAnimation);
            }
        });
        this.imageViewCharacter.startAnimation(jumpAnimation);
    }


    /**
     * make a break animation on the view
     */
    public void animateCharacterBreak(){
        Animation jumpAnimation = AnimationUtils.loadAnimation(context, R.anim.break_right);
        jumpAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.break_left);
                imageViewCharacter.startAnimation(hyperspaceJumpAnimation);
            }
        });
        this.imageViewCharacter.startAnimation(jumpAnimation);
    }


    /**
     * add a bump view on the layout and start moving it
     */
    public void addBump(){
        ImageView bump = new ImageView(context);
        bump.setAdjustViewBounds(true);
        bump.setBackground(getResources().getDrawable(R.drawable.bomb));
        this.viewObstacleList.add(bump);
        bump.setId((int) Math.random());
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(150, 0);
        bump.setLayoutParams(layoutParams);
        ConstraintSet set = new ConstraintSet();
        constraintLayoutObstacleBump.addView(bump);
        set.clone(constraintLayoutObstacleBump);
        set.connect(bump.getId(),ConstraintSet.RIGHT,bump.getId(),ConstraintSet.RIGHT,0);
        set.connect(bump.getId(), ConstraintSet.TOP, bump.getId(), ConstraintSet.TOP, 0);
        set.connect(bump.getId(), ConstraintSet.BOTTOM, bump.getId(), ConstraintSet.BOTTOM, 0);
        set.applyTo(constraintLayoutObstacleBump);
        adding = false;
    }


    @Override
    public void onResume() {
        super.onResume();
        // Register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(mTiltDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(mTiltDetector, magnetometer, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onPause() {
        // Unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        mSensorManager.unregisterListener(mTiltDetector);
        super.onPause();
    }

    /**
     * add a bump view on the layout and start moving it
     */
    public void addBird(){
        ImageView bump = new ImageView(context);
        this.viewObstacleList.add(bump);
        bump.setId((int) Math.random());
        bump.setBackground(getResources().getDrawable(R.drawable.shurikenstable));
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(150, 150);
        bump.setLayoutParams(layoutParams);
        ConstraintSet set = new ConstraintSet();
        constraintLayoutObstacleBird.addView(bump);
        set.clone(constraintLayoutObstacleBird);
        set.connect(bump.getId(),ConstraintSet.RIGHT,bump.getId(),ConstraintSet.RIGHT,0);
        set.connect(bump.getId(), ConstraintSet.TOP, bump.getId(), ConstraintSet.TOP, 0);
        set.connect(bump.getId(), ConstraintSet.BOTTOM, bump.getId(), ConstraintSet.BOTTOM, 0);
        set.applyTo(constraintLayoutObstacleBird);
        adding = false;

    }


    /**
     * add a wall view on the layout and start moving it
     */
    public void addWall(){
        // add the bottom part of the wall
        ImageView wall = new ImageView(context);
        wall.setAdjustViewBounds(true);
        this.viewObstacleList.add(wall);
        wall.setId((int) Math.random());
        wall.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.wall));
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(50, 0);
        wall.setLayoutParams(layoutParams);
        ConstraintSet set = new ConstraintSet();
        constraintLayoutObstacleWall.addView(wall);
        set.clone(constraintLayoutObstacleWall);
        set.connect(wall.getId(),ConstraintSet.RIGHT,wall.getId(),ConstraintSet.RIGHT,0);
        set.connect(wall.getId(), ConstraintSet.TOP, wall.getId(), ConstraintSet.TOP, 0);
        set.connect(wall.getId(), ConstraintSet.BOTTOM, wall.getId(), ConstraintSet.BOTTOM, 0);
        set.applyTo(constraintLayoutObstacleWall);
        adding = false;

    }


    public void animateObstacles(){
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                //Looper.prepare();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 0; i < viewObstacleList.size();i++){
                            viewObstacleList.get(i).setX(viewObstacleList.get(i).getX()-1);
                            viewObstacleList.get(i).bringToFront();
                            // handle colliding here
                            if(isViewOverlapping(viewObstacleList.get(i), imageViewCharacter) && gameOver == false){
                                gameOver = true;
                                Toast.makeText(context,"COLLISIOOOOOOON",Toast.LENGTH_SHORT).show();
                            }
                            if(!isViewOverlapping(constraintLayoutGameActivity, viewObstacleList.get(i))) {
                                try {
                                    constraintLayoutObstacleBump.removeView(viewObstacleList.get(i));
                                }catch(Exception e){

                                }
                                try {
                                    constraintLayoutObstacleBird.removeView(viewObstacleList.get(i));
                                }catch(Exception e){

                                }
                                try {
                                    constraintLayoutObstacleWall.removeView(viewObstacleList.get(i));
                                }catch(Exception e){

                                }
                                viewObstacleList.remove(viewObstacleList.get(i));

                            }
                        }
                        if(viewObstacleList.isEmpty() && !adding){
                            adding = true;
                            Random r = new Random();
                            int rand = r.nextInt(3 - 0) +  0;
                            if (rand == 0) {
                                addBump();
                            }else if(rand == 1 ){
                                addBird();
                            }else{
                                addWall();
                            }
                        }
                    }
                });
            }
        }, 10, 5);
    }

}
