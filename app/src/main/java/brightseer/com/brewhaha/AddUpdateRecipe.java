package brightseer.com.brewhaha;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import brightseer.com.brewhaha.adapter.AddUpdateRecipeTabAdapter;
import brightseer.com.brewhaha.fragment.AddInstructionsFragment;
import brightseer.com.brewhaha.objects.KeyValuepair;
import brightseer.com.brewhaha.repository.JsonToObject;

/**
 * Created by Michael McCulloch on 2/26/2015.
 */
public class AddUpdateRecipe extends BaseActivity {
    AddUpdateRecipeTabAdapter adapter;
    ViewPager mViewPager;
    String contentPk;
    Toolbar toolbar;
    TabLayout sliding_tabs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_tabs);
        _mContext = this;
        initGoogleAnalytics(this.getClass().getSimpleName());
        initAdMob();

        Toolbar toolbar = (Toolbar) findViewById(R.id.standard_toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
            ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
            layoutParams.height = layoutParams.height + getStatusBarHeight();
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        sliding_tabs = (TabLayout) findViewById(R.id.sliding_tabs);
        sliding_tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        sliding_tabs.setSelectedTabIndicatorColor(getResources().getColor(R.color.tabsScrollColor));
        Intent activityThatCalled = getIntent();
        contentToken = activityThatCalled.getExtras().getString(Constants.spContentToken);
        contentPk = activityThatCalled.getExtras().getString(Constants.exContentItemPk);

        if (TextUtils.isEmpty(contentPk)) {
            Snackbar.make(findViewById(R.id.relative_layout), "A new recipe has been added to 'My Recipes'.", Snackbar.LENGTH_LONG)
                    .show();
        }

//        PrepareContent();
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_item_share).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

//    private void PrepareContent() {
//        if (TextUtils.isEmpty(contentPk)) {
//            Ion.with(_mContext)
//                    .load(Constants.wcfAddDefaultRecipe + BrewSharedPrefs.getUserToken())
//                    .asJsonObject()
//                    .setCallback(new FutureCallback<JsonObject>() {
//                        @Override
//                        public void onCompleted(Exception e, JsonObject result) {
//                            try {
//                                KeyValuepair pair = JsonToObject.JsonToKeyValuepair(result);
//                                contentPk = String.valueOf(pair.getKey());
//                                contentToken = pair.getValue();
//                                SetAdapter();
//                            } catch (Exception ex) {
//                                if (BuildConfig.DEBUG) {
//                                    Log.e(Constants.LOG, ex.getMessage());
//                                }
//                            }
//                        }
//                    });
//        } else {
//            SetAdapter();
//        }
//
//    }

    private void SetAdapter() {
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
        }
        adapter = new AddUpdateRecipeTabAdapter(getSupportFragmentManager(), contentPk, contentToken);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(adapter);
        sliding_tabs.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float v, int i1) {


            }

            @Override
            public void onPageSelected(int position) {
                if (position == 4) {
                    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                    AddInstructionsFragment fragment = (AddInstructionsFragment) fragmentManager.findFragmentByTag(Constants.fragTagInstructions);
                    fragment.loadInstructions();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
