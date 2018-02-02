package com.takahide.memoapplication.view;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.takahide.memoapplication.R;

public class BoardActivity extends AppCompatActivity
    implements View.OnClickListener, View.OnTouchListener {

    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        Log.d ( "boardActivity", "creating the BoardActivity" );

        layout = (RelativeLayout) findViewById ( R.id.relative );
    }

    void initButton () {
        Button button = new Button( this );
        button.setText ( "動的ボタン" );
        button.setOnClickListener ( this );
        button.setOnTouchListener ( this );
        layout.addView ( button );
    }

    @Override
    public void onClick ( View v ) {
        Snackbar.make ( layout, "button pushed", Snackbar.LENGTH_SHORT );
    }



    int currentX;
    int currentY;

    int offsetX;
    int offsetY;


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

                break;

            case MotionEvent.ACTION_DOWN:
                Log.d ( "onTouch", "ACTION_DOWN" );
                currentX = v.getLeft();
                currentY = v.getTop();

                offsetX = x;
                offsetY = y;

                break;

            case MotionEvent.ACTION_UP:
                Log.d ( "onTouch", "ACTION_UP" );
                break;
        }
        return false;
    }
}
