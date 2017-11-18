package com.forzametrix.forzametrix.splash;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.os.Handler;
import android.content.Intent;

import com.forzametrix.forzametrix.R;
import com.forzametrix.forzametrix.pager.FragmentPagerActivity;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
     //   fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
*/

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

            /*New Handler to start the Menu-Activity
              and close this Splash Screen after some seconds
             */

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                startActivity(new Intent(SplashActivity.this, FragmentPagerActivity.class));
                finish();
            }

        }, SPLASH_DISPLAY_LENGTH);
    }

}
