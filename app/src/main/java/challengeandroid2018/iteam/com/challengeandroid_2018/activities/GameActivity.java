package challengeandroid2018.iteam.com.challengeandroid_2018.activities;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import challengeandroid2018.iteam.com.challengeandroid_2018.R;
import util.ShakeDetector;

public class GameActivity extends AppCompatActivity {

    private ImageView imageViewCharacter;

    private ConstraintLayout constraintLayoutGameActivity;

    private Context context;

    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

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
        this.constraintLayoutGameActivity = findViewById(R.id.constraintLayoutGameActivity);
        constraintLayoutGameActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateCharacterJump();
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
     * this method handle the collision between two views
     * @param v1
     * @param v2
     * @return
     */
    public boolean CheckCollision(View v1, View v2) {
        Rect R1=new Rect(v1.getLeft(), v1.getTop(), v1.getRight(), v1.getBottom());
        Rect R2=new Rect(v2.getLeft(), v2.getTop(), v2.getRight(), v2.getBottom());
        return R1.intersect(R2);
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
}
