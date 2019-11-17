package com.example.cities.presentation.cities_search.presenter;

import androidx.paging.PagedList;

import com.example.SchedulerProviderStub;
import com.example.cities.domain.cities_search.CitiesScreenInteractor;
import com.example.cities.domain.cities_search.CitiesSearchInteractor;
import com.example.cities.model.CitiesSearchResult;
import com.example.cities.model.CitiesSearchResultCode;
import com.example.cities.model.CitiesSearchResultData;
import com.example.cities.model.data.CityCoordinates;
import com.example.cities.model.data.CityData;
import com.example.cities.presentation.cities_search.CitiesSearch;
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
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@DisplayName("CitiesSearchPresenterImpl tests")
class CitiesSearchPresenterImplTest {

    @Mock
    private CitiesSearch.View view;

    @Mock
    private CitiesSearchInteractor citiesSearchInteractor;

    @Mock
    private CitiesScreenInteractor citiesScreenInteractor;

    private SchedulerProvider schedulerProvider = new SchedulerProviderStub();

    private CitiesSearchPresenterImpl citiesSearchPresenter;

    // Used for tests with stream of CityData values
    private List<Integer> cityIdsStream = new ArrayList<>();

    private static final String LAST_ENTERED_TEXT = "LAST ENTERED TEXT";

    private static final int PAGE_SIZE = 5;
    private static final int INITIAL_PAGE_SIZE_FACTOR = 3;
    private static final int CITY_COUNT = 47;
    private static final int INITIAL_PAGE_SIZE = INITIAL_PAGE_SIZE_FACTOR * PAGE_SIZE;

    @BeforeEach
    void beforeEachTest() {
        for (int i = 0; i < CITY_COUNT; ++i) {
            cityIdsStream.add(i);
        }

        XLog.enable(false);
        MockitoAnnotations.initMocks(this);

        createAndSetupPresenter();

        when(citiesScreenInteractor.getCurrentSearchText()).thenReturn("");

        citiesSearchPresenter.onViewReady();
    }

    @Nested
    @DisplayName("When error is occurred, should show error message")
    class OnError {

        @Nested
        @DisplayName("CitiesSearchInteractor.requestCities() returns ERROR result")
        class ErrorResult {
            @BeforeEach
            void beforeEachTest() {
                // when
                when(citiesSearchInteractor.requestCities(anyString(), anyInt(), anyInt()))
                        .thenReturn(Single.just(new CitiesSearchResult(CitiesSearchResultCode.ERROR, null)));
            }

            @Test
            @DisplayName("On text entered")
            void test0() {
                // action
                citiesSearchPresenter.onSearchTextSubmitted(LAST_ENTERED_TEXT);
            }

            @Test
            @DisplayName("On text typing")
            void test1() {
                // action
                citiesSearchPresenter.onSearchTextChanged(LAST_ENTERED_TEXT);
            }
        }

        @Nested
        @DisplayName("CitiesSearchInteractor.requestCities() emits error (loadInitial())")
        class ExceptionResult {
            @BeforeEach
            void beforeEachTest() {
                // when
                when(citiesSearchInteractor.requestCities(anyString(), anyInt(), anyInt()))
                        .thenReturn(Single.error(new RuntimeException("Exception")));
            }

            @Test
            @DisplayName("On text entered")
            void test0() {
                // action
                citiesSearchPresenter.onSearchTextSubmitted(LAST_ENTERED_TEXT);
            }

            @Test
            @DisplayName("On text typing")
            void test1() {
                // action
                citiesSearchPresenter.onSearchTextChanged(LAST_ENTERED_TEXT);
            }
        }

        @Nested
        @DisplayName("CitiesSearchInteractor.requestCities() emits error (loadAfter())")
        class ExceptionResultLoadAfter {
            @BeforeEach
            void beforeEachTest() {
                final List<CityData> cityList = new ArrayList<>();

                // For first request return list with number of items equal to initial loading
                for (int i = 0; i < citiesSearchPresenter.pageSize * citiesSearchPresenter.initialPageSizeFactor; ++i) {
                    cityList.add(new CityData("" + i, "c" + i, new CityCoordinates(i * 2.0, i * 3.0), i));
                }

                // when
                when(citiesSearchInteractor.requestCities(anyString(), anyInt(), anyInt()))
                        .thenReturn(Single.just(new CitiesSearchResult(CitiesSearchResultCode.OK, new CitiesSearchResultData(cityList))))
                        .thenReturn(Single.error(new RuntimeException("Exception")));
            }

