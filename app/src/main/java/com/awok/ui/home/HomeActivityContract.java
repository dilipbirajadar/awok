package com.awok.ui.home;

import com.awok.ui.BasePresenter;
import com.awok.ui.BaseView;


/**
 * Created by dilip on 21/1/18.
 */

public class HomeActivityContract {


    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView {

        void navigateToLogin();
    }
}
