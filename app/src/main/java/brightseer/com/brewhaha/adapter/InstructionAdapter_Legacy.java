package brightseer.com.brewhaha.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.BrewSharedPrefs;
import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.RecipeActivity;
import brightseer.com.brewhaha.objects.Instruction;
import brightseer.com.brewhaha.objects.InstructionSelected;
import brightseer.com.brewhaha.repository.DBHelper_InstructionSelected;

public class InstructionAdapter_Legacy extends ArrayAdapter<Instruction> implements CompoundButton.OnCheckedChangeListener {
    private Context _context;
    Activity _Activity;
    private DBHelper_InstructionSelected repoSelected;
    private List<InstructionSelected> _appDataList = new Vector<>();
    private int _ContentPk;

    public InstructionAdapter_Legacy(Context context, int resourceId, List<Instruction> objects, RecipeActivity activity, int contentPk) {
        super(context, resourceId, objects);
        _Activity = activity;
        _context = context;
        _ContentPk = contentPk;
        repoSelected = new DBHelper_InstructionSelected(_context);
        _appDataList = repoSelected.getInstructionSelectedByContentItemPk(_ContentPk, BrewSharedPrefs.getUserToken());
    }

    static class ViewHolder {
        private TextView comment_item_author;
        private CheckBox instruction_checkbox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater theInflater = LayoutInflater.from(getContext());
        View theView = theInflater.inflate(R.layout.row_instruction, parent, false);
        try {
            Instruction item = getItem(position);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.comment_item_author = (TextView) theView.findViewById(R.id.instruction_order);
            viewHolder.comment_item_author.setText(String.valueOf(item.getOrder()));

            viewHolder.instruction_checkbox = (CheckBox) theView.findViewById(R.id.instruction_checkbox);
            if (BrewSharedPrefs.getIsUserLoggedIn()) {
                for (InstructionSelected appDataItem : _appDataList) {
                    if (appDataItem.getInstructionsId() == item.getInstructionId() && _ContentPk == appDataItem.getRecipeContentId()) {
                        viewHolder.instruction_checkbox.setChecked(true);
                    }
                }

                viewHolder.instruction_checkbox.setOnCheckedChangeListener(this);
                viewHolder.instruction_checkbox.setTag(position);
            }

            TextView comment_text_view = (TextView) theView.findViewById(R.id.instruction_instruction_text);
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
                Instruction instructionItem = getItem(position);

                if (isChecked) {
                    InstructionSelected newItem = new InstructionSelected();
                    newItem.setUserToken(BrewSharedPrefs.getUserToken());
                    newItem.setRecipeContentId(instructionItem.getRecipeContentId());
                    newItem.setInstructionsId(instructionItem.getInstructionId());
                    repoSelected.insertInstructionSelected(newItem);
                } else {
                    repoSelected.deleteInstructionSelectedRecord(instructionItem.getRecipeContentId(), instructionItem.getInstructionId());
                }
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }
}
