package com.example.doublerv;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int BOOK_ADD = 100;
    private static final int REQ_CODE_SELECT_IMAGE = 1;

    //verticalAdapter에 넣을 변수
    private ArrayList<ArrayList<Movie>> allMovieList = new ArrayList();
    private Shelf shelf = new Shelf("책장");

    //책장옮기는 아이템터치헬퍼
    ItemTouchHelper helper;

    //Floating 버튼 변수 선언
    private FloatingActionButton fab_main, fab_sub1, fab_sub2;
    private Animation fab_open, fab_close;
    private boolean isFabOpen = false;

    //어댑터를 여기에 선언해야 notifyData를 다른데에서도 쓸 수 있다.
    private VerticalAdapter verticalAdapter;

    private int shelf_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();


        //Floating Button 구현부
        fab_open =  AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close);

        fab_main = (FloatingActionButton) findViewById(R.id.fab_main);
        fab_sub1 = (FloatingActionButton) findViewById(R.id.fab_sub1);
        fab_sub2 = (FloatingActionButton) findViewById(R.id.fab_sub2);

        fab_main.setOnClickListener(this);
        fab_sub1.setOnClickListener(this);
        fab_sub2.setOnClickListener(this);


        //툴바구현
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        //굳이 이걸해줄 필요없이 툴바 title은 string.xml파일에서 app_name를 바꾸면 된다.
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);


        //수직 리사이클러뷰에 어댑터 붙이기
        RecyclerView view = findViewById(R.id.recyclerViewVertical);
        verticalAdapter = new VerticalAdapter(this, allMovieList);
        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        view.setAdapter(verticalAdapter);


        //ItemTouchHelper 생성/위아래롤 책장이동가능
        helper = new ItemTouchHelper(new ItemTouchHelperCallBack(verticalAdapter));
        helper.attachToRecyclerView(view);


        //하드코딩 부분
        //this.initializeData();
        SharedPreferences pref = getSharedPreferences("count", MODE_PRIVATE);
        int shelfCount = pref.getInt("shelf_count", 0);
        int size = pref.getInt("book_count_size", 0);

        //만약에 어레이리스트로 받을 경우