            @Test
            @DisplayName("On text entered")
            void test0() {
                // action
                citiesSearchPresenter.onSearchTextSubmitted(LAST_ENTERED_TEXT);
                simulateScroll();
            }
        }

        @AfterEach
        void afterEachTest() {
            waitForRequestCompleteness();

            // verify
            verifyProgressIsShowed();
            verify(citiesSearchInteractor, times(1)).requestCities(LAST_ENTERED_TEXT, 0, INITIAL_PAGE_SIZE);

            verify(view).showError();
        }
    }



    @Nested
    @DisplayName("On exception in search text chain")
    class ExceptionInTextSearchChain {
        @BeforeEach
        void beforeEachTest() {
            // when
            doThrow(new RuntimeException("Exception")).when(citiesScreenInteractor).setCurrentSearchText(anyString());
            when(citiesSearchInteractor.requestCities(anyString(), anyInt(), anyInt()))
                    .thenReturn(Single.just(new CitiesSearchResult(CitiesSearchResultCode.ERROR, null)));
        }

        @Test
        @DisplayName("On text entered")
        void test0() {
            // action
            citiesSearchPresenter.onSearchTextSubmitted(LAST_ENTERED_TEXT);
        }

        @Test
        @DisplayName("On text typing")
        void test1() {
            // action
            citiesSearchPresenter.onSearchTextChanged(LAST_ENTERED_TEXT);
        }

        @AfterEach
        void afterEachTest() {
            // verify
            verify(view).clearList();
            verify(view).hideProgress();
            verify(view).hideError();
            verifyNoMoreInteractions(view);
        }
    }


    @Nested
    @DisplayName("When interactor successful (single request)")
    class OnSuccessSingle {

        private final List<CityData> cityList = Arrays.asList(
                new CityData("Amsterdam", "NL", null, 1),
                new CityData("Athens", "GR", null, 2)
        );

        @BeforeEach
        void beforeEachTest() {
            // when
            when(citiesSearchInteractor.requestCities(anyString(), anyInt(), anyInt()))
                    .thenReturn(Single.just(new CitiesSearchResult(CitiesSearchResultCode.OK, new CitiesSearchResultData(cityList))));

        }

        @Nested
        @DisplayName("Non-empty text entered")
        class NonEmptyTextTests {
            @Nested
            @DisplayName("Should be only one request with the last entered text")
            class ShouldBeOnlyOneRequestTests {
                @Test
                @DisplayName("when typing text and then submit")
                void shouldBeOnlyOneRequest1() {
                    // action
                    citiesSearchPresenter.onSearchTextChanged("aaaaaa");
                    citiesSearchPresenter.onSearchTextChanged("bbbbb");
                    citiesSearchPresenter.onSearchTextSubmitted(LAST_ENTERED_TEXT);
                }

                @Test
                @DisplayName("when typing one text, then another")
                void shouldBeOnlyOneRequest2() {
                    // action
                    citiesSearchPresenter.onSearchTextChanged("aaaa");
                    citiesSearchPresenter.onSearchTextChanged("bbbb");
                    citiesSearchPresenter.onSearchTextChanged("cccc");
                    citiesSearchPresenter.onSearchTextChanged(LAST_ENTERED_TEXT);
                }

                @Test
                @DisplayName("when submit text")
                void shouldBeOnlyOneRequest3() {
                    // action
                    citiesSearchPresenter.onSearchTextSubmitted(LAST_ENTERED_TEXT);
                }
            }

            @Test
            @DisplayName("text should be trimmed")
            void textShouldBeTrimmed() {
                // action
                citiesSearchPresenter.onSearchTextSubmitted("    " + LAST_ENTERED_TEXT + "   ");
            }

            @AfterEach
            void afterEachTest() {
                waitForRequestCompleteness();

                // verify
                verify(view, never()).showError();
                verifyProgressIsShowed();

                verify(citiesSearchInteractor, times(1)).requestCities(LAST_ENTERED_TEXT, 0, INITIAL_PAGE_SIZE);
            }
        }

        @Nested
        @DisplayName("On empty text entered, request shouldn't be performed")
        class EmptyTextTests {

