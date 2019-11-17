package com.example.cities.presentation.about_app.presenter;

import com.example.SchedulerProviderStub;
import com.example.cities.domain.about_app.AboutAppInfoInteractor;
import com.example.cities.model.AboutInfoResult;
import com.example.cities.model.AboutInfoResultCode;
import com.example.cities.model.AboutInfoResultData;
import com.example.cities.model.data.AboutInfo;
import com.example.cities.presentation.about_app.About;
import com.example.cities.utils.XLog;
import com.example.cities.utils.rx.SchedulerProvider;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("AboutPresenterImpl tests")
class AboutPresenterImplTest {

    @Mock
    private About.View view;

    @Mock
    private AboutAppInfoInteractor aboutAppInfoInteractor;

    private SchedulerProvider schedulerProvider = new SchedulerProviderStub();

    private AboutPresenterImpl aboutPresenter;

    @BeforeEach
    void beforeEachTest() {
        XLog.enable(false);
        MockitoAnnotations.initMocks(this);

        aboutPresenter = new AboutPresenterImpl(view, schedulerProvider, aboutAppInfoInteractor);
        aboutPresenter.requestDelayMs = 1;
    }

    @Nested
    @DisplayName("When view is shown, should show progress and perform request")
    class onViewReadyTests {

        @Test
        @DisplayName("When request successful, should show correspond info")
        void onViewReadyTest0() {
            String companyName = "companyName cnvdfjkvncdfk";
            String companyAddress = "companyAddress dfkcndfj";
            String companyPostal = "companyPostal cjecvneru";
            String companyCity = "companyCity cejkerifjcei";
            String aboutInfo = "aboutInfo cjerfjeiri";

            //when
            when(aboutAppInfoInteractor.requestAboutInfo())
                    .thenReturn(Single.just(new AboutInfoResult(AboutInfoResultCode.OK,
                            new AboutInfoResultData(new AboutInfo(companyName, companyAddress, companyPostal, companyCity, aboutInfo)))));

            // action
            aboutPresenter.onViewReady();

            waitForRequestCompleteness();

            // verify
            verify(view).setCompanyName(companyName);
            verify(view).setCompanyAddress(companyAddress);
            verify(view).setCompanyPostalCode(companyPostal);
            verify(view).setCompanyCity(companyCity);
            verify(view).setAboutInfo(aboutInfo);
        }

        @Nested
        @DisplayName("When request failed, should show error and do not show info")
        class OnRequestFailedTests {
            @Test
            @DisplayName("when the request returns error")
            void onViewReadyTest0() {
                //when
                when(aboutAppInfoInteractor.requestAboutInfo())
                        .thenReturn(Single.just(new AboutInfoResult(AboutInfoResultCode.ERROR, null)));

                // action
                aboutPresenter.onViewReady();
            }

            @Test
            @DisplayName("when the request emits error")
            void onViewReadyTest1() {
                //when
                when(aboutAppInfoInteractor.requestAboutInfo())
                        .thenReturn(Single.error(new RuntimeException("Error!")));

                // action
                aboutPresenter.onViewReady();
            }

            @Test
            @DisplayName("when the request throws an exception")
            void onViewReadyTest2() {
                //when
                when(aboutAppInfoInteractor.requestAboutInfo())
                        .thenThrow(new RuntimeException("Error!"));

                // action
                aboutPresenter.onViewReady();
            }

            @AfterEach
            void afterEachTest() {
                waitForRequestCompleteness();

                // verify
                verify(view).showError();
                verify(view, never()).setCompanyName(anyString());
                verify(view, never()).setCompanyAddress(anyString());
                verify(view, never()).setCompanyPostalCode(anyString());
                verify(view, never()).setCompanyCity(anyString());
                verify(view, never()).setAboutInfo(anyString());
            }
        }

        @AfterEach
        void afterEachTest() {
            // verify
            verifyProgressIsShowed();
            verify(aboutAppInfoInteractor).requestAboutInfo();
        }
    }

    @Nested
    @DisplayName("When view is destroyed, request's disposable should be disposed")
    class onDestroyViewTests {
        private AboutInfo aboutInfo = new AboutInfo("", "", "", "", "");

        @Test
        @DisplayName("if request completed")
        void onDestroyTest0() {
            //when
            when(aboutAppInfoInteractor.requestAboutInfo())
                    .thenReturn(Single.just(new AboutInfoResult(AboutInfoResultCode.OK,
                            new AboutInfoResultData(aboutInfo))));

            // action
            aboutPresenter.onViewReady();
            waitForRequestCompleteness();
            aboutPresenter.onDestroyView();
        }

        @Test
        @DisplayName("if request is active, it should be interrupted")
        void onDestroyTest1() {
            //when
            when(aboutAppInfoInteractor.requestAboutInfo())
                    .thenReturn(Single.timer(Long.MAX_VALUE, TimeUnit.DAYS) // simulate very long request
                            .map(aLong -> new AboutInfoResult(AboutInfoResultCode.OK,
                                    new AboutInfoResultData(aboutInfo))));

            // action
            aboutPresenter.onViewReady();
            // To make sure the request in actually performed
            verify(aboutAppInfoInteractor, timeout(Long.MAX_VALUE)).requestAboutInfo();
            aboutPresenter.onDestroyView();
        }

        @AfterEach
        void afterEachTest() {
            // verify
            assertTrue(aboutPresenter.requestAboutInfoDisposable.isDisposed());
        }
    }

    private void waitForRequestCompleteness() {
        try {
            aboutPresenter.requestCompletedCompletable
                    .test()
                    .await()
                    .assertTerminated();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void verifyProgressIsShowed() {
        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showProgress();
        inOrder.verify(view).hideProgress();
    }
}