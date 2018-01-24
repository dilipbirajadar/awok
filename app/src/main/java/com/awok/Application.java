package com.awok;

import android.support.multidex.MultiDexApplication;

import com.awok.datanetwork.HeaderInterceptor;
import com.awok.datanetwork.NetWorkService;
import com.awok.datanetwork.NetworkClient;
import com.awok.utils.Constants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by dilip on 21/1/18.
 */

public class Application  extends MultiDexApplication {
    private NetWorkService netWorkService;

    @Override
    public void onCreate() {
        super.onCreate();
        setUpRetrofit();
        setUpFont();
    }

    private void setUpFont() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/aileron_regular.ttf")
                .setFontAttrId(com.awok.R.attr.font)
                .build());
    }

    private void setUpRetrofit() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new HeaderInterceptor(this));
        httpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        httpClient.connectTimeout(1000, TimeUnit.SECONDS);
        httpClient.readTimeout(2000,TimeUnit.SECONDS);
        httpClient.writeTimeout(2000,TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        netWorkService = retrofit.create(NetWorkService.class);

        //create retrofit client
    }

    public NetworkClient getRetrofitClient(){
        return new NetworkClient(netWorkService,this);
    }

}
