package com.example.first_application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    TextInputEditText TextInputEditText_email, TextInputEditText_password;
    LinearLayout LinearLayout_login;
    String emailOk = "1234@gmail.com";
    String passwordOk = "1234";
    String inputEmail ="";
    String inputpassword = "";


    //main(String[] args)와 같다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputEditText_email    = findViewById(R.id.TextInputEditText_email);
        TextInputEditText_password = findViewById(R.id.TextInputEditText_password);
        LinearLayout_login = findViewById(R.id.LinearLayout_login);

        //1. 값을 가져온다.- 검사(hackorkr@gmail.com / 1234)
        //2. 클릭을 감지한다. -
        //3. 1번의 값을 다음 액티비티로 넘긴다.

        LinearLayout_login.setClickable(false);
        TextInputEditText_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d( "ID", s.toString());
                if (s != null) {
                    inputEmail = s.toString();
                    LinearLayout_login.setClickable(validation());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        TextInputEditText_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d( "PASSWORD", s.toString());
                if (s != null) {
                    inputpassword = s.toString();
                    LinearLayout_login.setClickable(validation());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        LinearLayout_login.setClickable(true);
        LinearLayout_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //onClick
                String email = TextInputEditText_email.getText().toString();
                String password = TextInputEditText_password.getText().toString();

                Intent intent = new Intent(MainActivity.this, LoginResultActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                startActivity(intent);
            }
        });

    }
    public boolean validation(){
        Log.d( "ID/Password", inputEmail + "/" + inputpassword + "/" + (inputEmail.equals(emailOk)) + "/" + inputpassword.equals(passwordOk));
        return inputEmail.equals(emailOk) && inputpassword.equals(passwordOk);
    }
}
