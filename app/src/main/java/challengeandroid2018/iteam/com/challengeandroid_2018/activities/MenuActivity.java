package challengeandroid2018.iteam.com.challengeandroid_2018.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import challengeandroid2018.iteam.com.challengeandroid_2018.R;
import challengeandroid2018.iteam.com.challengeandroid_2018.util.Constant;

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
        pseudoEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                setPseudoOnSharedPreferences();
            }
        });

        playButton = (Button) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, ModeActivity.class));
            }
        });
        scoresButton = (Button) findViewById(R.id.scoresButton);
        scoresButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, ScoreActivity.class));
            }
        });
        creditsButton = (Button) findViewById(R.id.creditButton);
        creditsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, CreditsActivity.class));
            }
        });

    }

    private void setPseudoOnSharedPreferences()
    {
        SharedPreferences sharedPref = getSharedPreferences(Constant.SHARED_PREFERENCES_KEY_PSEUDO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constant.SHARED_PREFERENCES_KEY_PSEUDO, getPseudo());
        editor.commit();
    }

    private String getPseudo()
    {
        String pseudo = pseudoEditText.getText().toString();
        if (pseudo.equals(""))
        {
            pseudo = Constant.SHARED_PREFERENCES_KEY_PSEUDO;
        }
        return pseudo;
    }

}
