package com.awok.ui;

import android.content.res.Resources;

/**
 * Created by dilip on 21/1/18.
 */

public interface BaseView {
    void showProgress();
    void showCancelableProgress();
    void hideProgress();
    void showMessage(String message);
    void handleError(Throwable throwable);

    Resources getResources();
}
