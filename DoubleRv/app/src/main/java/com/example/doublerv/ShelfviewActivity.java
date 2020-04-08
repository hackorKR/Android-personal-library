package com.example.doublerv;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShelfViewActivity extends AppCompatActivity {

    private ArrayList<Movie> movieList = new ArrayList();
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
        this.initializeData();

        RecyclerView rv = findViewById(R.id.recyclerView_shelfview);

        HorizontalAdapter horizontalAdapter = new HorizontalAdapter(this, movieList);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(this,3));
        rv.setAdapter(horizontalAdapter);


    }

    public void initializeData() {

        Bitmap book_titan = BitmapFactory.decodeResource(this.getResources(), R.drawable.book_titan);
        Bitmap book_perfect = BitmapFactory.decodeResource(this.getResources(), R.drawable.book_perfect);
        Bitmap book_sayno = BitmapFactory.decodeResource(this.getResources(), R.drawable.book_sayno);

        movieList.add(new Movie(book_titan, "타이탄의 도구들", "상위 20퍼 재주 여러개가 상위 1퍼 재주 하나보다 낫다."));
        movieList.add(new Movie(book_perfect, "완벽한 공부법", "완벽하게 공부하는 방법은 없다"));
        movieList.add(new Movie(book_sayno, "세이노의 가르침", "인생이 마음에 들지 않는다면 분노해야할 대상은 세상이 아니라 너 자신이다."));


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
