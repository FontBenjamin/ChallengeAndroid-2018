package challengeandroid2018.iteam.com.challengeandroid_2018.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import challengeandroid2018.iteam.com.challengeandroid_2018.R;
import challengeandroid2018.iteam.com.challengeandroid_2018.database.ScoreDAO;
import challengeandroid2018.iteam.com.challengeandroid_2018.model.Score;

public class ScoreActivity extends AppCompatActivity {
    TabHost tabHost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setup();
        TextView textview = findViewById(R.id.textnormale);
        TextView textspeed = findViewById(R.id.textspeed);

        List<Score> Listscore = ScoreDAO.getAllNormalScore(this);
        List<Score> Listscore2 = ScoreDAO.getAllSpeedScore(this);

        String result = "";
        for (int i = 0; i < Listscore.size(); i++) {
            result = result + Listscore.get(i).getPseudo() + "      " + Listscore.get(i).getScore() + "\n";
        }

        textview.setText(result);
        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Normal Score");
        spec.setContent(R.id.normalscore);
        spec.setIndicator("Normal Score");
        host.addTab(spec);


        //Tab 2
        spec = host.newTabSpec("Speed Score");
        spec.setContent(R.id.speedscore);
        spec.setIndicator("Speed Score");
        host.addTab(spec);
        List<Score> scoreNormal = ScoreDAO.getAllNormalScore(this);


        String resultSpeed = "";
        for (int i = 0; i < Listscore2.size(); i++) {
            resultSpeed = resultSpeed + Listscore2.get(i).getPseudo() + "      " + Listscore2.get(i).getScore() + "\n";
        }

        textspeed.setText(resultSpeed);

    }


}
