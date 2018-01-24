package com.awok.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.awok.R;
import com.awok.ui.BasePresenter;
import com.awok.ui.BaseView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.List;

import retrofit2.HttpException;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by dilip on 21/1/18.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView, DialogInterface.OnCancelListener{

    protected T mPresenter;
    ProgressDialog mProgressDialog;

    private boolean mRequireToolbar = true;
    private boolean mNavigationRequires = true;
    private boolean requiresUserLogin = true;
    private SharedPreferences mSettings;
    private SharedPreferences.Editor mEditor;
    private TextView mToolbarTitle;


    @Override
    public void setContentView(@LayoutRes int layoutId) {
        super.setContentView(layoutId);

        init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettings = PreferenceManager.getDefaultSharedPreferences(this);
        onViewReturn();
    }


    protected abstract void onViewReturn();


    public void savePreference(String key, Object value) {
        mEditor = mSettings.edit();

        String savedValue = null;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;

        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(value);

            savedValue = Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                out.close();
                bos.close();
            } catch (Exception ex) {
            }
        }

        if (savedValue != null) {
            mEditor.putString(key, savedValue);
            mEditor.commit();
        }
    }

    public void removePreference(String key) {
        mEditor = mSettings.edit();
        mEditor.remove(key);
        mEditor.commit();
    }






    private void init() {
        //set toolbar
        mPresenter = getPresenter();
        if(mRequireToolbar){
            try {
                setToolBar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void setToolBar()throws Exception {

        Toolbar toolbar = findViewById(R.id.toolbarId);

        //setSupportActionBar(toolbar);
        if(getToolbarTitle() != null){
            mToolbarTitle  = ((TextView)toolbar.findViewById(R.id.title));
            mToolbarTitle.setText(getToolbarTitle());
        }
        // getSupportActionBar().setDisplayShowTitleEnabled(false);

        if(mNavigationRequires){
            toolbar.setNavigationIcon(R.drawable.back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
    }

    public abstract T getPresenter();

    public abstract String getToolbarTitle();


    @Override
    public void showProgress() {
        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setCancelable(false);
        mProgressDialog.setOnCancelListener(this);
        mProgressDialog.show();
    }

    @Override
    public void showCancelableProgress(){
        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialog (this);
        }
        mProgressDialog.setCancelable(true);
        mProgressDialog.setOnCancelListener(this);
        mProgressDialog.show();
    }

    @Override
    public void hideProgress() {
        if(mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    public boolean ismRequireToolbar() {
        return mRequireToolbar;
    }

    public void setmRequireToolbar(boolean mRequireToolbar) {
        this.mRequireToolbar = mRequireToolbar;
    }

    public void setRequiresNavigation(boolean mNavigationRequires) {
        this.mNavigationRequires = mNavigationRequires;
    }


    public  void startActivity(Class<?> cls ) {
        Intent intent = new Intent(this,cls);
        startActivity(intent);
    }

    public  void startActivity(Class<?> cls,Bundle bundle ) {
        Intent intent = new Intent(this,cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public Resources getResources() {
        return super.getResources();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected void setToolbarTitleColor(int color){
        if(ismRequireToolbar()){
            mToolbarTitle.setTextColor(color);
        }
    }

    public void setToolbarTitle(String toolBarTitle) {
        mToolbarTitle.setText(toolBarTitle);

    }

    public void hideToolbar(){
        findViewById(R.id.toolbarId).setVisibility(View.GONE);
    }

    public void showToolbar(){
        findViewById(R.id.toolbarId).setVisibility(View.VISIBLE);
    }

    public void setBackButtonWhite(){
        //((Toolbar)findViewById(R.id.toolbarId)).setNavigationIcon(R.drawable.user);
    }

    @Override
    public void handleError(Throwable throwable) {
        hideProgress();
        //hide progress bar
        Log.e("ERROR", "Error msg"+throwable.getMessage());
        throwable.printStackTrace();
        // EventBus.getDefault().post(throwable);
        if(throwable instanceof NoConnectivityException) {
            showAppError("Please connect to the internet to continue using Win Win");
        }else if(throwable instanceof IOException){
            showAppError("Please connect to the internet to continue using Win Win");
        }else if(throwable instanceof HttpException){
            showAppError(throwable.getMessage());
        }else {
            showAppError(throwable.getMessage());
        }
    }

    protected void showAppError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        List<Fragment> list = getSupportFragmentManager().getFragments();

        if(list.size() > 0){
            if(list.get(list.size() - 1) instanceof BaseFragment){
                BaseFragment fragment = (BaseFragment) list.get(list.size() - 1);
                fragment.cancelRequest();
            }
        }else {
            mPresenter.cancelRequest();
        }
    }

    protected void showNavigationButton(){
        Toolbar toolbar = findViewById(R.id.toolbarId);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    protected void hideNavigationButton(){
        Toolbar toolbar = findViewById(R.id.toolbarId);
        toolbar.setNavigationIcon(null);
        toolbar.setNavigationOnClickListener(null);
    }


    public void addFragment(Fragment fragment, String tag,int container) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.add(container,fragment,tag);
        fragmentTransaction.addToBackStack(tag).commit();
    }

    public void replaceFragment(Fragment fragment, String tag,int container) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(container,fragment,tag);
        fragmentTransaction.addToBackStack(tag).commit();
    }

    @Override
    public void onBackPressed(){
        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
        }
        else {
            super.onBackPressed();
        }
    }

}
