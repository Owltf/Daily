package cn.owltf.daily.view.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import java.text.DateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.owltf.daily.R;
import cn.owltf.daily.domain.application.App;
import cn.owltf.daily.domain.utils.Constants;
import cn.owltf.daily.domain.utils.ThemeUtils;
import cn.owltf.daily.presenters.MainActivityPresenter;
import cn.owltf.daily.view.IMainActivity;
import cn.owltf.daily.view.ui.activity.base.BaseActivity;
import cn.owltf.daily.view.ui.fragment.DailyListFragment;

public class MainActivity extends BaseActivity implements IMainActivity {
    public static final String TAG = "MainActivity";


    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout mContainer;
    @Bind(R.id.navigation_view)
    NavigationView mNavigationView;
    @Bind(R.id.tabs)
    TabLayout mTabLayout;
    @Bind(R.id.pager)
    ViewPager mViewPager;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;


    private MainActivityPresenter mPresenter;
    /**
     * 实现再按一次退出提醒
     */
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        layoutResID = R.layout.activity_main;
        super.onCreate(savedInstanceState);
        App.addActivity(this);
        ButterKnife.bind(this);

        if (mNavigationView != null) {
            setupDrawerContent();
        }

        setupActionBarToggle();

        if (mViewPager != null) {
            setupViewPager();
        }
        mPresenter = new MainActivityPresenter(this);
        mPresenter.clearCache();
//        mPresenter.checkUpdate();
        mPresenter.handleCrashLog();


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//        SystemBarTintManager tintManager;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
////            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
////            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            tintManager = new SystemBarTintManager(this);
//            tintManager.setStatusBarTintEnabled(true);
////            tintManager.setStatusBarTintColor(R.color.primary);
//            SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
//            mAppBarLayout.setPadding(0, config.getStatusBarHeight(), 0, 0);
//        }
    }

    private void setupDrawerContent() {
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        //Checking if the item is in checked state or not, if not make it in checked state
                        if (menuItem.isChecked()) menuItem.setChecked(false);
                        else menuItem.setChecked(true);
                        //Closing drawer on item click
                        mDrawerLayout.closeDrawers();
                        //Check to see which item was being clicked and perform appropriate action
                        switch (menuItem.getItemId()) {
                            case R.id.nav_setting:
                                return prepareIntent(PrefsActivity.class);
                            case R.id.nav_about:
                                return prepareIntent(AboutActivity.class);
                            case R.id.nav_night_mode:
                                ThemeUtils.isLight = !ThemeUtils.isLight;
                                MainActivity.this.recreate();
                            default:
                                return true;
                        }
                    }
                });
    }

    private void setupActionBarToggle() {
        // Initializing Drawer Layout and ActionBarToggle
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.openDrawerContentDescRes, R.string.closeDrawerContentDescRes) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    private void setupViewPager() {
        mViewPager.setOffscreenPageLimit(Constants.PAGE_COUNT);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private boolean prepareIntent(Class clazz) {
        startActivity(new Intent(MainActivity.this, clazz));
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            mDrawerLayout.closeDrawers();

            if ((System.currentTimeMillis() - exitTime) > 3000) {
                Snackbar.make(mContainer, R.string.snack_exit_once_more, Snackbar.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showSnackBar(String data, int time) {
        Snackbar.make(mContainer, data, time).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        App.removeActivity(this);
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Bundle bundle = new Bundle();
            Fragment newFragment = DailyListFragment.newInstance();

            Calendar dateToGetUrl = Calendar.getInstance();
            dateToGetUrl.add(Calendar.DAY_OF_YEAR, 1 - i);
            int date = Integer.parseInt(Constants.simpleDateFormat.format(dateToGetUrl.getTime()));

            bundle.putBoolean("first_page?", i == 0);
            bundle.putBoolean("single?", false);
            bundle.putInt("date", date);

            newFragment.setArguments(bundle);
            return newFragment;
        }

        @Override
        public int getCount() {
            return Constants.PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Calendar displayDate = Calendar.getInstance();
            displayDate.add(Calendar.DAY_OF_YEAR, -position);

            return DateFormat.getDateInstance().format(displayDate.getTime());
        }
    }
}
