package com.example.first_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Item_Data> arrayList;
    private Item_Adapter item_Adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;

    private ArrayList<BookShelf_Data> arrayList2;
    private BookShelf_Adapter bookShelf_Adapter;
    private RecyclerView recyclerView2;
    private LinearLayoutManager linearLayoutManager2;
    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "onCreate 호출 됨",Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_main);

        //툴바구현
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //상태바없애기
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //리사이클러뷰 두개 사용하는 법
//        RecyclerView view = findViewById(R.id.bookshelf_recycler_view);
//        BookShelf_Adapter bookShelfAdapter = new BookShelf_Adapter(this, bookList);
//        view.setHasFixedSize(true);
//        view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //아이템적용시키기
        recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
//        linearLayoutManager = new LinearLayoutManager(this);
        //Grid레이아웃을 사용
        gridLayoutManager = new GridLayoutManager(this,3);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        arrayList = new ArrayList<>();

        item_Adapter = new Item_Adapter(arrayList);
        recyclerView.setAdapter(item_Adapter);

        //책장적용시키기
        recyclerView2 = (RecyclerView) findViewById(R.id.main_recycler_view2);
        linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView2.setLayoutManager(linearLayoutManager2);

        arrayList2 = new ArrayList<>();

        bookShelf_Adapter = new BookShelf_Adapter(arrayList2);
        recyclerView2.setAdapter(bookShelf_Adapter);


        //커스텀 다이얼로그 만들기
        final TextView main_label = (TextView)findViewById(R.id.main_label);
        final TextView main_content = (TextView)findViewById(R.id.main_content);
        final TextView main_sentence = (TextView)findViewById(R.id.main_sentence);
        //커스텀 다이얼로그를 호출할 버튼을 정의한다.
        TextView main_name = (TextView)findViewById(R.id.main_name);

        main_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_Dialog edit_dialog = new Edit_Dialog(MainActivity.this);

                edit_dialog.callFunction(main_label, main_sentence, main_content);
            }
        });


        //메뉴버튼누르면 edit으로
        ImageView main_menu = (ImageView)findViewById(R.id.main_menu);
        main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                startActivity(intent);
            }
        });


        //버튼으로 아이템 만들기
        ImageView main_add = (ImageView)findViewById(R.id.main_add);
        main_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item_Data item_data = new Item_Data(R.mipmap.ic_launcher, "안드로이드", "안드로이드");
                arrayList.add(item_data); //추가하고
                item_Adapter.notifyDataSetChanged(); //페이지 새로고침
            }
        });

//        ImageView main_add = (ImageView)findViewById(R.id.main_add);
//        main_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, EditActivity.class);
//                startActivity(intent);
//            }
//        });

        //버튼으로 책장 만들기
        ImageView main_search = (ImageView)findViewById(R.id.main_search);
        main_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookShelf_Data bookShelf_data = new BookShelf_Data("책장" + count);
                arrayList2.add(bookShelf_data);
                bookShelf_Adapter.notifyDataSetChanged();
                count++;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume 호출 됨",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause 호출 됨",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop 호출 됨",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy 호출 됨",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart 호출 됨",Toast.LENGTH_SHORT).show();
    }

    protected void onRestart(){
        super.onRestart();
        Toast.makeText(this, "onRestart 호출 됨",Toast.LENGTH_SHORT).show();
    }

    //리사이클러뷰 두개 사용하는법
//    public void initializeData(){
//        ArrayList<Item_Data> itemList1 = new ArrayList();
//
//        itemList1.add(new Item_Data(R.mipmap.ic_launcher, "안드로이드", "안드로이드"));
//        itemList1.add(new Item_Data(R.mipmap.ic_launcher, "안드로이드", "안드로이드"));
//        itemList1.add(new Item_Data(R.mipmap.ic_launcher, "안드로이드", "안드로이드"));
//
//        bookList.add(itemList1);
//
//        ArrayList<Item_Data> itemList2 = new ArrayList();
//
//        itemList2.add(new Item_Data(R.mipmap.ic_launcher, "안드로이드", "안드로이드"));
//        itemList2.add(new Item_Data(R.mipmap.ic_launcher, "안드로이드", "안드로이드"));
//        itemList2.add(new Item_Data(R.mipmap.ic_launcher, "안드로이드", "안드로이드"));
//
//        bookList.add(itemList2);
//
//        ArrayList<Item_Data> itemList3 = new ArrayList();
//
//        itemList3.add(new Item_Data(R.mipmap.ic_launcher, "안드로이드", "안드로이드"));
//        itemList3.add(new Item_Data(R.mipmap.ic_launcher, "안드로이드", "안드로이드"));
//        itemList3.add(new Item_Data(R.mipmap.ic_launcher, "안드로이드", "안드로이드"));
//
//        bookList.add(itemList3);
//    }

}
