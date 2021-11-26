package ca.chesm.it.smartcity.models;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import ca.chesm.it.smartcity.PreSenter.ICityLight;

public class Citylight {
    private String cityname;

    private FirebaseFirestore db;
    private ICityLight callback;

    public Citylight(ICityLight callback){
        this.callback=callback;
        db = FirebaseFirestore.getInstance();

    }
    public Citylight(){

    }
    public void getDataCityLight(){
        db.collection("City").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
              for (QueryDocumentSnapshot q : queryDocumentSnapshots){

                  callback.getDataCityname(q.getData().get("cityname").toString());

              }

            }
        });
    }

    public String getCityname() {
        return cityname;
    }

    public void getDataNameStreet(String name_city) {
        db.collection("Street").whereEqualTo("cityname", name_city).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot q : queryDocumentSnapshots){

                    callback.getDataStreetname(q.getData().get("streetname").toString());

                }
            }
        });
    }
}
