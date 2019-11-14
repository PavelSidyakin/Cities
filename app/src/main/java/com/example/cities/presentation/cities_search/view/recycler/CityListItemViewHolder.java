package com.example.cities.presentation.cities_search.view.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cities.R;
import com.example.cities.model.data.CityCoordinates;
import com.example.cities.model.data.CityData;


public class CityListItemViewHolder extends RecyclerView.ViewHolder {

    private final CityListItemClickHandler itemClickHandler;
    private final CityListItemInfoButtonClickHandler infoButtonClickHandler;

    private CityListItemViewHolder(View view, CityListItemClickHandler itemClickHandler, CityListItemInfoButtonClickHandler infoButtonClickHandler) {
        super(view);
        this.itemClickHandler = itemClickHandler;
        this.infoButtonClickHandler = infoButtonClickHandler;
    }

    void bind(CityData cityData, int position) {
        if (cityData != null) {
            itemView.<TextView>findViewById(R.id.text_view_city_list_item).setText(cityData.getName());
            itemView.<TextView>findViewById(R.id.text_view_country_list_item).setText(cityData.getCountry());
            itemView.<TextView>findViewById(R.id.text_view_coordinates_list_item).setText(formatCoordinates(cityData.getCityCoordinates()));
        }

        itemView.findViewById(R.id.image_view_info_list_item)
                .setOnClickListener(v -> {
                    if (infoButtonClickHandler != null) {
                        infoButtonClickHandler.onInfoButtonClicked(position);
                    }
                });

        if (position % 2 != 0) {
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.cities_search_recycler_item_odd));
        } else {
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.cities_search_recycler_item_even));
        }

        itemView.setOnClickListener(v -> {
            if (itemClickHandler != null) {
                itemClickHandler.onItemClicked(position);
            }
        });

    }

    private String formatCoordinates(CityCoordinates cityCoordinates) {
        return itemView.getContext().getString(R.string.city_list_item_coordinates_mask,
                cityCoordinates.getLat(), cityCoordinates.getLng());

    }

    public static CityListItemViewHolder create(ViewGroup parent,
                                                CityListItemClickHandler itemClickHandler,
                                                CityListItemInfoButtonClickHandler infoButtonClickHandler) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cities_search_recycler_item, parent, false);

        return new CityListItemViewHolder(view, itemClickHandler, infoButtonClickHandler);
    }
}