            @Test
            @DisplayName("on empty string submitted")
            void emptyTextTest1() {
                // action
                citiesSearchPresenter.onSearchTextSubmitted("");
            }

            @Test
            @DisplayName("on empty string entered")
            void emptyTextTest3() {
                // action
                citiesSearchPresenter.onSearchTextChanged("");
            }

            @Test
            @DisplayName("on empty string entered, then submitted")
            void emptyTextTest2() {
                // action
                citiesSearchPresenter.onSearchTextChanged("");
                citiesSearchPresenter.onSearchTextSubmitted("");
            }

            @Test
            @DisplayName("on non-empty string entered, then empty submitted")
            void emptyTextTest4() {
                // action
                citiesSearchPresenter.onSearchTextChanged("cdddd");
                citiesSearchPresenter.onSearchTextChanged("rfefer");
                citiesSearchPresenter.onSearchTextSubmitted("");
            }

            @Test
            @DisplayName("on non-empty string entered, then empty entered")
            void emptyTextTest5() {
                // action
                citiesSearchPresenter.onSearchTextChanged("cdddd");
                citiesSearchPresenter.onSearchTextChanged("rfefer");
                citiesSearchPresenter.onSearchTextChanged("");
            }

            @Test
            @DisplayName("on spaces-only string submitted")
            void spacedTextTest1() {
                // action
                citiesSearchPresenter.onSearchTextSubmitted("    ");
            }

            @Test
            @DisplayName("on spaces-only string entered")
            void spacedTextTest3() {
                // action
                citiesSearchPresenter.onSearchTextChanged("    ");
            }

            @Test
            @DisplayName("on empty string entered, then submitted")
            void spacedTextTest2() {
                // action
                citiesSearchPresenter.onSearchTextChanged("");
                citiesSearchPresenter.onSearchTextSubmitted("");
            }

            @Test
            @DisplayName("on non-empty string entered, then spaces-only string submitted")
            void spacedTextTest4() {
                // action
                citiesSearchPresenter.onSearchTextChanged("cdddd");
                citiesSearchPresenter.onSearchTextChanged("rfefer");
                citiesSearchPresenter.onSearchTextSubmitted("   ");
            }

            @Test
            @DisplayName("on non-empty string entered, then spaces-only string entered")
            void spacedTextTest5() {
                // action
                citiesSearchPresenter.onSearchTextChanged("cdddd");
                citiesSearchPresenter.onSearchTextChanged("rfefer");
                citiesSearchPresenter.onSearchTextChanged("   ");
            }

            @AfterEach
            void afterEachTest() {
                // verify
                verify(view, never()).showError();

                verify(citiesSearchInteractor, never()).requestCities(anyString(), anyInt(), anyInt());

                // Here should be performed good request to wait for request completeness
                // action
                citiesSearchPresenter.onSearchTextChanged("cece");

                waitForRequestCompleteness();
            }
        }
    }

    @Nested
    @DisplayName("When interactor successful (multiple requests)")
    class OnSuccessMultiple {

        @BeforeEach
        void beforeEachTest() {
            mockRequestCitiesWithRealPagingProcessing();
        }

        @Test
        @DisplayName("view's stream should correspond to source")
        void viewStreamShouldCorrespondToSource() {
            // action
            citiesSearchPresenter.onSearchTextSubmitted("cat");

            // verify
            verify(view).updateCityList(argThat(list -> {
                boolean areListsIdentical = false;

                for (int i = PAGE_SIZE * INITIAL_PAGE_SIZE_FACTOR; i < CITY_COUNT; ++i) {

                    List<Integer> cityIdListToCheck = new ArrayList<>();

                    for (int i1 = 0; i1 < list.size(); ++i1) {
                        cityIdListToCheck.add(cityIdsStream.get(i1));
                    }

                    areListsIdentical = areListsIdentical(cityIdListToCheck, list);

                    if (!areListsIdentical) {
                        System.out.println("lists are different:");
                        System.out.println("expected list: " + cityIdListToCheck);

                        List<Integer> actualCityIdList = new ArrayList<>();
                        for (int ii = 0; ii < list.size(); ++ii) {
                            actualCityIdList.add(cityIdsStream.get(ii));
                        }

                        System.out.println("actual list: " + actualCityIdList);

                        break;
                    }

                    list.loadAround(list.size() - 1);
                }
                return areListsIdentical;
            }));

        }

