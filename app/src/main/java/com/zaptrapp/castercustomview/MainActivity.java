package com.zaptrapp.castercustomview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    TimerView mTimerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimerView = (TimerView) findViewById(R.id.timer);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTimerView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTimerView.stop();
    }
}
