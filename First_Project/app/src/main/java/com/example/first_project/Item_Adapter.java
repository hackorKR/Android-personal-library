package com.example.first_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Item_Adapter extends RecyclerView.Adapter<Item_Adapter.CustomViewHolder> {

    private ArrayList<Item_Data> arrayList;

    public Item_Adapter(ArrayList<Item_Data> arrayList) {
        this.arrayList = arrayList;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView item_profile;
        protected TextView item_title;
        protected TextView item_sentence;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.item_profile = (ImageView) itemView.findViewById(R.id.item_profile);
            this.item_title = (TextView) itemView.findViewById(R.id.item_title);
            this.item_sentence = (TextView) itemView.findViewById(R.id.item_sentence);
        }
    }

    @NonNull
    @Override //List뷰 생명주기 시작
    public Item_Adapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override //실제 추가될때
    public void onBindViewHolder(@NonNull final Item_Adapter.CustomViewHolder holder, int position) {
        holder.item_profile.setImageResource(arrayList.get(position).getItem_profile());
        holder.item_title.setText(arrayList.get(position).getItem_title());
        holder.item_sentence.setText(arrayList.get(position).getItem_sentence());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String curName = holder.item_title.getText().toString();
            Toast.makeText(v.getContext(), curName, Toast.LENGTH_SHORT).show();
        }
    });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            remove(holder.getAdapterPosition());//포지션값 넘김
            return true;
        }
    });
}

    public void remove(int position){
        try{
            arrayList.remove(position);
            notifyItemRemoved(position); //새로고침(notufy)을 해야 화면상에 변화가 바로 일어남
        } catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }
}
