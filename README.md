11 October 2019
CSCI 4176
Erin Aucoin
B00631829

This project is a simple weather application for android.

Extra features include:
-   when stopping the app, the most recently searched city will be saved in a shared preferences file.  Upon starting the app, the new weather data for that city will be loaded and shown.  A user would likely use the app forprimarily one city so this will save them time from typing in the name of the city every time.
-   when typing the name of a city, either the enter key or the button will trigger the search. (https://stackoverflow.com/a/53854254)
-   the horizontal view is blocked to preserve the placement of information.
-   Basic error handling: When the user enters something other than a known city name, an error message appears.  When the user's phone is not connected to the internet, a different error message appears.
-   the city entered by the user is removed from the editText field after the search has occured.  This allows the user to search for another city without having to erase the first one.
