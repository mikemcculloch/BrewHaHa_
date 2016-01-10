package brightseer.com.brewhaha.objects;

/**
 * Created by Michael McCulloch on 3/2/2015.
 */
public class IngredientHop {
    private int IngredientHopsPk;
    private int ContentItemPk;
    private int HopsUsePk;
    private int HopsFormPk;
    private int UnitOfMeasurePk;
    private int HopsPk;
    private double Amount;
    private String Name;
    private double CookTime;
    //    private String TimeIndicator;
    private int TimeUnitOfMeasureId;
    private double AlphaAcidPercentage;


    private IngredientHop() {
    }

    public IngredientHop(int ingredientHopsPk, int contentItemPk, int hopsUsePk, int hopsFormPk, int unitOfMeasurePk, int hopsPk, double amount, String name, double cookTime, int timeUnitOfMeasureId, double alphaAcidPercentage) {
        IngredientHopsPk = ingredientHopsPk;
        ContentItemPk = contentItemPk;
        HopsUsePk = hopsUsePk;
        HopsFormPk = hopsFormPk;
        UnitOfMeasurePk = unitOfMeasurePk;
        HopsPk = hopsPk;
        Amount = amount;
        Name = name;
        CookTime = cookTime;
        TimeUnitOfMeasureId = timeUnitOfMeasureId;
        AlphaAcidPercentage = alphaAcidPercentage;
    }

    public int getIngredientHopsPk() {
        return IngredientHopsPk;
    }

    public void setIngredientHopsPk(int ingredientHopsPk) {
        IngredientHopsPk = ingredientHopsPk;
    }

    public int getContentItemPk() {
        return ContentItemPk;
    }

    public void setContentItemPk(int contentItemPk) {
        ContentItemPk = contentItemPk;
    }

    public int getHopsUsePk() {
        return HopsUsePk;
    }

    public void setHopsUsePk(int hopsUsePk) {
        HopsUsePk = hopsUsePk;
    }

    public int getHopsFormPk() {
        return HopsFormPk;
    }

    public void setHopsFormPk(int hopsFormPk) {
        HopsFormPk = hopsFormPk;
    }

    public int getUnitOfMeasurePk() {
        return UnitOfMeasurePk;
    }

    public void setUnitOfMeasurePk(int unitOfMeasurePk) {
        UnitOfMeasurePk = unitOfMeasurePk;
    }

    public int getHopsPk() {
        return HopsPk;
    }

    public void setHopsPk(int hopsPk) {
        HopsPk = hopsPk;
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

