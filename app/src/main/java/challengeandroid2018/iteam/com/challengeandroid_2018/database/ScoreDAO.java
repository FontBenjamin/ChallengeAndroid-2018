package challengeandroid2018.iteam.com.challengeandroid_2018.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import challengeandroid2018.iteam.com.challengeandroid_2018.model.Score;

/**
 *
 */

public class ScoreDAO {



    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "runner.db";
    ;

    private SQLiteDatabase bdd;
    private DatabaseHandler maBaseSQLite;


    public ScoreDAO(Context context){
        maBaseSQLite = new DatabaseHandler(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        //open the acces to BDD
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        //close the acces to BDD
        bdd.close();
    }

    public long addNormalScore(Score score) {

        //Creat of ContentValues (like a HashMap)
        ContentValues values = new ContentValues();
        //add the value to key
        values.put("idScore", score.getIdScore());
        values.put("pseudo", score.getPseudo());
        values.put("score", score.getScore());



        //Insert object to BDD with ContentValues

        return bdd.insert("NormalScore", null, values);
    }
    public long addSpeedScore(Score score) {

        //Creat of ContentValues (like a HashMap)
        ContentValues values = new ContentValues();
        //add the value to key
        values.put("idScore", score.getIdScore());
        values.put("pseudo", score.getPseudo());
        values.put("score", score.getScore());



        //Insert object to BDD with ContentValues

        return bdd.insert("SpeedScore", null, values);
    }


    /**
     * find a score with his nickname
     * @param pseudo
     * @return
     */
    public Score getSpeedScore(String pseudo) {
        Score score = new Score(pseudo,0);

        Cursor c = bdd.rawQuery("select  pseudo,score from SpeedScore where pseudo = "+pseudo,null);
        if(c.moveToFirst()){
            String p = c.getString(0);
            String s = c.getString(1);

            score = new Score(p, Integer.parseInt(s));

        }
        return score;
    }
    public Score getNormalScore(String pseudo) {
        Score score = new Score(pseudo,0);

        Cursor c = bdd.rawQuery("select  pseudo,score from NormalScore where pseudo = "+pseudo,null);
        if(c.moveToFirst()){
            String p = c.getString(0);
            String s = c.getString(1);

            score = new Score(p, Integer.parseInt(s));

        }
        return score;


        /**
         * retrieves All Score
         * @param
         * @return
         */
    }
    public List<Score> getAllNormalScore() {
        Score score = new Score("null",0);
        List<Score> scoreList =  new ArrayList<Score>();
        Cursor c = bdd.rawQuery("select pseudo,score from NormalScore order by score",null);
        if(c.moveToFirst()){
            do {
                String s =c.getString(0);
                String pseudo = c.getString(1);
                score = new Score(pseudo, Integer.parseInt(s));
                scoreList.add(score);
            }while(c.moveToNext());
        }

        return scoreList;
    }
    public List<Score> getAllSpeedScore() {
        Score score = new Score("null",0);
        List<Score> scoreList =  new ArrayList<Score>();
        Cursor c = bdd.rawQuery("select pseudo,score from SpeedScore order by score",null);
        if(c.moveToFirst()){
            do {
                String s =c.getString(0);
                String pseudo = c.getString(1);
                score = new Score(pseudo, Integer.parseInt(s));
                scoreList.add(score);
            }while(c.moveToNext());
        }

        return scoreList;
    }


    public SQLiteDatabase getBdd() {
        return bdd;
    }


}
