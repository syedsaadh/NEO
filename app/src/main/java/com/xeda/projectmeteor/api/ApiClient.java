package com.xeda.projectmeteor.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.xeda.projectmeteor.models.NEO;
import com.xeda.projectmeteor.utils.NeoDeserializer;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by Xeda on 24-09-2017.
 */

public class ApiClient {
    public static final String BASE_URL = "https://api.nasa.gov/neo/rest/v1/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(buildGsonConverter())
                    .build();
        }
        return retrofit;
    }
    private static GsonConverterFactory buildGsonConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        // Adding custom deserializers
        gsonBuilder.registerTypeAdapter(NEO.class, new NeoDeserializer());
        Gson myGson = gsonBuilder.create();

        return GsonConverterFactory.create(myGson);
    }
}
