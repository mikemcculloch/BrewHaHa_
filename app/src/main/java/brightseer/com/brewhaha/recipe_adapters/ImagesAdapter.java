package brightseer.com.brewhaha.recipe_adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.koushikdutta.ion.Ion;

import java.util.List;

import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.objects.RecipeImage;

/**
 * Created by wooan on 4/10/2016.
 */
public class ImagesAdapter extends PagerAdapter {

    private Activity _activity;
    private List<RecipeImage> _recepeImages;
    private LayoutInflater inflater;

    // constructor
    public ImagesAdapter(Activity activity,List<RecipeImage> imagePaths) {
        this._activity = activity;
        this._recepeImages = imagePaths;
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
        ImageView imgDisplay;

        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View viewLayout = inflater.inflate(R.layout.pager_simple, container, false);

        imgDisplay = (ImageView) viewLayout.findViewById(R.id.simple_image_view);
        imgDisplay.setContentDescription(String.valueOf(position));

        RecipeImage recipeImage = _recepeImages.get(position);

        Ion.with(imgDisplay)
                .placeholder(R.mipmap.ic_beercap)
                .load(recipeImage.getImageUrl());

        (container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        (container).removeView((RelativeLayout) object);
    }
}
