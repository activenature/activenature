package com.active.chdating.net;


import android.view.View;

public interface BaseView<T> {
    void setPresenter(T presenter);
    void initViews(View view);
}
