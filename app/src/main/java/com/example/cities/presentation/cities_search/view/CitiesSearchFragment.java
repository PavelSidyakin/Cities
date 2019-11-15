package com.example.cities.presentation.cities_search.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cities.R;
import com.example.cities.model.data.CityData;
import com.example.cities.presentation.cities_search.CitiesSearch;
import com.example.cities.presentation.cities_search.view.recycler.CityListAdapter;
import com.example.cities.presentation.main_screen.MainScreen;
import com.example.cities.utils.recyclerview.RecyclerItemClickListener;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class CitiesSearchFragment extends DaggerFragment implements CitiesSearch.View {

    public static final String FRAGMENT_TAG = "CitiesSearchFragment";

    @Inject
    CitiesSearch.Presenter presenter;

    @Inject
    MainScreen.View mainView;

    private RecyclerView citiesRecyclerView;
    private ProgressBar progressBar;
    private TextView errorTextView;
    private SearchView searchView;

    private CityListAdapter cityListAdapter;

    @Inject
    public CitiesSearchFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cities_search_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Activity activity = getActivity();
        if (activity instanceof AppCompatActivity) {
            ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        }

        citiesRecyclerView = view.findViewById(R.id.recycler_list_cities_search);
        createAndSetAdapter();

        errorTextView = view.findViewById(R.id.text_view_error_cities_search);
        progressBar = view.findViewById(R.id.progress_bar_cities_search);

        errorTextView.setOnClickListener(v -> presenter.retry());

        searchView = view.findViewById(R.id.search_view_cities_search);

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

        view.findViewById(R.id.image_view_help_cities_search).setOnClickListener(v -> presenter.onAboutAppClicked());

        presenter.onViewReady();
    }

    @Override
    public void updateCityList(PagedList<CityData> cityList) {
        cityListAdapter.submitList(cityList);
    }

    @Override
    public void clearList() {
        createAndSetAdapter();
        cityListAdapter.notifyDataSetChanged();
    }

    private void createAndSetAdapter() {
        cityListAdapter = new CityListAdapter();
        cityListAdapter.setCityListItemInfoButtonClickHandler(position -> {
            CityData cityData = getCityDataByPosition(position);

            if (cityData == null) {
                return;
            }

            presenter.onCityInfoClicked(cityData);

        });

        cityListAdapter.setItemClickHandler(position -> {
            CityData cityData = getCityDataByPosition(position);

            if (cityData == null) {
                return;
            }

            presenter.onCityClicked(cityData);

        });

        citiesRecyclerView.setAdapter(cityListAdapter);

    }

    @Nullable
    private CityData getCityDataByPosition(int position) {
        PagedList pagedList = cityListAdapter.getCurrentList();

        if (pagedList == null) {
            return null;
        }

        return cityListAdapter.getCurrentList().get(position);

    }

    @Override
    public void setSearchText(String currentSearchText) {
        searchView.setQuery(currentSearchText, false);
    }

    @Override
    public void showMapWithSelectedCity() {
        mainView.showCityMapScreen();
    }

    @Override
    public void showCityInfoForSelectedCity() {
        mainView.showCityInfoScreen();
    }

    @Override
    public void showAboutApp() {
        mainView.showAboutAppScreen();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroyView();
    }
}
