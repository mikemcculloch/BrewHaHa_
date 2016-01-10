package brightseer.com.brewhaha.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.fragment.AddContentFragment;
import brightseer.com.brewhaha.fragment.AddGrainsFragment;
import brightseer.com.brewhaha.fragment.AddHopsFragment;
import brightseer.com.brewhaha.fragment.AddImagesFragment;
import brightseer.com.brewhaha.fragment.AddInstructionsFragment;
import brightseer.com.brewhaha.fragment.AddYeastFragment;

/**
 * Created by Michael McCulloch on 2/26/2015.
 */
public class AddUpdateRecipeTabAdapter extends FragmentStatePagerAdapter {
    String _contentPk;
    String _contentToken;
    FragmentManager _fm;

    public AddUpdateRecipeTabAdapter(FragmentManager fm, String contentPk, String contentToken) {
        super(fm);
        _fm = fm;
        _contentPk = contentPk;
        _contentToken = contentToken;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        Fragment fr = null;
        switch (position) {
            case 0:
                fr = new AddContentFragment();
                break;
            case 1:
                fr = new AddGrainsFragment();
                break;
            case 2:
                fr = new AddHopsFragment();
                break;
            case 3:
                fr = new AddYeastFragment();
                break;
            case 4:
                fr = new AddInstructionsFragment();
                _fm.beginTransaction()
                        .replace(R.id.pager, fr, Constants.fragTagInstructions);
                break;
            case 5:
                fr = new AddImagesFragment();
                break;
        }

        bundle.putString(Constants.spContentToken, _contentToken);
        bundle.putString(Constants.exContentItemPk, _contentPk);

        fr.setArguments(bundle);
        return fr;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Overview";
            case 1:
                return "Grains";
            case 2:
                return "Hops";
            case 3:
                return "Yeast";
            case 4:
                return "Directions";
            case 5:
                return "Images";
        }

        return null;
    }

}