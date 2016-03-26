package brightseer.com.brewhaha.objects;

import java.io.Serializable;

/**
 * Created by Michael McCulloch on 3/2/2015.
 */
public class RecipeHop  implements Serializable {
    private int IngredientHopsId;
    private int RecipeContentId;
    private int HopsUseId;
    private int HopsFormId;
    private int UnitOfMeasureId;
    private int HopsId;
    private double Amount;
    private String Name;
    private double CookTime;
    //    private String TimeIndicator;
    private int TimeUnitOfMeasureId;
    private double AlphaAcidPercentage;


    private RecipeHop() {
    }

    public RecipeHop(int ingredientHopsId, int recipeContentId, int hopsUseId, int hopsFormId, int unitOfMeasureId, int hopsId, double amount, String name, double cookTime, int timeUnitOfMeasureId, double alphaAcidPercentage) {
        IngredientHopsId = ingredientHopsId;
        RecipeContentId = recipeContentId;
        HopsUseId = hopsUseId;
        HopsFormId = hopsFormId;
        UnitOfMeasureId = unitOfMeasureId;
        HopsId = hopsId;
        Amount = amount;
        Name = name;
        CookTime = cookTime;
        TimeUnitOfMeasureId = timeUnitOfMeasureId;
        AlphaAcidPercentage = alphaAcidPercentage;
    }

    public int getIngredientHopsId() {
        return IngredientHopsId;
    }

    public void setIngredientHopsId(int ingredientHopsId) {
        IngredientHopsId = ingredientHopsId;
    }

    public int getRecipeContentId() {
        return RecipeContentId;
    }

    public void setRecipeContentId(int recipeContentId) {
        RecipeContentId = recipeContentId;
    }

    public int getHopsUseId() {
        return HopsUseId;
    }

    public void setHopsUseId(int hopsUseId) {
        HopsUseId = hopsUseId;
    }

    public int getHopsFormId() {
        return HopsFormId;
    }

    public void setHopsFormId(int hopsFormId) {
        HopsFormId = hopsFormId;
    }

    public int getUnitOfMeasureId() {
        return UnitOfMeasureId;
    }

    public void setUnitOfMeasureId(int unitOfMeasureId) {
        UnitOfMeasureId = unitOfMeasureId;
    }

    public int getHopsId() {
        return HopsId;
    }

    public void setHopsId(int hopsId) {
        HopsId = hopsId;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getCookTime() {
        return CookTime;
    }

    public void setCookTime(double cookTime) {
        CookTime = cookTime;
    }

    public int getTimeUnitOfMeasureId() {
        return TimeUnitOfMeasureId;
    }

    public void setTimeUnitOfMeasureId(int timeUnitOfMeasureId) {
        TimeUnitOfMeasureId = timeUnitOfMeasureId;
    }

    public double getAlphaAcidPercentage() {
        return AlphaAcidPercentage;
    }

    public void setAlphaAcidPercentage(double alphaAcidPercentage) {
        AlphaAcidPercentage = alphaAcidPercentage;
    }
}

