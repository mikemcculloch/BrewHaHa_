package brightseer.com.brewhaha.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koushikdutta.ion.Ion;

import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.models.RecipeImage;
import brightseer.com.brewhaha.objects.RecyclerObjects;

/**
 * Created by Michael McCulloch on 3/2/2015.
 */
public class BeerMyRecipeRecycler extends RecyclerView.Adapter<RecyclerObjects.ImageMyRecipeViewHolder> {
    private List<RecipeImage> jsonObjects = new Vector<>();
    private Fragment _fragment;
    private int _imageWidth;

    public BeerMyRecipeRecycler(List<RecipeImage> jsonObject, Fragment fragment, int imageWidth) {
        jsonObjects = jsonObject;
        _fragment = fragment;
        _imageWidth = imageWidth;
    }

    public void add(RecipeImage item, int position) {
        jsonObjects.add(item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        jsonObjects.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public RecyclerObjects.ImageMyRecipeViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.image_simple, viewGroup, false);
        return new RecyclerObjects.ImageMyRecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerObjects.ImageMyRecipeViewHolder itemViewHolder, int i) {
        RecipeImage item = jsonObjects.get(i);


        Ion.with(itemViewHolder.my_beer_image_view)
                .placeholder(R.mipmap.ic_beercap)
                .centerCrop()
                .load(item.getImageUrl());

        //                .placeholder(R.mipmap.ic_beercap)
//        itemViewHolder.my_beer_image_view.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        itemViewHolder.my_beer_image_view.setLayoutParams(new GridView.LayoutParams(_imageWidth,
//                _imageWidth));
//
//        Ion.with(itemViewHolder.my_beer_image_view)
//                .placeholder(R.mipmap.ic_beercap)
//                .error(R.drawable.ic_action_error)
//                .load(item.getImageUrl());

    }

    @Override
    public int getItemCount() {
        return jsonObjects.size();
    }

}
