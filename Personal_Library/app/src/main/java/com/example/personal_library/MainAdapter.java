package com.example.personal_library;

import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> {

    private ArrayList<MainData> arrayList;

    public MainAdapter(ArrayList<MainData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MainAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MainAdapter.CustomViewHolder holder, int position) {
        holder.ImageVIew_profile.setImageResource(arrayList.get(position).getImageView_profile());
        holder.TextView_title.setText(arrayList.get(position).getTextView_title());
        holder.TextView_content.setText(arrayList.get(position).getTextView_content());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curName =holder.TextView_title.getText().toString();
                Toast.makeText(v.getContext(), curName, Toast.LENGTH_SHORT).show();
            }
        });


       holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v){
               remove(holder.getAdapterPosition());
               return true;
           }
       });

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }


    public void remove(int position){
        try{
            arrayList.remove(position);
            notifyItemRemoved(position);
        }catch(IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView ImageVIew_profile;
        protected TextView TextView_title;
        protected TextView TextView_content;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ImageVIew_profile = (ImageView) itemView.findViewById(R.id.ImageView_profile);
            this.TextView_title = (TextView) itemView.findViewById(R.id.TextView_title);
            this.TextView_content = (TextView) itemView.findViewById(R.id.TextView_content);

        }
    }
}
