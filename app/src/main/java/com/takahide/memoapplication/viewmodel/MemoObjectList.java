package com.takahide.memoapplication.viewmodel;

import android.content.Context;
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


import com.takahide.memoapplication.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by JavaQuest on 2018/02/06.
 */

public class MemoObjectList {
//    implements Parcelable {

    private Context context;

    private RelativeLayout layout;

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
    }

    public void createNewMemoObject ( String text ) {
        memoList.add (
                memoList.size(),
                new MemoObject( context ).setupNewInstance( text )
                );
        layout.addView (
                memoList.get ( memoList.size() - 1 )
        );
    }











    /**
     *  MemoObject インナークラス
     */
    private class MemoObject extends AppCompatTextView
        implements View.OnTouchListener, View.OnClickListener {

        private RelativeLayout.LayoutParams memoLayoutParams;

        // オブジェクトの配置 x軸（左端）
        private int putX = DEFAULT_X;
        public void setPutX ( int x ) { this.putX = x; }

        // オブジェクトの配置 y軸（上端）
        private int putY = DEFAULT_Y;
        public void setPutY ( int y ) { this.putY = y; }

        private final static int PADDING_VERTICAL = 20;
        private final static int PADDING_HORIZONTAL = 30;




        MemoObject setupNewInstance ( String text ) {
            Log.d ( "MemoObject", "Create New MemoObject" );

            memoLayoutParams = new RelativeLayout.LayoutParams (
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
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

                    if ( recentMotion != MotionEvent.ACTION_DOWN ) {
                        recentMotion = MotionEvent.ACTION_UP;
                        return true;
                    }
            }
            return false;
        }

        @Override
        public void onClick ( View v ) {
            Log.d ( "MemoObject", "onClick" );
            Snackbar.make ( layout, "Click the Button", Snackbar.LENGTH_SHORT ).show();
        }

        MemoObject ( Context context ) { super( context ); }
        MemoObject ( Context context, AttributeSet attrs ) { super ( context, attrs ); }
        MemoObject ( Context context, AttributeSet attrs, int defStyle ) {
            super( context, attrs, defStyle );
        }

    }   // MemoObjectクラス
}
