package com.awok.ui.splash;

import com.awok.ui.BasePresenter;
import com.awok.ui.BaseView;

/**
 * Created by dilip on 21/1/18.
 */

public class SplashContract {
    interface Presenter extends BasePresenter {
        void start();
    }

    interface View extends BaseView {
        void navigateToHome();

    }
}
