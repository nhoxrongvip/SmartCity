package ca.chesm.it.smartcity.PreSenter;

import ca.chesm.it.smartcity.models.Citylight;

public class CityLightPreSenter implements  ICityLight{

    private Citylight citylight;
    private CityView callback;

    public CityLightPreSenter(CityView callback){
        this.callback=callback;
        citylight = new Citylight(this);
    }

    public void getDataCityLight(){
        citylight.getDataCityLight();
    }


    @Override
    public void getDataCityname(String cityname) {
        callback.getDataCityname(cityname);
    }

    @Override
    public void getDataStreetname(String streetname) {
        callback.getDataStreetname(streetname);
    }

    public void getDataListCityStreet(String name_city) {
        citylight.getDataNameStreet(name_city);
    }
}
