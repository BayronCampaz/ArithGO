package edu.icesi.arithgo.model.driver;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBDriver extends SQLiteOpenHelper {

    private static DBDriver instance;

    public static synchronized DBDriver getInstance(Context context){
        if(instance == null){
            instance = new DBDriver(context);
        }
        return instance;
    }

    public static final String DBNAME = "arithgo";
    public static final int DBVERSION = 1;

    public static final String TABLE_SCORE = "score";
    public static final String SCORE_ID = "id";
    public static final String SCORE_POINTS = "points";


    private DBDriver(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_SCORE = "CREATE TABLE $TABLE($ID TEXT PRIMARY KEY, $POINTS INTEGER)";

        CREATE_TABLE_SCORE = CREATE_TABLE_SCORE
                .replace("$TABLE", TABLE_SCORE)
                .replace("$ID", SCORE_ID)
                .replace("$POINTS", SCORE_POINTS);

        db.execSQL(CREATE_TABLE_SCORE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);
    }
}
