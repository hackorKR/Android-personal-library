package com.example.personal_library;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

public class LoadingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}

//       setContentView(R.layout.activity_loading);
//        startLoading();
//    }
//    private void startLoading(){
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            public void run(){
//                finish();
//            }
//        },2500);
//    }

