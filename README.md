# Cities

The sample application demonstrates using Clean Architecture.

##### Used technologies/libraries:

The App:
- The Clean Architecture
- Dagger 2
- RxJava
- Paging Library
- AndroidX
- Gson

Unit Tests:

- Mockito
- JUnit 5

UI tests:

- Espresso
- UI Automator


##### Implementation details

On application initialization the city list is loaded from raw resources, then it is sorted in alphabetical order with standard Collections.sort() algorithm.

When user is typing text in the search box, binary search is performed on the sorted list. Binary sort algorithm is effective in this situation. It runs in O(log(n)) time.

 