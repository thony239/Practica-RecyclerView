package com.example.app5eartquake;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.app5eartquake.API.EqApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private MutableLiveData<List<Earthquake>> eqlist =new MutableLiveData<>();

    public LiveData<List<Earthquake>> getEqlist() {
        return eqlist;
    }

    public void getEarthquakes(){
        /*ArrayList<Earthquake> eqlist= new ArrayList<>();
        eqlist.add(new Earthquake("001", "Carchi-Tulcan",5.2, 12082022, 100.5,154.8  ));
        eqlist.add(new Earthquake("002", "Guayas-Guayaquil",4.1, 12082022, 100.5,154.8  ));
        eqlist.add(new Earthquake("003", "Chimborazo- Alusi",3.15, 12082022, 100.5,154.8  ));
        eqlist.add(new Earthquake("004", "Azuay-Cuenca",5.0, 12082022, 100.5,154.8  ));
        eqlist.add(new Earthquake("005", "Azuay-Paute",5.0, 12082022, 100.5,154.8  ));
        this.eqlist.setValue(eqlist);*/
        EqApiClient.EqService service = EqApiClient.getInstance().getService();
        service.getEarthquakes().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //Log.d("MainViewModel", response.body());
                List<Earthquake> earthquakeList =parseEarthquake(response.body());
                eqlist.setValue(earthquakeList);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("MainViewModel",t.getMessage());
            }
        });
    }
    private List<Earthquake> parseEarthquake (String responseString){
        ArrayList<Earthquake> eqList = new ArrayList<>();

        try {
            JSONObject jsonResponse = new JSONObject(responseString);
            JSONArray featuresJSONArray = jsonResponse.getJSONArray("features");
            for (int i=0; i<featuresJSONArray.length();i++){
                JSONObject jsonFeatures = featuresJSONArray.getJSONObject(i);
                String id=jsonFeatures.getString("id");

                JSONObject jsonProperties =jsonFeatures.getJSONObject("properties");
                double magnitude =jsonProperties.getDouble("mag");

                String place=jsonProperties.getString("place");
                long time =jsonProperties.getLong("time");

                JSONObject jsonGeometry =jsonFeatures.getJSONObject("geometry");
                JSONArray coordinatesJSONArray= jsonGeometry.getJSONArray("coordinates");
                double longitude = coordinatesJSONArray.getDouble(0);
                double latitude=coordinatesJSONArray.getDouble(1);

                Earthquake earthquake=new Earthquake(id,place,magnitude,time,latitude,longitude);
                eqList.add(earthquake);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return eqList;
    }
}
