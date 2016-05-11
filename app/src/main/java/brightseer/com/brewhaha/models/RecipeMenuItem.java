package brightseer.com.brewhaha.models;

/**
 * Created by wooan on 5/10/2016.
 */
public class RecipeMenuItem {
    private int mDrawableRes;
    private String mTitle;
    private int mId;


    public RecipeMenuItem() {
    }

    public RecipeMenuItem(int mDrawableRes, String mTitle, int mId) {
        this.mDrawableRes = mDrawableRes;
        this.mTitle = mTitle;
        this.mId = mId;
    }

    public int getmDrawableRes() {
        return mDrawableRes;
    }

    public void setmDrawableRes(int mDrawableRes) {
        this.mDrawableRes = mDrawableRes;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }
}
