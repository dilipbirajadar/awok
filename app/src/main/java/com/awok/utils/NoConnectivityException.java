package com.awok.utils;

import java.io.IOException;

/**
 * Created by dilip on 21/1/18.
 */

public class NoConnectivityException  extends IOException {

    @Override
    public String getMessage() {
        return "No Internet found";
    }
}
