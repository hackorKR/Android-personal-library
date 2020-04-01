package com.example.first_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class EditActivity extends AppCompatActivity {

    ImageView edit_save, edit_exit, edit_attachment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edit_save = (ImageView)findViewById(R.id.edit_save);
        edit_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        edit_exit = (ImageView)findViewById(R.id.edit_exit);
        edit_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        edit_attachment = (ImageView)findViewById(R.id.edit_attachment);
        edit_attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditActivity.this, Environment.getExternalStorageDirectory().getAbsolutePath().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

//    public void OnClickHandler(View view){
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.putExtra("bookshelf_data", bookShelf_data);
//        startActivity(intent);
//    }
}
