package brightseer.com.brewhaha.recipe_fragments;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;

import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.helper.RecyclerItemClickListener;
import brightseer.com.brewhaha.helper.Utilities;
import brightseer.com.brewhaha.models.RecipeGrain;
import brightseer.com.brewhaha.models.RecipeHop;
import brightseer.com.brewhaha.models.RecipeYeast;
import brightseer.com.brewhaha.objects.Country;
import brightseer.com.brewhaha.objects.Grain;
import brightseer.com.brewhaha.objects.GrainUse;
import brightseer.com.brewhaha.objects.HopsForm;
import brightseer.com.brewhaha.objects.HopsUse;
import brightseer.com.brewhaha.objects.Laboratory;
import brightseer.com.brewhaha.objects.UnitOfMeasure;
import brightseer.com.brewhaha.recipe_adapters.RecipeGrainViewHolder;
import brightseer.com.brewhaha.recipe_adapters.RecipeHopViewHolder;
import brightseer.com.brewhaha.recipe_adapters.RecipeYeastViewHolder;
import brightseer.com.brewhaha.repository.DBHelper_Country;
import brightseer.com.brewhaha.repository.DBHelper_Grain;
import brightseer.com.brewhaha.repository.DBHelper_GrainUse;
import brightseer.com.brewhaha.repository.DBHelper_HopUse;
import brightseer.com.brewhaha.repository.DBHelper_HopsForm;
import brightseer.com.brewhaha.repository.DBHelper_Laboratory;
import brightseer.com.brewhaha.repository.DBHelper_UnitOfMeasure;

/**
 * Created by wooan on 3/21/2016.
 */
public class IngredientFragment extends BaseRecipeFragment {
    private View rootView;
    private RecyclerView recipe_grain_recycler_view, recipe_hop_recycler_view, recipe_yeast_recycler_view;

    FirebaseRecyclerAdapter mAdapterGrain ;
    FirebaseRecyclerAdapter mAdapterHop;
    FirebaseRecyclerAdapter mAdapterYeast;
//    private GrainRecycler GrainRecyclerAdapter;
//    private HopsRecycler HopsRecyclerAdapter;
//    private YeastRecycler YeastRecyclerAdapter;

    private String feedKey;
    private Firebase rootRef;

//    private int ingredientGrainId = 0, ingredientHopId = 0, ingredientYeastId = 0, selectedGrainId = 0, selectedCountyId = 0, selectedGrainUseId = 0, selectedUnitOfMeasureId = 0, selectedSrm = 0;
//    private int colorPickerProgress = 0, listPosition = 0;

    private List<UnitOfMeasure> unitOfMeasures = new Vector<>();
    private List<Country> counties = new Vector<>();
    private List<GrainUse> grainUse = new Vector<>();
    private List<Grain> grainList = new Vector<>();
    private List<HopsUse> hopUseList = new Vector<>();
    private List<HopsForm> hopsFormList = new Vector<>();
    private List<Laboratory> laboratoryList = new Vector<>();

    public static IngredientFragment newInstance(int centerX, int centerY, int color, String _feedKey) {
        Bundle args = new Bundle();
        args.putInt("cx", centerX);
        args.putInt("cy", centerY);
        args.putInt("color", color);

        args.putString(Constants.fbFeedKey, _feedKey);
//        args.putSerializable(Constants.bundleRecipeGrains, (Serializable) grains);
//        args.putSerializable(Constants.bundleRecipeHops, (Serializable) hops);
//        args.putSerializable(Constants.bundleRecipeYeasts, (Serializable) yeasts);

        IngredientFragment fragment = new IngredientFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recipe_ingredient, container, false);
        rootView = SetCircularReveal(rootView);
//        rootView.setBackgroundColor(getArguments().getInt("color"));

        ReadBundle();
        initFirebaseDb();
//        GetDbHelper();

//        addTestGrainData();
//        addTestHopData();
//        addTestYeastData();