//        ArrayList<Integer> bookCount = new ArrayList<>();
//        for(int k = 0; k < size; k++){
//            bookCount.add(pref.getInt("book_count"+k, 0));
//        }
        int[] bookCount = new int[size];
        for(int k = 0; k < size; k++){
            bookCount[k] = pref.getInt("book_count"+k, 0);
        }

        for(int i=0; i < shelfCount; i++){
            getSettingShelfData(i);
            Log.d("onCreate에서 일어나는 일", "책장 불러옴");
            for(int j = 0; j < bookCount[i]; j++){
                getSettingBookData(i, j);
                Log.d("onCreate에서 일어나는 일", "책 불러옴");
            }
        }
    }


    //저장되어있는 bookData 정보를 가져오는 코드

    private void getSettingShelfData(int shelf_position){
        SharedPreferences shelfData = getSharedPreferences("shelfData"+shelf_position, MODE_PRIVATE);
        String shelf_title = shelfData.getString("shelf_title", "");
        ArrayList<Movie> movieList = new ArrayList<>();
        allMovieList.add(movieList);
    }

    private void getSettingBookData(int shelf_position, int book_position){
        SharedPreferences bookData = getSharedPreferences("bookData"+shelf_position+book_position, MODE_PRIVATE);
        String book_title = bookData.getString("book_title", "");
        String book_sentence = bookData.getString("book_sentence", "");
        String stringByteArray = bookData.getString("book_poster", null);

        byte[] arr = null;

        if(stringByteArray != null){
            byte[] array = stringByteArray.getBytes();
//            String[] split = stringByteArray.substring(1, stringByteArray.length()-1).split(",");
//            byte[] byteArray = new byte[split.length];
//            for(int i = 0; i <split.length; i++){
//                byteArray[i] = Byte.parseByte(split[i]);
//            }

            arr = array;
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(arr, 0, arr.length);

        allMovieList.get(shelf_position).add(book_position, new Movie(bitmap, book_title, book_sentence));
    }


    public void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();

        for(int i = 0; i < allMovieList.size(); i++){
            setSettingShelfData(i);
            Log.d("onStop에서 일어나는 일", "책장 저장");
            for(int j = 0; j < allMovieList.get(i).size(); j++){
                setSettingBookData(i, j);
                Log.d("onStop에서 일어나는 일", "책 저장");
            }
            verticalAdapter.notifyDataSetChanged();
        }

        int[] book_count = new int[allMovieList.size()];
        for(int i = 0; i< allMovieList.size(); i++){
            book_count[i] = allMovieList.get(i).size();
        }

        int shelf_count = allMovieList.size();

        SharedPreferences preferences = getSharedPreferences("count", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("shelf_count", shelf_count);

        //만약에 어레이리스트로 넣을 경우
//        for(int i = 0; i < book_count.size(); i++){
//            editor.putInt("book_count"+i, book_count.get(i));
//        }
//        editor.putInt("book_count_size", book_count.size());

        for(int i = 0; i < book_count.length; i++){
            editor.putInt("book_count"+i, book_count[i]);
        }
        editor.putInt("book_count_size", book_count.length);

        editor.commit();
    }


    private void setSettingShelfData(int shelf_position) {
        SharedPreferences shelfData = getSharedPreferences("shelfData"+shelf_position,MODE_PRIVATE);
        SharedPreferences.Editor editor = shelfData.edit();
        editor.putString("shelf_title", "책장");
        editor.commit();
    }

    private void setSettingBookData(int shelf_position, int book_position) {
        SharedPreferences bookData = getSharedPreferences("bookData"+shelf_position+book_position, MODE_PRIVATE);
        SharedPreferences.Editor editor = bookData.edit();

        editor.putString("book_title", allMovieList.get(shelf_position).get(book_position).getTitle());
        editor.putString("book_sentence", allMovieList.get(shelf_position).get(book_position).getSentence());

        Bitmap bitmap = allMovieList.get(shelf_position).get(book_position).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        //비트맵->바이트어레이->스트링
        editor.putString("book_poster", Arrays.toString(byteArray));
        editor.commit();
    }


    public void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
    }
    public void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
    }





    public void initializeData()
    {
        ArrayList<Movie> movieList1 = new ArrayList();

        Bitmap book_titan = BitmapFactory.decodeResource(this.getResources(), R.drawable.book_dna);
        Bitmap book_perfect = BitmapFactory.decodeResource(this.getResources(), R.drawable.book_perfect);
        Bitmap book_sayno = BitmapFactory.decodeResource(this.getResources(), R.drawable.book_dna);

        movieList1.add(new Movie(book_titan, "타이탄의 도구들", "상위 20퍼 재주 여러개가 상위 1퍼 재주 하나보다 낫다."));
        movieList1.add(new Movie(book_perfect, "완벽한 공부법", "완벽하게 공부하는 방법은 없다"));
        movieList1.add(new Movie(book_sayno, "세이노의 가르침", "인생이 마음에 들지 않는다면 분노해야할 대상은 세상이 아니라 너 자신이다."));

        allMovieList.add(movieList1);

        ArrayList<Movie> movieList2 = new ArrayList();

        Bitmap book_dna = BitmapFactory.decodeResource(this.getResources(), R.drawable.book_dna);
        Bitmap book_secret= BitmapFactory.decodeResource(this.getResources(), R.drawable.book_secret);
        Bitmap book_sapiens = BitmapFactory.decodeResource(this.getResources(), R.drawable.book_sapiens);

        movieList2.add(new Movie(book_dna, "이기적 유전자", "사람의 행동과 의사결정은 결국에는 유전자의 영향을 받는다."));
        movieList2.add(new Movie(book_secret, "비밀의 언어", "암호학의 역사와 발전을 통찰력있게"));
        movieList2.add(new Movie(book_sapiens, "사피엔스", "인류의 변천사와 미래를 한눈에"));

        allMovieList.add(movieList2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

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
        searchView.setQueryHint("책 제목을 입력해주세요.");
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
                startActivity(new Intent(getApplicationContext(), BookViewActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_main:
                toggleFab();
                break;
            case R.id.fab_sub1:
                toggleFab();
                Toast.makeText(this, "책장 추가", Toast.LENGTH_SHORT).show();
                alertShow();
                break;
            case R.id.fab_sub2:
                toggleFab();
                Toast.makeText(this, "책 추가", Toast.LENGTH_SHORT).show();
                alertShow2();
                break;
        }
    }

    //다이얼로그 책장추가 버튼 메서드
    public void alertShow(){
        View dialogView = getLayoutInflater().inflate(R.layout.alertdialog_shelfadd, null);
        final EditText shelfadd_title = (EditText)dialogView.findViewById(R.id.shelfadd_title);
        final TextView shelf_title =(TextView)findViewById(R.id.shelf_title);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = shelfadd_title.getText().toString();
//                shelf_title.setText(value);
                Toast.makeText(MainActivity.this, "확인 버튼 클릭됨" + value, Toast.LENGTH_SHORT).show();

                //비어있는 무비리스트를 책장에 추가
                ArrayList<Movie> movieList = new ArrayList();
                allMovieList.add(movieList);
                verticalAdapter.notifyDataSetChanged();

                dialog.dismiss();
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "취소 버튼 클릭됨", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //책장에 책을 추가하는 메서드
    public void alertShow2(){
        final List<String> ListItems = new ArrayList<>();
        for(int i= 0; i < allMovieList.size(); i++){
            ListItems.add("책장" + (i+1));
        }
        final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("책장 선택하기");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int pos) {
                String selectedText = items[pos].toString();
                Toast.makeText(MainActivity.this, selectedText, Toast.LENGTH_SHORT).show();

                for(int i = 0; i < allMovieList.size(); i++){
                    if(pos == i){
                        Intent intent = new Intent(MainActivity.this, BookNewActivity.class);
                        shelf_position = i;
                        startActivityForResult(intent, BOOK_ADD);
                        break;
                    }
                }
            }
        });
        builder.show();
    }

    //
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;
        switch (requestCode){
            case REQ_CODE_SELECT_IMAGE:
                Toast.makeText(this, "갤러리에서 이미지 보기만 하는 인텐트", Toast.LENGTH_SHORT).show();
                break;
            case BOOK_ADD:
                Toast.makeText(this, "책추가를 했을시 보이는 인텐트", Toast.LENGTH_SHORT).show();

                String book_title = data.getStringExtra("book_title");
                String book_sentence = data.getStringExtra("book_sentence");

                //이미지 정보가 담겨있는 바이트어레이를 받아서 비트맵으로 전환
                byte[] arr = data.getByteArrayExtra("book_poster");
                Bitmap bitmap = BitmapFactory.decodeByteArray(arr, 0, arr.length);

                //선택한 책장 맨 앞에 내가 입력한 책 정보를 추가
                allMovieList.get(shelf_position).add(0, new Movie(bitmap, book_title, book_sentence));

                //화면 새로고침
                verticalAdapter.notifyDataSetChanged();
                break;
        }
    }

    //Floating버튼 사용
    private void toggleFab() {
        if (isFabOpen) {
            fab_main.setImageResource(R.drawable.ic_add_circle_white);
            fab_sub1.startAnimation(fab_close);
            fab_sub2.startAnimation(fab_close);
            fab_sub1.setClickable(false);
            fab_sub2.setClickable(false);
            isFabOpen = false;
        } else {
            fab_main.setImageResource(R.drawable.ic_close_circle_white);
            fab_sub1.startAnimation(fab_open);
            fab_sub2.startAnimation(fab_open);
            fab_sub1.setClickable(true);
            fab_sub2.setClickable(true);
            isFabOpen = true;
        }
    }
}