package com.takahide.memoapplication.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Hide on 2018/02/07.
 */

public class MemoDatabase extends SQLiteOpenHelper {

    static final String DB_NAME = "memo.db";
    static final int DB_VERSION = 1;

    // テーブル名
    static final String MEMO = "memo";
    // カラム名
    public static final String ID = "id";
    public static final String BODY = "body";
    public static final String MARGIN_L = "margin_left";
    public static final String MARGIN_T = "margin_top";
    public static final String COMPLETE = "complete";

    //行数取得用
    public static final String ROWS = "rows";

    final String SQL_MEMO = "CREATE TABLE IF NOT EXISTS " + MEMO + " ( " +
            ID + " INTEGER NOT NULL PRIMARY KEY, " +
            BODY + " TEXT NOT NULL, " +
            MARGIN_L + " INTEGER NOT NULL, " +
            MARGIN_T + " INTEGER NOT NULL, " +
            COMPLETE + " INTEGER NOT NULL CHECK ( complete IN( 0, 1 ) )" + " )";

    public static final int FALSE = 0;
    public static final int TRUE = 1;




    // データベースの行数取得のSQL文
    public static final String countRow = "SELECT count(*) as rows FROM " + MEMO;
    // データベース情報取得のためのSQL文
    public static final String selectSQL = "SELECT id, body, margin_left, margin_top, complete FROM " + MEMO +
                                            " WHERE complete = " + FALSE + " ORDER BY id ASC";

    // データ追加のためのSQL文
    public static final String insertSQL = "INSERT INTO " + MEMO + " VALUES \r\n";
    /** MemoObject情報の追加 */
    public static String createInsertState ( String body, int margin_left, int margin_top ) {
        String sql = MemoDatabase.insertSQL + "( ( " + MemoDatabase.countRow + " ), " +
                processForDB ( body ) + ", " + margin_left + ", " + margin_top + ", " + FALSE + " )";
        return sql;
    }

    // 更新のためのSQL文
    public static final String updateSQL = "UPDATE " + MEMO + " SET ";

    /** Viewの位置情報の更新 */
    public static String getSQLForUpdateMargin ( int id, int margin_left, int margin_top ) {
        return updateSQL + MARGIN_L + " = " + margin_left + ", " + MARGIN_T + " = " + margin_top +
                "\r\nWHERE " + ID + " = " + id;
    }

    /** Viewを非表示への変更 */
    public static String getSQLForUpdateComplete ( int id ) {
        return updateSQL + COMPLETE + " = " + TRUE +
                "\r\nWHERE " + ID + " = " + id;
    }









    // コンストラクタ
    public MemoDatabase ( Context context ) {
        super ( context, DB_NAME, null, DB_VERSION);
        log ( "Connected the MemoDatabase" );
    }





    @Override
    public void onCreate ( SQLiteDatabase db ) {
        log ( "Created the Memo Database" );
        db.execSQL ( SQL_MEMO );
        db.execSQL ( insertSQL +
                "( 0, 'テスト', 100, 100, 0 )"
        );
    }

    @Override
    public void onUpgrade ( SQLiteDatabase db, int oldVersion, int newVersion ) {
        log ( "Upgrade the Memo Database" );
        String drop_sql = "DROP TABLE IF EXISTS " + MEMO;
        db.execSQL ( drop_sql );
        onCreate ( db );
    }


    /** メモ内容を加工 ( for SQL ) */
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
