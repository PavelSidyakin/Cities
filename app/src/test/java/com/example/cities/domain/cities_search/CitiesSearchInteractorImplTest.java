package com.example.cities.domain.cities_search;

import com.example.SchedulerProviderStub;
import com.example.cities.model.CitiesSearchResult;
import com.example.cities.model.CitiesSearchResultCode;
import com.example.cities.model.CitiesSearchResultData;
import com.example.cities.model.data.CityData;
import com.example.cities.utils.XLog;
import com.example.cities.utils.rx.SchedulerProvider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("CitiesSearchInteractorImpl tests")
class CitiesSearchInteractorImplTest {

    @Mock
    private CitiesSearchPreLoadInteractor citiesSearchPreLoadInteractor;
    
    private SchedulerProvider schedulerProvider = new SchedulerProviderStub();

    private CitiesSearchInteractorImpl citiesSearchInteractor;
    
    private final List<CityData> sortedListOfCityData = createSortedListOfCityData();
    private final List<CityData> aaaExpectedListOfCityData = createAaaExpectedListOfCityData();
    private final List<CityData> bbbExpectedListOfCityData = createBbbExpectedListOfCityData();
    private final List<CityData> zzzExpectedListOfCityData = createZzzExpectedListOfCityData();

    @BeforeEach
    void beforeEachTest() {
        XLog.enable(false);
        MockitoAnnotations.initMocks(this);

        citiesSearchInteractor = new CitiesSearchInteractorImpl(citiesSearchPreLoadInteractor, schedulerProvider);

        // when
        when(citiesSearchPreLoadInteractor.getSortedListOfCityData()).thenReturn(sortedListOfCityData);
    }

    @Nested
    @DisplayName("Tests where the search string corresponds to different parts of the original list")
    class TestDifferentPartsOfTheList {

        @Test
        @DisplayName("When \"aaa\" entered, should return list with cities starts on \"aaa\"")
        void testAaa() throws InterruptedException {
            // action
            citiesSearchInteractor.requestCities("aaa", 0, 100)
                .test()
                .await()
                .assertResult(new CitiesSearchResult(CitiesSearchResultCode.OK, new CitiesSearchResultData(aaaExpectedListOfCityData)));
        }

        @Test
        @DisplayName("When \"bbb\" entered, should return list with cities starts on \"bbb\"")
        void testBbb() throws InterruptedException {
            // action
            citiesSearchInteractor.requestCities("bbb", 0, 100)
                .test()
                .await()
                .assertResult(new CitiesSearchResult(CitiesSearchResultCode.OK, new CitiesSearchResultData(bbbExpectedListOfCityData)));
        }

        @Test
        @DisplayName("When \"zzz\" entered, should return list with cities starts on \"zzz\"")
        void testZzz() throws InterruptedException {
            // action
            citiesSearchInteractor.requestCities("zzz", 0, 100)
                .test()
                .await()
                .assertResult(new CitiesSearchResult(CitiesSearchResultCode.OK, new CitiesSearchResultData(zzzExpectedListOfCityData)));
        }
    }

    @Test
    @DisplayName("When the same text requested multiple times, should request whole list only once (use cached list)")
    void testCachingOfResult() throws InterruptedException {
        // action
        citiesSearchInteractor.requestCities("zzz", 0, 100)
            .test()
            .await()
            .assertComplete();

        citiesSearchInteractor.requestCities("zzz", 1, 10)
            .test()
            .await()
            .assertComplete();

        citiesSearchInteractor.requestCities("zzz", 2, 5)
            .test()
            .await()
            .assertComplete();

        // verify
        verify(citiesSearchPreLoadInteractor, times(1)).getSortedListOfCityData();
    }

    @Nested
    @DisplayName("Test return correct data for different page parameters")
    class TestPaging {

        @Test
        @DisplayName("When 0 page requested, should return correspond values from start")
        void pagingTest0() throws InterruptedException {
            List<CityData> expectedList = Arrays.asList(
                    new CityData("bbba", null, null, 0),
                    new CityData("bbbb", null, null, 0),
                    new CityData("bbbc", null, null, 0),
                    new CityData("bbbd", null, null, 0),
                    new CityData("bbbe", null, null, 0)
            );

            // action
            citiesSearchInteractor.requestCities("bbb", 0, 5)
                .test()
                .await()
                .assertResult(new CitiesSearchResult(CitiesSearchResultCode.OK, new CitiesSearchResultData(expectedList)));
        }

