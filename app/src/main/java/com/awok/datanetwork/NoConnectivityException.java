package com.awok.datanetwork;

import java.io.IOException;

/**
 * Created by dilip on 21/1/18.
 */

class NoConnectivityException extends IOException {
    @Override
    public String getMessage() {
        return "No Internet found";
    }
}

