package com.awok.ui.home;

import android.content.Context;


/**
 * Created by dilip on 24/1/18.
 */

public class HomeActivityPresenter implements HomeActivityContract.Presenter {



    private HomeActivityContract.View mView;
    //private CustomerNetworkClient customerNetworkClient;

    public <T extends Context & HomeActivityContract.View> HomeActivityPresenter(T view) {
      //  customerNetworkClient = new CustomerNetworkClient();
        mView = view;
    }


    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void cancelRequest() {

    }
}
