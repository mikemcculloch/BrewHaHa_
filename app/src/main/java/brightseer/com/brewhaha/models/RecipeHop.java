package brightseer.com.brewhaha.models;

import java.io.Serializable;

/**
 * Created by Michael McCulloch on 3/2/2015.
 */
public class RecipeHop  implements Serializable {
    private String Key;
    private String FeedKey;
    private String UserProfileKey;
    private String Name;
    private String HopUse;
    private String HopForm;
    private String UnitOfMeasure;
    private double Amount;
    private double CookTime;
    private String UnitOfTime;
    private double AlphaAcidPercentage;

    public RecipeHop() {
    }

    public RecipeHop(String key, String feedKey, String userProfileKey, String name, String hopUse, String hopForm, String unitOfMeasure, double amount, double cookTime, String unitOfTime, double alphaAcidPercentage) {
        Key = key;
        FeedKey = feedKey;
        UserProfileKey = userProfileKey;
        Name = name;
        HopUse = hopUse;
        HopForm = hopForm;
        UnitOfMeasure = unitOfMeasure;
        Amount = amount;
        CookTime = cookTime;
        UnitOfTime = unitOfTime;
        AlphaAcidPercentage = alphaAcidPercentage;
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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getHopUse() {
        return HopUse;
    }

    public void setHopUse(String hopUse) {
        HopUse = hopUse;
    }

    public String getHopForm() {
        return HopForm;
    }

    public void setHopForm(String hopForm) {
        HopForm = hopForm;
    }

    public String getUnitOfMeasure() {
        return UnitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        UnitOfMeasure = unitOfMeasure;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public double getCookTime() {
        return CookTime;
    }

    public void setCookTime(double cookTime) {
        CookTime = cookTime;
    }

    public String getUnitOfTime() {
        return UnitOfTime;
    }

    public void setUnitOfTime(String unitOfTime) {
        UnitOfTime = unitOfTime;
    }

    public double getAlphaAcidPercentage() {
        return AlphaAcidPercentage;
    }

    public void setAlphaAcidPercentage(double alphaAcidPercentage) {
        AlphaAcidPercentage = alphaAcidPercentage;
    }
}

