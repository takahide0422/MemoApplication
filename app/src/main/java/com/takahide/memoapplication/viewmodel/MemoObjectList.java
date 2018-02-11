package com.takahide.memoapplication.viewmodel;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import com.takahide.memoapplication.MyApplication;
import com.takahide.memoapplication.R;
import com.takahide.memoapplication.model.MemoDatabase;

import java.util.ArrayList;


/**
 * Created by JavaQuest on 2018/02/06.
 */

public class MemoObjectList {
//    implements Parcelable {

    private Context context;
    private RelativeLayout layout;

    private SQLiteDatabase db;


    private final static int DEFAULT_WIDTH = 300;
    private final static int DEFAULT_HEIGHT = 200;

    private final static int DEFAULT_X = 0;
    private final static int DEFAULT_Y = 0;


    ArrayList<MemoObject> memoList;

    public MemoObjectList ( Context context, RelativeLayout layout ) {
        Log.d ( "MemoObjectList", "Connect the MemoObject" );
        this.context = context;
        this.layout = layout;

        memoList = new ArrayList<>();

        readMemoData();
    }

    /**
     * 新しくメモを作成する。
     * @param text メモの内容
     */
    public void createNewMemoObject ( String text ) {
        MemoObject memo = new MemoObject( context ).setupNewInstance( text, DEFAULT_X, DEFAULT_Y );
        memoList.add ( memoList.size(), memo );
        layout.addView ( memo );
        try {
            db.execSQL(
                    MemoDatabase.createInsertState ( text, (int) memo.getX(), (int) memo.getY() ) );
        } catch ( SQLException e ) {
            e.getStackTrace();
            Log.d ( "MemoObjectList", "ERROR : Insert new Memo Data" );
        }
    }


    /**
     * 起動時に既存のデータを取得
     */
    void readMemoData () {
        db = new MemoDatabase ( context ).getWritableDatabase();

        Cursor cursor = db.rawQuery (
                MemoDatabase.selectSQL, null );

        while ( cursor.moveToNext() ) {
            Log.d ( "MemoObjectList", "Output the Data" );
            int id = cursor.getInt ( cursor.getColumnIndex ( MemoDatabase.ID ) );
            String body = cursor.getString ( cursor.getColumnIndex ( MemoDatabase.BODY ) );
            int margin_left = cursor.getInt ( cursor.getColumnIndex ( MemoDatabase.MARGIN_L ) );
            int margin_top = cursor.getInt ( cursor.getColumnIndex ( MemoDatabase.MARGIN_T ) );

            MemoObject memo = new MemoObject ( context ).setupExistingData ( id, body, margin_left, margin_top );
            memoList.add ( memo );
            layout.addView ( memo );
        }

        Log.d ( "MemoObjectList", "memoList size=" + memoList.size() );
    }










