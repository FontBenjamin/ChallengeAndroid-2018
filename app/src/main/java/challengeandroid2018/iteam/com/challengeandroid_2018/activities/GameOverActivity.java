package challengeandroid2018.iteam.com.challengeandroid_2018.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import challengeandroid2018.iteam.com.challengeandroid_2018.R;
import challengeandroid2018.iteam.com.challengeandroid_2018.util.Constant;

public class GameOverActivity extends AppCompatActivity {

    private TextView scoreTxt;
    private Button backMenuBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        //Retrieve the timetable link to display
        Intent i = getIntent();
        int score = i.getIntExtra(Constant.INTENT_KEY_PLAYER_SCORE, 0);

        // Recovering of pseudo from SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences(Constant.SHARED_PREFERENCES_KEY_PSEUDO, Context.MODE_PRIVATE);
        String pseudo = sharedPref.getString(Constant.SHARED_PREFERENCES_KEY_PSEUDO, Constant.SHARED_PREFERENCES_KEY_PSEUDO);

        this.scoreTxt = findViewById(R.id.scoreTxt);
        this.scoreTxt.append(score + "");

        this.backMenuBtn = findViewById(R.id.backMenuBtn);
        this.backMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GameOverActivity.this, MenuActivity.class));
            }
        });
    }

}
