package com.example.cities.presentation.about_app;

import com.example.cities.model.AboutInfo;

import io.reactivex.Single;

/**
 * Created by Backbase R&D B.V on 28/06/2018.
 * MVP contract for AboutActivity
 */

public interface About {

    // TODO: create interactor and repository
    interface Data {
        Single<AboutInfo> requestAboutInfo();
    }

    interface Presenter {
        void onViewReady();
        void onSuccess(AboutInfo aboutInfo);
        void onFail();
        void onDestroyView();
    }

    interface View {
        void setCompanyName(String companyName);
        void setCompanyAddress(String companyAddress);
        void setCompanyPostalCode(String postalCode);
        void setCompanyCity(String companyCity);
        void setAboutInfo(String info);
        void showError();
        void showProgress();
        void hideProgress();
    }
}
