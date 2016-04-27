package brightseer.com.brewhaha;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.plus.PlusOneButton;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.adapter.GridImagesRecycler;
import brightseer.com.brewhaha.models.RecipeImage;
import brightseer.com.brewhaha.objects.RecipeContent;
import brightseer.com.brewhaha.repository.JsonToObject;

public class GridViewActivity extends BaseActivity {
    private GridImagesRecycler adapter;
    private RecyclerView grid;
    private ArrayList<String> imageUrlList = new ArrayList<>();
    List<RecipeImage> recipeImageList = new Vector<>();

    private String responseContentItemPk, recipeTitle;
    private android.support.design.widget.FloatingActionButton fab;
    private boolean toggleStar = false;
    private String userToken;
    private PlusOneButton mPlusOneButton;

    private int previousTotal = 0;
    private boolean loadingBool = true;
    private int visibleThreshold = 5;
    private int firstVisibleItem, visibleItemCount, totalItemCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);

        initGoogleAnalytics(this.getClass().getSimpleName());
        initAdMob();
        getExtra();
        initCheckbox();
        initGridLayout();

        _mContext = this;
//        userToken = BrewSharedPrefs.getUserToken();

        Toolbar toolbar = (Toolbar) findViewById(R.id.standard_toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
                ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
                layoutParams.height = layoutParams.height + getStatusBarHeight();
            }
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            getSupportActionBar().setTitle(recipeTitle);
        }
    }

    private void getExtra() {
        Intent activityThatCalled = getIntent();
        recipeTitle = activityThatCalled.getExtras().getString(Constants.exRecipeTitle);
        responseContentItemPk = activityThatCalled.getExtras().getString(Constants.exContentItemPk);
        if (TextUtils.isEmpty(responseContentItemPk)) {
//            responseContentItemPk = String.valueOf(BrewSharedPrefs.getLastContentItemPk());
        }
    }

