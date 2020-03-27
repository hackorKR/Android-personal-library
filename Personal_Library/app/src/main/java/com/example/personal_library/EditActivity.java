package com.example.personal_library;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    TextView TextView_editSave;
    EditText EditText_title, EditText_oneSentence, EditText_content;
    ImageView ImageView_editInternet;
    ImageView ImageView_editClip;

    String shared = "file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        EditText_title = findViewById(R.id.EditText_title);
        EditText_oneSentence = findViewById(R.id.EditText_oneSentence);
        EditText_content = findViewById(R.id.EditText_content);

        //저장된 값을 불러오기 위해 같은 네임의 파일을 찾는다
        SharedPreferences sharedPreferences = getSharedPreferences(shared, 0);
        //bookTitle이라는 key값에 저장된 값이 있는지 확인, 아무값도 들어있지 않으면 ""를 반환
        String bookTitle = sharedPreferences.getString("bookTitle", "");
        String bookOneSentence = sharedPreferences.getString("bookOneSentence", "");
        String bookContent = sharedPreferences.getString("bookContent", "");
        EditText_title.setText(bookTitle);
        EditText_oneSentence.setText(bookOneSentence);
        EditText_content.setText(bookContent);

        //Save버튼 명시적으로 MainActivity로 넘어가는 버튼
        TextView_editSave = (TextView)findViewById(R.id.TextView_editSave);
        TextView_editSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditActivity.this, "Explicit Intent", Toast.LENGTH_SHORT).show();

                String bookTitle = EditText_title.getText().toString();
                String bookOneSentence = EditText_oneSentence.getText().toString();
                String bookContent = EditText_content.getText().toString();

                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                intent.putExtra("bookTitle", bookTitle);
                intent.putExtra("bookOneSentence", bookOneSentence);
                intent.putExtra("bookContent", bookContent);
                startActivity(intent);
            }
        });

        //Internet버튼 암시적 인텐트의 예시
        ImageView_editInternet = (ImageView)findViewById(R.id.ImageView_editInternet);
        ImageView_editInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditActivity.this, "Implicit Intent", Toast.LENGTH_SHORT).show();

                //Uri클래스 - 컨텐츠 프로바이터의 접근 규칙, 즉 컨첸츠 프로바이더의 주소
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.naver.com/"));
                startActivity(intent);

//                Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
//                pickContactIntent.setType(Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
//                startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);

            }
        });

        //Clip버튼 정보보내기 (onCreate를 재호출해서 정보를 보내도 지워짐)
        ImageView_editClip = (ImageView)findViewById(R.id.ImageView_editClip);
        ImageView_editClip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditActivity.this, "Implicit Intent", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");

                startActivityForResult(intent, 10);
            }
        });
    }

    protected void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getSharedPreferences(shared, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String bookTitle = EditText_title.getText().toString();
        String bookOneSentence = EditText_oneSentence.getText().toString();
        String bookContent = EditText_content.getText().toString();

        editor.putString("bookTitle", bookTitle);
        editor.putString("bookOneSentence", bookOneSentence);
        editor.putString("bookContent", bookContent);

        editor.commit();
    }

    public void onBackPressed(){
        super.onBackPressed();
    }
}
