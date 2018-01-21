package com.example.architecture;

public interface Contract {

    interface Presenter extends BasePresenter{
        void getData();
    }

    interface View extends BaseView<Presenter> {
        void showLoading();
        void refreshUI();
        void showError();
    }
}
