package com.example.first_application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    TextInputEditText TextInputEditText_email, TextInputEditText_password;
    Button Button_login;

    //main(String[] args)와 같다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputEditText_email    = findViewById(R.id.TextInputEditText_email);
        TextInputEditText_password = findViewById(R.id.TextInputEditText_password);
        Button_login               = findViewById(R.id.Button_login);

        //1. 값을 가져온다.
        //2. 클릭을 감지한다.
        //3. 1번의 값을 다음 액티비티로 넘긴다.


        Button_login.setClickable(true);
        Button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = TextInputEditText_email.getText().toString();
                String password = TextInputEditText_password.getText().toString();


            }
        });

    }
}
