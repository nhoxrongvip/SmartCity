# SmartCityMonitor

## Fragments

Air quality fragment
```java
/*
Thanh Phat Lam has use API in order to retrieve Air quality data from the waqi API and then display it into the bar chart
Current location is also display on top of the fragment.

The average pollutant level of the current displayed city. The "human face" ImageView and the background color will change according to the level of pollutant.

API takes city's name and get data from that city.

Data will be pass into the array list, and from this Array List pass into the bar chart
Data will consist pollutants O3 level, pollutants PM10 level, pollutants PM2.5 level.

User can choose which data to display from the radio group buttons.
*/

//API 
url = "https://api.waqi.info/feed/" + city_name + "/?token=" + TOKEN;


```

Garbage Bin control
```java
/*
Since real time data is unavailable for this fragments. Dung Ly has used Firebase to 
*/
```
## Usage

```java
import foobar

```

## Contributors
Thanh Phat Lam N01335598.

Dung Ly N01327929.

Dinh Hoa Tran N01354661.

Hieu Chu N01371619.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
