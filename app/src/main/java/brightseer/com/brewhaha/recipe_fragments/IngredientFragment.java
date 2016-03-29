package brightseer.com.brewhaha.recipe_fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.adapter.RecyclerItemClickListener;
import brightseer.com.brewhaha.adapter.YeastMyRecipeRecycler;
import brightseer.com.brewhaha.objects.Country;
import brightseer.com.brewhaha.objects.Grain;
import brightseer.com.brewhaha.objects.GrainUse;
import brightseer.com.brewhaha.objects.HopsForm;
import brightseer.com.brewhaha.objects.HopsUse;
import brightseer.com.brewhaha.objects.Laboratory;
import brightseer.com.brewhaha.objects.RecipeGrain;
import brightseer.com.brewhaha.objects.RecipeHop;
import brightseer.com.brewhaha.objects.RecipeYeast;
import brightseer.com.brewhaha.objects.UnitOfMeasure;
import brightseer.com.brewhaha.recipe_adapters.GrainRecycler;
import brightseer.com.brewhaha.recipe_adapters.HopsRecycler;
import brightseer.com.brewhaha.recipe_adapters.YeastRecycler;
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
    private List<RecipeGrain> recipeGrains = new Vector<>();
    private List<RecipeHop> recipeHops = new Vector<>();
    private List<RecipeYeast> recipeYeasts = new Vector<>();
    private RecyclerView recipe_grain_recycler_view, recipe_hop_recycler_view, recipe_yeast_recycler_view;

    private GrainRecycler GrainRecyclerAdapter;
    private HopsRecycler HopsRecyclerAdapter;
    private YeastRecycler YeastRecyclerAdapter;

    private int ingredientGrainId = 0, ingredientHopId = 0, ingredientYeastId = 0, selectedGrainId = 0, selectedCountyId = 0, selectedGrainUseId = 0, selectedUnitOfMeasureId = 0, selectedSrm = 0;
    private int colorPickerProgress = 0, listPosition = 0;

    private List<UnitOfMeasure> unitOfMeasures = new Vector<>();
    private List<Country> counties = new Vector<>();
    private List<GrainUse> grainUse = new Vector<>();
    private List<Grain> grainList = new Vector<>();
    private List<HopsUse> hopUseList = new Vector<>();
    private List<HopsForm> hopsFormList = new Vector<>();
    private List<Laboratory> laboratoryList = new Vector<>();
    
    public static IngredientFragment newInstance(int centerX, int centerY, int color, List<RecipeGrain> grains, List<RecipeHop> hops, List<RecipeYeast> yeasts) {
        Bundle args = new Bundle();
        args.putInt("cx", centerX);
        args.putInt("cy", centerY);
        args.putInt("color", color);

        args.putSerializable(Constants.bundleRecipeGrains, (Serializable) grains);
        args.putSerializable(Constants.bundleRecipeHops, (Serializable) hops);
        args.putSerializable(Constants.bundleRecipeYeasts, (Serializable) yeasts);

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
        GetDbHelper();
        initGrainRecyclerView();
        initHopRecyclerView();
        initYeastRecyclerView();
        return rootView;
    }

    private void ReadBundle() {
        recipeGrains = (List<RecipeGrain>) getArguments().getSerializable(Constants.bundleRecipeGrains);
        recipeHops = (List<RecipeHop>) getArguments().getSerializable(Constants.bundleRecipeHops);
        recipeYeasts = (List<RecipeYeast>) getArguments().getSerializable(Constants.bundleRecipeYeasts);
    }

    private void initGrainRecyclerView() {
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

        GrainRecyclerAdapter = new GrainRecycler(recipeGrains, IngredientFragment.this);

        recipe_grain_recycler_view.setAdapter(GrainRecyclerAdapter);
        recipe_grain_recycler_view.setItemAnimator(new DefaultItemAnimator());

        recipe_grain_recycler_view.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recipe_grain_recycler_view, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        try {
                            RecipeGrain recipeGrain = GrainRecyclerAdapter.getItemAt(position);

//                            ingredientGrainId = recipeGrain.getIngredientGrainId();
//                            listPosition = position;
//                            registerForContextMenu(view);
//                            getActivity().openContextMenu(view);
//                            view.setLongClickable(false);
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

        HopsRecyclerAdapter = new HopsRecycler(recipeHops, IngredientFragment.this);

        recipe_hop_recycler_view.setAdapter(HopsRecyclerAdapter);
        recipe_hop_recycler_view.setItemAnimator(new DefaultItemAnimator());

        recipe_hop_recycler_view.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recipe_hop_recycler_view, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        try {
                            RecipeHop recipeHop = HopsRecyclerAdapter.getItemAt(position);
                            ingredientHopId = recipeHop.getIngredientHopsId();
                            listPosition = position;

//                            registerForContextMenu(view);
//                            getActivity().openContextMenu(view);
//                            view.setLongClickable(false);
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

        YeastRecyclerAdapter = new YeastRecycler(recipeYeasts, IngredientFragment.this);

        recipe_yeast_recycler_view.setAdapter(YeastRecyclerAdapter);
        recipe_yeast_recycler_view.setItemAnimator(new DefaultItemAnimator());


        recipe_yeast_recycler_view.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recipe_yeast_recycler_view, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        try {
                            RecipeYeast recipeYeast = YeastRecyclerAdapter.getItemAt(position);

                            ingredientYeastId = recipeYeast.getIngredientYeastId();
                            listPosition = position;
//                            registerForContextMenu(view);
//                            getActivity().openContextMenu(view);
//                            view.setLongClickable(false);
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
}
