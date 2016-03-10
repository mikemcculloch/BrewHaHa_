package brightseer.com.brewhaha;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import brightseer.com.brewhaha.fragment.FavoriteItemsFragment;

/**
 * Created by mccul_000 on 11/17/2014.
 */

public class FavoriteTabActivity extends BaseActivity {
    AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    ViewPager mViewPager;
    private int listType = 0;
    TabLayout sliding_tabs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_tabs);
        _mContext = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.standard_toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
                ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
                layoutParams.height = layoutParams.height + getStatusBarHeight();
            }
            toolbar.setTitle(getResources().getString(R.string.activity_favorites));
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        sliding_tabs = (TabLayout) findViewById(R.id.sliding_tabs);


        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());
        initGoogleAnalytics(this.getClass().getSimpleName());
        initAdMob();
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);

        }
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);

        sliding_tabs.setupWithViewPager(mViewPager);
        sliding_tabs.setSelectedTabIndicatorColor(getResources().getColor(R.color.tabsScrollColor));
    }

    public static class AppSectionsPagerAdapter extends FragmentStatePagerAdapter {
        public AppSectionsPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            Fragment fr;
            switch (position) {
                case 0:
                    bundle.putInt("listType", 0);
                    break;
                case 1:
                    bundle.putInt("listType", 1);
                    break;
                case 2:
                    bundle.putInt("listType", 2);
                    break;
                case 3:
                    bundle.putInt("listType", 4);
                    break;
                default:
                    bundle.putInt("listType", 0);
                    break;
            }

            fr = new FavoriteItemsFragment();
            fr.setArguments(bundle);
            return fr;
//            return (position == 0) ? new HomeItemsAllFragment() : new HomeItemsRecipeFragment();
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "All";
                case 1:
                    return "Recipe's";
                case 2:
                    return "Images";
            }
            return null;
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_item_share).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }
}