        @Test
        @DisplayName("When 1 page requested, should return correspond values from pageItemCount")
        void pagingTest1() throws InterruptedException {
            List<CityData> expectedList = Arrays.asList(
                    new CityData("bbbg", null, null, 0),
                    new CityData("bbbh", null, null, 0),
                    new CityData("bbbi", null, null, 0),
                    new CityData("bbbj", null, null, 0),
                    new CityData("bbbk", null, null, 0),
                    new CityData("bbbl", null, null, 0)
            );

            // action
            citiesSearchInteractor.requestCities("bbb", 1, 6)
                .test()
                .await()
                .assertResult(new CitiesSearchResult(CitiesSearchResultCode.OK, new CitiesSearchResultData(expectedList)));
        }

        @Test
        @DisplayName("When last page requested, the rest of elements equals to page size, should return list with size = page size")
        void pagingTest2() throws InterruptedException {
            List<CityData> expectedList = Arrays.asList(
                    new CityData("bbbn", null, null, 0),
                    new CityData("bbbo", null, null, 0),
                    new CityData("bbbp", null, null, 0),
                    new CityData("bbbq", null, null, 0),
                    new CityData("bbbr", null, null, 0),
                    new CityData("bbbs", null, null, 0),
                    new CityData("bbbt", null, null, 0),
                    new CityData("bbbu", null, null, 0),
                    new CityData("bbbv", null, null, 0),
                    new CityData("bbbw", null, null, 0),
                    new CityData("bbbx", null, null, 0),
                    new CityData("bbby", null, null, 0),
                    new CityData("bbbz", null, null, 0)
            );

            // action
            citiesSearchInteractor.requestCities("bbb", 1, 13)
                .test()
                .await()
                .assertResult(new CitiesSearchResult(CitiesSearchResultCode.OK, new CitiesSearchResultData(expectedList)));
        }

        @Test
        @DisplayName("When last page requested, the rest of elements less then page size, should return list with the rest of list")
        void pagingTest3() throws InterruptedException {
            List<CityData> expectedList = Arrays.asList(
                    new CityData("bbby", null, null, 0),
                    new CityData("bbbz", null, null, 0)
            );

            // action
            citiesSearchInteractor.requestCities("bbb", 4, 6)
                .test()
                .await()
                .assertResult(new CitiesSearchResult(CitiesSearchResultCode.OK, new CitiesSearchResultData(expectedList)));
        }

        @Test
        @DisplayName("When requested 0 page size equals to the whole list size, should return whole list")
        void pagingTest5() throws InterruptedException {

            // action
            citiesSearchInteractor.requestCities("bbb", 0, bbbExpectedListOfCityData.size())
                .test()
                .await()
                .assertResult(new CitiesSearchResult(CitiesSearchResultCode.OK, new CitiesSearchResultData(bbbExpectedListOfCityData)));
        }

        @Test
        @DisplayName("When requested 0 page size greater then the whole list size, should return whole list")
        void pagingTest6() throws InterruptedException {

            // action
            citiesSearchInteractor.requestCities("bbb", 0, bbbExpectedListOfCityData.size() + 100)
                .test()
                .await()
                .assertResult(new CitiesSearchResult(CitiesSearchResultCode.OK, new CitiesSearchResultData(bbbExpectedListOfCityData)));
        }

        @Test
        @DisplayName("When requested page outside of list size, should return empty list")
        void pagingTest4() throws InterruptedException {
            List<CityData> expectedList = Collections.emptyList();

            // action
            citiesSearchInteractor.requestCities("bbb", 10000, 6)
                .test()
                .await()
                .assertResult(new CitiesSearchResult(CitiesSearchResultCode.OK, new CitiesSearchResultData(expectedList)));
        }
    }

    @Nested
    @DisplayName("Test wrong parameters")
    class TestWrongParams {

        @Test
        @DisplayName("When empty search text, should emit error")
        void wrongParamTest0() throws InterruptedException {
            // action
            citiesSearchInteractor.requestCities("", 10000, 6)
                .test()
                .await()
                .assertResult(new CitiesSearchResult(CitiesSearchResultCode.ERROR, null));
        }

        @Test
        @DisplayName("When spaces-only search text, should emit error")
        void wrongParamTest1() throws InterruptedException {
            // action
            citiesSearchInteractor.requestCities("   ", 10000, 6)
                .test()
                .await()
                .assertResult(new CitiesSearchResult(CitiesSearchResultCode.ERROR, null));
        }

        @Test
        @DisplayName("When negative page index, should emit error")
        void wrongParamTest2() throws InterruptedException {
            // action
            citiesSearchInteractor.requestCities("bbb", -1, 6)
                .test()
                .await()
                .assertResult(new CitiesSearchResult(CitiesSearchResultCode.ERROR, null));
        }

