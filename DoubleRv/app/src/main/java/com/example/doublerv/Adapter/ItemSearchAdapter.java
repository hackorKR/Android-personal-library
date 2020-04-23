package com.example.doublerv.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doublerv.Activity.BookNewActivity;
import com.example.doublerv.Activity.BookSearchActivity;
import com.example.doublerv.Activity.BookViewActivity;
import com.example.doublerv.ClassData.Book;
import com.example.doublerv.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ItemSearchAdapter extends RecyclerView.Adapter<ItemSearchAdapter.ItemSearchViewHolder> {

    private ArrayList<Book> bookList;
    private Context mContext;

    public ItemSearchAdapter(Context mContext, ArrayList<Book> book){
        this.mContext =mContext;
        this.bookList = book;
    }

    public class ItemSearchViewHolder extends RecyclerView.ViewHolder {
        protected ImageView image;
        protected TextView title;
        protected TextView author;

        public ItemSearchViewHolder(@NonNull View view) {
            super(view);
            this.image = (ImageView)view.findViewById(R.id.searchItem_image);
            this.title = (TextView)view.findViewById(R.id.searchItem_title);
            this.author = (TextView)view.findViewById(R.id.searchItem_author);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(v.getContext(), BookNewActivity.class);

                    Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    Log.d("아이템을 클릭할때", position +" 존재하냐?");

                    intent.putExtra("image", byteArray);
                    intent.putExtra("title", bookList.get(position).getTitle());
                    intent.putExtra("author", bookList.get(position).getAuthor());
                    intent.putExtra("book_position", position);
                    intent.putExtra("shelf_position", bookList.get(position).getShelf_position());

                    mContext.startActivity(intent);
                }
            });
        }
    }
    @NonNull
    @Override
    public ItemSearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item, viewGroup, false);

        return new ItemSearchAdapter.ItemSearchViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemSearchAdapter.ItemSearchViewHolder holder, int position) {
        holder.image.setImageBitmap(bookList.get(position).getBitmap());
        holder.title.setText(bookList.get(position).getTitle());
        holder.author.setText(bookList.get(position).getAuthor());

        //작가를 맨밑에 표시하도록 만듬
//        holder.sentence.setText(bookList.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return (null != bookList ? bookList.size() : 0);
    }
}
