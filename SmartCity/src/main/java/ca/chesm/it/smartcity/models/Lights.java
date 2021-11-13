package ca.chesm.it.smartcity.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Lights {

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private boolean state;
    private long distance;
    private  LightView callback;
    public Lights(){
        firebaseDatabase = FirebaseDatabase.getInstance("https://smartcity-69701-default-rtdb.firebaseio.com/");
         databaseReference = firebaseDatabase.getReference();

    }
    public Lights(LightView callback){
        this.callback=callback;
        firebaseDatabase = FirebaseDatabase.getInstance("https://smartcity-69701-default-rtdb.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference();

    }
    public void SetValueOn(String city, String street){

    }
    public List<String> getDataListCity(){

        List<String>  list = new ArrayList<>();

        databaseReference.child("CityLight").child("City").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot value : snapshot.getChildren()){
                    list.add(value.getKey().toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return  list;
    }
    public List<String> getDataListStreet(String name_city){

        List<String>  list = new ArrayList<>();

        databaseReference.child("CityLight").child("City").child(name_city).child("Street").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot value : snapshot.getChildren()){
                    list.add(value.getKey().toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return  list;
    }
    public List<String> getDataListLight(String namecity, String name_street){
        List<String> list = new ArrayList<>();
        databaseReference.child("CityLight").child("City").child(namecity).child("Street").child(name_street).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot value : snapshot.getChildren()){
                   list.add(value.getKey().toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return  list;
    }
    public List<String> getDataListLight(String namecity, String name_street,String name_light){
        List<String> list = new ArrayList<>();
        databaseReference.child("CityLight").child("City").child(namecity).child("Street").child(name_street).child(name_light).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot value : snapshot.getChildren()){
                  callback.getDataLightNumber(value.getKey().toString());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return  list;
    }

    public Lights(boolean state, long distance){
        this.state = state;
        this.distance= distance;
    }

    public long getDistance() {
        return distance;
    }

    public boolean isState() {
        return state;
    }

    public void setValuesLightOn(String name_city, String name_street, String name_light, String key_state,int state_change) {


         if(state_change == 1){
             databaseReference.child("CityLight").child("City").child(name_city).child("Street").child(name_street)
                     .child(name_light).child(key_state).child("state").setValue(true);
        }else{
            databaseReference.child("CityLight").child("City").child(name_city).child("Street").child(name_street)
                     .child(name_light).child(key_state).child("state").setValue(false);
         }


    }
}