        @Test
        @DisplayName("When negative page count, should emit error")
        void wrongParamTest3() throws InterruptedException {
            // action
            citiesSearchInteractor.requestCities("bbb", 1, -1)
                .test()
                .await()
                .assertResult(new CitiesSearchResult(CitiesSearchResultCode.ERROR, null));
        }

        @Test
        @DisplayName("When negative page count and page index, should emit error")
        void wrongParamTest4() throws InterruptedException {
            // action
            citiesSearchInteractor.requestCities("bbb", -5, -10)
                .test()
                .await()
                .assertResult(new CitiesSearchResult(CitiesSearchResultCode.ERROR, null));
        }
    }

    @Test
    @DisplayName("When search text is not found, should return empty list")
    void pagingTest4() throws InterruptedException {
        List<CityData> expectedList = Collections.emptyList();

        // action
        citiesSearchInteractor.requestCities("ddd", 0, 10)
                .test()
                .await()
                .assertResult(new CitiesSearchResult(CitiesSearchResultCode.OK, new CitiesSearchResultData(expectedList)));
    }

    private List<CityData> createSortedListOfCityData() {
        return Arrays.asList(
                new CityData("aaaa", null, null, 0),
                new CityData("aaab", null, null, 0),
                new CityData("aaac", null, null, 0),
                new CityData("aaad", null, null, 0),
                new CityData("bbba", null, null, 0),
                new CityData("bbbb", null, null, 0),
                new CityData("bbbc", null, null, 0),
                new CityData("bbbd", null, null, 0),
                new CityData("bbbe", null, null, 0),
                new CityData("bbbf", null, null, 0),
                new CityData("bbbg", null, null, 0),
                new CityData("bbbh", null, null, 0),
                new CityData("bbbi", null, null, 0),
                new CityData("bbbj", null, null, 0),
                new CityData("bbbk", null, null, 0),
                new CityData("bbbl", null, null, 0),
                new CityData("bbbm", null, null, 0),
                new CityData("bbbn", null, null, 0),
                new CityData("bbbo", null, null, 0),
                new CityData("bbbp", null, null, 0),
                new CityData("bbbq", null, null, 0),
                new CityData("bbbr", null, null, 0),
                new CityData("bbbs", null, null, 0),
                new CityData("bbbt", null, null, 0),
                new CityData("bbbu", null, null, 0),
                new CityData("bbbv", null, null, 0),
                new CityData("bbbw", null, null, 0),
                new CityData("bbbx", null, null, 0),
                new CityData("bbby", null, null, 0),
                new CityData("bbbz", null, null, 0),
                new CityData("zzza", null, null, 0),
                new CityData("zzzb", null, null, 0),
                new CityData("zzzc", null, null, 0)
        );
    }

    private List<CityData> createAaaExpectedListOfCityData() {
        return Arrays.asList(
                new CityData("aaaa", null, null, 0),
                new CityData("aaab", null, null, 0),
                new CityData("aaac", null, null, 0),
                new CityData("aaad", null, null, 0)
        );
    }

    private List<CityData> createBbbExpectedListOfCityData() {
        return Arrays.asList(
                new CityData("bbba", null, null, 0),
                new CityData("bbbb", null, null, 0),
                new CityData("bbbc", null, null, 0),
                new CityData("bbbd", null, null, 0),
                new CityData("bbbe", null, null, 0),
                new CityData("bbbf", null, null, 0),
                new CityData("bbbg", null, null, 0),
                new CityData("bbbh", null, null, 0),
                new CityData("bbbi", null, null, 0),
                new CityData("bbbj", null, null, 0),
                new CityData("bbbk", null, null, 0),
                new CityData("bbbl", null, null, 0),
                new CityData("bbbm", null, null, 0),
                new CityData("bbbn", null, null, 0),
                new CityData("bbbo", null, null, 0),
                new CityData("bbbp", null, null, 0),
                new CityData("bbbq", null, null, 0),
                new CityData("bbbr", null, null, 0),
                new CityData("bbbs", null, null, 0),
                new CityData("bbbt", null, null, 0),
                new CityData("bbbu", null, null, 0),
                new CityData("bbbv", null, null, 0),
                new CityData("bbbw", null, null, 0),
                new CityData("bbbx", null, null, 0),
                new CityData("bbby", null, null, 0),
                new CityData("bbbz", null, null, 0)
        );
    }
    private List<CityData> createZzzExpectedListOfCityData() {
        return Arrays.asList(
                new CityData("zzza", null, null, 0),
                new CityData("zzzb", null, null, 0),
                new CityData("zzzc", null, null, 0)
        );
    }
}