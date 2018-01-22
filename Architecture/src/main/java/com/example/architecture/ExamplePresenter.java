package com.example.architecture;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExamplePresenter implements Contract.Presenter {

    private static final String TAG = ExampleFragment.class.getSimpleName();

    private Contract.View mView;

    public ExamplePresenter(Contract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mView.showLoading();
        RetrofitHelper.getRankAPI()
                .getOriginalRanks("origin-03.json")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<OriginalRankInfo>() {
                    @Override
                    public void accept(OriginalRankInfo originalRankInfo) throws Exception {
                        Log.d(TAG, "save: " + originalRankInfo.toString());
                    }
                })
                .subscribe(new Consumer<OriginalRankInfo>() {
                    @Override
                    public void accept(OriginalRankInfo originalRankInfo) throws Exception {
                        Log.d(TAG, originalRankInfo.toString());
                        mView.refreshUI();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showError();
                    }
                });


        final Call<OriginalRankInfo> call = RetrofitHelper.getRankAPI().callOriginalRanks("origin-03.json");
        // 同步请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 后台线程
                    call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // 异步请求
        call.enqueue(new Callback<OriginalRankInfo>() {
            @Override
            public void onResponse(Call<OriginalRankInfo> call, Response<OriginalRankInfo> response) {
                // 主线程
                String body = "";
                if (response.body() != null) {
                    body = response.body().toString();
                }
                Log.d(TAG, body);
            }

            @Override
            public void onFailure(Call<OriginalRankInfo> call, Throwable t) {
                // 主线程
                Log.d("", t.getMessage());
            }
        });


        Observable.create(
                new ObservableOnSubscribe<okhttp3.Response>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<okhttp3.Response> e) throws Exception {
                        Request.Builder builder = new Request.Builder()
                                .url("http://www.bilibili.com/index/rank/origin-03.json")
                                .get();
                        Request request = builder.build();
                        okhttp3.Call call = new OkHttpClient().newCall(request);
                        okhttp3.Response response = call.execute();
                        e.onNext(response);
                    }
                })
                .map(new Function<okhttp3.Response, OriginalRankInfo>() {
                    @Override
                    public OriginalRankInfo apply(@NonNull okhttp3.Response response) throws Exception {
                        if (response.isSuccessful()) {
                            ResponseBody body = response.body();
                            if (body != null) {
                                Log.e(TAG, "map: " + response.body());
                                return new Gson().fromJson(body.string(), OriginalRankInfo.class);
                            }
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<OriginalRankInfo>() {
                    @Override
                    public void accept(@NonNull OriginalRankInfo s) throws Exception {
                        Log.e(TAG, "doOnNext: " + s.toString() + "\n");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<OriginalRankInfo>() {
                    @Override
                    public void accept(@NonNull OriginalRankInfo data) throws Exception {
                        Log.e(TAG, data.toString() + "\n");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.e(TAG, throwable.getMessage() + "\n");
                    }
                });
    }

    @Override
    public void getData() {
        mView.refreshUI();
    }
}