        private boolean areListsIdentical(List<Integer> cityIdList, PagedList<CityData> pagedList) {
            if (cityIdList.size() != pagedList.size()) {
                return false;
            }

            for (int i = 0; i < cityIdList.size(); ++i) {
                if (cityIdList.get(i) != pagedList.get(i).getId()) {
                    return false;
                }
            }

            return true;
        }
    }

    @Nested
    @DisplayName("When failed and then successful requestCities() result, should show error")
    class RetryTests {
        @BeforeEach
        void beforeEachTest() {
            // when
            when(citiesSearchInteractor.requestCities(anyString(), anyInt(), anyInt()))
                    .thenReturn(Single.just(new CitiesSearchResult(CitiesSearchResultCode.ERROR, null)))
                    .thenAnswer(createAnswerRequestCitiesWithRealPagingProcessing());

        }

        @Nested
        @DisplayName("CitiesSearchDataSource.loadInitial() processing")
        class LoadInitialTests {
            @BeforeEach
            void beforeEachTest() {
                // when
                when(citiesSearchInteractor.requestCities(anyString(), anyInt(), anyInt()))
                        .thenReturn(Single.just(new CitiesSearchResult(CitiesSearchResultCode.ERROR, null)))
                        .thenAnswer(createAnswerRequestCitiesWithRealPagingProcessing());
            }

            @Test
            @DisplayName("requestCities() shouldn't be called one more time")
            void onlyOnce() {
                // action
                citiesSearchPresenter.onSearchTextSubmitted(LAST_ENTERED_TEXT);

                // verify
                verify(citiesSearchInteractor, times(1)).requestCities(eq(LAST_ENTERED_TEXT), anyInt(), anyInt());
            }

            @Test
            @DisplayName("when retry clicked, should perform request")
            void retryInitial() {
                // action
                citiesSearchPresenter.onSearchTextSubmitted(LAST_ENTERED_TEXT);
                citiesSearchPresenter.onRetryClicked();

                // verify
                verify(citiesSearchInteractor, times(2)).requestCities(eq(LAST_ENTERED_TEXT), anyInt(), anyInt());
            }

        }

        @Nested
        @DisplayName("CitiesSearchDataSource.loadAfter() processing")
        class LoadAfterTests {
            @BeforeEach
            void beforeEachTest() {
                final List<CityData> cityList = new ArrayList<>();

                // For first request return list with number of items equal to initial loading
                for (int i = 0; i < citiesSearchPresenter.pageSize * citiesSearchPresenter.initialPageSizeFactor; ++i) {
                    cityList.add(new CityData("" + i, "c" + i, new CityCoordinates(i * 2.0, i * 3.0), i));
                }

                // when
                when(citiesSearchInteractor.requestCities(anyString(), anyInt(), anyInt()))
                        .thenReturn(Single.just(new CitiesSearchResult(CitiesSearchResultCode.OK, new CitiesSearchResultData(cityList))))
                        .thenReturn(Single.just(new CitiesSearchResult(CitiesSearchResultCode.ERROR, null)))
                        .thenAnswer(createAnswerRequestCitiesWithRealPagingProcessing());
            }

            @Test
            @DisplayName("requestCities() shouldn't be called one more time")
            void onlyOnce() {
                // action
                citiesSearchPresenter.onSearchTextSubmitted(LAST_ENTERED_TEXT);
                simulateScroll();

                // verify
                verify(citiesSearchInteractor, atLeast(2)).requestCities(eq(LAST_ENTERED_TEXT), anyInt(), anyInt());
            }

            @Test
            @DisplayName("when retry clicked, should perform request")
            void retryInitial() {
                // action
                citiesSearchPresenter.onSearchTextSubmitted(LAST_ENTERED_TEXT);
                simulateScroll();
                citiesSearchPresenter.onRetryClicked();
                simulateScroll();

                // verify
                // At least 3 - first error call, then second success call and then after "scroll" call
                verify(citiesSearchInteractor, atLeast(3)).requestCities(eq(LAST_ENTERED_TEXT), anyInt(), anyInt());
            }
        }

        @AfterEach
        void afterEachTest() {
            // verify
            verify(view).showError();
            verifyProgressIsShowed();
        }
    }

