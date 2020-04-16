package com.example.doublerv.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;


import androidx.appcompat.app.AppCompatActivity;

import com.example.doublerv.R;

import java.io.ByteArrayOutputStream;

public class BookViewActivity extends AppCompatActivity {

    protected ImageView poster;
    protected TextView title, sentence, author;
    private Toolbar toolbar;
    protected Button edit_edit;
    private int book_position;
    private int shelf_position;

    private static final int EDIT_CODE = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookview);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //뒤로가기 버튼
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //HorizontalAdapter에서 보내준 정보
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        this.title=findViewById(R.id.bookview_title);
        this.sentence=findViewById(R.id.bookview_sentence);
        this.poster=findViewById(R.id.bookview_poster);
        this.author=findViewById(R.id.bookview_author);

        title.setText(bundle.getString("title"));
        sentence.setText(bundle.getString("sentence"));
        author.setText(bundle.getString("author"));
        shelf_position = bundle.getInt("shelf_position");

        this.book_position = bundle.getInt("book_position");

        //바이트어레이를 받아와서 비트맵으로 바꾸고 이미지뷰에 띄우기
        byte[] arr = intent.getByteArrayExtra("poster");
        Bitmap bitmap = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        poster.setImageBitmap(bitmap);

        //툴바 title
        actionBar.setTitle(title.getText().toString());

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

    //뒤로가기버튼 이전 액티비티로 이동하게 해줌
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작

                //뒤로가기버튼
                //onCreate를 실행시켜야 바뀐 bookData가 적용이 된다.
                Intent home = new Intent(BookViewActivity.this, MainActivity.class);
                startActivity(home);
                finish();
                return true;
            }
            //편집모양 아이콘을 누르면 발생
            case R.id.item_edit:
                Intent intent = new Intent(BookViewActivity.this, BookEditActivity.class);

                Bitmap bitmap = ((BitmapDrawable)poster.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                intent.putExtra("book_title", title.getText().toString());
                intent.putExtra("book_sentence", sentence.getText().toString());
                intent.putExtra("book_poster", byteArray);
                intent.putExtra("book_position", book_position);
                intent.putExtra("book_author", author.getText().toString());
                intent.putExtra("shelf_position", shelf_position);

                startActivityForResult(intent, EDIT_CODE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);

        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK)
            return;
        switch (requestCode){
            case EDIT_CODE:
                title.setText(data.getStringExtra("book_title"));
                sentence.setText(data.getStringExtra("book_sentence"));
                author.setText(data.getStringExtra("book_author"));

                //바이트어레이를 받아와서 비트맵으로 바꾸고 이미지뷰에 띄우기
                byte[] arr = data.getByteArrayExtra("book_poster");
                Bitmap bitmap = BitmapFactory.decodeByteArray(arr, 0, arr.length);
                poster.setImageBitmap(bitmap);

                break;
        }
    }
}
