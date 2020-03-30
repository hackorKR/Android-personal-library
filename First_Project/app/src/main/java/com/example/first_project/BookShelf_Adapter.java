package com.example.first_project;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookShelf_Adapter extends RecyclerView.Adapter<BookShelf_Adapter.CustomViewHolder2> {

    private ArrayList<BookShelf_Data> arrayList2;

    public BookShelf_Adapter(ArrayList<BookShelf_Data> data) {
        this.arrayList2 = data;
    }

    public class CustomViewHolder2 extends RecyclerView.ViewHolder{

        protected TextView bookshelf_title;

        public CustomViewHolder2(@NonNull View itemView) {
            super(itemView);
            this.bookshelf_title = (TextView)itemView.findViewById(R.id.bookshelf_title);
        }
    }

    @NonNull
    @Override
    public BookShelf_Adapter.CustomViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookshelf_list, parent, false);
        CustomViewHolder2 holder2 = new CustomViewHolder2(view);

        return holder2;
    }

    @Override
    public void onBindViewHolder(@NonNull final BookShelf_Adapter.CustomViewHolder2 holder2, int position) {
        //리사이클러뷰 두개 사용하는 법
//        Item_Adapter adapter = new Item_Adapter((ArrayList<Item_Data>) arrayList2.get(position));
//        holder2.bookshelf_recycler_view.setHasFixedSize(true);
//        holder2.bookshelf_recycler_view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
//        holder2.bookshelf_recycler_view.setAdapter(adapter);
        holder2.bookshelf_title.setText(arrayList2.get(position).getBookshelf_title());

        holder2.itemView.setTag(position);
        holder2.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curName = holder2.bookshelf_title.getText().toString();
                Toast.makeText(v.getContext(), curName, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != arrayList2 ? arrayList2.size() : 0);
    }


}
