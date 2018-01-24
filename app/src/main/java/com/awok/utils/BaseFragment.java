package com.awok.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awok.ui.BasePresenter;
import com.awok.ui.BaseView;

/**
 * Created by dilip on 21/1/18.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView {

    protected T mPresenter;
    //private Unbinder unbinder;
    private final Handler handler = new Handler();
    protected BaseActivity mActivity;
    private boolean mRequireToolbar = true;

    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView =  inflater.inflate(getLayout(), container, false);
        mPresenter = getPresenter();
        setToolbarTitle();
        return rootView;
    }

    protected abstract T getPresenter();
    protected abstract int getLayout();
    protected abstract String getToolBarTitle();

    @Override
    public void showProgress() {
        if(mActivity!=null) {
            mActivity.showProgress();
        }
    }

    @Override
    public void hideProgress() {
        if(mActivity!=null) {
            mActivity.hideProgress();
        }
    }

    @Override
    public void showMessage(String message) {
        if(mActivity!=null) {
            mActivity.showMessage(message);
        }
    }


    public void setRequireToolbar(boolean mRequireToolbar) {
        this.mRequireToolbar = mRequireToolbar;
    }

    private void setToolbarTitle(){
        if(mRequireToolbar){
            ((BaseActivity)getActivity()).setToolbarTitle(getToolBarTitle());
        }
    }


    public boolean ismRequireToolbar() {
        return mRequireToolbar;
    }


    @Override
    public void onDestroyView() {
//        if(unbinder!=null) {
//            unbinder.unbind();
//        }
        super.onDestroyView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity= (BaseActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    protected void hideToolbar(){
        ((BaseActivity)getActivity()).hideToolbar();
    }

    protected void showToolbar(){
        ((BaseActivity)getActivity()).showToolbar();
    }
    @Override
    public void showCancelableProgress() {
        mActivity.showCancelableProgress();
    }

    @Override
    public void handleError(Throwable throwable) {
        mActivity.handleError(throwable);
    }


    public void addFragment(Fragment fragment, String tag,int container) {
        mActivity.addFragment(fragment,tag,container);
    }

    public void replaceFragment(Fragment fragment, String tag,int container) {
        mActivity.replaceFragment(fragment,tag,container);
    }

    public void cancelRequest(){
        if(mPresenter != null){
            mPresenter.cancelRequest();
        }

    }


    public void onBackPressed() {
        mActivity.onBackPressed();
    }
}
