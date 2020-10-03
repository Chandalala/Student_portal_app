package com.chandalala.wua.models;

import android.util.Log;

import com.chandalala.wua.Constants;
import com.chandalala.wua.database.WUA_RetrofitAPI_DataProvider;
import com.chandalala.wua.helper_classes.Utils;
import com.chandalala.wua.objects.Event;

import java.io.IOException;

import retrofit2.Call;

public class EventsModel {

    private Event[] events;
    private static final String TAG = "EventsModel";


    public EventsModel() {


    }


    public Event[] requestServerEvents(){

        WUA_RetrofitAPI_DataProvider wua_retrofitAPI_dataProvider = Utils.buildRetrofit(Constants.RETROFIT_BASE_URL);

        Call<Event[]> call = wua_retrofitAPI_dataProvider.getEvents();

        try {
            events = call.execute().body();
            Log.d(TAG, "requestServerEvents: "+events[0].getTime());
            Log.d(TAG, "requestServerEvents: "+events[0].getContent());
            Log.d(TAG, "requestServerEvents: "+events[0].getImage());

        }
        catch (IOException e) {
            Log.d(TAG, "requestServerEvents: "+e.getMessage());
        }

        return events;
    }


    

}
