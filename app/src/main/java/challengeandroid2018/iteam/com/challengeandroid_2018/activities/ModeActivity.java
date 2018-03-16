package challengeandroid2018.iteam.com.challengeandroid_2018.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import challengeandroid2018.iteam.com.challengeandroid_2018.R;
import challengeandroid2018.iteam.com.challengeandroid_2018.util.Constant;
import challengeandroid2018.iteam.com.challengeandroid_2018.model.GameModeEnum;

public class ModeActivity extends AppCompatActivity {

    private Button easyModeBtn;
    private Button speedModeBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
        this.easyModeBtn = findViewById(R.id.easyModeBtn);
        this.speedModeBtn = findViewById(R.id.speedModeBtn);

        //launch game with normal mode
        this.easyModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ModeActivity.this, GameActivity.class);
                i.putExtra(Constant.INTENT_KEY_GAME_MODE, GameModeEnum.NORMAL);
                startActivity(i);
            }
        });

        //launch game with speed mode
        this.speedModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ModeActivity.this, GameActivity.class);
                i.putExtra(Constant.INTENT_KEY_GAME_MODE,  GameModeEnum.SPEED);
                startActivity(i);
            }
        });


    }




}
