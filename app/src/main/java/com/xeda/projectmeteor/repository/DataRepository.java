package com.xeda.projectmeteor.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.xeda.projectmeteor.BuildConfig;
import com.xeda.projectmeteor.api.ApiClient;
import com.xeda.projectmeteor.api.NEOService;
import com.xeda.projectmeteor.models.NEO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Xeda on 25-09-2017.
 */

public class DataRepository {
    private NEOService neoService;

    public DataRepository() {
        this.neoService = ApiClient.getClient().create(NEOService.class);
    }
    public static DataRepository getInstance() {
        return new DataRepository();
    }
    public LiveData<NEO> getNeo(String startDate, String endDate) {
        final MutableLiveData<NEO> data = new MutableLiveData<>();
        neoService.getFeeds(startDate, endDate, BuildConfig.API_KEY).enqueue(new Callback<NEO>() {
            @Override
            public void onResponse(Call<NEO> call, Response<NEO> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<NEO> call, Throwable t) {

            }
        });
        return data;
    }
}
