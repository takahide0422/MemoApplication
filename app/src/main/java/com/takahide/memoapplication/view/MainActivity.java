package com.takahide.memoapplication.view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.takahide.memoapplication.MemoObject;
import com.takahide.memoapplication.R;

/**
 * Created by Hide on 2018/02/01.
 */

public class MainActivity extends AppCompatActivity
    implements Toolbar.OnMenuItemClickListener {

    private Toolbar toolbar;

    private RelativeLayout layout;


    @Override
    protected void onCreate (Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );


        Log.d ( "mainActivity", "creating the MainActivity" );

        layout = (RelativeLayout) findViewById ( R.id.layout );

        setupView();
    }

    void setupView () {
        toolbar = (Toolbar) findViewById ( R.id.toolbar );
        toolbar.inflateMenu( R.menu.toolbar_menu );
        toolbar.setOnMenuItemClickListener ( this );

        ( (Button) findViewById ( R.id.button ) ).setOnClickListener ( buttonClickListener );

        initButton();
    }


    void initButton () {
        Log.d ( "mainActivity", "initButton" );
        MemoObject memo = new MemoObject( this );
        memo.setupInstance ( this, layout );
        memo.setText ( "動的ボタン" );
        memo.setOnTouchListener ( memo );
        memo.setOnClickListener ( objectClickListener );
        layout.addView ( memo );
    }







    @Override
    public boolean onMenuItemClick ( MenuItem menuItem ) {
        switch ( menuItem.getItemId() ) {
            case R.id.add:
                Log.d ( "toolbar", "click on add" );
                initButton();
        }
        return true;
    }

    View.OnClickListener buttonClickListener = new View.OnClickListener () {
        @Override
        public void onClick ( View v ) {
        }
    };

    View.OnClickListener objectClickListener = new View.OnClickListener () {
        @Override
        public void onClick ( View v ) {
            Log.d ( "mainActivity", "onClick" );
            Snackbar.make ( layout, "button pushed", Snackbar.LENGTH_SHORT ).show();
        }
    };


    @Override
    public View onCreateView ( String name, Context context, AttributeSet attrs ) {
        super.onCreateView ( name, context, attrs );

        Log.d ( "mainActivity", "onCreateView" );
        return super.onCreateView ( name, context, attrs );
    }
}
