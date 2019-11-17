package com.example.cities.presentation.about_app.view;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cities.R;
import com.example.cities.presentation.about_app.About;

import javax.inject.Inject;

import dagger.android.AndroidInjection;


public class AboutActivity extends AppCompatActivity implements About.View {

    private TextView companyName;
    private TextView companyAddress;
    private TextView companyPostal;
    private TextView companyCity;
    private TextView aboutInfo;
    private ProgressBar progressBar;
    private android.view.View errorView;
    private android.view.View infoContainer;

    @Inject
    About.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        companyName = findViewById(R.id.text_view_company_name_about_activity);
        companyAddress = findViewById(R.id.text_view_company_address_about_activity);
        companyPostal = findViewById(R.id.text_view_company_postal_about_activity);
        companyCity = findViewById(R.id.text_view_company_city_about_activity);
        aboutInfo = findViewById(R.id.text_view_about_info_about_activity);
        progressBar = findViewById(R.id.progress_bar_about_activity);
        errorView = findViewById(R.id.text_view_error_about_activity);
        infoContainer = findViewById(R.id.layout_info_container_about_activity);
        presenter.onViewReady();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroyView();
    }

    @Override
    public void setCompanyName(String companyNameString) {
        infoContainer.setVisibility(android.view.View.VISIBLE);
        companyName.setText(companyNameString);
    }

    @Override
    public void setCompanyAddress(String companyAddressString) {
        companyAddress.setText(companyAddressString);
    }

    @Override
    public void setCompanyPostalCode(String postalCodeString) {
        companyPostal.setText(postalCodeString);
    }

    @Override
    public void setCompanyCity(String companyCityString) {
        companyCity.setText(companyCityString);
    }

    @Override
    public void setAboutInfo(String infoString) {
        aboutInfo.setText(infoString);
    }

    @Override
    public void showError() {
        errorView.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(android.view.View.GONE);
    }
}
