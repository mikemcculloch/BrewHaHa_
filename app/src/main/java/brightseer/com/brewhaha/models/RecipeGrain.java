package brightseer.com.brewhaha.models;

import java.io.Serializable;

/**
 * Created by Michael McCulloch on 3/2/2015.
 */
public class RecipeGrain implements Serializable {
    private String Key;

    private String GrainUse;
    private String Country;
    private String UnitOfMeasure;

    private double Amount;
    private String Name;
    private String HexColor;
    private int Color;

    public RecipeGrain() {
    }

    public RecipeGrain(String key, String grainUse, String country, String unitOfMeasure, double amount, String name, String hexColor, int color) {
        Key = key;
        GrainUse = grainUse;
        Country = country;
        UnitOfMeasure = unitOfMeasure;
        Amount = amount;
        Name = name;
        HexColor = hexColor;
        Color = color;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getGrainUse() {
        return GrainUse;
    }

    public void setGrainUse(String grainUse) {
        GrainUse = grainUse;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getHexColor() {
        return HexColor;
    }

    public void setHexColor(String hexColor) {
        HexColor = hexColor;
    }

    public int getColor() {
        return Color;
    }

    public void setColor(int color) {
        Color = color;
    }
}

