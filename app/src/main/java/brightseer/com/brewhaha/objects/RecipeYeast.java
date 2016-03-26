package brightseer.com.brewhaha.objects;

import java.io.Serializable;

/**
 * Created by Michael McCulloch on 3/2/2015.
 */
public class RecipeYeast  implements Serializable {
    private int IngredientYeastId;
    private int RecipeContentId;
    private int LaboratoryId;
    private int YeastId;
    private String Name;
    private double AttenuationPercentage;

    private RecipeYeast() {
    }

    public RecipeYeast(int ingredientYeastId, int recipeContentId, int laboratoryId, int yeastId, String name, double attenuationPercentage) {
        IngredientYeastId = ingredientYeastId;
        RecipeContentId = recipeContentId;
        LaboratoryId = laboratoryId;
        YeastId = yeastId;
        Name = name;
        AttenuationPercentage = attenuationPercentage;
    }

    public int getIngredientYeastId() {
        return IngredientYeastId;
    }

    public void setIngredientYeastId(int ingredientYeastId) {
        IngredientYeastId = ingredientYeastId;
    }

    public int getRecipeContentId() {
        return RecipeContentId;
    }

    public void setRecipeContentId(int recipeContentId) {
        RecipeContentId = recipeContentId;
    }

    public int getLaboratoryId() {
        return LaboratoryId;
    }

    public void setLaboratoryId(int laboratoryId) {
        LaboratoryId = laboratoryId;
    }

    public int getYeastId() {
        return YeastId;
    }

    public void setYeastId(int yeastId) {
        YeastId = yeastId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getAttenuationPercentage() {
        return AttenuationPercentage;
    }

    public void setAttenuationPercentage(double attenuationPercentage) {
        AttenuationPercentage = attenuationPercentage;
    }
}
