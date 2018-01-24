package com.awok.ui.home;

import android.content.Intent;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;


import com.awok.R;
import com.awok.adapters.NavigationDrawerAdapter;
import com.awok.model.NavigationOption;
import com.awok.ui.popular.PopularFragment;
import com.awok.ui.search.SearchFragment;
import com.awok.ui.top.TopFragment;
import com.awok.utils.BaseActivity;
import com.awok.utils.Constants;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by dilip on 23/1/18.
 */

public class HomeActivity extends BaseActivity <HomeActivityPresenter> implements HomeActivityContract.View {
    MaterialSearchView searchView;

    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawerLayout;
    NavigationDrawerAdapter mDrawerAdapter;
    RecyclerView mRvDrawable;
    RelativeLayout rlLeftDrawer;
    FrameLayout mContainer;
    Handler handler;
    Toolbar toolbar;
    int currentPosition = 0;

    public HomeActivity(){
        setRequiresNavigation(false);

    }


    @Override
    protected void onViewReturn() {
        setContentView(R.layout.home_activity);
        handler = new Handler();
        inflateViews();
        setupUI();
        try {
            //setListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public HomeActivityPresenter getPresenter() {
        return null;
    }

    @Override
    public String getToolbarTitle() {
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        //searchView.setMenuItem(item);

        return true;
    }


    private void setListener()throws Exception {
        //searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }*/
    }

    @Override
    public void navigateToLogin() {

    }

    private void inflateViews() {
        toolbar = findViewById(R.id.toolbarId);
        mContainer = findViewById(R.id.fragment_container);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mRvDrawable = findViewById(R.id.nav_drawer_recycle);
        rlLeftDrawer = findViewById(R.id.leftDrawer);
    }

    private void setupUI() {
        setUpDrawer();
        setDrawerItemClickListner();
        hideToolbar();
        replaceFragment(new PopularFragment(), PopularFragment.class.getSimpleName(), R.id.fragment_container);

    }

    private void setUpDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }


            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float moveFactor = (rlLeftDrawer.getWidth() * slideOffset);
                mContainer.setTranslationX(moveFactor);
            }


        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        List<NavigationOption> navigationOptions = getDrawerOptions();
        mDrawerAdapter = new NavigationDrawerAdapter(navigationOptions);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRvDrawable.setLayoutManager(manager);
        mRvDrawable.setAdapter(mDrawerAdapter);

    }

    private List<NavigationOption> getDrawerOptions() {
        ArrayList<NavigationOption> optionList = new ArrayList<>();

        NavigationOption navigationOption = new NavigationOption();
        navigationOption.setIconResource(R.drawable.moments);
        navigationOption.setTitle(Constants.POPULAR);
        optionList.add(navigationOption);


        NavigationOption navigationOption1 = new NavigationOption();
        navigationOption1.setIconResource(R.drawable.offers);
        navigationOption1.setTitle(Constants.TOP);
        optionList.add(navigationOption1);

        NavigationOption navigationOption2 = new NavigationOption();
        navigationOption2.setIconResource(R.drawable.track_route);
        navigationOption2.setTitle(Constants.SEARCH);
        optionList.add(navigationOption2);



        return optionList;
    }



    public void setDrawerItemClickListner() {

        if (mDrawerAdapter != null) {
            mDrawerAdapter.setOnItemSelectedListener(new NavigationDrawerAdapter.OnItemClickListener() {
                @Override
                public void onItemClickListener(int position, String navigationOption) {
                    if(position == currentPosition) {
                        mDrawerLayout.closeDrawer(rlLeftDrawer);
                        return;
                    }
                    //depending upon position add item
                    loadFragments(navigationOption);

                }
            });
        }
    }


    private void loadFragments(String navigationOption) {

        Fragment fragment = null;
        switch (navigationOption) {

            case Constants.POPULAR:
                fragment = new PopularFragment();
                break;
            case Constants.TOP:
                fragment = new TopFragment();

                break;
            case Constants.SEARCH:
                fragment = new SearchFragment();
                break;

        }

        if (fragment != null) {
            mDrawerLayout.closeDrawer(rlLeftDrawer);
            final Fragment finalFragment = fragment;
            handler.postDelayed(new TimerTask() {
                @Override
                public void run() {
                    replaceFragment(finalFragment);
                }
            }, 200);
        }
    }

    private void replaceFragment(Fragment fragment) {
        String name = fragment.getClass().getSimpleName();
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(name, 0);
        if (!fragmentPopped) {
            manager.beginTransaction().replace(R.id.fragment_container, fragment,name).addToBackStack(name).commit();
        }
    }


}
