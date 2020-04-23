package com.example.doublerv.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.doublerv.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookEditActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView poster;
    private EditText title, sentence, author;
    private Button save;
    private final int GET_GALLERY_IMAGE =200;
    private int shelf_position = 0;
    private int book_position = 0;
    private String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newbook);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //뒤로가기 버튼
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //툴바 title
        actionBar.setTitle("책 편집 페이지");

        title = findViewById(R.id.newbook_title);
        sentence = findViewById(R.id.newbook_sentence);
        poster = (ImageView)findViewById(R.id.newbook_image);
        author = findViewById(R.id.newbook_author);

        //BookView에서 오는 intent받기
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();


        title.setText(bundle.getString("book_title"));
        sentence.setText(bundle.getString("book_sentence"));
        this.book_position = (bundle.getInt("book_position"));
        author.setText(bundle.getString("book_author"));
        shelf_position = bundle.getInt("shelf_position");

        //이미지 정보가 담겨있는 바이트어레이를 받아서 비트맵으로 전환
        byte[] arr = intent.getByteArrayExtra("book_poster");
        Bitmap bitmap = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        poster.setImageBitmap(bitmap);


        //저장하기 눌렀을때 정보를 보내는 코드
        save = findViewById(R.id.newbook_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                Bitmap bitmap = ((BitmapDrawable)poster.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                intent.putExtra("book_title", title.getText().toString());
                intent.putExtra("book_sentence", sentence.getText().toString());
                intent.putExtra("book_poster", byteArray);
                intent.putExtra("book_position", book_position);
                intent.putExtra("book_author", author.getText().toString());

                SharedPreferences bookData = getSharedPreferences("bookData" + shelf_position + book_position, MODE_PRIVATE);
                SharedPreferences.Editor editor = bookData.edit();

                editor.putString("book_title", title.getText().toString());
                editor.putString("book_sentence", sentence.getText().toString());
                editor.putString("book_author", author.getText().toString());

                //비트맵->바이트어레이->스트링
                editor.putString("book_poster", Arrays.toString(byteArray));
                editor.commit();

                setResult(RESULT_OK, intent);
                finish();
            }
        });


        //이미지를 눌렀을 때 갤러리에서 사진가져오는 버튼
        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d("Tag", "권한 설정 완료");
                    } else {
                        Log.d("Tag", "권한 설정 요청");
                        ActivityCompat.requestPermissions(BookEditActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                }

                final List<String> ListItems = new ArrayList<>();
                ListItems.add("카메라로 찍어서 가져오기");
                ListItems.add("갤러리에서 사진 가져오기");
                final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);

                AlertDialog.Builder builder = new AlertDialog.Builder(BookEditActivity.this);
                builder.setTitle("사진 가져오기");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int pos) {
                        String selectedText = items[pos].toString();
                        Toast.makeText(BookEditActivity.this, selectedText, Toast.LENGTH_SHORT).show();
                        switch (pos){
                            case 0:
                                //처음 액티비티가 생성되는 부분인 onCreate에서 사용자에게 permission을 받음
                                takeCameraAction();
                                break;

                            case 1:
                                takeAlbumAction();
                                break;
                        }
                    }
                });
                builder.show();
            }
        });
    }


    //1. 카메라로 사진찍기
    protected void takeCameraAction(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_TAKE_PHOTO);
    }

    //2. 앨범에서 이미지 가져오기
    protected void takeAlbumAction(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, GET_GALLERY_IMAGE);
    }



    //사진을 가져온 후 startActivityForResult에 대한 응답
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //&& data.hasExtra("data") 이전 파일 저장하지 않을 때는 이것도 조건에 썻었음

        if(resultCode != RESULT_OK)
            return;
        switch (requestCode){

            //1. 카메라로 찍었을 때 처리 과정
            case REQUEST_TAKE_PHOTO:
            {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                if (bitmap != null) {
                    poster.setImageBitmap(bitmap);
                }
            }

            //2. 갤러리에서 사진을 가져올때 처리과정
            //이해해야한다. Uri사용하는 법
            case GET_GALLERY_IMAGE:
            {
                if(data != null && data.getData() != null){
                    Uri selectImageUri = data.getData();
                    poster.setImageURI(selectImageUri);
                }
            }
        }

    }
}
