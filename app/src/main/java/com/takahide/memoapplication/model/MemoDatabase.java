package com.takahide.memoapplication.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Hide on 2018/02/07.
 */

public class MemoDatabase extends SQLiteOpenHelper {

    static final String DB_NAME = "memo.db";
    static final int DB_VERSION = 0;

    // テーブル名
    static final String MEMO = "memo";
    // カラム名
    static final String ID = "id";
    static final String BODY = "body";
    static final String MARGIN_L = "margin_left";
    static final String MARGIN_T = "margin_top";
    static final String COMPLETE = "complete";

    final String sql_memo = "CREATE TABLE IF NOT EXISTS " + MEMO + " ( " +
            ID + " INTEGER NOT NULL PRIMARY KEY, " +
            BODY + " TEXT NOT NULL, " +
            MARGIN_L + " INTEGER NOT NULL, " +
            MARGIN_T + " INTEGER NOT NULL, " +
            COMPLETE + " INTEGER NOT NULL CHECK ( complete IN( 0, 1 ) )" + " )";


    public MemoDatabase ( Context context ) {
        super ( context, DB_NAME, null, DB_VERSION );
    }

    @Override
    public void onCreate ( SQLiteDatabase db ) {
        db.execSQL ( sql_memo );
    }



    @Override
    public void onUpgrade ( SQLiteDatabase db, int oldVersion, int newVersion ) {
        String drop_sql = "DROP TABLE IF EXISTS " + MEMO;
        db.execSQL ( drop_sql );
        onCreate ( db );
    }

    public static String processForDB ( String line ) {
        final char SINGLE_QUATATION = '\'';
        StringBuffer sb = new StringBuffer();
        sb.append ( SINGLE_QUATATION );
        for ( int i = 0; i < line.length(); i++ ) {
            char c = line.charAt ( i );
            if ( c == SINGLE_QUATATION )
                sb.append ( "\\" );
            sb.append ( c );
        }
        sb.append ( SINGLE_QUATATION );
        return new String ( sb );
    }

    void log ( String msg ) {
        Log.d ( "MemoDatabase", msg );
    }
}
