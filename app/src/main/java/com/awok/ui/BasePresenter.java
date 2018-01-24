package com.awok.ui;

/**
 * Created by dilip on 21/1/18.
 */

public interface BasePresenter {
    void handleError(Throwable throwable);
    void cancelRequest();
}
