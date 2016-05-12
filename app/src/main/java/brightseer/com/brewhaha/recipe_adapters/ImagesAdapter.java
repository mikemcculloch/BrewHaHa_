package brightseer.com.brewhaha.recipe_adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.helper.TouchImageView;
import brightseer.com.brewhaha.helper.Utilities;
import brightseer.com.brewhaha.models.RecipeImage;

/**
 * Created by wooan on 4/10/2016.
 */
public class ImagesAdapter extends PagerAdapter {

    private Activity _activity;
    private List<RecipeImage> _recepeImages;
    private LayoutInflater inflater;

    // constructor
    public ImagesAdapter(Activity activity, List<RecipeImage> recepeImages) {
        this._activity = activity;
        this._recepeImages = recepeImages;
    }


    @Override
    public int getCount() {
        return this._recepeImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TouchImageView imgDisplay;
        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container, false);

        viewLayout.setTag(position);

        viewLayout.setLongClickable(true);
        imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);
        imgDisplay.setContentDescription(String.valueOf(position));
        imgDisplay.setLongClickable(true);

        RecipeImage recipeImage = _recepeImages.get(position);

        Picasso.with(_activity)
                .load(recipeImage.getImageUrl())
                .into(imgDisplay);

//        Ion.with(imgDisplay)
//                .placeholder(R.mipmap.ic_beercap)
//                .load(recipeImage.getImageUrl());

        _activity.registerForContextMenu(imgDisplay);
        (container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        (container).removeView((RelativeLayout) object);

    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

}
