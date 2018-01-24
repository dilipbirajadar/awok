package com.awok.datanetwork;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dilip on 21/1/18.
 */

public class HeaderInterceptor implements Interceptor {
    Context context;

    public HeaderInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!isOnline(context)) {
            throw new NoConnectivityException();
        }

        Request request = chain.request().newBuilder()
                .addHeader("api_key", "782d00f3e2f205bbdac67c4439fe43df")
                .build();
        return chain.proceed(request);
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
}