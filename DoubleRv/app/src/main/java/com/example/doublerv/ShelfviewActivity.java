package com.example.doublerv;

import android.content.Intent;
import android.os.Bundle;
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

public class ShelfviewActivity extends AppCompatActivity {

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
        actionBar.setTitle("선반 페이지");

        RecyclerView rv = findViewById(R.id.recyclerView_shelfview);

        HorizontalAdapter horizontalAdapter = new HorizontalAdapter(this, movieList);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(this,3));
        rv.setAdapter(horizontalAdapter);

    }

}
