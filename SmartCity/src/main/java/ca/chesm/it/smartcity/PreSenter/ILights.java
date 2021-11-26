package ca.chesm.it.smartcity.PreSenter;

public interface ILights {
    void getDataLight(Long distance, Boolean state,  String id);

    void EventsChange();
}