        initHopRecyclerView();
        initYeastRecyclerView();
        initGrainRecyclerView();
        return rootView;
    }

    private void initFirebaseDb() {
        rootRef = new Firebase(Constants.fireBaseRoot).child(Constants.fbIngredients).child(feedKey);
    }

    public void addTestGrainData() {
        try {
            ///ADD NEW RecipeGrain//////////////////
            Firebase refGrain = rootRef.child(Constants.fbGrains);

            RecipeGrain recipeGrain = new RecipeGrain();
            recipeGrain.setAmount((double) 1.4);
            recipeGrain.setColor(15);
            recipeGrain.setCountry("USA");
            recipeGrain.setGrainUse("Pellet 2");
            recipeGrain.setHexColor("#FF5252");
            recipeGrain.setName("Lamma Pellet");
            recipeGrain.setUnitOfMeasure("oz");
            refGrain.push().setValue(recipeGrain);


            recipeGrain = new RecipeGrain();
            recipeGrain.setAmount(5);
            recipeGrain.setColor(15);
            recipeGrain.setCountry("USA geg from list");
            recipeGrain.setGrainUse("Pellet - get from list");
            recipeGrain.setHexColor("#F34452");
            recipeGrain.setName("Lamma Mama Pellet 2");
            recipeGrain.setUnitOfMeasure("Lb");
            refGrain.push().setValue(recipeGrain);


            recipeGrain = new RecipeGrain();
            recipeGrain.setAmount(3.5);
            recipeGrain.setColor(15);
            recipeGrain.setCountry("USA");
            recipeGrain.setGrainUse("Pellet");
            recipeGrain.setHexColor("#FF52fe");
            recipeGrain.setName("Lamma Mama Pajama Pellet 2");
            recipeGrain.setUnitOfMeasure("Ton");
            refGrain.push().setValue(recipeGrain);


            //////////////////
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    public void addTestHopData() {
        try {
            ///ADD NEW RecipeGrain//////////////////
            Firebase refHop = rootRef.child(Constants.fbHops);

            RecipeHop recipeHop = new RecipeHop();
            recipeHop.setUnitOfMeasure("oz");
            recipeHop.setName("Lamma Drama 2");
            recipeHop.setAlphaAcidPercentage((double) 16);
            recipeHop.setAmount((double) 4);
            recipeHop.setCookTime((double) 15);
            recipeHop.setHopForm("Pellet");
            recipeHop.setHopUse("Boil");
            recipeHop.setUnitOfTime("Mins");
            refHop.push().setValue(recipeHop);

            recipeHop = new RecipeHop();
            recipeHop.setUnitOfMeasure("oz");
            recipeHop.setName("Lamma Drama To YoMama 2");
            recipeHop.setAlphaAcidPercentage((double) 16);
            recipeHop.setAmount((double) 4);
            recipeHop.setCookTime((double) 12.9);
            recipeHop.setHopForm("Pellet");
            recipeHop.setHopUse("Boil");
            recipeHop.setUnitOfTime("Mins");
            refHop.push().setValue(recipeHop);
            //////////////////
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    public void addTestYeastData() {
        try {
            ///ADD NEW RecipeGrain//////////////////
            Firebase refHop = rootRef.child(Constants.fbYeasts);

            RecipeYeast recipeYeast = new RecipeYeast();
            recipeYeast.setAttenuationPercentage((double) 4);
            recipeYeast.setLaboratory("WestSide");
            recipeYeast.setName("Pellet Yeast Farts 2");
            refHop.push().setValue(recipeYeast);

            recipeYeast = new RecipeYeast();
            recipeYeast.setAttenuationPercentage((double) 9);
            recipeYeast.setLaboratory("EastSide");
            recipeYeast.setName("Wood Yeast Farts 2");
            refHop.push().setValue(recipeYeast);

            //////////////////
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    private void ReadBundle() {
        feedKey = String.valueOf(getArguments().get(Constants.fbFeedKey));
    }

    private void initGrainRecyclerView() {
        try {
            LinearLayoutManager recylerViewLayoutManager = new LinearLayoutManager(getActivity());
            recylerViewLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recylerViewLayoutManager.scrollToPosition(0);

            recipe_grain_recycler_view = (RecyclerView) rootView.findViewById(R.id.recipe_grain_recycler_view);
            recipe_grain_recycler_view.setLayoutManager(recylerViewLayoutManager);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                recipe_grain_recycler_view.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider, getActivity().getTheme()), true));
            } else {
                recipe_grain_recycler_view.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider), true));
            }

            Firebase ref = rootRef.child(Constants.fbGrains);
            mAdapterGrain = new FirebaseRecyclerAdapter<RecipeGrain, RecipeGrainViewHolder>(RecipeGrain.class, R.layout.recipe_grain_row, RecipeGrainViewHolder.class, ref) {
                @Override
                public void populateViewHolder(RecipeGrainViewHolder recipeGrainViewHolder, RecipeGrain recipeGrain, int position) {

                    recipeGrainViewHolder.my_grain_amount_text_view.setText(String.valueOf(recipeGrain.getAmount()));

                    String decription = recipeGrain.getName() + ",";
                    recipeGrainViewHolder.my_grain_description_text_view.setText(decription);

                    recipeGrainViewHolder.my_grain_weight_text_view.setText(recipeGrain.getUnitOfMeasure());

                    if (TextUtils.isEmpty(recipeGrain.getHexColor())) {
                        recipeGrain.setHexColor("#fee799");
                    }

                    Drawable mDrawable = Utilities.SetDrawableColor(recipeGrain.getHexColor(), getContext(), getActivity());
                    recipeGrainViewHolder.my_grain_color_image_view.setImageDrawable(mDrawable);
                }
            };
            recipe_grain_recycler_view.setAdapter(mAdapterGrain);

            recipe_grain_recycler_view.setItemAnimator(new DefaultItemAnimator());
            recipe_grain_recycler_view.addOnItemTouchListener(
                    new RecyclerItemClickListener(getContext(), recipe_grain_recycler_view, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            try {

                            } catch (Exception e) {
                                if (BuildConfig.DEBUG) {
                                    Log.e(Constants.LOG, e.getMessage());
                                }
                            }
                        }

                        @Override
                        public void onItemLongClick(View view, int position) {
                        }
                    })
            );
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    private void initHopRecyclerView() {
        recipe_hop_recycler_view = (RecyclerView) rootView.findViewById(R.id.recipe_hop_recycler_view);
        recipe_hop_recycler_view.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);

        recipe_hop_recycler_view.setLayoutManager(layoutManager);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            recipe_hop_recycler_view.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider, getActivity().getTheme()), true));
        } else {
            recipe_hop_recycler_view.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider), true));
        }

        Firebase ref = rootRef.child(Constants.fbHops);
