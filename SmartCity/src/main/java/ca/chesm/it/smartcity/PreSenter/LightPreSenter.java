package ca.chesm.it.smartcity.PreSenter;

import ca.chesm.it.smartcity.models.Lights;

public class LightPreSenter implements  ILights {

    private Lights lights;
    private LightView callback;
    public LightPreSenter(LightView callback){
        this.callback  = callback;
        lights = new Lights(this);
    }
    public void getDataListLight(String cityname,String streetname){
        lights.getDataLight(cityname,streetname);
    }



    @Override
    public void getDataLight(Long distance, Boolean state,  String id) {
        callback.getDataLight(distance,state,id);
    }

    @Override
    public void EventsChange() {
        callback.EventChange();
    }
}
