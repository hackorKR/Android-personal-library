package com.example.doublerv.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
        protected Button shelfItem_add;

        protected TextView shelf_count;

        public VerticalViewHolder(@NonNull View view)
        {
            super(view);
            this.recyclerView = (RecyclerView)itemView.findViewById(R.id.recyclerView_horizontal);
            this.shelf_title = (TextView)itemView.findViewById(R.id.shelf_title);
            this.shelfItem_add = (Button)itemView.findViewById(R.id.shelfItem_add);

            this.shelf_count = (TextView)itemView.findViewById(R.id.shelf_count);
        }

    }
//    public String getShelfTitle() {
//        return shelf_title.getText().toString();
//    }
//
//    public void setShelfTitle(String shelf) {
//        this.shelf_title.setText(shelf);
//    }

    @NonNull
    @Override
    public VerticalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_vertical, viewGroup, false);
        return new VerticalAdapter.VerticalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final VerticalAdapter.VerticalViewHolder verticalViewHolder, final int position) {
        HorizontalAdapter adapter = new HorizontalAdapter(mContext, AllBookList.get(position));

        //ShelfList에서 가져오는 bookList
        //HorizontalAdapter asd = new HorizontalAdapter(mContext, ShelfList.get(position).bookList);

        verticalViewHolder.recyclerView.setHasFixedSize(true);
        verticalViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,false));
        verticalViewHolder.recyclerView.setAdapter(adapter);

        //책장안에 있는 추가를 누를때 일어나는 일
//        verticalViewHolder.itemView.setTag(position);
//        verticalViewHolder.shelfItem_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //아래 한줄을 키면 하드코딩 객체 생성 가능
//                itemAdd(verticalViewHolder.getAdapterPosition());
//                notifyDataSetChanged();
//            }
//        });

        //책장 안에 있는 번호를 누를 경우 일어나는 일
        verticalViewHolder.itemView.setTag(position);
        verticalViewHolder.shelf_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verticalViewHolder.shelf_count.setText("5");
            }
        });

        //편집을 누르면 책장 이름을 바꿀 수 있다.
        verticalViewHolder.shelfItem_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                View dialogView = inflater.inflate(R.layout.alertdialog_shelfadd, null);
                final EditText shelfadd_title = (EditText)dialogView.findViewById(R.id.shelfadd_title);

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setView(dialogView);

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value = shelfadd_title.getText().toString();
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
            }
        });


        //책장이름, 그 뷰를 한번 클릭했을때 선반페이지로 이동
        verticalViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ShelfViewActivity.class);
                intent.putExtra("shelf_position", verticalViewHolder.getAdapterPosition());
//                for(int i = 0; i < AllBookList.get(position).size(); i++){
//                }
                mContext.startActivity(intent);
            }
        });

        //책장뷰를 길게 눌렀을때 다른 책장과의 스와이프가 가능하도록 만들기
        verticalViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final List<String> ListItems = new ArrayList<>();
                ListItems.add("편집");
                ListItems.add("삭제");
                final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("책장 정보 바꾸기");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int pos) {
                        String selectedText = items[pos].toString();
                        Toast.makeText(mContext, selectedText, Toast.LENGTH_SHORT).show();
                        switch (pos){
                            case 0:
                                alertShow(verticalViewHolder.getAdapterPosition());
                                break;
                            case 1:
                                remove(verticalViewHolder.getAdapterPosition());
                                break;
                        }
                    }
                });
                builder.show();
                return true;
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
            AllBookList.remove(position);
            //새로고침(notify)을 해야 화면상에 변화가 바로 일어남
            notifyItemRemoved(position);
        } catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    //책장 옆에 추가를 누르면 책이 동적으로 할당되는 코드
    public void itemAdd(int position){
        ArrayList<Book> bookList = new ArrayList();
        ArrayList<Book> bookList2 = new ArrayList();
        try{
            Toast.makeText(mContext, AllBookList.get(position).toString(),Toast.LENGTH_LONG).show();

            for(int i =0; i < AllBookList.get(position).size(); i++){
                bookList2.add(AllBookList.get(position).get(i));
                Log.d("key", "영화리스트 늘어나는부분");
            }

            Bitmap book_homo= BitmapFactory.decodeResource(mContext.getResources(), R.drawable.book_homo);
            bookList.add(new Book(book_homo, "호모데우스", "신이 되는 인간의 미래를 상상해본다"));
            bookList.addAll(bookList2);

            AllBookList.set(position, bookList);

        } catch(IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }


    public boolean onItemMove(int from_position,int to_position){
        //이동할 객체 저장
        ArrayList<Book> allBooklist = AllBookList.get(from_position);
        //이동할 객체 삭제
        AllBookList.remove(from_position);
        //이동하고 싶은 position에 추가
        AllBookList.add(to_position,allBooklist);
        //Adapter에 데이터 이동을 알림
        notifyDataSetChanged();
        return true;
    }
    //swipe를 쓰기 위해서는 callback에 가서 코드를 수정해야함
    public void onItemSwipe(int position){
        AllBookList.remove(position);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return (null!= AllBookList? AllBookList.size() : 0);
    }
}