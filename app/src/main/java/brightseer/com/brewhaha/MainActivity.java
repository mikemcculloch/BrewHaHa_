package brightseer.com.brewhaha;

import android.app.ActivityOptions;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.vending.licensing.AESObfuscator;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.Policy;
import com.google.android.vending.licensing.ServerManagedPolicy;
import com.koushikdutta.ion.Ion;

import brightseer.com.brewhaha.helper.Utilities;
import brightseer.com.brewhaha.main_fragments.MainFeedFragment;
import brightseer.com.brewhaha.main_fragments.UserFeedsFragment;
import brightseer.com.brewhaha.models.UserProfile;

public class MainActivity extends NewActivtyBase {
    static public MainActivity instance;
    private LicenseCheckerCallback mLicenseCheckerCallback;
    private LicenseChecker mChecker;
    private Handler mHandler;
    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private android.support.design.widget.FloatingActionButton fab;
    private TextView drawer_displayName, drawer_useremail;
    private ImageView drawer_userImage;
    AppBarLayout app_bar_layout;
    private Menu _menu;
    private MenuItem navigation_item_5;
    boolean isFragLoaded = false;


    private Firebase rootRef;
    private String emailAddress;

    private static final byte[] SALT = new byte[]{
            -117, 47, -21, 24, -30,
            -41, 87, 56, -14, 98,
            19, 92, -16, 11, 52,
            69, -23, 11, 13, 8
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupTransistion();

        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
//            _mContext = this;
            instance = this;

//            initAdvertisingIdCollection();
            initViews();
            initFirebaseDb();
            initDrawer(savedInstanceState);

//            if (savedInstanceState == null && !BrewSharedPrefs.getEmailAddress().isEmpty() && !isFragLoaded) {
//                SetFragment(new UserFeedsFragment());
//                navigationView.setCheckedItem(R.id.navigation_my_recipes);
//                isFragLoaded = true;
//            }

            licenseCheck();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        _menu = menu;

        SetSignoutButton();
//        _menu.findItem(R.id.action_search_main).setVisible(false);
//        _menu.findItem(R.id.action_menu_options).setVisible(false);
//        _menu.findItem(R.id.menu_item_share).setVisible(false);

//        if (BrewSharedPrefs.getIsUserLoggedIn()) {
//            _menu.findItem(R.id.action_menu_options).setVisible(true);
//        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void SetSignoutButton() {
        if (_menu != null) {
            MenuItem action_menu_options = _menu.findItem(R.id.action_menu_options);
            if (drawer_displayName.getText().toString().isEmpty()) {
                action_menu_options.setVisible(false);
            } else {
                action_menu_options.setVisible(true);
            }
        }
    }

    public static MainActivity getInstance() {
        return instance;
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        app_bar_layout = (android.support.design.widget.AppBarLayout) findViewById(R.id.app_bar_layout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.app_name));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        fab = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthData authData = rootRef.getAuth();
                LaunchLoginActivity(authData);
                if (authData != null) {
                    //LAUNCH NEW RECIPE CREATION
                }
            }
        });

