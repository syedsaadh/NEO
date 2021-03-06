package com.xeda.projectmeteor.api;

import com.xeda.projectmeteor.models.NEO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Xeda on 21-09-2017.
 */

public interface NEOService {
    @GET("feed")
    Call<NEO> getFeeds(
            @Query("start_date") String startDate,
            @Query("end_date") String endDate,
            @Query("api_key") String apiKey);
}
