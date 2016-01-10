package brightseer.com.brewhaha;

import android.app.ActivityOptions;
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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.vending.licensing.AESObfuscator;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.Policy;
import com.google.android.vending.licensing.ServerManagedPolicy;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;

import java.net.MalformedURLException;
import java.net.URL;

import brightseer.com.brewhaha.fragment.AdminFragment;
import brightseer.com.brewhaha.fragment.AdvancedSearchFragment;
import brightseer.com.brewhaha.fragment.HomeFragment;
import brightseer.com.brewhaha.fragment.MyRecipeListFragment;

import static brightseer.com.brewhaha.R.string.app_name;

public class MainActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
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
    TextView drawer_displayName, drawer_useremail;
    ImageView drawer_userImage;
    AppBarLayout app_bar_layout;
    private Menu menu;
    private MenuItem navigation_item_5;


    private static final byte[] SALT = new byte[]{
            -117, 47, -21, 24, -30,
            -41, 87, 56, -14, 98,
            19, 92, -16, 11, 52,
            69, -23, 11, 13, 8
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupTransistionSlide();
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
            _mContext = this;
            instance = this;

            initAdvertisingIdCollection();
//            createTermsDialog();
            initViews();
            initDrawer(savedInstanceState);
            initGooglePlus();

            if (savedInstanceState == null) {
                SetFragment(new HomeFragment());
            }

            CheckForUpdates();
            licenseCheck();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        this.menu = menu;
        menu.findItem(R.id.action_search_main).setVisible(false);
        menu.findItem(R.id.action_menu_options).setVisible(false);
        menu.findItem(R.id.menu_item_share).setVisible(false);

        if (BrewSharedPrefs.getIsUserLoggedIn()) {
            menu.findItem(R.id.action_menu_options).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
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
        setAdminNav();


        fab = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BrewSharedPrefs.getIsUserLoggedIn()) {
                    StartAddUpdate("");
                } else {
                    AlertLoginPrompt(_mContext, "", getText(R.string.text_login_to_add_recipe).toString(), getText(R.string.text_sign_in).toString(), getText(R.string.text_close).toString());
                }
            }
        });
        ImageView image = (ImageView) findViewById(R.id.image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fatal error with out this click event
                //String test = "fart";
            }
        });
        Ion.with(image)
                .load("http://www.brewhaha.beer/Content/images/banner.jpg");

        drawer_displayName = (TextView) findViewById(R.id.drawer_displayName);
        drawer_useremail = (TextView) findViewById(R.id.drawer_useremail);
        drawer_userImage = (ImageView) findViewById(R.id.drawer_userImage);
        drawer_userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.connect();
                }
            }
        });

//        terms_webview = (WebView) termsView.findViewById(R.id.terms_webview);
//        terms_webview.loadUrl(Constants.urlTermsConditions);
//        terms_webview.setWebViewClient(new myWebViewClient());
    }

    private void setAdminNav() {
        Menu menuNav = navigationView.getMenu();
        MenuItem navigationAdmin = menuNav.findItem(R.id.navigation_item_5);

        if (BrewSharedPrefs.getUserToken().toUpperCase().equals("018430E8-0421-4D1D-9B42-871D8703A4BB")) {
            navigationAdmin.setVisible(true);
        } else
            navigationAdmin.setVisible(false);

    }

    private void initDrawer(Bundle savedInstanceState) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                mDrawerLayout.closeDrawers();
                Fragment fragment = null;
                Intent intent;
