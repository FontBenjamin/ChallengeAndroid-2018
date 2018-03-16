package challengeandroid2018.iteam.com.challengeandroid_2018.activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
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

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import challengeandroid2018.iteam.com.challengeandroid_2018.R;
import util.ShakeDetector;

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

    private ArrayList<View> viewObstacleList = new ArrayList<>();

    private boolean gameOver = false;

    private Timer timer = new Timer();
    private boolean adding = false;


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
        animateObstacles();
        // we add the listeners
        constraintLayoutGameActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
				/*
				 * The following method, "handleShakeEvent(count):" is a stub //
				 * method you would use to setup whatever you want done once the
				 * device has been shook.
				 */
                handleShakeEvent(count);
            }
        });
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
        bump.setBackgroundColor(Color.RED);
        bump.setId((int) Math.random());
        bump.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_launcher_background));
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(300, 0);
        bump.setLayoutParams(layoutParams);
        ConstraintSet set = new ConstraintSet();
        constraintLayoutObstacleBump.addView(bump,0);
        set.clone(constraintLayoutObstacleBump);
        set.connect(bump.getId(),ConstraintSet.RIGHT,bump.getId(),ConstraintSet.RIGHT,0);
        set.connect(bump.getId(), ConstraintSet.TOP, bump.getId(), ConstraintSet.TOP, 0);
        set.connect(bump.getId(), ConstraintSet.BOTTOM, bump.getId(), ConstraintSet.BOTTOM, 0);
        set.applyTo(constraintLayoutObstacleBump);
        this.viewObstacleList.add(bump);
        adding = false;
    }


    @Override
    public void onResume() {
        super.onResume();
        // Register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    /**
     * add a bump view on the layout and start moving it
     */
    public void addBird(){
        ImageView bump = new ImageView(context);
        bump.setBackgroundColor(Color.RED);
        bump.setId((int) Math.random());
        bump.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_launcher_background));
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(300, 0);
        bump.setLayoutParams(layoutParams);
        ConstraintSet set = new ConstraintSet();
        constraintLayoutObstacleBird.addView(bump,0);
        set.clone(constraintLayoutObstacleBird);
        set.connect(bump.getId(),ConstraintSet.RIGHT,bump.getId(),ConstraintSet.RIGHT,0);
        set.connect(bump.getId(), ConstraintSet.TOP, bump.getId(), ConstraintSet.TOP, 0);
        set.connect(bump.getId(), ConstraintSet.BOTTOM, bump.getId(), ConstraintSet.BOTTOM, 0);
        set.applyTo(constraintLayoutObstacleBird);
        this.viewObstacleList.add(bump);
        adding = false;

    }


    /**
     * add a wall view on the layout and start moving it
     */
    public void addWall(){
        // add the bottom part of the wall
        ImageView wall = new ImageView(context);
        wall.setBackgroundColor(Color.RED);
        wall.setId((int) Math.random());
        wall.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_launcher_background));
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(50, 0);
        wall.setLayoutParams(layoutParams);
        ConstraintSet set = new ConstraintSet();
        constraintLayoutObstacleWall.addView(wall,0);
        set.clone(constraintLayoutObstacleWall);
        set.connect(wall.getId(),ConstraintSet.RIGHT,wall.getId(),ConstraintSet.RIGHT,0);
        set.connect(wall.getId(), ConstraintSet.TOP, wall.getId(), ConstraintSet.TOP, 0);
        set.connect(wall.getId(), ConstraintSet.BOTTOM, wall.getId(), ConstraintSet.BOTTOM, 0);
        set.applyTo(constraintLayoutObstacleWall);
        this.viewObstacleList.add(wall);
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
                            View v = viewObstacleList.get(i);
                            v.setX(v.getX()-1);
                            // handle colliding here
                            if(isViewOverlapping(v, imageViewCharacter) && gameOver == false){
                                gameOver = true;
                                Toast.makeText(context,"COLLISIOOOOOOON",Toast.LENGTH_SHORT).show();
                            }
                            if(!isViewOverlapping(constraintLayoutGameActivity, v)) {
                                viewObstacleList.remove(v);
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

        }, 0, 5);
    }

}
