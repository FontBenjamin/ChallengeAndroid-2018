package challengeandroid2018.iteam.com.challengeandroid_2018.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    /**
     * Create Score table
     */
    public static final String SPEEDSCORE_TABLE_CREATE = "CREATE TABLE SpeedScore (" +
            "idScore INTEGER PRIMARY KEY AUTOINCREMENT," +
            "pseudo  TEXT," +
            "score INTEGER" +

            ");";
    
    public static final String NORMALSCORE_TABLE_CREATE = "CREATE TABLE NormalScore (" +
            "idScore INTEGER PRIMARY KEY AUTOINCREMENT," +
            "pseudo  TEXT," +
            "score INTEGER" +

            ");";
    /**
     * Constructor
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SPEEDSCORE_TABLE_CREATE);
        sqLiteDatabase.execSQL(NORMALSCORE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
