package com.xeda.projectmeteor.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.xeda.projectmeteor.BuildConfig;
import com.xeda.projectmeteor.api.ApiClient;
import com.xeda.projectmeteor.api.NEOService;
import com.xeda.projectmeteor.models.MeteorObjects;
import com.xeda.projectmeteor.models.NEO;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Xeda on 25-09-2017.
 */

public class DataRepository {
    private NEOService neoService;
    private static DataRepository mInstance;
    private final MutableLiveData<List<MeteorObjects>> data;

    public DataRepository() {
        this.neoService = ApiClient.getClient().create(NEOService.class);
        data = new MutableLiveData<>();
    }

    public static DataRepository getInstance() {
        if(mInstance == null) {
            mInstance = new DataRepository();
        }
        return new DataRepository();
    }

    public LiveData<List<MeteorObjects>> getNeo() {
        data.setValue(new ArrayList<MeteorObjects>());
        return data;
    }
    public void emptyData() {
        data.setValue(new ArrayList<MeteorObjects>());
    }
    public void loadNew(String startDate, String endDate){
        neoService.getFeeds(startDate, endDate, BuildConfig.API_KEY).enqueue(new Callback<NEO>() {
            @Override
            public void onResponse(Call<NEO> call, Response<NEO> response) {
                data.setValue(response.body().getNearEarthObjects().get(0).getMeteorObjects());
            }

            @Override
            public void onFailure(Call<NEO> call, Throwable t) {

            }
        });
    }
}
