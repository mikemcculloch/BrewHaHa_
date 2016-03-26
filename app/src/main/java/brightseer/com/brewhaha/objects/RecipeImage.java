package brightseer.com.brewhaha.objects;

import java.io.Serializable;

/**
 * Created by mccul_000 on 10/26/2014.
 */
public class RecipeImage  implements Serializable {
    private int RecipeContentId;
    private String ImageName;
    private int ImagePk;
    private String ImageUrl;
    //    private Date Timestamp;
    private String Caption;

    public RecipeImage() {
    }

    public RecipeImage(int recipeContentId, String imageName, int imagePk, String imageUrl, String caption) {
        RecipeContentId = recipeContentId;
        ImageName = imageName;
        ImagePk = imagePk;
        ImageUrl = imageUrl;
//        Timestamp = timestamp;
        Caption = caption;
    }

    public int getRecipeContentId() {
        return RecipeContentId;
    }

    public void setRecipeContentId(int recipeContentId) {
        RecipeContentId = recipeContentId;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public int getImagePk() {
        return ImagePk;
    }

    public void setImagePk(int imagePk) {
        ImagePk = imagePk;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

//    public Date getDateCreated() {
//        return Timestamp;
//    }
//
//    public void setDateCreated(Date timestamp) {
//        Timestamp = timestamp;
//    }

    public String getCaption() {
        return Caption;
    }

    public void setCaption(String caption) {
        Caption = caption;
    }
}
