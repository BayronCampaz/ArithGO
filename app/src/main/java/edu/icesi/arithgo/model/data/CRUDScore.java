package edu.icesi.arithgo.model.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import edu.icesi.arithgo.app.ArithGOApp;
import edu.icesi.arithgo.model.driver.DBDriver;
import edu.icesi.arithgo.model.entity.Score;

public class CRUDScore {

    public static void insertScore(Score score){
        DBDriver driver = DBDriver.getInstance(ArithGOApp.getAppContext());
        SQLiteDatabase db = driver.getWritableDatabase();

        String sql = "INSERT INTO $TABLE($ID,$POINTS) VALUES('$VID','$VPOINTS')";
        sql = sql
                .replace("$TABLE", DBDriver.TABLE_SCORE)
                .replace("$ID", DBDriver.SCORE_ID)
                .replace("$NAME", DBDriver.SCORE_POINTS)
                .replace("$VID", score.getId())
                .replace("$VPOINTS", ""+score.getPoints());


        db.execSQL(sql);
        db.close();
    }

    public static void updatePoints(Score score) {
        DBDriver driver = DBDriver.getInstance(ArithGOApp.getAppContext());
        SQLiteDatabase db = driver.getWritableDatabase();
        String sql = "UPDATE $TABLE SET $POINTS=$VPOINTS WHERE $ID = '$FID'";
        sql = sql
                .replace("$TABLE", DBDriver.TABLE_SCORE)
                .replace("$POINTS", DBDriver.SCORE_POINTS)
                .replace("$POINTS", ""+score.getPoints())
                .replace("$ID",DBDriver.SCORE_ID)
                .replace("$FID",score.getId());
        db.execSQL(sql);
        db.close();
    }

    public static Score getScore(){
        DBDriver driver = DBDriver.getInstance(ArithGOApp.getAppContext());
        SQLiteDatabase db = driver.getReadableDatabase();

        Score score= null;

        String sql = "SELECT * FROM "+DBDriver.TABLE_SCORE+ " WHERE ID = 1";
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
                String id = cursor.getString(  cursor.getColumnIndex(DBDriver.SCORE_ID)  );
                int points = cursor.getInt(cursor.getColumnIndex(DBDriver.SCORE_POINTS);
                score = new Score(id,points);
        }

        db.close();
        return score;
    }
}
