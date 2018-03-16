package challengeandroid2018.iteam.com.challengeandroid_2018.activities;

import android.content.Intent;
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
        String score = i.getStringExtra(Constant.INTENT_KEY_PLAYER_SCORE);

        this.scoreTxt = findViewById(R.id.scoreTxt);
        this.scoreTxt.append(score);

        this.backMenuBtn = findViewById(R.id.backMenuBtn);
        this.backMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GameOverActivity.this, MenuActivity.class));
            }
        });
    }
    
}
