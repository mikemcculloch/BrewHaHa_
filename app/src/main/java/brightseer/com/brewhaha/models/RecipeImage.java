package brightseer.com.brewhaha.models;

import java.io.Serializable;

/**
 * Created by mccul_000 on 10/26/2014.
 */
public class RecipeImage implements Serializable {
    private String Key;
    private String ImageName;
    private String ImageUrl;
    private String Caption;

    public RecipeImage() {
    }

    public RecipeImage(String key, String userProfileKey, String imageName, String imageUrl, String caption) {
        Key = key;
        ImageName = imageName;
        ImageUrl = imageUrl;
        Caption = caption;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getCaption() {
        return Caption;
    }

    public void setCaption(String caption) {
        Caption = caption;
    }
}
