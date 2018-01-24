package com.awok.datanetwork;

import android.content.Context;

/**
 * Created by dilip on 21/1/18.
 */

public class NetworkClient {
    private NetWorkService mNetWorkService;

    public NetworkClient(NetWorkService mNetWorkService, Context context) {
        this.mNetWorkService = mNetWorkService;
    }
}
