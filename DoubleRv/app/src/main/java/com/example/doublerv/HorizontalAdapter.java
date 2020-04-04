package com.example.doublerv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder> {

    private ArrayList<Movie> dataList;
    private Context mContext;

    public HorizontalAdapter(Context mContext, ArrayList<Movie> data)
    {
        this.mContext =mContext;
        this.dataList = data;
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder{
        protected ImageView image;
        protected TextView title;
        protected TextView sentence;

        public HorizontalViewHolder(View view)
        {
            super(view);
            image = view.findViewById(R.id.poster);
            title = view.findViewById(R.id.title);
            sentence = view.findViewById(R.id.sentence);
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
        horizontalViewHolder.image.setImageResource(dataList.get(position).getResourceID());
        horizontalViewHolder.title.setText(dataList.get(position).getTitle());
        horizontalViewHolder.sentence.setText(dataList.get(position).getSentence());

        horizontalViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(v.getContext(), EditActivity.class);
                intent.putExtra("poster", dataList.get(position).getResourceID());
                intent.putExtra("title", dataList.get(position).getTitle());
                intent.putExtra("sentence", dataList.get(position).getSentence());
                mContext.startActivity(intent);
            }
        });

        horizontalViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                remove(horizontalViewHolder.getAdapterPosition());
                notifyDataSetChanged();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size():0);
    }


    public void remove(int position){
        try{
            dataList.remove(position);
            notifyItemRemoved(position); //새로고침(notufy)을 해야 화면상에 변화가 바로 일어남
        } catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    public void itemAdd(int position){
        ArrayList<Movie> movieList = new ArrayList();
        ArrayList<Movie> movieList2 = new ArrayList();
        try{
            movieList.add(new Movie(R.drawable.movie1, "알라딘", "예시로 하나 넣는거다2"));
            movieList2.addAll(movieList);
//            Toast.makeText(context, "추가버튼 누름",Toast.LENGTH_SHORT).show();
            Toast.makeText(mContext, dataList.get(position).toString(),Toast.LENGTH_LONG).show();
        } catch(IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

}