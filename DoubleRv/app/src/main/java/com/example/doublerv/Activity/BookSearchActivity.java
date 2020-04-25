package com.example.doublerv.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doublerv.Adapter.ItemSearchAdapter;
import com.example.doublerv.ClassData.Book;
import com.example.doublerv.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookSearchActivity extends AppCompatActivity {

    private static ArrayList<Book> bookList = new ArrayList();

    private Toolbar toolbar;
    private Button searchBook_ok, searchBook_search;
    private EditText searchBook_title;
    private int shelf_position;

    private Handler handler;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchbook);




        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //뒤로가기 버튼
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //툴바 title
        actionBar.setTitle("NAVER 책 API 검색");

        //리사이클러뷰
        RecyclerView rv = findViewById(R.id.recyclerView_searchview);
        final ItemSearchAdapter itemSearchAdapter = new ItemSearchAdapter(this, bookList);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(itemSearchAdapter);

        //activity_newbook에서 보낸 intent받음
        final Intent intent = getIntent();

        //시작시 리사이클러뷰를 비우고 데이터 새로고침
        bookList.clear();
        itemSearchAdapter.notifyDataSetChanged();

        searchBook_title = findViewById(R.id.searchbook_title);

        searchBook_search = findViewById(R.id.searchbook_search);
        searchBook_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String keyword = searchBook_title.getText().toString();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        bookList.clear();
                        getNaverBookSearch(keyword);

                    }
                });thread.start();
                itemSearchAdapter.notifyDataSetChanged();
            }
        });
    }


    private void getNaverBookSearch(String keyword) {
        String clientId = "e5_pqW2q4brVx8PFe81s"; //애플리케이션 클라이언트 아이디값"
        String clientSecret = "eB482tTPVN"; //애플리케이션 클라이언트 시크릿값"

        String text = null;
        try {
            text = URLEncoder.encode(keyword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/book.json?query=" + text+ "&display=10" + "&start=1";    // json 결과

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders);

        System.out.println(responseBody);
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        BufferedReader lineReader = null;
        try {
            lineReader = new BufferedReader(streamReader);
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
                responseBody.append("\n");
            }

            //JSON String에서 필요한 값만 추출하기
            jsonParsing(responseBody.toString());


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

    private static void jsonParsing(String json){
        try{
            JSONObject jsonObject = new JSONObject(json);
            JSONArray bookArray = jsonObject.getJSONArray("items");

            for(int i = 0; i < bookArray.length(); i++){
                JSONObject bookObject = bookArray.getJSONObject(i);

                String imageURL = bookObject.getString("image");
                String title = bookObject.getString("title").replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", "");
                String author = bookObject.getString("author").replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", "");

                Log.e("imageURL", imageURL);
                Log.e("title", title);
                Log.e("author", author);

                URL url = new URL(imageURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);

                bookList.add(new Book(myBitmap, title, author, "", 0));
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
}
