package challengeandroid2018.iteam.com.challengeandroid_2018.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import challengeandroid2018.iteam.com.challengeandroid_2018.R;
import challengeandroid2018.iteam.com.challengeandroid_2018.model.GameModeEnum;
import challengeandroid2018.iteam.com.challengeandroid_2018.util.Constant;
import challengeandroid2018.iteam.com.challengeandroid_2018.util.AlertMessage;
import challengeandroid2018.iteam.com.challengeandroid_2018.util.ShakeDetector;
import challengeandroid2018.iteam.com.challengeandroid_2018.util.TiltDetector;
import challengeandroid2018.iteam.com.challengeandroid_2018.util.Util;

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
    private int scores;
    private TextView textViewScore;
    private int speed;
    private int gameMode;

    private ArrayList<View> viewObstacleList = new ArrayList<>();

    private boolean gameOver = false;
    private ValueAnimator va;
    private Timer timer = new Timer();
    private Timer timerScore = new Timer();
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
        this.textViewScore = findViewById(R.id.textViewScore);
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
        constraintLayoutFloor.bringToFront();

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

        getGameMode();
        this.speed = 5;

        //TODO update scores
        scores = 1;
    }

    private void getGameMode(){
        Intent i = getIntent();
        int gameMode = i.getIntExtra(Constant.INTENT_KEY_GAME_MODE, 0);
    }

    private void handleTiltEvent(int count) {
        animateCharacterCrouch();
        Log.d("Tilt !", "Don't bend your knees !");

    }

    private void handleShakeEvent(int count) {
        animateCharacterJump();
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
        final ValueAnimator va=ValueAnimator.ofFloat(0.0f,-400.0f);
        va.setDuration(1000);
        va.start();
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // TODO Auto-generated method stub
                imageViewCharacter.setTranslationY((Float) va.getAnimatedValue());
            }
        });
        va.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                final ValueAnimator va=ValueAnimator.ofFloat(-400.0f,0.0f);
                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        // TODO Auto-generated method stub
                        imageViewCharacter.setTranslationY((Float) va.getAnimatedValue());
                    }
                });
                va.setDuration(1000);
                va.start();            }
        });
    }

    /**
     * make a jump animation on the view
     */
    public void animateCharacterCrouch(){
        final ValueAnimator va=ValueAnimator.ofFloat(0.0f,150.0f);
        va.setDuration(1000);
        va.start();
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // TODO Auto-generated method stub
                imageViewCharacter.setTranslationY((Float) va.getAnimatedValue());
            }
        });
        va.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                final ValueAnimator va=ValueAnimator.ofFloat(150.0f,0.0f);
                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        // TODO Auto-generated method stub
                        imageViewCharacter.setTranslationY((Float) va.getAnimatedValue());
                    }
                });
                va.setDuration(1000);
                va.start();            }
        });
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
        /** anim = new TranslateAnimation(0, 0, 0, 350);
        anim.setDuration(1000);

        anim.setAnimationListener(new TranslateAnimation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationRepeat(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)imageViewCharacter.getLayoutParams();
                params.topMargin += 350;
                params.leftMargin += 0;
                imageViewCharacter.setLayoutParams(params);
            }
        });
        anim.setAnimationListener(new Animation.AnimationListener() {

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
        imageViewCharacter.startAnimation(anim);*/
    }


    /**
     * add a bump view on the layout and start moving it
     */
    public void addBump(){
        ImageView bump = new ImageView(context);
        bump.setAdjustViewBounds(true);
        bump.setBackground(getResources().getDrawable(R.drawable.spique));
        this.viewObstacleList.add(bump);
        bump.setId((int) Math.random());
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(50, 150);
        bump.setLayoutParams(layoutParams);
        ConstraintSet set = new ConstraintSet();
        constraintLayoutObstacleBump.addView(bump);
        set.clone(constraintLayoutObstacleBump);
        set.connect(bump.getId(),ConstraintSet.RIGHT,bump.getId(),ConstraintSet.RIGHT,0);
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
        set.connect(bump.getId(), ConstraintSet.BOTTOM, bump.getId(), ConstraintSet.BOTTOM, 0);
        set.applyTo(constraintLayoutObstacleBird);
        adding = false;

    }


    /**
     * add a wall view on the layout and start moving it
     */
    public void addWall(){
        // add the bottom part of the wall
        final ImageView wall = new ImageView(context);
        wall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewObstacleList.remove(wall);
            }
        });


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
        set.connect(wall.getId(), ConstraintSet.BOTTOM, wall.getId(), ConstraintSet.BOTTOM, 0);
        set.applyTo(constraintLayoutObstacleWall);
        adding = false;

    }


    public void animateObstacles(){
       // incrementScore();
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
                                System.out.println("*************************");
                                System.out.println(imageViewCharacter.getY());
                                gameOver = true;
                                Intent gameOverIntent = new Intent(GameActivity.this, GameOverActivity.class);
                                gameOverIntent.putExtra(Constant.INTENT_KEY_PLAYER_SCORE, scores);
                                startActivity(gameOverIntent);
                                
                                finish();
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
        }, 10, speed);
    }

    private void incrementScore(){
        timerScore.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                //Looper.prepare();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       scores+= 1;
                       textViewScore.setText("Score : " + scores);
                    }
                });
            }
        }, 10, 2000);
    }

}
