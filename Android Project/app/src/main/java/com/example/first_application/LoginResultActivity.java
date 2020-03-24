package com.example.first_application;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Intent;

public class LoginResultActivity extends AppCompatActivity {

    TextView TextView_get;

    //main(String[] args)와 같다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView_get = findViewById(R.id.TextView_get);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        String email = bundle.getString("email");
        String password = bundle.getString("password");

        TextView_get.setText(email + " / " + password);
    }
}
