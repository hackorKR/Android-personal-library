package com.example.doublerv.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doublerv.ClassData.Shelf;
import com.example.doublerv.Function.ItemTouchHelperListener;
import com.example.doublerv.ClassData.Book;
import com.example.doublerv.R;
import com.example.doublerv.Activity.ShelfViewActivity;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.VerticalViewHolder> implements ItemTouchHelperListener {

    private ArrayList<ArrayList<Book>> AllBookList;
    private Context mContext;

    private ArrayList<Shelf> ShelfList;
//    private ArrayList<ArrayList<Book>

    public VerticalAdapter(Context context, ArrayList<ArrayList<Book>> data, ArrayList<Shelf> shelfList)
    {
        this.mContext = context;
        this.AllBookList = data;

        this.ShelfList = shelfList;
    }

    public class VerticalViewHolder extends RecyclerView.ViewHolder{
        protected RecyclerView recyclerView;
        protected TextView shelf_title;
//        protected Button shelfItem_add;

        protected ImageView shelfItem_edit;

        public VerticalViewHolder(@NonNull View view)
        {
            super(view);
            this.recyclerView = (RecyclerView)itemView.findViewById(R.id.recyclerView_horizontal);
            this.shelf_title = (TextView)itemView.findViewById(R.id.shelf_title);
//            this.shelfItem_add = (Button)itemView.findViewById(R.id.shelfItem_add);

            this.shelfItem_edit = (ImageView)itemView.findViewById(R.id.shelfItem_add);
        }

    }

    @NonNull
    @Override
    public VerticalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_vertical, viewGroup, false);
        return new VerticalAdapter.VerticalViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull final VerticalAdapter.VerticalViewHolder verticalViewHolder, final int position) {

        //HorizontalAdapter 를 가져와 여기 리사이클러뷰에 붙인다.
        //HorizontalAdapter adapter = new HorizontalAdapter(mContext, AllBookList.get(position));
        //ShelfList에서 가져오는 bookList
        HorizontalAdapter asd = new HorizontalAdapter(mContext, ShelfList.get(position).bookList);

        verticalViewHolder.recyclerView.setHasFixedSize(true);
        verticalViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,false));
        verticalViewHolder.recyclerView.setAdapter(asd);

        verticalViewHolder.shelf_title.setText(ShelfList.get(position).shelf_title);

        //편집을 누르면 책장 이름을 바꿀 수 있다.
        verticalViewHolder.shelfItem_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<String> ListItems = new ArrayList<>();
                ListItems.add("책장 이름 편집");
                ListItems.add("책장 삭제");
                final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("책장 설정");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int pos) {
                        String selectedText = items[pos].toString();
                        Toast.makeText(mContext, selectedText, Toast.LENGTH_SHORT).show();
                        switch (pos){
                            //편집을 누를시
                            case 0:
                                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                                View dialogView = inflater.inflate(R.layout.alertdialog_shelfadd, null);
                                final EditText shelfadd_title = (EditText)dialogView.findViewById(R.id.shelfadd_title);
                                shelfadd_title.setText(ShelfList.get(position).shelf_title);
                                TextView shelfadd_name = (TextView)dialogView.findViewById(R.id.shelfadd_name);
                                shelfadd_name.setText("책장 이름 편집");

                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                builder.setView(dialogView);

                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String value = shelfadd_title.getText().toString();
                                        ShelfList.get(position).setShelf_title(value);
                                        verticalViewHolder.shelf_title.setText(value);

                                        Toast.makeText(mContext, "확인 버튼 클릭됨" + value, Toast.LENGTH_SHORT).show();

                                        dialog.dismiss();
                                    }
                                });
                                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(mContext, "취소 버튼 클릭됨", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                                break;

                            //삭제버튼을 누를시
                            case 1:
                                AlertDialog.Builder builder2 = new AlertDialog.Builder(mContext);

                                builder2.setTitle("삭제").setMessage("정말로 삭제하시겠습니까?");

                                builder2.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int id)
                                    {
                                        remove(verticalViewHolder.getAdapterPosition());
                                    }
                                });

                                builder2.setNegativeButton("취소", new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int id)
                                    {
                                    }
                                });
                                AlertDialog alertDialog2 = builder2.create();
                                alertDialog2.show();
                                break;
                        }
                    }
                });
                builder.show();
            }
        });


        //책장이름, 그 뷰를 한번 클릭했을때 선반페이지로 이동
        verticalViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ShelfViewActivity.class);
                intent.putExtra("shelf_position", verticalViewHolder.getAdapterPosition());
                intent.putExtra("shelf_title", ShelfList.get(verticalViewHolder.getAdapterPosition()).shelf_title);
                mContext.startActivity(intent);
            }
        });
    }

    public void alertShow(final int position){
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View dialogView = inflater.inflate(R.layout.alertdialog_shelfadd, null);
        final EditText shelfadd_title = (EditText)dialogView.findViewById(R.id.shelfadd_title);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(dialogView);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = shelfadd_title.getText().toString();


                Toast.makeText(mContext, "확인 버튼 클릭됨" + value, Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext, "취소 버튼 클릭됨", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //책장제거
    public void remove(int position){
        try{
            ShelfList.remove(position);
            //새로고침(notify)을 해야 화면상에 변화가 바로 일어남
            notifyItemRemoved(position);
        } catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    public boolean onItemMove(int from_position,int to_position){
        //이동할 객체 저장
        Shelf shelf = new Shelf(ShelfList.get(from_position).shelf_title);
        shelf.bookList = (ShelfList.get(from_position).bookList);
        //이동할 객체 삭제
        ShelfList.remove(from_position);
        //이동하고 싶은 position에 추가
        ShelfList.add(to_position, shelf);
        //Adapter에 데이터 이동을 알림
        notifyDataSetChanged();
        return true;
    }
    //swipe를 쓰기 위해서는 callback에 가서 코드를 수정해야함
    public void onItemSwipe(int position){
        ShelfList.remove(position);
        notifyDataSetChanged();
    }
    @Override
    /*public int getItemCount() {
        return (null!= AllBookList? AllBookList.size() : 0);
    }*/
    public int getItemCount() {
        return (null!= ShelfList? ShelfList.size() : 0);
    }
}