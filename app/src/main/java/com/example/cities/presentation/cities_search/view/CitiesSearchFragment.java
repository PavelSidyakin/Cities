package com.example.cities.presentation.cities_search.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cities.R;
import com.example.cities.model.data.CityData;
import com.example.cities.presentation.cities_search.CitiesSearch;
import com.example.cities.presentation.cities_search.view.recycler.CityListAdapter;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.DaggerFragment;

public class CitiesSearchFragment extends DaggerFragment implements CitiesSearch.View {

    @Inject
    CitiesSearch.Presenter presenter;

    private RecyclerView citiesRecyclerView;
    private ProgressBar progressBar;
    private TextView errorTextView;

    private CityListAdapter cityListAdapter = new CityListAdapter();

    @Inject
    public CitiesSearchFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_cities_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        citiesRecyclerView = view.findViewById(R.id.recycler_list_cities_search);

        citiesRecyclerView.setAdapter(cityListAdapter);

        errorTextView = view.findViewById(R.id.text_view_error_cities_search);
        progressBar = view.findViewById(R.id.progress_bar_cities_search);

        errorTextView.setOnClickListener(v -> presenter.retry());

        SearchView searchView = view.findViewById(R.id.search_view_cities_search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.onSearchTextSubmitted(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.onSearchTextChanged(newText);
                return true;
            }
        });


        presenter.onViewReady();
    }

    @Override
    public void updateCityList(PagedList<CityData> cityList) {
        cityListAdapter.submitList(cityList);
    }

    @Override
    public void clearList() {
        cityListAdapter = new CityListAdapter();
        citiesRecyclerView.setAdapter(cityListAdapter);
        cityListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        errorTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        errorTextView.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }
}