package com.awok.ui.popular;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.awok.R;
import com.awok.ui.BasePresenter;
import com.awok.utils.BaseFragment;

/**
 * Created by dilip on 25/1/18.
 */

public class PopularFragment extends BaseFragment {
    Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popular_fragment, container, false);
        setRequireToolbar(true);
        setUpview(view);
        return view;
    }

    private void setUpview(View view) {



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
