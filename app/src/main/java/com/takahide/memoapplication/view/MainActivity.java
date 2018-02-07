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
import com.takahide.memoapplication.model.MemoObjectList;

/**
 * Created by Hide on 2018/02/01.
 */

public class MainActivity extends AppCompatActivity
    implements Toolbar.OnMenuItemClickListener {

    private Toolbar toolbar;

    private RelativeLayout layout;

    private MemoObjectList memoObjects;


    @Override
    protected void onCreate (Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );


        log ( "creating the MainActivity" );

        layout = (RelativeLayout) findViewById ( R.id.layout );

        setupView();
    }

    void setupView () {
        toolbar = (Toolbar) findViewById ( R.id.toolbar );
        toolbar.inflateMenu( R.menu.toolbar_menu );
        toolbar.setOnMenuItemClickListener ( this );

        memoObjects = new MemoObjectList ( this, layout );

        initButton();
    }


    void initButton () {
        log ( "initButton" );

        memoObjects.createNewMemoObject();

//        MemoObject memo = new MemoObject ( this );
//        memo.setupInstance ( this, layout );
//        memo.setText ( "動的ボタン" );
//        layout.addView ( memo );
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

    void log ( String msg ) {
        Log.d ( "mainActivity", msg );
    }
}