    /**
     *  MemoObject インナークラス
     */
    private class MemoObject extends AppCompatTextView
        implements View.OnTouchListener, View.OnClickListener {

        private RelativeLayout.LayoutParams memoLayoutParams;

        // オブジェクトの配置 x軸（左端）
        private int putX;
        public void setPutX ( int x ) { this.putX = x; }

        // オブジェクトの配置 y軸（上端）
        private int putY;
        public void setPutY ( int y ) { this.putY = y; }

        private final static int PADDING_VERTICAL = 20;
        private final static int PADDING_HORIZONTAL = 30;


        private int memoNum;
        public int getMemoNum () { return this.memoNum; }


        MemoObject setupExistingData ( int id, String body, int margin_left, int margin_top ) {
            Log.d ( "MemoObject", "Setup the existing Data" );
            this.memoNum = id;
            return setLayout ( body, margin_left, margin_top );
        }


        /**
         * 新しくメモを作成する。
         * @param text          メモの内容
         * @param margin_left
         * @param margin_top
         * @return
         */
        MemoObject setupNewInstance ( String text, int margin_left, int margin_top ) {
            Log.d("MemoObject", "Create New MemoObject");
            Cursor cursor = db.rawQuery ( MemoDatabase.countRow, null );
            while ( cursor.moveToNext() ) {
                this.memoNum = cursor.getInt ( cursor.getColumnIndex ( MemoDatabase.ROWS ) );
            }

            return setLayout ( text, margin_left, margin_top );
        }

        MemoObject setLayout ( String text, int margin_left, int margin_top ) {
            memoLayoutParams = new RelativeLayout.LayoutParams (
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            putX = margin_left;     putY = margin_top;
            memoLayoutParams.setMargins ( this.putX, this.putY, 0, 0 );


            this.setBackgroundResource ( R.drawable.memo_drawable );
            this.setLayoutParams ( memoLayoutParams );
            this.setPadding ( PADDING_HORIZONTAL, PADDING_VERTICAL, PADDING_HORIZONTAL, PADDING_VERTICAL );

            this.setMinWidth ( 200 );   this.setMaxWidth ( 700 );
            this.setMinHeight ( 150 );

            this.setTextColor ( Color.WHITE );
            this.setText ( text );

            this.setOnClickListener ( this );   this.setOnTouchListener ( this );

            return this;
        }








        int currentX;       // タッチ時のx軸（View左端）
        int currentY;       // タッチ時のy軸（View上端）

        int offsetX;        // 画面タッチ位置 x軸
        int offsetY;        // 画面タッチ位置 y軸

        int recentMotion;   // 直近のMotionEvent

        @Override
        public boolean onTouch ( View v, MotionEvent event ) {
            MemoObject memo = (MemoObject) v;

            int x = (int) event.getRawX();
            int y = (int) event.getRawY();

            switch ( event.getAction() ) {
                case MotionEvent.ACTION_MOVE:
                    Log.d ( "onTouch", "ACTION_MOVE" );

                    int diffX = offsetX - x;    int diffY = offsetY - y;

                    currentX -= diffX;  currentY -= diffY;

                    memo.layout ( currentX, currentY,
                            currentX + memo.getWidth(),
                            currentY + memo.getHeight() );

                    offsetX = x;    offsetY = y;

                    recentMotion = MotionEvent.ACTION_MOVE;
                    break;

                case MotionEvent.ACTION_DOWN:
                    Log.d ( "onTouch", "ACTION_DOWN" );

                    layout.removeView ( memo );
                    layout.addView ( memo );

                    currentX = memo.getLeft();  currentY = memo.getTop();

                    offsetX = x;    offsetY = y;

                    recentMotion = MotionEvent.ACTION_DOWN;
                    break;

                case MotionEvent.ACTION_UP:
                    Log.d ( "onTouch", "ACTION_UP: x=" + getX() +
                        ", y=" + getY() );

                    memo.setPutX ( (int) memo.getX() );
                    memo.setPutY ( (int) memo.getY() );

                    memoLayoutParams.setMargins ( this.putX, this.putY, 0, 0 );
                    memo.setLayoutParams ( memoLayoutParams );

                    try {
                        updateMargin();
                    } catch ( SQLException e ) {
                        e.getStackTrace();
                        Log.d ( "MemoObject", "ERROR : Update Margins" );
                    }

                    if ( recentMotion != MotionEvent.ACTION_DOWN ) {
                        recentMotion = MotionEvent.ACTION_UP;
                        return true;
                    }
            }
            return false;
        }

        void updateMargin() {
            String sql = MemoDatabase.getSQLForUpdateMargin ( this.memoNum, this.putX, this.putY );
            db.execSQL ( sql );
        }


        @Override
        public void onClick ( View v ) {
            Log.d ( "MemoObject", "onClick" );

        }


        public void deleteMemoAction () {
            layout.removeView ( this );
        }

        MemoObject ( Context context ) { super( context ); }
        MemoObject ( Context context, AttributeSet attrs ) { super ( context, attrs ); }
        MemoObject ( Context context, AttributeSet attrs, int defStyle ) {
            super( context, attrs, defStyle );
        }

    }   // MemoObjectクラス
}
