package brightseer.com.brewhaha.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.BrewSharedPrefs;
import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.RecipeActivity;
import brightseer.com.brewhaha.helper.Utilities;
import brightseer.com.brewhaha.objects.Ingredient;
import brightseer.com.brewhaha.objects.IngredientSelected;
import brightseer.com.brewhaha.repository.DBHelper_IngredientSelected;

public class IngredientAdapter extends ArrayAdapter<Ingredient> implements CompoundButton.OnCheckedChangeListener {

    private Context _context;
    Activity _Activity;
    private List<Integer> _TitlesArray;
    private int _ContentPk;
    private List<IngredientSelected> _appDataList = new Vector<>();
    private DBHelper_IngredientSelected repoSelected;

    public IngredientAdapter(Context context, int resourceId, List<Ingredient> objects, RecipeActivity activity, List<Integer> titlesArray, int contentPk) {
        super(context, resourceId, objects);
        _Activity = activity;
        _context = context;
        _TitlesArray = titlesArray;
        _ContentPk = contentPk;
        repoSelected = new DBHelper_IngredientSelected(_context);
        _appDataList = repoSelected.getIngredientSelectedByContentItemPk(_ContentPk, BrewSharedPrefs.getUserToken());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View theView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            theView = inflater.inflate(R.layout.row_ingredient, parent, false);
        } else {
            theView = convertView;
        }
        try {
            Ingredient item = getItem(position);
            TextView ingredient_type_text_view = (TextView) theView.findViewById(R.id.ingredient_type_text_view);

            if (_TitlesArray.contains(position)) {
                ingredient_type_text_view.setText(item.getType());
                ingredient_type_text_view.setVisibility(View.VISIBLE);
            } else {
                ingredient_type_text_view.setVisibility(View.GONE);
            }
            CheckBox ingredient_checkbox = (CheckBox) theView.findViewById(R.id.ingredient_checkbox);
            if (BrewSharedPrefs.getIsUserLoggedIn()) {
                for (IngredientSelected appDataItem : _appDataList) {
                    if (appDataItem.getIngredientId() == item.getIngredientId() && appDataItem.getType() == Utilities.getIngredientTypeId(item.getType()) && _ContentPk == appDataItem.getContentItemPk()) {
                        ingredient_checkbox.setChecked(true);
                    }
                }
                ingredient_checkbox.setOnCheckedChangeListener(this);
                ingredient_checkbox.setTag(position);
            }
            ImageView ingredient_color_image_view = (ImageView) theView.findViewById(R.id.ingredient_color_image_view);
            if (item.getType().equals("Grain")) {

                if (TextUtils.isEmpty(item.getHexColor())) {
                    item.setHexColor("#fee799");
                }
                ingredient_color_image_view.setBackgroundColor(Color.parseColor(item.getHexColor()));
            } else {
                ingredient_color_image_view.setVisibility(View.GONE);
            }
            TextView comment_text_view = (TextView) theView.findViewById(R.id.ingredient_text_view);
            comment_text_view.setText(item.getDescription());
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }

        return theView;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        try {
            if (buttonView.isShown()) {
                int position = Integer.parseInt(buttonView.getTag().toString());
                Ingredient item = getItem(position);
                int typeInt = Utilities.getIngredientTypeId(item.getType());
                if (isChecked) {
                    IngredientSelected newItem = new IngredientSelected();
                    newItem.setUserToken(BrewSharedPrefs.getUserToken());
                    newItem.setContentItemPk(item.getContentItemPk());
                    newItem.setIngredientId(item.getIngredientId());
                    newItem.setType(typeInt);

                    repoSelected.insertIngredientSelected(newItem);
                } else {
                    repoSelected.deleteIngredientSelectedRecord(item.getIngredientId(), typeInt);
                }
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }
}
