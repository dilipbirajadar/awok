package com.awok.ui.splash;

import android.content.Context;

/**
 * Created by dilip on 21/1/18.
 */

public class SplashPresenter implements SplashContract.Presenter{
    private SplashContract.View mView;

    public <T extends Context & SplashContract.View> SplashPresenter(T view) {
        mView = view;
    }


    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void cancelRequest() {

    }

    @Override
    public void start() {
        mView.navigateToHome();

    }
}
