package brightseer.com.brewhaha.objects;

/**
 * Created by Michael McCulloch on 3/2/2015.
 */
public class IngredientYeast {
    private int IngredientYeastPk;
    private int ContentItemPk;
    private int LaboratoryPk;
    private int YeastPk;
    private String Name;
    private double AttenuationPercentage;

    private IngredientYeast() {
    }

    public IngredientYeast(int ingredientYeastPk, int contentItemPk, int laboratoryPk, int yeastPk, String name, double attenuationPercentage) {
        IngredientYeastPk = ingredientYeastPk;
        ContentItemPk = contentItemPk;
        LaboratoryPk = laboratoryPk;
        YeastPk = yeastPk;
        Name = name;
        AttenuationPercentage = attenuationPercentage;
    }

    public int getIngredientYeastPk() {
        return IngredientYeastPk;
    }

    public void setIngredientYeastPk(int ingredientYeastPk) {
        IngredientYeastPk = ingredientYeastPk;
    }

    public int getContentItemPk() {
        return ContentItemPk;
    }

    public void setContentItemPk(int contentItemPk) {
        ContentItemPk = contentItemPk;
    }

    public int getLaboratoryPk() {
        return LaboratoryPk;
    }

    public void setLaboratoryPk(int laboratoryPk) {
        LaboratoryPk = laboratoryPk;
    }

    public int getYeastPk() {
        return YeastPk;
    }

    public void setYeastPk(int yeastPk) {
        YeastPk = yeastPk;
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

