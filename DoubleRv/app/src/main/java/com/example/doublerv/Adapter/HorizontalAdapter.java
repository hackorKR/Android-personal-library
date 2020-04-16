package com.example.doublerv.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doublerv.Activity.BookViewActivity;
import com.example.doublerv.ClassData.Book;
//import com.example.doublerv.Classbook.Book;
import com.example.doublerv.ClassData.Shelf;
import com.example.doublerv.R;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder> {

    private ArrayList<Book> bookList;
    private Context mContext;

    public HorizontalAdapter(Context mContext, ArrayList<Book> book)
    {
        this.mContext =mContext;
        this.bookList = book;
    }


    public class HorizontalViewHolder extends RecyclerView.ViewHolder{
        protected ImageView image;
        protected TextView title;
        protected TextView sentence;

        public HorizontalViewHolder(View view)
        {
            super(view);
            this.image = view.findViewById(R.id.poster);
            this.title = view.findViewById(R.id.title);
            this.sentence = view.findViewById(R.id.sentence);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(v.getContext(), BookViewActivity.class);

                    Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    Log.d("아이템을 클릭할때", position +" 존재하냐?");

                    intent.putExtra("poster", byteArray);
                    intent.putExtra("title", bookList.get(position).getTitle());
                    intent.putExtra("sentence", bookList.get(position).getSentence());
                    intent.putExtra("book_position", position);
                    intent.putExtra("author", bookList.get(position).getAuthor());
                    intent.putExtra("shelf_position", bookList.get(position).getShelf_position());

                    mContext.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_horizontal, viewGroup, false);

        return new HorizontalAdapter.HorizontalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final HorizontalViewHolder horizontalViewHolder, final int position)
    {
        Book m = bookList.get(position);
        horizontalViewHolder.image.setImageBitmap(m.getBitmap());
        horizontalViewHolder.title.setText(bookList.get(position).getTitle());

        //작가를 맨밑에 표시하도록 만듬
        horizontalViewHolder.sentence.setText(bookList.get(position).getAuthor());

        horizontalViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(mContext);

                builder2.setTitle("선택한 책 삭제").setMessage("정말로 삭제하시겠습니까?");

                builder2.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        remove(horizontalViewHolder.getAdapterPosition());
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
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != bookList ? bookList.size():0);
    }


    public void remove(int position){
        try{
            bookList.remove(position);
            notifyItemRemoved(position); //새로고침(notufy)을 해야 화면상에 변화가 바로 일어남
        } catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

}