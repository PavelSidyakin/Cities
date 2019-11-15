package com.example.cities.presentation.cities_search.presenter;

import androidx.paging.PagedList;

import com.example.SchedulerProviderStub;
import com.example.cities.domain.cities_search.CitiesScreenInteractor;
import com.example.cities.domain.cities_search.CitiesSearchInteractor;
import com.example.cities.model.CitiesSearchResult;
import com.example.cities.model.CitiesSearchResultCode;
import com.example.cities.model.CitiesSearchResultData;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Single;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

    private static final String LAST_ENTERED_TEXT = "LAST ENTERED TEXT";

    private static final int INITIAL_PAGE_SIZE = CitiesSearchPresenterImpl.DEFAULT_INITIAL_PAGE_SIZE_FACTOR * CitiesSearchPresenterImpl.DEFAULT_PAGE_SIZE;

    @BeforeEach
    void beforeEachTest() {
        XLog.enable(false);
        MockitoAnnotations.initMocks(this);

        citiesSearchPresenter = new CitiesSearchPresenterImpl(view, schedulerProvider, citiesSearchInteractor, citiesScreenInteractor);

        citiesSearchPresenter.performSearchTimeoutMillis = 10;
        when(citiesScreenInteractor.getCurrentSearchText()).thenReturn("");
        citiesSearchPresenter.onViewReady();
    }

    @Nested
    @DisplayName("When error is occurred, should show error message")
    class OnError {
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

        @AfterEach
        void afterEachTest() {
            waitForRequestCompleteness();

            // verify
            verifyShowProgress();
            verify(citiesSearchInteractor, times(1)).requestCities(LAST_ENTERED_TEXT, 0, INITIAL_PAGE_SIZE);

            verify(view).showError();
        }
    }

    @Nested
    @DisplayName("When interactor successful (single request)")
    class OnSuccessSingle {

        private final List<CityData> cityList = Arrays.asList(
                new CityData("Amsterdam", "NL", null, 1),
                new CityData("Utrecht", "NL", null, 2)
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
                verifyShowProgress();

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
        private List<Integer> cityIdsStream = new ArrayList<>();
        private int pageSize = 5;
        private int initialPageSizeFactor = 3;
        private int cityCount = 47;

        @BeforeEach
        void beforeEachTest() {
            for (int i = 0; i < cityCount; ++i) {
                cityIdsStream.add(i);
            }

            citiesSearchPresenter.pageSize = pageSize;
            citiesSearchPresenter.initialPageSizeFactor = initialPageSizeFactor;

            // On each call requestCities(), return result corresponds to passed page parameters
            when(citiesSearchInteractor.requestCities(anyString(), anyInt(), anyInt())).thenAnswer(invocation -> {
                int pageIndex = invocation.getArgument(1);
                int pageItemCount = invocation.getArgument(2);

                List<CityData> cityList = new ArrayList<>();

                for (int i = pageIndex * pageItemCount; i < Math.min(pageItemCount * (pageIndex + 1), cityIdsStream.size()); ++i) {
                    cityList.add(new CityData(null, null, null, cityIdsStream.get(i)));
                }

                return Single.just(new CitiesSearchResult(CitiesSearchResultCode.OK, new CitiesSearchResultData(cityList)));
            });
        }

        @Test
        @DisplayName("view's stream should correspond to source")
        void viewStreamShouldCorrespondToSource() {
            // action
            citiesSearchPresenter.onSearchTextSubmitted("cat");

            // verify
            verify(view).updateCityList(argThat(list -> {
                boolean areListsIdentical = false;

                for (int i = pageSize * initialPageSizeFactor; i < cityCount; ++i) {

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

    private void waitForRequestCompleteness() {
        try {
            citiesSearchPresenter.requestCompletedCompletable
                    .test()
                    .await()
                    .assertComplete();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void verifyShowProgress() {
        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showProgress();
        inOrder.verify(view).hideProgress();
    }
}