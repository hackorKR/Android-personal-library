package com.example.first_application;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String[] myDataset = {"1", "2", "3"};

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        queue =Volley.newRequestQueue(this);
        getNews();
        //1. 화면이 로딩 -> 뉴스 정보를받아온다
        //2. 정보 -> 어댑터 넘겨준다
        //3. 어댑터 -> 셋팅
        Log.d("onCreate", "실행은 됫네");
    }

    public void getNews() {

        String url ="http://newsapi.org/v2/top-headlines?country=kr&apiKey=c79fd53c7f794f16b05af232554d273d";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject jsonObj = new JSONObject(response);

                            JSONArray arrayArticles = jsonObj.getJSONArray("articles");

                            //response ->> NewsData Class 분류
                            List<NewsData> news = new ArrayList<>();

                            for(int i = 0, j = arrayArticles.length(); i < j; i++){
                                JSONObject obj = arrayArticles.getJSONObject(i);

                                Log.d("NEWS", obj.toString());

                                NewsData newsData = new NewsData();
                                newsData.setTitle(obj.getString("title"));
                                newsData.setUrlImage(obj.getString("urlToImage"));
                                newsData.setContent(obj.getString("content"));
                                news.add(newsData);
                            }


                            // specify an adapter (see also next example)
                            mAdapter = new MyAdapter(news, NewsActivity.this);
                            recyclerView.setAdapter(mAdapter);

                        }catch(JSONException e){
                            e.printStackTrace();
                            Log.d("안됨", "왜안되는지 알려줘");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
