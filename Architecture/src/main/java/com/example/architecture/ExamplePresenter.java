package com.example.architecture;

import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ExamplePresenter implements Contract.Presenter {

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
                .subscribe(new Consumer<OriginalRankInfo>() {
                    @Override
                    public void accept(OriginalRankInfo originalRankInfo) throws Exception {
                        Log.d("TAG", originalRankInfo.toString());
                        mView.refreshUI();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showError();
                    }
                });
    }

    @Override
    public void getData() {
        mView.refreshUI();
    }
}
