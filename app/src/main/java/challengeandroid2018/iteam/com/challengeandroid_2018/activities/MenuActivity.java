package challengeandroid2018.iteam.com.challengeandroid_2018.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import challengeandroid2018.iteam.com.challengeandroid_2018.R;

public class MenuActivity extends AppCompatActivity {

    private EditText pseudoEditText;
    private Button playButton;
    private Button scoresButton;
    private Button creditsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        pseudoEditText = (EditText) findViewById(R.id.pseudoEditText);

        playButton = (Button) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent modeIntent = new Intent(MenuActivity.this, ModeActivity.class);
                modeIntent.putExtra("pseudo", getPseudo());
                startActivity(modeIntent);
            }
        });
        scoresButton = (Button) findViewById(R.id.scoresButton);
        scoresButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent scoresIntent = new Intent(MenuActivity.this, ScoreActivity.class);
                scoresIntent.putExtra("pseudo", getPseudo());
                startActivity(scoresIntent);
            }
        });
        creditsButton = (Button) findViewById(R.id.creditButton);
        creditsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, CreditsActivity.class));
            }
        });

    }

    private String getPseudo()
    {
        String pseudo = pseudoEditText.getText().toString();
        if (pseudo.equals(""))
        {
            pseudo = "Pseudo";
        }
        return pseudo;
    }

}
