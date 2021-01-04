package com.example.nectar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    List<String> titles;
    List<Integer> images;
    Context context;
    LayoutInflater inflater;

    public CategoryAdapter(Context ctx, List<String> titles, List<Integer> images){
        this.titles = titles;
        this.images = images;
        this.inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.category_grid_single,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.categoryNameTv.setText(titles.get(position));
        holder.categoryIconTv.setImageResource(images.get(position));


    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView categoryNameTv;
        ImageView categoryIconTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryNameTv = itemView.findViewById(R.id.categoryNameTv);
            categoryIconTv = itemView.findViewById(R.id.categoryIconTv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Intent intent = new Intent(v.getContext(),categoryProductActivity.class);
            intent.putExtra("categoryName",titles.get(position));
            v.getContext().startActivity(intent);
        }
    }
}
