package com.example.cities.presentation.cities.cities_search.view.recycler;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cities.model.data.CityData;

public class CityListAdapter extends PagedListAdapter<CityData, RecyclerView.ViewHolder> {

    private static final DiffUtil.ItemCallback<CityData> cityDataDiffCallback = new DiffUtil.ItemCallback<CityData>() {
        @Override
        public boolean areItemsTheSame(@NonNull CityData oldItem, @NonNull CityData newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull CityData oldItem, @NonNull CityData newItem) {
            return oldItem.equals(newItem);
        }
    };

    public CityListAdapter() {
        super(cityDataDiffCallback);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return CityListItemViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CityListItemViewHolder) {
            ((CityListItemViewHolder) holder).bind(getItem(position));
        }
    }
}