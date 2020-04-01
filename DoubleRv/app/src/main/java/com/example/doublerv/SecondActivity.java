package com.example.doublerv;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;


import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private Toolbar toolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //뒤로가기 버튼
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //툴바 title
        actionBar.setTitle("Newpage");
    }
}
