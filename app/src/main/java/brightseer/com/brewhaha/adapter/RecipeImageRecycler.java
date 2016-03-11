package brightseer.com.brewhaha.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koushikdutta.ion.Ion;

import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.RecipeCardsActivity;
import brightseer.com.brewhaha.objects.Image;
import brightseer.com.brewhaha.objects.RecyclerObjects;

/**
 * Created by wooan_000 on 12/16/2014.
 */
public class RecipeImageRecycler extends RecyclerView.Adapter<RecyclerObjects.RecipeImageViewHolder> {
    private List<Image> imageList = new Vector<>();
    private RecipeCardsActivity _activity;

    public RecipeImageRecycler(List<Image> jsonObject, RecipeCardsActivity activity) {
        this.imageList = imageList;
        _activity = activity;
    }

    public void add(Image item, int position) {
        imageList.add(item);
        notifyItemInserted(position);
    }

    public void add(Image item) {
        imageList.add(item);
    }

    public void sendUpdate(int position) {
        notifyItemChanged(position);
    }

    public void clear() {
        imageList.clear();
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
        Image displayImage = imageList.get(position);
        Ion.with(viewHolder.recipeImage)
                .placeholder(R.drawable.ic_crop_original_black_24dp)
                .load(displayImage.getImageUrl());
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public Image GetItemAt(int position) {
        return imageList.get(position);
    }


}
