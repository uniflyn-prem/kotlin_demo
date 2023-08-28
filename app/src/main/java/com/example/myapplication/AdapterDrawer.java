package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class AdapterDrawer extends RecyclerView.Adapter<AdapterDrawer.MyViewHolder> {

    public View view;
    public Context context;
    public ArrayList<MenuListResponse> menuListResponseArrayList;
    public ItemClickListener itemClickListener;
    int row_index = -1;

    public interface ItemClickListener{
        void ItemClick(String menu_id);
    }


    public AdapterDrawer(Context context, ArrayList<MenuListResponse> menuListResponseArrayList, ItemClickListener itemClickListener) {

        this.context = context;
        this.menuListResponseArrayList = menuListResponseArrayList;
        this.itemClickListener = itemClickListener;

    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.icon.setImageResource(menuListResponseArrayList.get(position).getImage());
        holder.title.setText(menuListResponseArrayList.get(position).getTitle());


        holder.menuSelect.setOnClickListener(v -> {

            String id = menuListResponseArrayList.get(position).getId();
            itemClickListener.ItemClick(id);
        });



    }

    @Override
    public int getItemCount() {
        return menuListResponseArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView icon;
        public TextView title;
        public LinearLayout menuSelect;
        public LinearLayout menuSselect;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.icon);
            title = itemView.findViewById(R.id.title);
            menuSelect = itemView.findViewById(R.id.menuSelect);

        }
    }


}
