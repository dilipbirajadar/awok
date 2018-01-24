package com.awok.ui.splash;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.awok.ActivityHome;
import com.awok.R;
import com.awok.ui.home.HomeActivity;
import com.awok.utils.BaseActivity;

/**
 * Created by dilip on 21/1/18.
 */

public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {


    int SPLASH_DISPLAY_LENGTH = 3000;
    public SplashActivity() {

        setmRequireToolbar(false);
        setRequiresNavigation(false);
    }

    @Override
    protected void onViewReturn() {
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                mPresenter.start();
            }
        }, SPLASH_DISPLAY_LENGTH);


    }


    /**
     * start Home  dashboard
     */
    @Override
    public void navigateToHome() {
        startActivity(HomeActivity.class);
        finish();
    }




    @Override
    public SplashPresenter getPresenter() {
        return new SplashPresenter(this);
    }

    @Override
    public String getToolbarTitle() {
        return null;
    }



}