//        Query queryRef = ref.orderByChild(Constants.fbFeedKey).equalTo(feedKey);
        mAdapterHop = new FirebaseRecyclerAdapter<RecipeHop, RecipeHopViewHolder>(RecipeHop.class, R.layout.recipe_hop_row, RecipeHopViewHolder.class, ref) {
            @Override
            public void populateViewHolder(RecipeHopViewHolder recipeHopViewHolder, RecipeHop recipeHop, int position) {

                recipeHopViewHolder.my_hop_amount_text_view.setText(String.valueOf(recipeHop.getAmount()));

                String timeDesctiption = String.valueOf(recipeHop.getCookTime()) + " " + recipeHop.getUnitOfTime() + ",";
                recipeHopViewHolder.my_hop_time_text_view.setText(timeDesctiption);

                String hopName = recipeHop.getName() + ",";
                recipeHopViewHolder.my_hop_type_text_view.setText(hopName);

                String hopWeight = recipeHop.getUnitOfMeasure() + ",";
                recipeHopViewHolder.my_hop_weight_text_view.setText(hopWeight);

                String hopUse = recipeHop.getHopUse() + ",";
                recipeHopViewHolder.my_hop_use_text_view.setText(hopUse);

                String hopForm = recipeHop.getHopForm() + ",";
                recipeHopViewHolder.my_hop_form_text_view.setText(hopForm);

                String hopAlpha = String.valueOf(recipeHop.getAlphaAcidPercentage()) + "%";
                recipeHopViewHolder.row_hop_alpha_text_view.setText(hopAlpha);
            }
        };
        recipe_hop_recycler_view.setAdapter(mAdapterHop);

        recipe_hop_recycler_view.setItemAnimator(new DefaultItemAnimator());
        recipe_hop_recycler_view.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recipe_hop_recycler_view, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        try {

                        } catch (Exception e) {
                            if (BuildConfig.DEBUG) {
                                Log.e(Constants.LOG, e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
//                        ingredientHopId = ingredientList.get(position).getIngredientHopsId();
//                        listPosition = position;
//                        registerForContextMenu(view);
//                        getActivity().openContextMenu(view);
//                        view.setLongClickable(false);
                    }
                })
        );
    }

    private void initYeastRecyclerView() {
        recipe_yeast_recycler_view = (RecyclerView) rootView.findViewById(R.id.recipe_yeast_recycler_view);
        recipe_yeast_recycler_view.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recipe_yeast_recycler_view.setLayoutManager(layoutManager);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            recipe_yeast_recycler_view.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider, getActivity().getTheme()), true));
        } else {
            recipe_yeast_recycler_view.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider), true));
        }

        Firebase ref = rootRef.child(Constants.fbYeasts);
        mAdapterYeast = new FirebaseRecyclerAdapter<RecipeYeast, RecipeYeastViewHolder>(RecipeYeast.class, R.layout.recipe_yeast_row, RecipeYeastViewHolder.class, ref) {
            @Override
            public void populateViewHolder(RecipeYeastViewHolder recipeYeastViewHolder, RecipeYeast recipeYeast, int position) {
                recipeYeastViewHolder.my_yeast_Lab_text_view.setText(recipeYeast.getLaboratory());

                String yeastName = recipeYeast.getName() + ",";
                recipeYeastViewHolder.my_yeast_type_text_view.setText(yeastName);
            }
        };
        recipe_yeast_recycler_view.setAdapter(mAdapterYeast);

        recipe_yeast_recycler_view.setItemAnimator(new DefaultItemAnimator());
        recipe_yeast_recycler_view.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recipe_yeast_recycler_view, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        try {

                        } catch (Exception e) {
                            if (BuildConfig.DEBUG) {
                                Log.e(Constants.LOG, e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                })
        );
    }

    private void GetDbHelper() {
        try {
            DBHelper_UnitOfMeasure repoUnitOfMeasure = new DBHelper_UnitOfMeasure(getContext());
            unitOfMeasures = repoUnitOfMeasure.getUnitOfMeasureList();

            DBHelper_GrainUse repoGrainUse = DBHelper_GrainUse.getInstance(getContext());
            grainUse = repoGrainUse.getGrainUseList();

            DBHelper_Country repoCountry = DBHelper_Country.getInstance(getContext());
            counties = repoCountry.getCountryList();

            DBHelper_Grain repoGrain = new DBHelper_Grain(getContext());
            grainList = repoGrain.getGrainList();

            DBHelper_HopUse repoHopsUse = new DBHelper_HopUse(getContext());
            hopUseList = repoHopsUse.getHopsUseList();

            DBHelper_HopsForm repoHopsForm = DBHelper_HopsForm.getInstance(getContext());
            hopsFormList = repoHopsForm.getHopsFormList();

            DBHelper_Laboratory repoLaboratory = DBHelper_Laboratory.getInstance(getContext());
            laboratoryList = repoLaboratory.getLaboratoryList();

        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    public UnitOfMeasure GetUnitOfMeasure(int itemId, int type) {
        for (UnitOfMeasure item : unitOfMeasures) {
            if (item.getUnitOfMeasurePk() == itemId && item.getType() == type) {
                return item;
            }
        }
        return null;
    }

    public Grain GetGrain(int itemId) {
        for (Grain item : grainList) {
            if (item.getGrainPk() == itemId) {
                return item;
            }
        }
        return null;
    }

    public GrainUse GetGrainUse(int itemId) {
        for (GrainUse item : grainUse) {
            if (item.getGrainUsePk() == itemId) {
                return item;
            }
        }
        return null;
    }

    public Country GetCountry(int itemId) {
        for (Country item : counties) {
            if (item.getCountryPk() == itemId) {
                return item;
            }
        }
        return null;
    }

    public HopsUse GetHopUse(int itemId) {
        for (HopsUse item : hopUseList) {
            if (item.getHopsUsePk() == itemId) {
                return item;
            }
        }
        return null;
    }

    public HopsForm GetHopForm(int itemId) {
        for (HopsForm item : hopsFormList) {
            if (item.getHopsFormPk() == itemId) {
                return item;
            }
        }
        return null;
    }

    public Laboratory GetLaboratory(int itemId) {
        for (Laboratory item : laboratoryList) {
            if (item.getLaboratoryPk() == itemId) {
                return item;
            }
        }
        return new Laboratory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapterGrain != null)
            mAdapterGrain.cleanup();
        if (mAdapterHop != null)
            mAdapterHop.cleanup();
        if (mAdapterYeast != null)
            mAdapterYeast.cleanup();
    }
}