//    private void load() {
//        String url = Constants.wcfGetContentById + responseContentItemPk + "/" + BrewSharedPrefs.getUserToken();
//        loadingObj = Ion.with(getApplicationContext())
//                .load(url)
//                .setHeader("Cache-Control", "No-Cache")
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonObject result) {
//                        try {
//                            if (result != null) {
//                                RecipeContent recipeContent = JsonToObject.JsonToRecipeContent(result);
//
//                                BrewSharedPrefs.setCurrentToken(recipeContent.getToken());
//                                BrewSharedPrefs.setCurrentContentTitle(recipeContent.getTitle());
//                                BrewSharedPrefs.setLastContentItemPk(recipeContent.getContentItemPk());
//                                BrewSharedPrefs.setNextContentItemId(recipeContent.getNextContentItemId());
//
//                                setShareIntent();
//
//                                toggleStar = recipeContent.isFavorite();
//                                recipeImageList = recipeContent.getImagesMList();
////                                imageUrlList = new ArrayList<String>();
//                                for (RecipeImage item : recipeImageList) {
////                                    imageUrlList.add(item.getImageUrl());
//                                    adapter.add(item);
//                                }
//
//                                boolean addRecipe = false;
//                                if (recipeContent.getNextContentItemId() > 0) {
//                                    RecipeImage placeHolder = new RecipeImage();
//                                    placeHolder.setImageUrl(Constants.urlRecipePlaceHolder);
//
//                                    adapter.add(placeHolder);
////                                    imageUrlList.add(Constants.urlRecipePlaceHolder);
//                                    addRecipe = true;
//                                }
//
//                                adapter.setAddRecipe(addRecipe);
//                                adapter.notifyDataSetChanged();
//
//                                addFabListener();
//                            }
//                        } catch (Exception ex) {
//                            if (BuildConfig.DEBUG) {
//                                Log.e(Constants.LOG, ex.getMessage());
//                            }
//                        }
//                    }
//                });
//    }

    private void initGridLayout() {
        mPlusOneButton = (PlusOneButton) findViewById(R.id.plus_one_button);

        try {
            int screenOrientation = getResources().getConfiguration().orientation;

//            if (!TextUtils.isEmpty(BrewSharedPrefs.getUserToken())) {
//                userToken = BrewSharedPrefs.getUserToken();
//            }

            grid = (RecyclerView) findViewById(R.id.grid);
            grid.setItemAnimator(new DefaultItemAnimator());
            grid.setHasFixedSize(false);

            int gridColumns = 3;
            if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                gridColumns = 4;
            }

            final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(gridColumns, LinearLayoutManager.VERTICAL);
            layoutManager.scrollToPosition(0);
            grid.setLayoutManager(layoutManager);

            List<RecipeImage> placeHolder = new Vector<>();
            adapter = new GridImagesRecycler(placeHolder, this);
            grid.setAdapter(adapter);

//            load();
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    public void addFabListener() {
        try {
            if (toggleStar) {
                fab.setImageResource(R.drawable.ic_star_white_24dp);
            } else {
                fab.setImageResource(R.drawable.ic_star_border_white_24dp);
            }

//            fab.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (!BrewSharedPrefs.getIsUserLoggedIn()) {
//                        AlertLoginPrompt(_mContext, "", getText(R.string.text_login_to_favorite).toString(), getText(R.string.text_sign_in).toString(), getText(R.string.text_close).toString());
//                    } else {
//                        if (toggleStar) {
//                            fab.setImageResource(R.drawable.ic_star_border_white_24dp);
//                            toggleStar = false;
//                        } else {
//                            fab.setImageResource(R.drawable.ic_star_white_24dp);
//                            toggleStar = true;
//                        }
//
//                        Ion.with(getApplicationContext())
//                                .load(Constants.wcfAddUpdateFavorite + responseContentItemPk + "/" + userToken)
//                                .asString();
//                    }
//                }
//            });
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    public void initCheckbox() {
        fab = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        menu.findItem(R.id.menu_item_share).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter = null;
        recipeImageList = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
//            if (requestCode == RC_SIGN_IN) {
//                mIntentInProgress = false;
//                if (!mGoogleApiClient.isConnecting()) {
//                    mGoogleApiClient.connect();
//                }
//            }
        }
        if (resultCode == RESULT_OK) {
            this.onCreate(null);
//            if (requestCode == RC_SIGN_IN) {
//                mIntentInProgress = false;
//                if (!mGoogleApiClient.isConnecting()) {
//                    mGoogleApiClient.connect();
//                }
//            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String URL = Constants.urlBrewHahaContent + recipeTitle.replace(" ", "-");
        // Refresh the state of the +1 button each time the activity receives focus.
//        mPlusOneButton.initialize(URL, PLUS_ONE_REQUEST_CODE);
    }

//    @Override
//    public void onConnected(Bundle bundle) {
//        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
//            BrewSharedPrefs.setIsUserLoggedIn(true);
//            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
//            BrewSharedPrefs.setFullName(currentPerson.getDisplayName());
//            String personPhoto = currentPerson.getImage().getUrl();
//
//            try {
//                URL url = new URL(personPhoto);
//                personPhoto = personPhoto.replace(url.getQuery(), "");
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//
//            BrewSharedPrefs.setGoolgePlusProfileUrl(currentPerson.getUrl());
//            BrewSharedPrefs.setUserProfileImageUrl(personPhoto);
//            BrewSharedPrefs.setEmailAddress(Plus.AccountApi.getAccountName(mGoogleApiClient));
//            PlusAccountSetup();
//        }
//    }

    public void openImageDetail(View view, int position) {
        try {
            RecipeImage recipeImageItem = adapter.GetItemAt(position);
            Intent i = new Intent(this, FullScreenViewActivity.class);
            i.putExtra("position", position);
//            i.putExtra(Constants.exContentItemPk, String.valueOf(recipeImageItem.getRecipeContentId()));
            i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

            startActivity(i);
            overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);

        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    public void openRecipeDetail(View view, int position) {
        try {
//            RecipeImage imageItem = adapter.GetItemAt(position);

            Intent intent = new Intent(this, RecipeActivity.class);

//            intent.putExtra(Constants.exRecipeTitle, String.valueOf(BrewSharedPrefs.getCurrentContentTitle()));
//            intent.putExtra(Constants.exContentItemPk, String.valueOf(BrewSharedPrefs.getNextContentItemId()));
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
                ActivityCompat.startActivity(this, intent, options.toBundle());
            } else {
                this.startActivity(intent);
                this.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);

            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }
}
