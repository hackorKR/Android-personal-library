package com.example.doublerv;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;


import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    protected ImageView poster;
    protected EditText title, sentence;
    private Toolbar toolbar;
    protected Button edit_edit;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //뒤로가기 버튼
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //툴바 title
        actionBar.setTitle("책 정보 페이지");

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        this.title=findViewById(R.id.edit_title);
        this.sentence=findViewById(R.id.edit_sentence);
        this.poster=findViewById(R.id.edit_poster);

        title.setText(bundle.getString("title"));
        sentence.setText(bundle.getString("sentence"));
        poster.setImageResource(bundle.getInt("poster"));

        //수정버튼기능
//        this.edit_edit=findViewById(R.id.edit_edit);
//        edit_edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(EditActivity.this, MainActivity.class);
//                intent.putExtra("Edit_title", title.getText());
//                intent.putExtra("Edit_sentence", sentence.getText());
//                startActivity(intent);
//            }
//        });
    }
}
