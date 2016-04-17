package brightseer.com.brewhaha.models;

import java.io.Serializable;


/**
 * Created by wooanaz on 4/17/2016.
 */
public class RecipeDetail implements Serializable {
    private String Key;
    private String FeedKey;
    private String UserProfileKey;

//    private String Title;
    private String Description;
//    private String Author;
//    private String UserImageUrl;

    private String Style;
    private String StyleDescription;

    private String AlcoholByVol;
    private String BitternessIbu;
    private String ColorSrm;
    private String FinalGravity;
    private String OriginalGravity;
    private String YieldByGallon;
    private String SrmHex;

    private String DateModified;
    private String DateCreated;

    public RecipeDetail() {
    }

    public RecipeDetail(String key, String feedKey, String userProfileKey, String description, String style, String styleDescription, String alcoholByVol, String bitternessIbu, String colorSrm, String finalGravity, String originalGravity, String yieldByGallon, String srmHex, String dateModified, String dateCreated) {
        Key = key;
        FeedKey = feedKey;
        UserProfileKey = userProfileKey;
        Description = description;
        Style = style;
        StyleDescription = styleDescription;
        AlcoholByVol = alcoholByVol;
        BitternessIbu = bitternessIbu;
        ColorSrm = colorSrm;
        FinalGravity = finalGravity;
        OriginalGravity = originalGravity;
        YieldByGallon = yieldByGallon;
        SrmHex = srmHex;
        DateModified = dateModified;
        DateCreated = dateCreated;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getFeedKey() {
        return FeedKey;
    }

    public void setFeedKey(String feedKey) {
        FeedKey = feedKey;
    }

    public String getUserProfileKey() {
        return UserProfileKey;
    }

    public void setUserProfileKey(String userProfileKey) {
        UserProfileKey = userProfileKey;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStyle() {
        return Style;
    }

    public void setStyle(String style) {
        Style = style;
    }

    public String getStyleDescription() {
        return StyleDescription;
    }

    public void setStyleDescription(String styleDescription) {
        StyleDescription = styleDescription;
    }

    public String getAlcoholByVol() {
        return AlcoholByVol;
    }

    public void setAlcoholByVol(String alcoholByVol) {
        AlcoholByVol = alcoholByVol;
    }

    public String getBitternessIbu() {
        return BitternessIbu;
    }

    public void setBitternessIbu(String bitternessIbu) {
        BitternessIbu = bitternessIbu;
    }

    public String getColorSrm() {
        return ColorSrm;
    }

    public void setColorSrm(String colorSrm) {
        ColorSrm = colorSrm;
    }

    public String getFinalGravity() {
        return FinalGravity;
    }

    public void setFinalGravity(String finalGravity) {
        FinalGravity = finalGravity;
    }

    public String getOriginalGravity() {
        return OriginalGravity;
    }

    public void setOriginalGravity(String originalGravity) {
        OriginalGravity = originalGravity;
    }

    public String getYieldByGallon() {
        return YieldByGallon;
    }

    public void setYieldByGallon(String yieldByGallon) {
        YieldByGallon = yieldByGallon;
    }

    public String getSrmHex() {
        return SrmHex;
    }

    public void setSrmHex(String srmHex) {
        SrmHex = srmHex;
    }

    public String getDateModified() {
        return DateModified;
    }

    public void setDateModified(String dateModified) {
        DateModified = dateModified;
    }

    public String getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(String dateCreated) {
        DateCreated = dateCreated;
    }
}
