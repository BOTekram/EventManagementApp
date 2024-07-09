package com.fit2081.assignment1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fit2081.assignment1.provider.CategoryEntity;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryEntity> db;
    private OnItemClickListener onItemClickListener;

    public CategoryAdapter(List<CategoryEntity> db, OnItemClickListener onItemClickListener) {
        this.db = db;
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(CategoryEntity category, View view);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryEntity category = db.get(position);
        holder.tvCategoryId.setText(category.getCategoryId());
        holder.tvCategoryName.setText(category.getCategoryName());
        holder.tvCategoryEventCount.setText(category.getEventCount());
        holder.tvCategoryIsActive.setText(category.getCategoryActive());
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(category, v));
    }

    @Override
    public int getItemCount() {
        return db == null ? 0 : db.size();
    } //if null return 0, else return size of db

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCategoryId, tvCategoryName, tvCategoryEventCount, tvCategoryIsActive;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCategoryId = itemView.findViewById(R.id.tvCategoryId);
            tvCategoryName = itemView.findViewById(R.id.tvEventName);
            tvCategoryEventCount = itemView.findViewById(R.id.tvTicketsAvailable);
            tvCategoryIsActive = itemView.findViewById(R.id.tvCategoryIsActive);
        }
    }
}