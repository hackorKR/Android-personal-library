package com.example.doublerv.Activity;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doublerv.Adapter.VerticalAdapter;
import com.example.doublerv.Function.ItemTouchHelperCallBack;
import com.example.doublerv.ClassData.Book;
import com.example.doublerv.R;
import com.example.doublerv.ClassData.Shelf;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int BOOK_ADD = 100;
    private static final int REQ_CODE_SELECT_IMAGE = 1;

    //Adapter
    //verticalAdapter에 넣을 변수
    private ArrayList<ArrayList<Book>> allMovieList = new ArrayList();

    private ArrayList<Shelf> allShelfList = new ArrayList();

    //어댑터를 여기에 선언해야 notifyData를 다른데에서도 쓸 수 있다.
    private VerticalAdapter verticalAdapter;

    //Function
    //책장옮기는 아이템터치헬퍼
    ItemTouchHelper helper;

    //Activity

    //Custom Fab 튼 변수 선언
    private com.github.clans.fab.FloatingActionButton fab_bookAdd, fab_shelfAdd;

    //툴바선언
    Toolbar toolbar;

    //shelf_position를 전역변수를 받음
    //전역변수야 하는 이유는 내가 선택한 책장의 번호가 다른 메서드에도 전달되고 사용되어야 하기 때문이다.
    private int shelf_position;

    //DrawerLayout를 밖에서 선언
    private DrawerLayout drawerLayout;
    private View drawerView;

    //Thread와 핸들러 구현
    Thread thread, thread2;
    boolean isThread = true;
    boolean isThread2 = true;
    Button thread_start, thread_stop;
    TextView main_time;
    //일반 쓰레드에서 요청한 화면처리용 핸들러
    Displayhandler displayhandler;


    //onCreate에는 앱 실행시 가져와야할 데이터와 표시될 설정들을 띄운다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();

        //전체화면인 main_drawLayout 객체 참조
        drawerLayout = (DrawerLayout)findViewById(R.id.main_drawerLayout);
        //Drawer 화면(뷰) 객체 참조
        drawerView = (View)findViewById(R.id.drawer);

        Button btnCloseDrawer = (Button)findViewById(R.id.btn_CloseDrawer);
        btnCloseDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(drawerView);
            }
        });

        //쓰레드 구현
        thread_start = findViewById(R.id.thread_start);
        thread_stop = findViewById(R.id.thread_stop);
        thread_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isThread = true;
                thread = new Thread(){
                    public void run(){
                        while (isThread){
                            try {
                                handler1.sendEmptyMessage(0);
                                sleep(3000);
                                handler2.sendEmptyMessage(0);
                                sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                thread.start();
            }
        });
        thread_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isThread = false;
            }
        });

        //시간구현
        main_time=(TextView)findViewById(R.id.main_time);
        ThreadClass thread2 = new ThreadClass();
        thread2.start();
        displayhandler = new Displayhandler();



        //Custom Fab 구현부
        fab_bookAdd = findViewById(R.id.fab_bookAdd);
        fab_shelfAdd = findViewById(R.id.fab_shelfAdd);
        fab_bookAdd.setOnClickListener(this);
        fab_shelfAdd.setOnClickListener(this);

        //툴바구현
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        //굳이 이걸해줄 필요없이 툴바 title은 string.xml파일에서 app_name를 바꾸면 된다.
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);


        //수직 리사이클러뷰에 어댑터 붙이기
        RecyclerView view = findViewById(R.id.recyclerViewVertical);
        verticalAdapter = new VerticalAdapter(this, allMovieList, allShelfList);
        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        view.setAdapter(verticalAdapter);


        //ItemTouchHelper 생성/위아래롤 책장이동가능
        helper = new ItemTouchHelper(new ItemTouchHelperCallBack(verticalAdapter));
        helper.attachToRecyclerView(view);

        //카운터 파일에서 정보 가져오기
        SharedPreferences pref = getSharedPreferences("count", MODE_PRIVATE);
        int shelfCount = pref.getInt("shelf_count", 0);
        int size = pref.getInt("book_count_size", 0);


        //저장된 책장 별 책들의 숫자를 센 후 bookCount배열에 저장하기
        int[] bookCount = new int[size];
        for(int k = 0; k < size; k++){
            bookCount[k] = pref.getInt("book_count"+k, 0);
        }

        //저장된 책장, 책 불러오기
        for(int i=0; i < shelfCount; i++){
            getSettingShelfData(i);
            Log.d("onCreate에서 일어나는 일", "책장 불러옴");
            for(int j = 0; j < bookCount[i]; j++){
                getSettingBookData(i, j);
                Log.d("onCreate에서 일어나는 일", "책 불러옴");
            }
        }
        verticalAdapter.notifyDataSetChanged();

        //안되면 되게하라
        //선택한 책장 번호를 shared에 저장해서 onCreate되어도
        //번호를 받아올 수 있게 만듬
        SharedPreferences SaveShelfPosition = getSharedPreferences("SaveShelfPosition", MODE_PRIVATE);
        int save_shelf_position = SaveShelfPosition.getInt("save_shelf_position", 0);
        //BookNewSearchActivity에서 보낸 intent를 받는 코드
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        if (data != null) {
            String book_title = data.getString("book_title");
            String book_sentence = data.getString("book_sentence");
            String book_author = data.getString("book_author");
            int book_shelf_position = data.getInt("book_shelf_position");

            //이미지 정보가 담겨있는 바이트어레이를 받아서 비트맵으로 전환
            byte[] arr = data.getByteArray("book_poster");
            Bitmap bitmap = BitmapFactory.decodeByteArray(arr, 0, arr.length);

            //선택한 책장 맨 앞에 내가 입력한 책 정보를 추가
            allShelfList.get(save_shelf_position).bookList.add(0, new Book(bitmap, book_title, book_author, book_sentence, save_shelf_position));

            //화면 새로고침
            verticalAdapter.notifyDataSetChanged();
        }
    }

    //화면과 관련된 작업에 필요한 데이터를 가져온다.(이곳에서)
    class ThreadClass extends Thread{
        public void run(){
            int x = 10;
            int y = 20;
            while(isThread2){
                long now = System.currentTimeMillis();
                SystemClock.sleep(100);
                displayhandler.sendEmptyMessage(0);
//                SystemClock.sleep(300);
//                displayhandler.sendEmptyMessage(1);
                //핸들러 요청을 할 때 화면 처리를 위한 데이터를 전달한다.
//                SystemClock.sleep(100);
//                Message msg = new Message();
//                msg.what = 2;
//                msg.arg1 = ++x; //정수값
//                msg.arg2 = ++y;
//                msg.obj = now;

//                displayhandler.sendMessage(msg);
            }
        }
    }

    //화면에 관련된 작업만 한다.
    class Displayhandler extends Handler{
        //개발자가 발생시킨 쓰래드에서 화면에 관련된 처리를 하기 위해 작업을 요청하면
        //자동으로 호출되는 메서드(메인 쓰래드)
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String getTime = simpleDate.format(date);
            main_time.setText("날짜와 시간: " + getTime);
//            switch (msg.what){
//                case 0:
//                    main_time.setText("핸들러 작업1");
//                    break;
//                case 1:
//                    main_time.setText("날짜와 시간: " + getTime);
//                    break;
//                case 2:
//                    main_time.setText(msg.arg1 + ", " + msg.arg2 + ", " + msg.obj);
//        }
        }
    }


    private Handler handler1 = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            toolbar.setTitle("나만의 도서관");
        }
    };

    private Handler handler2 = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            toolbar.setTitle("Personal Library");
        }
    };
    //생명주기 토스트 찍기
    public void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
        verticalAdapter.notifyDataSetChanged();
    }
    public void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
        verticalAdapter.notifyDataSetChanged();
    }
    public void onDestroy() {
        super.onDestroy();
        isThread2 = false;
    }


    //데이터가 저장되는 순간
    public void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
    }

    public void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();

        //책장과 책을 SharedPreference에 저장하는 코드
        for(int i = 0; i < allShelfList.size(); i++){
            setSettingShelfData(i);
            Log.d("onStop에서 일어나는 일", "책장 저장");
            for(int j = 0; j < allShelfList.get(i).bookList.size(); j++){
                setSettingBookData(i, j);
                Log.d("onStop에서 일어나는 일", "책 저장");
            }
            verticalAdapter.notifyDataSetChanged();
        }

        int[] book_count = new int[allShelfList.size()];
        for(int i = 0; i< allShelfList.size(); i++){
            book_count[i] = allShelfList.get(i).bookList.size();
        }

        SharedPreferences preferences = getSharedPreferences("count", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        //shelf_count 저장
        editor.putInt("shelf_count", allShelfList.size());

        //book_count 번호를 붙여서 저장
        for(int i = 0; i < book_count.length; i++){
            editor.putInt("book_count"+i, book_count[i]);
        }

        //book_count_size 저장
        editor.putInt("book_count_size", book_count.length);

        editor.commit();
    }

    //저장되어있는 ShelfData 정보를 가져오는 코드
    private void getSettingShelfData(int shelf_position){
        SharedPreferences shelfData = getSharedPreferences("shelfData" + shelf_position, MODE_PRIVATE);
        String shelf_title = shelfData.getString("shelf_title", "");
        Shelf shelf = new Shelf(shelf_title);
//        ArrayList<Book> bookList = new ArrayList<>();
//        allMovieList.add(bookList);

        allShelfList.add(shelf);
    }

    //저장되어있는 BookData 정보를 가져오는 코드
    private void getSettingBookData(int shelf_position, int book_position){
        SharedPreferences bookData = getSharedPreferences("bookData" + shelf_position + book_position, MODE_PRIVATE);
        String book_title = bookData.getString("book_title", "");
        String book_sentence = bookData.getString("book_sentence", "");
        String stringByteArray = bookData.getString("book_poster", null);
        String book_author = bookData.getString("book_author", "");
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

        allShelfList.get(shelf_position).bookList.add(book_position, new Book(bitmap, book_title, book_author, book_sentence, shelf_position));
    }


    //shelfData를 SharedPreference에 저장
    private void setSettingShelfData(int shelf_position) {
        SharedPreferences shelfData = getSharedPreferences("shelfData"+shelf_position, MODE_PRIVATE);
        SharedPreferences.Editor editor = shelfData.edit();
        editor.putString("shelf_title", allShelfList.get(shelf_position).shelf_title);
        editor.commit();
    }

    //bookData를 SharedPreference에 저장
    private void setSettingBookData(int shelf_position, int book_position) {
        SharedPreferences bookData = getSharedPreferences("bookData" + shelf_position + book_position, MODE_PRIVATE);
        SharedPreferences.Editor editor = bookData.edit();

        editor.putString("book_title", allShelfList.get(shelf_position).bookList.get(book_position).getTitle());
        editor.putString("book_sentence", allShelfList.get(shelf_position).bookList.get(book_position).getSentence());
        editor.putString("book_author", allShelfList.get(shelf_position).bookList.get(book_position).getAuthor());

        Bitmap bitmap = allShelfList.get(shelf_position).bookList.get(book_position).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        //비트맵->바이트어레이->스트링
        editor.putString("book_poster", Arrays.toString(byteArray));
        editor.commit();
    }

    //툴바의 메뉴 레이아웃을 가져오고 검색아이콘인 돋보기를 누르면 일어나는 이벤트 처리하는 곳
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        //검색 버튼 클릭했을 때 searchview 길이 꽉파게 늘려주기
        SearchView searchView = (SearchView)menu.findItem(R.id.item_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        //검색 버튼 클릭했을 때 searchableview에 힌트 추가
        searchView.setQueryHint("책 제목을 입력해주세요.");
        //검색 리스너 추가
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //검색어 입력시 이벤트 제어
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(), "검색을 완료했습니다.", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("searchview에 글 작성시", "입력중입니다.");
                return false;
            }
        });

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

        searchView.setIconifiedByDefault(true);
        return true;
        //setQueryHint(CharSequence) --> 쿼리 필드가 비어있을 때 나타나는 문장 설정
        //setOnQueryTextListener(SearchView.OnQueryTextListener listener) --> 쿼리 변경시 사용자 액션의 listener 설정
        //setSearchableInfo(SearchableInfo searchable) --> SearchableInfo 설정
        //setIconifiedByDefault(boolean iconified) --> 검책창의 기본 상태 설정
        //true: 필드가 보임, false: 아이콘이 보임
    }

    //툴바의 기능들을 클릭시 나타나는 이벤트
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(drawerView);
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
        }
        return super.onOptionsItemSelected(item);
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
                /*allShelfList.get(shelf_position).setShelf_title(value);
                shelf_title.setText(value);*/

                Toast.makeText(MainActivity.this, "확인 버튼 클릭됨" + value, Toast.LENGTH_SHORT).show();

                //비어있는 책장리스트를 책장에 추가
                Shelf shelf = new Shelf(value); //입력한 이름을 받아 책장을 만듬
                allShelfList.add(shelf); //책장 리스트에 그 이름을 가진 책장이 추가됨
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
    //shelf_position의 값을 설정하는 메서드이다.
    public void alertShow2(){
        final List<String> ListItems = new ArrayList<>();
        for(int i= 0; i < allShelfList.size(); i++){
            ListItems.add(allShelfList.get(i).shelf_title);
        }
        final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("책장 선택하기");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int pos) {
                String selectedText = items[pos].toString();
                Toast.makeText(MainActivity.this, selectedText, Toast.LENGTH_SHORT).show();

                for(int i = 0; i < allShelfList.size(); i++){
                    if(pos == i){
                        Intent intent = new Intent(MainActivity.this, BookNewActivity.class);
                        shelf_position = i;
                        //BookSearch를 위한 책장 번호 저장
                        SharedPreferences SaveShelfPosition = getSharedPreferences("SaveShelfPosition", MODE_PRIVATE);
                        SharedPreferences.Editor editor = SaveShelfPosition.edit();
                        editor.putInt("save_shelf_position", i);
                        editor.commit();
                        startActivityForResult(intent, BOOK_ADD);
                        break;
                    }
                }
            }
        });
        builder.show();
    }

    //startActivityForResult 돌아온 정보를 처리하는 곳
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;
        switch (requestCode){
            //없앨 기능이기 때문에 툴바 오른쪽 메뉴를 제거하면 없애도됨
            case REQ_CODE_SELECT_IMAGE:
                Toast.makeText(this, "갤러리에서 이미지 보기만 하는 인텐트", Toast.LENGTH_SHORT).show();
                break;
            //책을 추가하는 메서드에서 보낸 startActivityForResult 결과를 처리하는 곳
            case BOOK_ADD:
                Toast.makeText(this, "책추가를 했을시 보이는 인텐트", Toast.LENGTH_SHORT).show();

                String book_title = data.getStringExtra("book_title");
                String book_sentence = data.getStringExtra("book_sentence");
                String book_author = data.getStringExtra("book_author");

                //이미지 정보가 담겨있는 바이트어레이를 받아서 비트맵으로 전환
                byte[] arr = data.getByteArrayExtra("book_poster");
                Bitmap bitmap = BitmapFactory.decodeByteArray(arr, 0, arr.length);

                //선택한 책장 맨 앞에 내가 입력한 책 정보를 추가
                allShelfList.get(shelf_position).bookList.add(0, new Book(bitmap, book_title, book_author, book_sentence, shelf_position));

                //화면 새로고침
                verticalAdapter.notifyDataSetChanged();
                break;
        }
    }

    //Floting 버튼 클릭 이벤트
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //case R.id.fab_main:
            //toggleFab();
            //break;
            case R.id.fab_shelfAdd:
                Toast.makeText(this, "책장 추가", Toast.LENGTH_SHORT).show();
                alertShow();
                break;
            case R.id.fab_bookAdd:
                Toast.makeText(this, "책 추가", Toast.LENGTH_SHORT).show();
                alertShow2();
                break;
        }
    }
}