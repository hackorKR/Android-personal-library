package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainAc extends AppCompatActivity{
    private static String TAG = "MainActivity";
    Button load, save, delete;
    EditText inputText;
    String filename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        load= (Button) findViewById(R.id.load);
        save = (Button) findViewById(R.id.save);
        delete = (Button) findViewById(R.id.delete);
        inputText = (EditText) findViewById(R.id.inputText);

        load.setOnClickListener(listener);
        save.setOnClickListener(listener);
        delete.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.load:
                    Log.i("TAG", "load 진행");
                    LinearLayout alert_layout1 = (LinearLayout) View.inflate(MainAc.this, R.layout.alert_layout, null);
                    final EditText alert_edit = (EditText) alert_layout1.findViewById(R.id.search_memo);
                    new AlertDialog.Builder(MainAc.this)
                            .setTitle("메모 불러오기")
                            .setMessage("불러올 메모")
                            .setView(alert_layout1)
                            .setPositiveButton("Load", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    filename = alert_edit.getText().toString();
                                    Log.i("TAG", filename);
                                    load(filename);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(MainAc.this,"Load 취소",Toast.LENGTH_SHORT).show();
                                }
                            }).show();
                    break;
                case R.id.save:
                    Log.i("TAG", "save 진행");
                    LinearLayout alert_layout2 = (LinearLayout) View.inflate(MainAc.this, R.layout.alert_layout, null);
                    final EditText alert_edit2 = (EditText) alert_layout2.findViewById(R.id.search_memo);
                    if(filename == null){
                        new AlertDialog.Builder(MainAc.this)
                                .setTitle("메모 저장하기")
                                .setMessage("저장할 이름")
                                .setView(alert_layout2)
                                .setPositiveButton("LOAD", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        filename = alert_edit2.getText().toString();
                                        save(filename);
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(MainAc.this,"save 취소",Toast.LENGTH_SHORT).show();
                                    }
                                }).show();
                    }else{
                        save(filename);
                    }
                    break;
                case R.id.delete:
                    Log.i("TAG", "delete 진행");
                    if(filename == null){
                        Toast.makeText(MainAc.this, "삭제파일이 없습니다.", Toast.LENGTH_SHORT).show();
                    }else {
                        delete(filename);
                        filename = null;
                    }
                    break;
            }
        }
    };

    public void load(String filename){
        FileInputStream fis = null;
        try{
            fis = openFileInput(filename);
            byte[] data = new byte[fis.available()];
            while( fis.read(data) != -1){
            }
            inputText.setText(new String(data));
            Toast.makeText(MainAc.this, "load 완료", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{ if(fis != null) fis.close(); }catch(Exception e){e.printStackTrace();}
        }
    }



    public void save(String filename){
        FileOutputStream fos = null;
        try{
            fos = openFileOutput(filename, Context.MODE_PRIVATE);
            String out = inputText.getText().toString();
            fos.write(out.getBytes());
            Toast.makeText(MainAc.this, "save 완료", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(MainAc.this, "저장할 파일명을 입력하세요", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }finally{
            try{ if(fos != null) fos.close(); }catch(Exception e){e.printStackTrace();}
        }
    }



    public void delete(String filename){
        boolean b = deleteFile(filename);
        if(b){
            Toast.makeText(MainAc.this, "delete 완료", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainAc.this, "delete 실패", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
//menu를 생성해주는 메소드
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
//menu 선택시 리스너 메소등
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.newMemo:
                inputText.setText("");
                filename = null;
                break;
        }
        return false;
    }
}
