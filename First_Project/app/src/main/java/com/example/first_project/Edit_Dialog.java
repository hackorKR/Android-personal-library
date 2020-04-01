package com.example.first_project;


import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-08-07.
 */

public class Edit_Dialog{

    private Context context;

    public Edit_Dialog(Context context) {
        this.context = context;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(final TextView main_label,final TextView main_sentence, final TextView main_content) {


        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.activity_edit_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final EditText edit_dialog_title = (EditText) dlg.findViewById(R.id.edit_dialog_title);
        final EditText edit_dialog_content = (EditText) dlg.findViewById(R.id.edit_dialog_content);
        final EditText edit_dialog_sentence = (EditText) dlg.findViewById(R.id.edit_dialog_sentence);
        final Button edit_dialog_ok = (Button) dlg.findViewById(R.id.edit_dialog_ok);
        final Button edit_dialog_cancel = (Button) dlg.findViewById(R.id.edit_dialog_cancel);

        edit_dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // '확인' 버튼 클릭시 메인 액티비티에서 설정한 main_label에
                // 커스텀 다이얼로그에서 입력한 메시지를 대입한다.
                main_label.setText(edit_dialog_title.getText().toString());
                Toast.makeText(context, "\"" +  edit_dialog_title.getText().toString() + "\" 을 입력하였습니다.", Toast.LENGTH_SHORT).show();
                main_sentence.setText(edit_dialog_sentence.getText().toString());
                Toast.makeText(context, "\"" +  edit_dialog_sentence.getText().toString() + "\" 을 입력하였습니다.", Toast.LENGTH_SHORT).show();
                main_content.setText(edit_dialog_content.getText().toString());
                Toast.makeText(context, "\"" +  edit_dialog_content.getText().toString() + "\" 을 입력하였습니다.", Toast.LENGTH_SHORT).show();

                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
        edit_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "취소 했습니다.", Toast.LENGTH_SHORT).show();

                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
    }
}
    
