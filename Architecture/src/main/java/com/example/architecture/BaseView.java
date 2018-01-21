package com.example.architecture;

public interface BaseView<P extends BasePresenter> {
    void setPresenter(P presenter);
}
