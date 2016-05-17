package brightseer.com.brewhaha.Depricated;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.models.RecipeImage;
import brightseer.com.brewhaha.objects.RecyclerObjects;

/**
 * Created by wooan_000 on 12/16/2014.
 */
public class RecipeImageRecycler extends RecyclerView.Adapter<RecyclerObjects.RecipeImageViewHolder> {
    private List<RecipeImage> recipeImageList = new Vector<>();
//    private RecipeCardsActivity _activity;

    public RecipeImageRecycler(List<RecipeImage> recipeImageList) {
        this.recipeImageList = recipeImageList;
//        _activity = activity; , RecipeCardsActivity activity
    }

    public void add(RecipeImage item, int position) {
        recipeImageList.add(item);
        notifyItemInserted(position);
    }

    public void add(RecipeImage item) {
        recipeImageList.add(item);
    }

    public void sendUpdate(int position) {
        notifyItemChanged(position);
    }

    public void clear() {
        recipeImageList.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerObjects.RecipeImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.row_single_image, viewGroup, false);
        return new RecyclerObjects.RecipeImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerObjects.RecipeImageViewHolder viewHolder, int position) {
        RecipeImage displayRecipeImage = recipeImageList.get(position);
//        Ion.with(viewHolder.recipeImage)
//                .placeholder(R.drawable.ic_crop_original_black_24dp)
//                .load(displayRecipeImage.getImageUrl());

//        Picasso.with(_activity)
//                .load(displayRecipeImage.getImageUrl())
//                .into(viewHolder.recipeImage);
    }

    @Override
    public int getItemCount() {
        return recipeImageList.size();
    }

    public RecipeImage GetItemAt(int position) {
        return recipeImageList.get(position);
    }


}
