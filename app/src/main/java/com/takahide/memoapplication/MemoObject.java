package com.takahide.memoapplication;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Hide on 2018/02/01.
 */

public class MemoObject extends AppCompatTextView
        implements View.OnTouchListener {

    public static MemoObject createInstance ( Context context, RelativeLayout layout ) {
        MemoObject memoObject = new MemoObject ( context );
        memoObject.layout = layout;

        return memoObject;
    }



    int currentX;
    int currentY;

    int offsetX;
    int offsetY;

    int recentMotion;

    static RelativeLayout layout;

    @Override
    public boolean onTouch ( View v, MotionEvent event ) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch ( event.getAction() ) {
            case MotionEvent.ACTION_MOVE:
                Log.d ( "onTouch", "ACTION_MOVE" );
                int diffX = offsetX - x;
                int diffY = offsetY - y;

                currentX -= diffX;
                currentY -= diffY;

                v.layout ( currentX, currentY,
                        currentX + v.getWidth(),
                        currentY + v.getHeight() );
                offsetX = x;
                offsetY = y;

                recentMotion = MotionEvent.ACTION_MOVE;
                break;

            case MotionEvent.ACTION_DOWN:
                Log.d ( "onTouch", "ACTION_DOWN" );
                currentX = v.getLeft();
                currentY = v.getTop();

                offsetX = x;
                offsetY = y;

                recentMotion = MotionEvent.ACTION_DOWN;
                break;

            case MotionEvent.ACTION_UP:
                Log.d ( "onTouch", "ACTION_UP" );
                if ( recentMotion != MotionEvent.ACTION_DOWN ) {
                    recentMotion = MotionEvent.ACTION_UP;
                    return true;
                }
        }
        return false;
    }

    View.OnClickListener clickListener = new View.OnClickListener () {
        @Override
        public void onClick ( View v ) {
            Log.d ( "onClick", "onClick" );

            Snackbar.make ( layout, "button pushed", Snackbar.LENGTH_SHORT );
        }
    };









    public MemoObject ( Context context ) {
        super ( context );
    }
    public MemoObject (Context context, AttributeSet attrs ) {
        super ( context, attrs );
    }
    public MemoObject ( Context context, AttributeSet attrs, int defStyle ) {
        super ( context, attrs, defStyle );
    }
}
