package ca.chesm.it.smartcity.models;

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import ca.chesm.it.smartcity.PreSenter.ICityLight;
import ca.chesm.it.smartcity.PreSenter.ILights;

public class Lights {

    private long distance;
    private boolean state;
    private FirebaseFirestore db;
    private ILights callback;
    private String Id;
    public Lights(){

    }

    public Lights(long distance, boolean state, String id) {
        this.distance = distance;
        this.state = state;

        this.Id = id;
    }

    public Lights(ILights callback){
        this.callback = callback;
        db= FirebaseFirestore.getInstance();
    }
    public void getDataLight(String cityname,String streetname){
        db.collection("Light")
                .document(cityname)
                .collection(streetname).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {


                 if(value!=null){
                     for(DocumentChange d : value.getDocumentChanges()){
                         switch (d.getType()){
                             case ADDED:case REMOVED: case MODIFIED:
                                 callback.EventsChange();break;

                         }
                     }

                     for(QueryDocumentSnapshot q : value ){
                         Lights lights = q.toObject(Lights.class);
                         callback.getDataLight(lights.getDistance(),
                                 lights.isState(),q.getId());
                     }
                 }

            }
        });


    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }



    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public void HandleUpdate(String id, int i,String cityname,String streetname) {
        boolean checked = false;
        switch (i){
            case 0 : checked = false;break;
            case  1: checked = true ;break;

        }

        db.collection("Light")
                .document(cityname)
                .collection(streetname).document(id)
                .update("state",checked).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        });

    }
}
