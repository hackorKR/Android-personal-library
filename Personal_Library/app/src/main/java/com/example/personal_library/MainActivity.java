package com.example.personal_library;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<MainData> arrayList;
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    String shared = "file2";

    ArrayList<String> bookTitle = new ArrayList<>();
    ArrayList<String> bookOneSentence = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "onCreate 호출 됨",Toast.LENGTH_SHORT).show();

        recyclerView = (RecyclerView)findViewById(R.id.RecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<MainData>();

        mainAdapter = new MainAdapter(arrayList);
        recyclerView.setAdapter(mainAdapter);

        //저장된 값을 불러오기 위해 같은 네임의 파일을 찾는다
        SharedPreferences sharedPreferences = getSharedPreferences(shared, 0);
        //bookTitle이라는 key값에 저장된 값이 있는지 확인, 아무값도 들어있지 않으면 ""를 반환
        for(int i =0; i < arrayList.size(); i++){
            String title = sharedPreferences.getString(bookTitle.get(i), "");
            String oneSentence = sharedPreferences.getString(bookOneSentence.get(i), "");

            MainData mainData = new MainData(R.mipmap.ic_launcher, title, oneSentence);
            arrayList.add(mainData);
            mainAdapter.notifyDataSetChanged();
            Log.d("존재유무: ", "만들어짐");
        }
//        String bookTitle = sharedPreferences.getString("bookTitle", "");
//        String bookOneSentence = sharedPreferences.getString("bookOneSentence", "");

//        EditText_title.setText(bookTitle);
//        EditText_oneSentence.setText(bookOneSentence);

        ImageView ImageView_mainMenu = (ImageView)findViewById(R.id.ImageView_mainMenu);
        ImageView_mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();

                Bundle bundle = intent.getExtras();
                String bookTitle = bundle.getString("bookTitle");
                String bookOneSentence = bundle.getString("bookOneSentence");
                String bookContent = bundle.getString("bookContent");

                MainData mainData = new MainData(R.mipmap.ic_launcher, bookTitle, bookOneSentence);
                arrayList.add(mainData);
                mainAdapter.notifyDataSetChanged();
            }
        });

        ImageView ImageView_mainAdd = (ImageView) findViewById(R.id.ImageView_mainAdd);
        ImageView_mainAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                startActivity(intent);
            }
        });

        ImageView ImageView_mainMglass = (ImageView)findViewById(R.id.ImageView_mainMglass);
        ImageView_mainMglass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainData mainData = new MainData(R.mipmap.ic_launcher, "안드로이드1", "또 한번더!");
                arrayList.add(mainData);
                mainAdapter.notifyDataSetChanged();
            }
        });

    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Toast.makeText(this, "onSaveInstanceState 호출 됨",Toast.LENGTH_SHORT).show();

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

        //shared="file"에 저장하기 위한 코드
        //editor에 key + value를 저장하는 코드
        SharedPreferences sharedPreferences = getSharedPreferences(shared, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        String value = EditText_title.getText().toString();


        for(int i =0; i < arrayList.size(); i++){
            bookTitle.add("bookTitle" + i);
            bookOneSentence.add("bookOneSentence" + i);

            editor.putString(bookTitle.get(i), arrayList.get(i).getTextView_title());
            editor.putString(bookOneSentence.get(i), arrayList.get(i).getTextView_content());
        }
//        editor.putString("bookTitle", value);
        editor.commit();

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

    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "onRestart 호출 됨",Toast.LENGTH_SHORT).show();

//        Intent intent = getIntent();
//        finish();
//        startActivity(intent);
//        Bundle bundle = intent.getExtras();
//        String bookTitle = bundle.getString("bookTitle");
//        String bookOneSentence = bundle.getString("bookOneSentence");
//        String bookContent = bundle.getString("bookContent");
//
//        //Restart를 할 경우 현재 키에 담겨있는 값들이 객체를 만듬
//        MainData mainData = new MainData(R.mipmap.ic_launcher, bookTitle, bookOneSentence);
//        arrayList.add(mainData);
//        mainAdapter.notifyDataSetChanged();
    }



//    //ArrayList를 Json으로 변환하여 SharedPreferences에 String을 저장하는 코드
//    public void setStringArrayPref(Context context, String key, ArrayList<String> values){
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = prefs.edit();
//        JSONArray a = new JSONArray();
//        for(int i = 0; i < values.size(); i++){
//            a.put(values.get(i));
//        }
//        if(!values.isEmpty()){
//            editor.putString(key, a.toString());
//        } else {
//            editor.putString(key, null);
//        }
//        editor.apply();
//    }
//
//    //getStringArrayPref는 SharedPreferences에서 Json형식의 String을 가져와서 다시 AraayList로 변환하는 코드
//    public ArrayList<String> getStringArrayPref(Context context, String key) {
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//        String json = prefs.getString(key, null);
//        ArrayList<String> urls = new ArrayList<String>();
//        if(json != null) {
//            try{
//                JSONArray a = new JSONArray(json);
//                for(int i = 0; i < a.length(); i++) {
//                    String url = a.optString(i);
//                    urls.add(url);
//                }
//            }catch (JSONException e){
//                e.printStackTrace();
//            }
//        }
//        return urls;
//    }
}
