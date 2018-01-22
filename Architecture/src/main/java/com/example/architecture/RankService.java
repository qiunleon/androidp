package com.example.architecture;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RankService {

    @GET("index/rank/{type}")
    Observable<OriginalRankInfo> getOriginalRanks(@Path("type") String type);

    @GET("index/rank/{type}")
    Call<OriginalRankInfo> callOriginalRanks(@Path("type") String type);
}