//        ViewGroup paralax = (ViewGroup) findViewById(R.id.includeLayout);
        ImageView image = (ImageView) findViewById(R.id.image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fatal error with out this click event
            }
        });
        Ion.with(image)
                .load(Constants.bannerUrl);

        View header = navigationView.getHeaderView(0);
        drawer_displayName = (TextView) header.findViewById(R.id.drawer_displayName);
        drawer_useremail = (TextView) header.findViewById(R.id.drawer_useremail);
        drawer_userImage = (ImageView) header.findViewById(R.id.drawer_userImage);
        drawer_userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                googleSignIn();
                AuthData authData = rootRef.getAuth();
                LaunchLoginActivity(authData);
            }
        });
    }

    private void LaunchLoginActivity(AuthData authData) {
        try {

            if (authData == null) {
                Intent newIntent = new Intent(MainActivity.this, LoginActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
                    ActivityCompat.startActivityForResult(MainActivity.this, newIntent, 0, options.toBundle());
                } else {
                    startActivity(newIntent);
                    overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                }
            }
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    private void initFirebaseDb() {
        rootRef = new Firebase(Constants.fireBaseRoot);
    }

    private void initDrawer(Bundle savedInstanceState) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                try {
                    if (menuItem.isChecked()) menuItem.setChecked(false);
                    else menuItem.setChecked(true);

                    mDrawerLayout.closeDrawers();
                    Fragment fragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_home:
                            fab.setVisibility(View.VISIBLE);
                            fragment = new MainFeedFragment();
                            collapsingToolbarLayout.setTitle(getResources().getString(R.string.app_name));
                            eventGoogleAnalytics(Constants.gacMainActivity, "Open", "Drawer.MainFeedFragment");
                            break;

//                    case R.id.navigation_item_2:
//                        fab.setVisibility(View.GONE);
//                        fragment = new AdvancedSearchFragment();
//                        collapsingToolbarLayout.setTitle(getResources().getString(R.string.activty_search));
//                        eventGoogleAnalytics(Constants.gacMainActivity, "Open", "Drawer.AdvancedSearchFragment");
//                        break;

                        case R.id.navigation_favorites:
//                        if (!BrewSharedPrefs.getIsUserLoggedIn()) {
//                            AlertLoginPrompt(_mContext, "", getText(R.string.text_login_to_view_favorite).toString(), getText(R.string.text_sign_in).toString(), getText(R.string.text_close).toString());
//                        } else {
//                            eventGoogleAnalytics(Constants.gacMainActivity, "Open", "Drawer.Favorites");
//
//                            intent = new Intent(getApplicationContext(), FavoriteTabActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//                                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
//                                ActivityCompat.startActivity(MainActivity.this, intent, options.toBundle());
////                                finishAfterTransition();
//                            } else {
//                                startActivity(intent);
//                            }
//                        }
                            break;

                        case R.id.navigation_my_recipes:
                            fab.setVisibility(View.VISIBLE);
                            fragment = new UserFeedsFragment();
                            collapsingToolbarLayout.setTitle(getResources().getString(R.string.app_name));
                            eventGoogleAnalytics(Constants.gacUserFeedsFragment, "Open", "Drawer.UserFeedsFragment");
                            break;
                        case R.id.navigation_admin:
//                        if (BrewSharedPrefs.getUserKey().toUpperCase().equals("018430E8-0421-4D1D-9B42-871D8703A4BB")) {
//                            fragment = new AdminFragment();
//                            collapsingToolbarLayout.setTitle(getResources().getString(R.string.fragment_admin));
//                        }
                            break;

//                    case R.id.navigation_item_6:
//                        intent = new Intent(getApplicationContext(), RecipeCardsActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
//                            ActivityCompat.startActivity(MainActivity.this, intent, options.toBundle());
////                                finishAfterTransition();
//                        } else {
//                            startActivity(intent);
//                        }
//                        break;
                        default:
                            break;
                    }
                    SetFragment(fragment);

                    return true;
                } catch (Exception ex) {
                    if (BuildConfig.DEBUG) {
                        Log.e(Constants.LOG, ex.getMessage());
                    }
                    return false;
                }
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void SetFragment(Fragment fragment) {
        try {
            if (fragment != null) {
                if (!isChangingConfigurations()) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame, fragment);
                    fragmentTransaction.commitAllowingStateLoss();

                    if (fragment.getClass().getName().equals("brightseer.com.brewhaha.main_fragments.UserFeedsFragment"))
                    {
                        BrewSharedPrefs.setLastFragment(Constants.spUserFragement);
                    }
                    if (fragment.getClass().getName().equals("brightseer.com.brewhaha.main_fragments.MainFeedFragment"))
                    {
                        BrewSharedPrefs.setLastFragment(Constants.spMainFragement);
                    }
                }
            } else {
                Log.e("MainActivity", "Error in creating fragment");
            }
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    private void licenseCheck() {
        try {
            mHandler = new Handler();

            String licenseKey = Constants.BASE64_PUBLIC_KEY;
            if (BuildConfig.FLAVOR == Constants.flavorFull) {
                licenseKey = Constants.BASE64_PUBLIC_KEY_FULL;
            }

            mLicenseCheckerCallback = new MyLicenseCheckerCallback();
            // Construct the LicenseChecker with a policy.
            mChecker = new LicenseChecker(
                    this, new ServerManagedPolicy(this,
                    new AESObfuscator(SALT, getPackageName(), Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID))),
                    licenseKey);

            doCheck();
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    private class MyLicenseCheckerCallback implements LicenseCheckerCallback {
        public void allow(int policyReason) {
            if (isFinishing()) {
                // Don't update UI if Activity is finishing.
                return;
            }
            // Should allow user access.
            displayResult(getString(R.string.allow));
        }

        public void dontAllow(int policyReason) {
            if (isFinishing()) {
                // Don't update UI if Activity is finishing.
                return;
            }
            displayResult(getString(R.string.dont_allow));
            // Should not allow access. In most cases, the app should assume
            // the user has access unless it encounters this. If it does,
            // the app should inform the user of their unlicensed ways
            // and then either shut down the app or limit the user to a
            // restricted set of features.
            // In this example, we show a dialog that takes the user to Market.
            // If the reason for the lack of license is that the service is
            // unavailable or there is another problem, we display a
            // retry button on the dialog and a different message.
            displayDialog(policyReason == Policy.RETRY);
        }

        public void applicationError(int errorCode) {
            if (isFinishing()) {
                // Don't update UI if Activity is finishing.
                return;
            }
            // This is a polite way of saying the developer made a mistake
            // while setting up or calling the license checker library.
            // Please examine the error code and fix the error.
            String result = String.format(getString(R.string.application_error), errorCode);
            displayResult(result);
        }
    }

    private void displayDialog(final boolean showRetry) {
        mHandler.post(new Runnable() {
            public void run() {
                setProgressBarIndeterminateVisibility(false);
                showDialog(showRetry ? 1 : 0);
//                mCheckLicenseButton.setEnabled(true);
            }
        });
    }

    private void displayResult(final String result) {
        mHandler.post(new Runnable() {
            public void run() {
//                mStatusText.setText(result);
                setProgressBarIndeterminateVisibility(false);
//                mCheckLicenseButton.setEnabled(true);
            }
        });
    }

    private void doCheck() {
        setProgressBarIndeterminateVisibility(true);
        mChecker.checkAccess(mLicenseCheckerCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mChecker.onDestroy();\
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        SearchManager SManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        android.support.v7.widget.SearchView searchViewAction =
                (android.support.v7.widget.SearchView) menu.findItem(R.id.action_search_main).getActionView();
        searchViewAction.setSearchableInfo(SManager.getSearchableInfo(getComponentName()));
        searchViewAction.setIconifiedByDefault(false);
        searchViewAction.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_signout:
                resetUi();

            case R.id.action_search_main:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BrewSharedPrefs.getLastFragment() > 0 && !isFragLoaded) {
            Fragment fragment = null;
            if (BrewSharedPrefs.getLastFragment() == Constants.spUserFragement) {
                fragment = new UserFeedsFragment();
                navigationView.setCheckedItem(R.id.navigation_my_recipes);
            }

            if (BrewSharedPrefs.getLastFragment() == Constants.spMainFragement) {
                fragment = new MainFeedFragment();
                navigationView.setCheckedItem(R.id.navigation_home);
            }

            SetFragment(fragment);
            isFragLoaded = true;
        }
        CheckAuth();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        CheckAuth();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (actionBarDrawerToggle != null)
            actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            actionBarDrawerToggle.syncState();
        }

        if (resultCode == BackPressed) {
            isFragLoaded = true;
        }
    }

    public void googleSignOut() {
        try {
            if (mGoogleApiClient.isConnected()) {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                // ...
                            }
                        });
            }
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    private void CheckAuth() {
        try {
            AuthData authData = rootRef.getAuth();
            if (authData != null) {
                UpdateUi(GetUserProfile(authData));
            } else {
                resetUi();
            }
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    private void UpdateUi(UserProfile userProfile) {
        try {
            if (userProfile != null) {
                if (drawer_displayName.getText().toString().isEmpty()) {
//                    BrewSharedPrefs.setEmailAddress(userProfile.getEmailAddress());

                    drawer_displayName.setText(userProfile.getDisplayName());
                    drawer_useremail.setText(Utilities.decodeEmail(userProfile.getEmailAddress()));

                    String personPhoto = userProfile.getImageUrl();
                    if (!personPhoto.isEmpty()) {
                        int dpConversion = (int) (65 * Resources.getSystem().getDisplayMetrics().density);
                        cornerRadius = 100;
                        Ion.with(drawer_userImage)
                                .transform(Utilities.GetRoundTransform())
                                .load(personPhoto);

                        drawer_userImage.setMinimumWidth(dpConversion);
                        drawer_userImage.setMinimumHeight(dpConversion);
                    } else {
                        drawer_userImage.setImageResource(R.drawable.ic_person_white_48dp);
                        drawer_userImage.setMinimumWidth(0);
                        drawer_userImage.setMinimumHeight(0);
                    }
                    SetSignoutButton();
                    if (!isFragLoaded) {
                        navigationView.setCheckedItem(R.id.navigation_my_recipes);
                        SetFragment(new UserFeedsFragment());
                        isFragLoaded = true;
                    }
                }
            }
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    private void resetUi() {
        try {
            drawer_displayName.setText("");
            drawer_useremail.setText("");
            drawer_userImage.setImageResource(R.drawable.ic_person_white_48dp);
            drawer_userImage.setMinimumWidth(0);
            drawer_userImage.setMinimumHeight(0);
            rootRef.unauth();
            googleSignOut();

            SetSignoutButton();

            if (!isFragLoaded) {
                SetFragment(new MainFeedFragment());
                navigationView.setCheckedItem(R.id.navigation_home);
                isFragLoaded = false;
            }

            BrewSharedPrefs.clearAllPrefs();
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }
//    public void StartAddUpdate(String contentPk) {
//        try {
//            Intent newActivityIntent = new Intent(this, AddUpdateRecipe.class);
//            newActivityIntent.putExtra(Constants.spContentToken, contentToken);
//            newActivityIntent.putExtra(Constants.exContentItemPk, contentPk);
//            newActivityIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
//                ActivityCompat.startActivity(this, newActivityIntent, options.toBundle());
//            } else {
//                startActivity(newActivityIntent);
////                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
//            }
//        } catch (Exception e) {
//            if (BuildConfig.DEBUG) {
//                Log.e(Constants.LOG, e.getMessage());
//            }
//        }
//    }

//    private void setAdminNav() {
//        Menu menuNav = navigationView.getMenu();
//        MenuItem navigationAdmin = menuNav.findItem(R.id.navigation_item_5);
//
////        if (BrewSharedPrefs.getUserKey().toUpperCase().equals("018430E8-0421-4D1D-9B42-871D8703A4BB")) {
////            navigationAdmin.setVisible(true);
////        } else
////            navigationAdmin.setVisible(false);
//
//    }
}
