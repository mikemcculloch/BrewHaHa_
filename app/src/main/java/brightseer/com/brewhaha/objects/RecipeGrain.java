package brightseer.com.brewhaha.objects;

import java.io.Serializable;

/**
 * Created by Michael McCulloch on 3/2/2015.
 */
public class RecipeGrain implements Serializable {
    private int IngredientGrainId;
    private int GrainUseId;
    private int CountryId;
    private int UnitOfMeasureId;
    private int GrainId;
    private double Amount;
    private String Name;
    private String HexColor;
    private double Color;

    public RecipeGrain() {
    }

    public RecipeGrain(double amount, int countryId, int grainId, int grainUseId, String hexColor, int ingredientGrainId, String name, int unitOfMeasureId, double color) {
        Amount = amount;
        CountryId = countryId;
        GrainId = grainId;
        GrainUseId = grainUseId;
        HexColor = hexColor;
        IngredientGrainId = ingredientGrainId;
        Name = name;
        UnitOfMeasureId = unitOfMeasureId;
        Color = color;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public int getCountryId() {
        return CountryId;
    }

    public void setCountryId(int countryId) {
        CountryId = countryId;
    }

    public int getGrainId() {
        return GrainId;
    }

    public void setGrainId(int grainId) {
        GrainId = grainId;
    }

    public int getGrainUseId() {
        return GrainUseId;
    }

    public void setGrainUseId(int grainUseId) {
        GrainUseId = grainUseId;
    }

    public String getHexColor() {
        return HexColor;
    }

    public void setHexColor(String hexColor) {
        HexColor = hexColor;
    }

    public int getIngredientGrainId() {
        return IngredientGrainId;
    }

    public void setIngredientGrainId(int ingredientGrainId) {
        IngredientGrainId = ingredientGrainId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getUnitOfMeasureId() {
        return UnitOfMeasureId;
    }

    public void setUnitOfMeasureId(int unitOfMeasureId) {
        UnitOfMeasureId = unitOfMeasureId;
    }

    public double getColor() {
        return Color;
    }

    public void setColor(double color) {
        Color = color;
    }
}

