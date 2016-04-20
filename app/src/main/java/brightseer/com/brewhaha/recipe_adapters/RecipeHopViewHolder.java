package brightseer.com.brewhaha.recipe_adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import brightseer.com.brewhaha.R;

/**
 * Created by wooan on 4/18/2016.
 */
public class RecipeHopViewHolder extends RecyclerView.ViewHolder {
    public TextView my_hop_amount_text_view;
    public TextView my_hop_weight_text_view;
    public TextView my_hop_type_text_view;
    public TextView my_hop_use_text_view;
    public TextView my_hop_time_text_view;
    public TextView my_hop_form_text_view;
    public TextView row_hop_alpha_text_view;


    public RecipeHopViewHolder(View itemView) {
        super(itemView);
        my_hop_amount_text_view = (TextView) itemView.findViewById(R.id.my_hop_amount_text_view);
        my_hop_weight_text_view = (TextView) itemView.findViewById(R.id.my_hop_weight_text_view);
        my_hop_type_text_view = (TextView) itemView.findViewById(R.id.my_hop_type_text_view);
        my_hop_use_text_view = (TextView) itemView.findViewById(R.id.my_hop_use_text_view);
        my_hop_time_text_view = (TextView) itemView.findViewById(R.id.my_hop_time_text_view);
        my_hop_form_text_view = (TextView) itemView.findViewById(R.id.my_hop_form_text_view);
        row_hop_alpha_text_view = (TextView) itemView.findViewById(R.id.row_hop_alpha_text_view);
    }
}