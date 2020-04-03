package com.example.doublerv;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.VerticalViewHolder>{

    private ArrayList<ArrayList<Movie>> AllMovieList;
    private Context context;

    public VerticalAdapter(Context context, ArrayList<ArrayList<Movie>> data)
    {
        this.context = context;
        this.AllMovieList = data;

    }

    public class VerticalViewHolder extends RecyclerView.ViewHolder{
        protected RecyclerView recyclerView;
        protected TextView shelf_title;
        protected Button shelfItem_add;

        public VerticalViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.recyclerView = (RecyclerView)itemView.findViewById(R.id.recyclerViewVertical);
            this.shelf_title = (TextView)itemView.findViewById(R.id.shelf_title);
            this.shelfItem_add = (Button)itemView.findViewById(R.id.shelfItem_add);

            }
        }


    @NonNull
    @Override
    public VerticalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_vertical, viewGroup, false);

        return new VerticalAdapter.VerticalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final VerticalAdapter.VerticalViewHolder verticalViewHolder, int position) {
        HorizontalAdapter adapter = new HorizontalAdapter(context, AllMovieList.get(position));

//        verticalViewHolder.shelf.setText(shelfArrayList.get(position).getShelf());
        verticalViewHolder.recyclerView.setHasFixedSize(true);
        verticalViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
        verticalViewHolder.recyclerView.setAdapter(adapter);


        verticalViewHolder.itemView.setTag(position);
        verticalViewHolder.shelfItem_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemAdd(verticalViewHolder.getAdapterPosition());
                //눈속임 바꿔치기 가능함ㅋㅋ
                // remove(verticalViewHolder.getAdapterPosition()+1);

                notifyDataSetChanged();
            }
        });

        verticalViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                remove(verticalViewHolder.getAdapterPosition());
                notifyDataSetChanged();
                return true;
            }
        });
    }
    public void remove(int position){

        try{
            AllMovieList.remove(position);
            notifyItemRemoved(position); //새로고침(notufy)을 해야 화면상에 변화가 바로 일어남
        } catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    public void itemAdd(int position){
        ArrayList<Movie> movieList = new ArrayList();
        ArrayList<Movie> movieList2 = new ArrayList();
        try{
//            movieList.add(new Movie(R.drawable.movie1, "알라딘", "예시로 하나 넣는거다1"));
//            Toast.makeText(context, "추가버튼 누름",Toast.LENGTH_SHORT).show();
            Toast.makeText(context, AllMovieList.get(position).toString(),Toast.LENGTH_LONG).show();

            for(int i =0; i < AllMovieList.get(position).size(); i++){
                movieList2.add(AllMovieList.get(position).get(i));
                Log.d("key", "영화리스트 늘어나는부분");
            }

            movieList.add(new Movie(R.drawable.book_homo, "호모데우스", "신이 되는 인간의 미래를 상상해본다"));
            movieList2.addAll(movieList);

            AllMovieList.set(position, movieList2);
//            AllMovieList.set(position, movieList2);

        } catch(IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return (null!= AllMovieList? AllMovieList.size() : 0);
    }
}