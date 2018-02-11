package com.takahide.memoapplication.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.takahide.memoapplication.R;
import com.takahide.memoapplication.viewmodel.MemoObjectList;

/**
 * Created by Hide on 2018/02/01.
 */

public class MainActivity extends AppCompatActivity {
//    implements Toolbar.OnMenuItemClickListener {

    private Toolbar toolbar;

    private RelativeLayout layout;
//    public static int windowWidth;
//    public static int windowHeight;

    private InputMethodManager imm;



    private MemoObjectList memoObjects;

    private EditText editText;

    @Override
    protected void onCreate (Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );


        log ( "creating the MainActivity" );

        layout = (RelativeLayout) findViewById ( R.id.layout );

        setupView();
    }




    void setupView () {
        imm = (InputMethodManager) getSystemService ( Context.INPUT_METHOD_SERVICE );
        toolbar = (Toolbar) findViewById ( R.id.toolbar );
        editText = (EditText) findViewById ( R.id.new_note );
        editText.setOnKeyListener ( new View.OnKeyListener () {
                                        @Override
                                        public boolean onKey ( View v, int keyCode, KeyEvent event ) {
                                            if ( keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN ) {
                                                if ( ((EditText) v).getLineCount() >= 5 ) {
                                                    return true;
                                                }
                                            }
                                            return false;
                                        }
                                    });

        memoObjects = new MemoObjectList ( this, layout );

        ( (Button) findViewById ( R.id.add ) ).setOnClickListener (
                new View.OnClickListener () {
                    @Override
                    public void onClick ( View v ) {
                        log ( "pushed the Button 'add' " );
                        memoObjects.createNewMemoObject ( editText.getText().toString() );
                        imm.hideSoftInputFromWindow ( layout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS );
                        editText.setText ( "" );
                    }
                }
        );
    }


//    @Override
//    public void onWindowFocusChanged ( boolean hasFocus ) {
//        super.onWindowFocusChanged ( hasFocus );
//
//        windowWidth = layout.getWidth();
//        windowHeight = layout.getHeight();
//    }




    void log ( String msg ) {
        Log.d ( "mainActivity", msg );
    }


//        MemoObject memo = new MemoObject ( this );
//        memo.setupInstance ( this, layout );
//        memo.setText ( "動的ボタン" );
//        layout.addView ( memo );


//    void setToolbar () {
//        toolbar = (Toolbar) findViewById ( R.id.toolbar );
//        toolbar.inflateMenu ( R.menu.toolbar_menu );
//    }

}