    @Test
    @DisplayName("When view ready, should set search text from saved result")
    void onViewReadyTest() {
        String savedSearchString = "aaaa";

        // when
        when(citiesScreenInteractor.getCurrentSearchText()).thenReturn(savedSearchString);

        // action
        citiesSearchPresenter.onViewReady();

        // verify
        verify(view).setSearchText(savedSearchString);
    }

    @Test
    @DisplayName("When city is clicked, should save current city and show map")
    void onCityClickedTest() {
        CityData clickedCity = new CityData("trgjtr",
                "efwefe",
                new CityCoordinates(22.0, 55.2),
                444);

        // action
        citiesSearchPresenter.onCityClicked(clickedCity);

        // verify
        verify(citiesScreenInteractor).setCurrentSelectedCity(clickedCity);
        verify(view).showMapWithSelectedCity();
    }

    @Test
    @DisplayName("When city info is clicked, should save current city and show info")
    void onCityInfoClickedTest() {
        CityData clickedCity = new CityData("uikui",
                "uju",
                new CityCoordinates(212.0, 555.2),
                633);

        // action
        citiesSearchPresenter.onCityInfoClicked(clickedCity);

        // verify
        verify(citiesScreenInteractor).setCurrentSelectedCity(clickedCity);
        verify(view).showCityInfoForSelectedCity();
    }

    @Test
    @DisplayName("When about app is clicked, should show app info")
    void onAboutAppClickedTest() {
        // action
        citiesSearchPresenter.onAboutAppClicked();

        // verify
        verify(view).showAboutApp();
    }

    @Test
    @DisplayName("When view is destroyed and request completed, should dispose completable disposable")
    void onDestroyViewTest1() {
        // action
        citiesSearchPresenter.onSearchTextSubmitted("cecwecwe");
        waitForRequestCompleteness();
        citiesSearchPresenter.onDestroyView();

        // verify
        assertTrue(citiesSearchPresenter.compositeDisposable.isDisposed());
    }

    @Test
    @DisplayName("When view is destroyed and request is active, the request should be interrupted")
    void onDestroyViewTest2() throws InterruptedException {
        // when
        when(citiesSearchInteractor.requestCities(anyString(), anyInt(), anyInt()))
                .thenReturn(Single.timer(Long.MAX_VALUE, TimeUnit.DAYS) // simulate very long request
                        .map(aLong -> new CitiesSearchResult(CitiesSearchResultCode.OK, null)));

        // action
        citiesSearchPresenter.onSearchTextSubmitted("cecwecwe");
        // To make sure the request in actually performed
        verify(citiesSearchInteractor, timeout(Long.MAX_VALUE)).requestCities(anyString(), anyInt(), anyInt());
        citiesSearchPresenter.onDestroyView();

        // verify
        assertTrue(citiesSearchPresenter.compositeDisposable.isDisposed());
    }

    private void createAndSetupPresenter() {
        citiesSearchPresenter = new CitiesSearchPresenterImpl(view, schedulerProvider, citiesSearchInteractor, citiesScreenInteractor);
        citiesSearchPresenter.pageSize = PAGE_SIZE;
        citiesSearchPresenter.initialPageSizeFactor = INITIAL_PAGE_SIZE_FACTOR;
        citiesSearchPresenter.performSearchTimeoutMillis = 10;
    }

    private void waitForRequestCompleteness() {
        try {
            citiesSearchPresenter.requestCompletedCompletable
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

    private void mockRequestCitiesWithRealPagingProcessing() {
        // On each call requestCities(), return result corresponds to passed page parameters
        when(citiesSearchInteractor.requestCities(anyString(), anyInt(), anyInt())).thenAnswer(createAnswerRequestCitiesWithRealPagingProcessing());
    }

    private Answer createAnswerRequestCitiesWithRealPagingProcessing() {
        return invocation -> {
            int pageIndex = invocation.getArgument(1);
            int pageItemCount = invocation.getArgument(2);

            List<CityData> cityList = new ArrayList<>();

            for (int i = pageIndex * pageItemCount; i < Math.min(pageItemCount * (pageIndex + 1), cityIdsStream.size()); ++i) {
                cityList.add(new CityData(null, null, null, cityIdsStream.get(i)));
            }

            return Single.just(new CitiesSearchResult(CitiesSearchResultCode.OK, new CitiesSearchResultData(cityList)));
        };
    }

    private void simulateScroll() {
        verify(view).updateCityList(argThat(list -> {
            list.loadAround(list.size() - 1);
            return true;
        }));
    }
}