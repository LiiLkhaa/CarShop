package android.upem.carshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="users.db";
    public static final String TABLE_NAME="users";
    public static final String COL_1="ID";
    public static final String COL_2="name";
    public static final String COL_3="email";
    public static final String COL_4="password";

    public DatabseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (ID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("password", password);
        long res = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return res;
    }

    public boolean checkUser(String email, String password) {
        String [] columns = { COL_1 };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_3 + "=?" + " and " + COL_4 + "=?";
        String [] selectionArg =  { email, password };
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArg, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if (count>0) {
            return true;
        }
        else {
            return false;
        }
    }
}
