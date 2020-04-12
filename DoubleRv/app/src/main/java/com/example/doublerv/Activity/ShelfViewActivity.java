package com.example.doublerv.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doublerv.Adapter.HorizontalAdapter;
import com.example.doublerv.ClassData.Book;
import com.example.doublerv.R;

import java.util.ArrayList;

public class ShelfViewActivity extends AppCompatActivity {

    private ArrayList<Book> bookList = new ArrayList();
    protected ImageView poster;
    protected EditText title, sentence;
    private Toolbar toolbar;
    protected Button edit_edit;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelfview);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //뒤로가기 버튼
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //툴바 title
        actionBar.setTitle("책장 페이지");

        //하드코딩 부분
        //this.initializeData();

        //리사이클러뷰 붙이는 곳
        RecyclerView rv = findViewById(R.id.recyclerView_shelfview);
        HorizontalAdapter horizontalAdapter = new HorizontalAdapter(this, bookList);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(this,3));
        rv.setAdapter(horizontalAdapter);

        //Intent 받아오기 verticalAdapter에서 보내는 shelf의 포지션
        Intent intent = getIntent();
        int shelf_position = intent.getIntExtra("shelf_position", 0);

        SharedPreferences pref = getSharedPreferences("count", MODE_PRIVATE);
        int size = pref.getInt("book_count_size", 0);

        //저장된 책장 별 책들의 숫자를 센 후 bookCount배열에 저장하기
        int[] bookCount = new int[size];
        for(int k = 0; k < size; k++){
            bookCount[k] = pref.getInt("book_count"+k, 0);
        }

        //저장된 책장, 책 불러오기
        Log.d("onCreate에서 일어나는 일", "책장 불러옴");
        //책의 숫자를 책장의 번호에 기반해서 불러옴
        //shelf_position에 해당하는 bookCount만큼 반복하는 코드이다.
        for(int j = 0; j < bookCount[shelf_position]; j++){
            getShelfBookData(shelf_position, j);
//            Bitmap book_perfect = BitmapFactory.decodeResource(this.getResources(), R.drawable.book_perfect);
//            movieList.add(new Movie(book_perfect, "완벽한 공부법", "완벽하게 공부하는 방법은 없다"));
            Log.d("onCreate에서 일어나는 일", "책 불러옴");
        }


    }
    //저장되어있는 BookData 정보를 가져오는 코드
    private void getShelfBookData(int shelf_position, int book_position){
        SharedPreferences bookData = getSharedPreferences("bookData" + shelf_position + book_position, MODE_PRIVATE);
        String book_title = bookData.getString("book_title", "");
        String book_sentence = bookData.getString("book_sentence", "");
        String stringByteArray = bookData.getString("book_poster", null);
        Log.d("onCreate에서 일어남", stringByteArray);
        byte[] arr = null;

        if(stringByteArray != null){

            byte[] array = stringByteArray.getBytes();
            Log.e("onCreate에서 일어남", array.toString());

            String[] stringByteArrays = stringByteArray.substring(1, stringByteArray.length()-1).replace(" ", "").split(",");
            byte[] byteArray = new byte[stringByteArrays.length];
            for(int i = 0; i <stringByteArrays.length; i++){
                byteArray[i] = Byte.parseByte(stringByteArrays[i]);
            }

            arr = byteArray;
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(arr, 0, arr.length);

        bookList.add(book_position, new Book(bitmap, book_title, book_sentence));
    }


    public void initializeData() {

        Bitmap book_titan = BitmapFactory.decodeResource(this.getResources(), R.drawable.book_titan);
        Bitmap book_perfect = BitmapFactory.decodeResource(this.getResources(), R.drawable.book_perfect);
        Bitmap book_sayno = BitmapFactory.decodeResource(this.getResources(), R.drawable.book_sayno);

        bookList.add(new Book(book_titan, "타이탄의 도구들", "상위 20퍼 재주 여러개가 상위 1퍼 재주 하나보다 낫다."));
        bookList.add(new Book(book_perfect, "완벽한 공부법", "완벽하게 공부하는 방법은 없다"));
        bookList.add(new Book(book_sayno, "세이노의 가르침", "인생이 마음에 들지 않는다면 분노해야할 대상은 세상이 아니라 너 자신이다."));


    }

    //뒤로가기버튼 이전 액티비티로 이동
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
