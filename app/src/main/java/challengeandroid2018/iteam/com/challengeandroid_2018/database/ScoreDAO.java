package challengeandroid2018.iteam.com.challengeandroid_2018.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.StaticLayout;

import java.util.ArrayList;
import java.util.List;

import challengeandroid2018.iteam.com.challengeandroid_2018.model.Score;

/**
 *
 */

public class ScoreDAO {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "runner.db";

    private static SQLiteDatabase sqLiteDatabase;
    private static DatabaseHandler databaseHandler;

    public static SQLiteDatabase getDatabase(Context context) {
        if (databaseHandler == null) {
            databaseHandler = new DatabaseHandler(context, NOM_BDD, null, VERSION_BDD);
            sqLiteDatabase = databaseHandler.getWritableDatabase();
        }
        return sqLiteDatabase;
    }

    public static void close(Context context) {

        getDatabase(context).close();
    }

    public static long addNormalScore(Score score, Context context) {

        //Creat of ContentValues (like a HashMap)
        ContentValues values = new ContentValues();
        //add the value to key
        values.put("pseudo", score.getPseudo());
        values.put("score", score.getScore());
        //Insert object to BDD with ContentValues

        return getDatabase(context).insert("NormalScore", null, values);
    }


    public static long addSpeedScore(Score score, Context context) {

        //Creat of ContentValues (like a HashMap)
        ContentValues values = new ContentValues();
        //add the value to key
        values.put("pseudo", score.getPseudo());
        values.put("score", score.getScore());
        //Insert object to BDD with ContentValues
        return getDatabase(context).insert("SpeedScore", null, values);
    }


    /**
     * find a score with his nickname
     *
     * @param pseudo
     * @return
     */

    public static Score getSpeedScore(String pseudo, Context context) {
        Score score = new Score(pseudo, 0);

        Cursor c = getDatabase(context).rawQuery("select  pseudo,score from SpeedScore where pseudo = " + pseudo, null);
        if (c.moveToFirst()) {
            String p = c.getString(0);
            String s = c.getString(1);

            score = new Score(p, Integer.parseInt(s));

        }
        return score;
    }

    public Score getNormalScore(String pseudo, Context context) {
        Score score = new Score(pseudo, 0);

        Cursor c = getDatabase(context).rawQuery("select  pseudo,score from NormalScore where pseudo = " + pseudo, null);
        if (c.moveToFirst()) {
            String p = c.getString(0);
            String s = c.getString(1);

            score = new Score(p, Integer.parseInt(s));

        }
        return score;

    }

    public static List<Score> getAllNormalScore(Context context) {
        Score score = new Score("null", 0);
        List<Score> scoreList = new ArrayList<Score>();
        Cursor c = getDatabase(context).rawQuery("select pseudo,score from NormalScore order by score", null);
        if (c.moveToFirst()) {
            do {
                String pseudo = c.getString(0);
                String p = c.getString(1);
                score = new Score(pseudo, Integer.parseInt(p));
                scoreList.add(score);
            } while (c.moveToNext());
        }

        return scoreList;
    }

    public static List<Score> getAllSpeedScore(Context context) {
        Score score = new Score("null", 0);
        List<Score> scoreList = new ArrayList<Score>();
        Cursor c = getDatabase(context).rawQuery("select pseudo,score from SpeedScore order by score", null);
        if (c.moveToFirst()) {
            do {
                String s = c.getString(0);
                String pseudo = c.getString(1);
                score = new Score(pseudo, Integer.parseInt(s));
                scoreList.add(score);
            } while (c.moveToNext());
        }

        return scoreList;
    }


}
