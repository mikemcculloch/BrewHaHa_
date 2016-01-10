package brightseer.com.brewhaha.objects;

/**
 * Created by Michael McCulloch on 3/2/2015.
 */
public class IngredientGrain {
    private double Amount;
    private int CountryPk;
    private int GrainPk;
    private int GrainUsePk;
    private String HexColor;
    private int IngredientGrainPk;
    private String Name;
    private int UnitOfMeasurePk;
    private double Color;

    public IngredientGrain() {
    }

    public IngredientGrain(double amount, int countryPk, int grainPk, int grainUsePk, String hexColor, int ingredientGrainPk, String name, int unitOfMeasurePk, double color) {
        Amount = amount;
        CountryPk = countryPk;
        GrainPk = grainPk;
        GrainUsePk = grainUsePk;
        HexColor = hexColor;
        IngredientGrainPk = ingredientGrainPk;
        Name = name;
        UnitOfMeasurePk = unitOfMeasurePk;
        Color = color;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public int getCountryPk() {
        return CountryPk;
    }

    public void setCountryPk(int countryPk) {
        CountryPk = countryPk;
    }

    public int getGrainPk() {
        return GrainPk;
    }

    public void setGrainPk(int grainPk) {
        GrainPk = grainPk;
    }

    public int getGrainUsePk() {
        return GrainUsePk;
    }

    public void setGrainUsePk(int grainUsePk) {
        GrainUsePk = grainUsePk;
    }

    public String getHexColor() {
        return HexColor;
    }

    public void setHexColor(String hexColor) {
        HexColor = hexColor;
    }

    public int getIngredientGrainPk() {
        return IngredientGrainPk;
    }

    public void setIngredientGrainPk(int ingredientGrainPk) {
        IngredientGrainPk = ingredientGrainPk;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getUnitOfMeasurePk() {
        return UnitOfMeasurePk;
    }

    public void setUnitOfMeasurePk(int unitOfMeasurePk) {
        UnitOfMeasurePk = unitOfMeasurePk;
    }

    public double getColor() {
        return Color;
    }

    public void setColor(double color) {
        Color = color;
    }
}

