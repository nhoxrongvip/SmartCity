## SmartCityMonitor

##Main Fragments

Air quality fragment
```java
/*
Thanh Phat Lam has used API in order to retrieve Air quality data from the waqi API and then display it into the bar chart
The current location is also displayed on top of the fragment.

The average pollutant level of the currently displayed city. The "human face" ImageView and the background color will change according to the level of pollutant.

API takes the city's name and gets data from that city.

Data will be passed into the array list, and from this Array List pass into the bar chart
Data will consist of pollutants O3 level, pollutants PM10 level, pollutants PM2.5 level.

Users can choose which data to display from the radio group buttons.
*/

//API 
url = "https://api.waqi.info/feed/" + city_name + "/?token=" + TOKEN;


```

Garbage Bin control
```java
/*
Since real-time data is unavailable for these fragments. Dung Ly has used Firebase to implement his own simulation data and send it back to the fragments.

Garbage bin fragments display a level of garbage, recycle garbage, and organic garbage in a specific location address.

The level of garbage is displayed using the circular progress bar is displayed as the percentage of the garbage level.
*/

//Firebase data receiver
DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Garbage").child("City").child(city.getName()).child(String.valueOf(city.getId()));

/*
There is a random data generator button that generates a random percentage number of garbage levels which is used for simulation

As the garbage reaches 80% of the garbage level, there will be a push notification toward users' phones.
Collect button will notify the garbage collector company to collect the garbage.
*/
```
City Light Control
```java
/*
Light control fragment updates the state of lights during the time of day (On state during the time between 5 PM to 5 AM).
Similar to the Garbage control fragment, state of lights data will be saved and controlled through Firebase.

Lights' state will be displayed according to the specific location that can be chosen by users.

Users can also turn on/off the lights manually or leave the light to turn on and off automatically
*/
```
Snow Level
```java
/*
Snow level fragment displays the level of snow of assigned cities using OpenWeatherMap API. Similar to the Air Quality fragment, the snow level will be displayed using a bar chart.
However, the snow level will be displayed as a forecast of 3 hours in the future of snow level of the chosen city
*/
```

## Contributors
Thanh Phat Lam N01335598.

Dung Ly N01327929.

Dinh Hoa Tran N01354661.

Hieu Chu N01371619.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
