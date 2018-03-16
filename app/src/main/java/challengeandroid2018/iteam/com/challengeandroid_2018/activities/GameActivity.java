package challengeandroid2018.iteam.com.challengeandroid_2018.activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
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
import java.util.Timer;
import java.util.TimerTask;

import challengeandroid2018.iteam.com.challengeandroid_2018.R;

public class GameActivity extends AppCompatActivity {

    private ImageView imageViewCharacter;

    private ConstraintLayout constraintLayoutGameActivity;

    private ConstraintLayout constraintLayoutObstacleUp;

    private ConstraintLayout constraintLayoutObstacleDown;

    private Context context;

    private ArrayList<View> viewObstacleList = new ArrayList<>();

    private boolean gameOver = false;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        this.context = this;
        this.index = 0;
        // we get the graphical elements
        this.imageViewCharacter = (ImageView) findViewById(R.id.imageViewCharacter);
        this.constraintLayoutObstacleUp = (ConstraintLayout) findViewById(R.id.constraintLayoutObstacleUp);
        this.constraintLayoutObstacleDown = (ConstraintLayout) findViewById(R.id.constraintLayoutObstacleDown);
        this.constraintLayoutGameActivity = (ConstraintLayout) findViewById(R.id.constraintLayoutGameActivity);
        animateObstacles();
        // we add the listeners
        constraintLayoutGameActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // animateCharacterJump();
                addBird();
            }
        });
        imageViewCharacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateCharacterCrouch();
            }
        });
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
        constraintLayoutObstacleDown.addView(bump,0);
        set.clone(constraintLayoutObstacleDown);
        set.connect(bump.getId(),ConstraintSet.RIGHT,bump.getId(),ConstraintSet.RIGHT,0);
        set.connect(bump.getId(), ConstraintSet.TOP, bump.getId(), ConstraintSet.TOP, 0);
        set.connect(bump.getId(), ConstraintSet.BOTTOM, bump.getId(), ConstraintSet.BOTTOM, 0);
        set.applyTo(constraintLayoutObstacleDown);
        this.viewObstacleList.add(bump);
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
        constraintLayoutObstacleUp.addView(bump,0);
        set.clone(constraintLayoutObstacleUp);
        set.connect(bump.getId(),ConstraintSet.RIGHT,bump.getId(),ConstraintSet.RIGHT,0);
        set.connect(bump.getId(), ConstraintSet.TOP, bump.getId(), ConstraintSet.TOP, 0);
        set.connect(bump.getId(), ConstraintSet.BOTTOM, bump.getId(), ConstraintSet.BOTTOM, 0);
        set.applyTo(constraintLayoutObstacleUp);
        this.viewObstacleList.add(bump);
    }


    /**
     * add a wall view on the layout and start moving it
     */
    public void addWall(){
        // add the bottom part of the wall
        ImageView wallBottom = new ImageView(context);
        wallBottom.setBackgroundColor(Color.RED);
        wallBottom.setId((int) Math.random());
        wallBottom.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_launcher_background));
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(300, 0);
        wallBottom.setLayoutParams(layoutParams);
        ConstraintSet set = new ConstraintSet();
        constraintLayoutObstacleDown.addView(wallBottom,0);
        set.clone(constraintLayoutObstacleDown);
        set.connect(wallBottom.getId(),ConstraintSet.RIGHT,wallBottom.getId(),ConstraintSet.RIGHT,0);
        set.connect(wallBottom.getId(), ConstraintSet.TOP, wallBottom.getId(), ConstraintSet.TOP, 0);
        set.connect(wallBottom.getId(), ConstraintSet.BOTTOM, wallBottom.getId(), ConstraintSet.BOTTOM, 0);
        set.applyTo(constraintLayoutObstacleDown);
        this.viewObstacleList.add(wallBottom);

        // add the top part of the wall
        ImageView wallTop = new ImageView(context);
        wallTop.setBackgroundColor(Color.RED);
        wallTop.setId((int) Math.random());
        wallTop.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_launcher_background));
        ConstraintLayout.LayoutParams layoutParamsTop = new ConstraintLayout.LayoutParams(300, 0);
        wallTop.setLayoutParams(layoutParams);
        ConstraintSet setTop = new ConstraintSet();
        constraintLayoutObstacleUp.addView(wallTop,0);
        set.clone(constraintLayoutObstacleUp);
        set.connect(wallTop.getId(),ConstraintSet.RIGHT,wallTop.getId(),ConstraintSet.RIGHT,0);
        set.connect(wallTop.getId(), ConstraintSet.TOP, wallTop.getId(), ConstraintSet.TOP, 0);
        set.connect(wallTop.getId(), ConstraintSet.BOTTOM, wallTop.getId(), ConstraintSet.BOTTOM, 0);
        set.applyTo(constraintLayoutObstacleUp);
        this.viewObstacleList.add(wallTop);
    }

    Timer timer = new Timer();

    public void animateObstacles(){
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                //Looper.prepare();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(viewObstacleList.size());
                        for(View v : viewObstacleList){
                            v.setX(v.getX()-1);
                            // handle colliding here
                            if(isViewOverlapping(v, imageViewCharacter) && gameOver == false){
                                gameOver = true;
                                Toast.makeText(context,"COLLISIOOOOOOON",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

            }

        }, 0, 1);
    }

}
