package com.hpzhang.radarscan;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private RadarView radarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radarView = (RadarView) findViewById(R.id.radarView);

        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                radarView.startRotateAnimation();
                return false;
            }
        });
        handler.sendEmptyMessageDelayed(1, 2000);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
