package com.triples.bf_skku;

/**
 * Created by Kwon on 2016-02-22.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NotesDbAdapter {

    public static final String KEY_BUILDING_NAME = "building_name";
    public static final String KEY_BUILDING_ENG = "building_eng";
    public static final String KEY_TOILET = "toilet";
    public static final String KEY_CLASS_NUM = "class_num";
    public static final String KEY_ClASS_TYPE = "class_type";
    public static final String KEY_TYPE_OPTION = "type_option";
    public static final String KEY_ROWID = "_id";
    private static final String TAG = "NotesDbAdapter";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    /**
     *
     * Database creation sql statement
     */

    private static final String DATABASE_CREATE = "create table notes (_id integer primary key autoincrement, "
            + "building_eng text not null, building_name text not null, toilet text not null);";
    private static final String DATABASE_CREATE2 = "create table notes2 (_id integer primary key autoincrement, "
            + "building_eng text not null, class_num text not null, class_type text not null, type_option text not null);";

    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "notes";
    private static final String DATABASE_TABLE2 = "notes2";
    private static final int DATABASE_VERSION = 2;
    private final Context mCtx;


    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE2);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion
                    + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS notes");
            onCreate(db);
        }
    }

    public NotesDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public NotesDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public long createNote(String building_eng, String building_name, String toilet) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_BUILDING_ENG, building_eng);
        initialValues.put(KEY_BUILDING_NAME, building_name);
        initialValues.put(KEY_TOILET, toilet);
        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    public long createNote2(String building_eng, String class_num, String class_type, String type_option) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_BUILDING_ENG, building_eng);
        initialValues.put(KEY_CLASS_NUM, class_num);
        initialValues.put(KEY_ClASS_TYPE, class_type);
        initialValues.put(KEY_TYPE_OPTION, type_option);
        return mDb.insert(DATABASE_TABLE2, null, initialValues);
    }

//    public boolean deleteNote(long rowId) {
//        Log.i("Delete called", "value__" + rowId);
//        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
//    }
//
//    public Cursor fetchAllNotes() {
//        return mDb.query(DATABASE_TABLE, new String[] { KEY_ROWID, KEY_BUILDING_NUM, KEY_BUILDING_NAME, KEY_TOILET }, null, null, null, null, null);
//    }

    /*id값으로 건물정보 얻기*/
    public Cursor fetchNote(String building_eng) throws SQLException {

        Cursor mCursor = mDb.query(true, DATABASE_TABLE, new String[] { KEY_ROWID, KEY_BUILDING_ENG, KEY_BUILDING_NAME, KEY_TOILET },KEY_BUILDING_ENG
                + "='" + building_eng +"'", null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /*id값으로 강의실정보 얻기*/
    public Cursor fetchNote2(String building_eng) throws SQLException {

        Cursor mCursor2 = mDb.query(true, DATABASE_TABLE2, new String[] { KEY_ROWID, KEY_BUILDING_ENG, KEY_CLASS_NUM, KEY_ClASS_TYPE, KEY_TYPE_OPTION },KEY_BUILDING_ENG
                + "='" +building_eng +"'", null, null, null, null, null);
        if (mCursor2 != null) {
            mCursor2.moveToFirst();
        }
        return mCursor2;
    }

    /*강의실번호로 강의실정보 얻기*/
    public Cursor fetchNote3(String class_num) throws SQLException {

        Cursor mCursor3 = mDb.query(true, DATABASE_TABLE2, new String[] { KEY_ROWID, KEY_BUILDING_ENG, KEY_CLASS_NUM, KEY_ClASS_TYPE, KEY_TYPE_OPTION },KEY_CLASS_NUM
                + "='" +class_num +"'", null, null, null, null, null);
        if (mCursor3 != null) {
            mCursor3.moveToFirst();
        }
        return mCursor3;
    }

    /*강의실 종류별 보기*/
    public Cursor fetchNote4(String class_type, String building_eng) throws SQLException {

        Cursor mCursor4 = mDb.query(true, DATABASE_TABLE2, new String[] { KEY_ROWID, KEY_BUILDING_ENG, KEY_CLASS_NUM, KEY_ClASS_TYPE, KEY_TYPE_OPTION },KEY_ClASS_TYPE
                + "='"+class_type + "' and " + KEY_BUILDING_ENG + "='" + building_eng +"'", null, null, null, null, null);
        if (mCursor4 != null) {
            mCursor4.moveToFirst();
        }
        return mCursor4;
    }

    public boolean updateNote(long rowId, String building_num, String building_name, String toilet) {
        ContentValues args = new ContentValues();
        args.put(KEY_BUILDING_ENG, building_num);
        args.put(KEY_BUILDING_NAME, building_name);
        args.put(KEY_TOILET, toilet);
        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public boolean updateNote2(long rowId, String building_name, String class_num, String class_type, String type_option) {
        ContentValues args = new ContentValues();
        args.put(KEY_BUILDING_NAME, building_name);
        args.put(KEY_CLASS_NUM, class_num);
        args.put(KEY_ClASS_TYPE, class_type);
        args.put(KEY_TYPE_OPTION, type_option);
        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }



}