//                int viewId = R.id.frame;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_item_1:
                        fab.setVisibility(View.VISIBLE);
                        fragment = new HomeFragment();
                        collapsingToolbarLayout.setTitle(getResources().getString(R.string.app_name));
                        eventGoogleAnalytics(Constants.gacMainActivity, "Open", "Drawer.HomeFragment");
                        break;

                    case R.id.navigation_item_2:
                        fab.setVisibility(View.GONE);
                        fragment = new AdvancedSearchFragment();
                        collapsingToolbarLayout.setTitle(getResources().getString(R.string.activty_search));
                        eventGoogleAnalytics(Constants.gacMainActivity, "Open", "Drawer.AdvancedSearchFragment");
                        break;

                    case R.id.navigation_item_3:
                        if (!BrewSharedPrefs.getIsUserLoggedIn()) {
                            AlertLoginPrompt(_mContext, "", getText(R.string.text_login_to_view_favorite).toString(), getText(R.string.text_sign_in).toString(), getText(R.string.text_close).toString());
                        } else {
                            eventGoogleAnalytics(Constants.gacMainActivity, "Open", "Drawer.Favorites");

                            intent = new Intent(getApplicationContext(), FavoriteTabActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
                                ActivityCompat.startActivity(MainActivity.this, intent, options.toBundle());
//                                finishAfterTransition();
                            } else {
                                startActivity(intent);
                            }
                        }
                        break;

                    case R.id.navigation_item_4:
                        if (BrewSharedPrefs.getIsUserLoggedIn()) {
                            fragment = new MyRecipeListFragment();
                            collapsingToolbarLayout.setTitle(getResources().getString(R.string.fragment_myrecipes));
                        } else {
                            AlertLoginPrompt(_mContext, "", getText(R.string.text_login_to_add_recipe).toString(), getText(R.string.text_sign_in).toString(), getText(R.string.text_close).toString());
                        }
                        break;
                    case R.id.navigation_item_5:
                        if (BrewSharedPrefs.getUserToken().toUpperCase().equals("018430E8-0421-4D1D-9B42-871D8703A4BB")) {
                            fragment = new AdminFragment();
                            collapsingToolbarLayout.setTitle(getResources().getString(R.string.fragment_admin));
                        }
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
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, app_name, app_name) {

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
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void SetFragment(Fragment fragment) {
        if (fragment != null) {
            if (!isChangingConfigurations()) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commit();
            }
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        if (actionBarDrawerToggle != null)
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void CheckForUpdates() {
        try {
            String url = Constants.wcfGetRefreshDate;
            Ion.with(getApplicationContext())
                    .load(url)
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result) {
                            try {
                                if (result != null) {
                                    ForceReferenceUpdate(result.replaceAll("\"", ""));
                                }
                            } catch (Exception ex) {
                                if (BuildConfig.DEBUG) {
                                    Log.e(Constants.LOG, ex.getMessage());
                                }
                            }
                        }
                    });
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    private static void ForceReferenceUpdate(String serverDate) {
        Intent mServiceIntent = new Intent(getInstance().getApplicationContext(), DataPullerService.class);

        DateTime itemTime = new DateTime(serverDate);
        String prefLastUpdate = BrewSharedPrefs.getLastForcedUpdateDate();
        DateTime prefTime = new DateTime(prefLastUpdate);
        int result = DateTimeComparator.getInstance().compare(prefTime, itemTime);
        switch (result) {
            case -1:
                // System.out.println("d1 is less than d2");
                BrewSharedPrefs.setLastForcedUpdateDate(serverDate);
                mServiceIntent.putExtra(Constants.exForceUpdate, true);
                break;
            case 0:
                mServiceIntent.putExtra(Constants.exForceUpdate, false);
//                System.out.println("d1 is equal to d2");
                break;
            case 1:
                mServiceIntent.putExtra(Constants.exForceUpdate, false);
//                System.out.println("d1 is greater than d2");
                break;

            default:
                break;
        }

        getInstance().getApplicationContext().startService(mServiceIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            actionBarDrawerToggle.syncState();
            if (requestCode == RC_SIGN_IN) {
                mIntentInProgress = false;
                if (!mGoogleApiClient.isConnecting()) {
                    mGoogleApiClient.connect();
                }
            }
        } else {
            initGooglePlus();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void licenseCheck() {
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
//        mCheckLicenseButton.setEnabled(false);
        setProgressBarIndeterminateVisibility(true);
//        mStatusText.setText(R.string.checking_license);
        mChecker.checkAccess(mLicenseCheckerCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mChecker.onDestroy();
    }

//    public class myWebViewClient extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
//            return true;
//        }
//    }

//    public void createTermsDialog() {
//        try {
//            Rect displayRectangle = new Rect();
//            Window window = getWindow();
//            window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
//
//            LayoutInflater factory = LayoutInflater.from(_mContext);
//            termsView = factory.inflate(
//                    R.layout.dialog_terms, null);
//
//            termsView.setMinimumWidth((int) (displayRectangle.width() * 0.9f));
//            termsView.setMinimumHeight((int) (displayRectangle.height() * 0.9f));
//
//            termsDialog = new AlertDialog.Builder(_mContext).create();
//            termsDialog.setView(termsView);
//
//            termsView.findViewById(R.id.accept_terms_button).setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    BrewSharedPrefs.setAcceptedTerms(true);
//                    AcceptedDisclaimer();
//                    termsDialog.dismiss();
//                }
//            });
//            termsView.findViewById(R.id.decline_terms_button).setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    BrewSharedPrefs.setAcceptedTerms(false);
//                    signOut();
//                    termsDialog.dismiss();
//                }
//            });
//        } catch (Exception ex) {
//            if (BuildConfig.DEBUG) {
//                Log.e(Constants.LOG, ex.getMessage());
//            }
////                                         dialogProgress.dismiss();
//        }
//
//    }

//    private void logout() {
//        try {
//            BrewSharedPrefs.clearAllPrefs();
//            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
//                    MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
//            suggestions.clearHistory();
//
//            _mContext.deleteDatabase(Constants.DATABASE_NAME_IngredientSelected);
//            _mContext.deleteDatabase(Constants.DATABASE_NAME_InstructionSelected);
//
//            recreate();
//        } catch (Exception e) {
//            if (BuildConfig.DEBUG) {
//                Log.e(Constants.LOG, e.getMessage());
//            }
//        }
//    }

//    private void AcceptedDisclaimer() {
//        String url = Constants.wcfAcceptDisclaimer + BrewSharedPrefs.getUserToken();
//        Ion.with(_mContext)
//                .load(url)
//                .asString()
//                .setCallback(new FutureCallback<String>() {
//                    @Override
//                    public void onCompleted(Exception e, String s) {
//                        try {
//                            Boolean success = Boolean.valueOf(s);
//
//                        } catch (Exception ex) {
//                            if (BuildConfig.DEBUG) {
//                                Log.e(Constants.LOG, ex.getMessage());
//                            }
//                        }
//                    }
//                });
//    }

    @Override
    public void onConnected(Bundle bundle) {
        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            BrewSharedPrefs.setIsUserLoggedIn(true);
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            BrewSharedPrefs.setFullName(currentPerson.getDisplayName());
            String personPhoto = currentPerson.getImage().getUrl();

            try {
                URL url = new URL(personPhoto);
                personPhoto = personPhoto.replace(url.getQuery(), "");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            BrewSharedPrefs.setGoolgePlusProfileUrl(currentPerson.getUrl());
            BrewSharedPrefs.setUserProfileImageUrl(personPhoto);
            BrewSharedPrefs.setEmailAddress(Plus.AccountApi.getAccountName(mGoogleApiClient));
            PlusAccountSetup();
            setUserViews();
            SetFragment(new HomeFragment());
            collapsingToolbarLayout.setTitle(getResources().getString(R.string.app_name));
        }
    }

    private void setUserViews() {
        drawer_displayName.setText(BrewSharedPrefs.getFullName());
        drawer_useremail.setText(BrewSharedPrefs.getEmailAddress());

        String personPhoto = BrewSharedPrefs.getUserProfileImageUrl();
        if (!personPhoto.isEmpty()) {
            int dpConversion = (int) (65 * Resources.getSystem().getDisplayMetrics().density);
            cornerRadius = 100;
            Ion.with(drawer_userImage)
                    .transform(trans)
                    .load(personPhoto);


            drawer_userImage.setMinimumWidth(dpConversion);
            drawer_userImage.setMinimumHeight(dpConversion);
        } else {
            drawer_userImage.setImageResource(R.mipmap.ic_person_white_48dp);
            drawer_userImage.setMinimumWidth(0);
            drawer_userImage.setMinimumHeight(0);
        }

        if (menu != null) {
            MenuItem action_menu_options = menu.findItem(R.id.action_menu_options);
            action_menu_options.setVisible(true);
        }
        setAdminNav();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_signout:
                signOut();
                setUserViews();

                MenuItem action_menu_options = menu.findItem(R.id.action_menu_options);
                if (action_menu_options != null)
                    action_menu_options.setVisible(false);

                SetFragment(new HomeFragment());
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUserViews();
    }

    public void StartAddUpdate(String contentPk) {
        try {
            Intent newActivityIntent = new Intent(this, AddUpdateRecipe.class);
            newActivityIntent.putExtra(Constants.spContentToken, contentToken);
            newActivityIntent.putExtra(Constants.exContentItemPk, contentPk);
            newActivityIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
                ActivityCompat.startActivity(this, newActivityIntent, options.toBundle());
            } else {
                startActivity(newActivityIntent);
//                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }
}