package com.awok.ui.top;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awok.R;
import com.awok.ui.BasePresenter;
import com.awok.utils.BaseFragment;

/**
 * Created by dilip on 25/1/18.
 */

public class TopFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top_fragment, container, false);
        return view;
    }


    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected String getToolBarTitle() {
        return null;
    }
}
