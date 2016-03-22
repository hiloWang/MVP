package com.user.hilo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.user.hilo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/19.
 */
public class PhotoFiltersAdapter extends RecyclerView.Adapter<PhotoFiltersAdapter.PhotoFiltersViewHolder> {

    private List<String> items;

    public void updateItem(List<String> items) {
        if (items == null) this.items = new ArrayList<>();
        this.items = items;
    }

    @Override
    public PhotoFiltersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoFiltersViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_filter, parent, false));
    }

    @Override
    public void onBindViewHolder(PhotoFiltersViewHolder holder, int position) {
        holder.bindData(holder, items, position);
    }


    @Override
    public int getItemCount() {
        if (items != null)
            return items.size();
        return 12;
    }

    @OnClick(R.id.ivFilterMock)
    public void onClick() {
    }

    public static class PhotoFiltersViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.ivFilterMock)
        ImageView mIvFilterMock;

        public PhotoFiltersViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(PhotoFiltersViewHolder holder, List<String> items, int position) {

        }
    }
}
