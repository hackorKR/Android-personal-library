package com.example.doublerv;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_SELECT_IMAGE = 1;

    private ArrayList<ArrayList<Movie>> allMovieList = new ArrayList();
    private Button nextButton, shelfAdd;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //툴바구현
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        //굳이 이걸해줄 필요없이 string.xml파일에서 app_name를 바꾸면 된다.
//        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);


        this.initializeData();
        RecyclerView view = findViewById(R.id.recyclerViewVertical);

        final VerticalAdapter verticalAdapter = new VerticalAdapter(this, allMovieList);

        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        view.setAdapter(verticalAdapter);

        shelfAdd=findViewById(R.id.main_shelfadd);
        shelfAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Movie> movieList = new ArrayList();
                allMovieList.add(movieList);
                verticalAdapter.notifyDataSetChanged();
            }
        });

    }

    public void initializeData()
    {
        ArrayList<Movie> movieList1 = new ArrayList();

        movieList1.add(new Movie(R.drawable.book_titan, "타이탄의 도구들", "상위 20퍼 재주 여러개가 상위 1퍼 재주 하나보다 낫다."));
        movieList1.add(new Movie(R.drawable.book_perfect, "완벽한 공부법", "완벽하게 공부하는 방법은 없다"));
        movieList1.add(new Movie(R.drawable.book_sayno, "세이노의 가르침", "인생이 마음에 들지 않는다면 분노해야할 대상은 세상이 아니라 너 자신이다."));

        allMovieList.add(movieList1);

        ArrayList<Movie> movieList2 = new ArrayList();

        movieList2.add(new Movie(R.drawable.book_dna, "이기적 유전자", "사람의 행동과 의사결정은 결국에는 유전자의 영향을 받는다."));
        movieList2.add(new Movie(R.drawable.book_secret, "비밀의 언어", "암호학의 역사와 발전을 통찰력있게"));
        movieList2.add(new Movie(R.drawable.book_sapiens, "사피엔스", "인류의 변천사와 미래를 한눈에"));


        allMovieList.add(movieList2);

        ArrayList<Movie> movieList3 = new ArrayList();

        movieList3.add(new Movie(R.drawable.book_titan, "타이탄의 도구들", "상위 20퍼 재주 여러개가 상위 1퍼 재주 하나보다 낫다."));
        movieList3.add(new Movie(R.drawable.book_perfect, "완벽한 공부법", "완벽하게 공부하는 방법은 없다"));
        movieList3.add(new Movie(R.drawable.book_sayno, "세이노의 가르침", "인생이 마음에 들지 않는다면 분노해야할 대상은 세상이 아니라 너 자신이다."));

        allMovieList.add(movieList3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(MainActivity.this, "Search is Expanded", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(MainActivity.this, "Search is Collapse", Toast.LENGTH_SHORT).show();
                return true;
            }
        };
        menu.findItem(R.id.item_search).setOnActionExpandListener(onActionExpandListener);
        SearchView searchView = (SearchView)menu.findItem(R.id.item_search).getActionView();
        searchView.setQueryHint("Search Data here...");
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.item_gallery:
                Toast.makeText(this, "Gallery is pressed", Toast.LENGTH_SHORT).show();
                //갤러리로 가는 인텐트
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);

                break;
            case R.id.item_share:
                Toast.makeText(this, "share is pressed", Toast.LENGTH_SHORT).show();
                //공유할 수 있는 프로그램을 여는 인텐트
                Intent intent2 = new Intent(android.content.Intent.ACTION_SEND);
                intent2.setType("text/plain");
                String text = "원하는 책을 선택해주세요";
                intent2.putExtra(Intent.EXTRA_TEXT, text);
                Intent chooser = Intent.createChooser(intent2, "친구에게 공유하기");
                startActivity(chooser);

                break;
            case R.id.item_setting:
                Toast.makeText(this, "Setting is pressed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_Newpage:
                Toast.makeText(this, "Newpage is pressed", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), EditActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}