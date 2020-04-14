package com.example.doublerv.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doublerv.Activity.BookViewActivity;
import com.example.doublerv.ClassData.Book;
//import com.example.doublerv.Classbook.Book;
import com.example.doublerv.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

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

                    intent.putExtra("poster", byteArray);
                    intent.putExtra("title", bookList.get(position).getTitle());
                    intent.putExtra("sentence", bookList.get(position).getSentence());
                    intent.putExtra("book_position", position);

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
        horizontalViewHolder.sentence.setText(bookList.get(position).getSentence());

        horizontalViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                remove(horizontalViewHolder.getAdapterPosition());
//                notifybookSetChanged();
//                final List<String> ListItems = new ArrayList<>();
//                ListItems.add("삭제");
//                final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//                builder.setTitle("책 삭제");
//                builder.setItems(items, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int pos) {
//                        String selectedText = items[pos].toString();
//                        Toast.makeText(mContext, selectedText, Toast.LENGTH_SHORT).show();
//                        switch (pos){
//                            case 0:
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
//                    }
//                });
//                builder.show();
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