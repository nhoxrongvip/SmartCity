package ca.chesm.it.smartcity.PreSenter;

public interface LightView {
    void getDataLight(Long distance, Boolean state,  String id);

    void EventChange();
}
