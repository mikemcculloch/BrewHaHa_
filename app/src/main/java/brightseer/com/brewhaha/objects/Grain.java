package brightseer.com.brewhaha.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import brightseer.com.brewhaha.Constants;

/**
 * Created by wooan_000 on 1/6/2015.
 */
@DatabaseTable(tableName = Constants.grain_TableName)
public class Grain {
    @DatabaseField
    private boolean Active;
    @DatabaseField
    private int ColorSrm;
    @DatabaseField
    private String Country;
    @DatabaseField
    private String CountryAbbreviation;
    @DatabaseField
    private String GrainDescription;
    @DatabaseField
    private int GrainPk;
    @DatabaseField
    private int GrainTypePk;
    @DatabaseField
    private boolean Mash;
    @DatabaseField
    private double MaxPercentageInBatch;
    @DatabaseField
    private String Name;
    @DatabaseField
    private String PotentialSg;
    @DatabaseField
    private String Timestamp;

    public Grain() {
    }

    public Grain(boolean active, int colorSrm, String country, String countryAbbreviation, String grainDescription, int grainPk, int grainTypePk, boolean mash, double maxPercentageInBatch, String name, String potentialSg, String timestamp) {
        Active = active;
        ColorSrm = colorSrm;
        Country = country;
        CountryAbbreviation = countryAbbreviation;
        GrainDescription = grainDescription;
        GrainPk = grainPk;
        GrainTypePk = grainTypePk;
        Mash = mash;
        MaxPercentageInBatch = maxPercentageInBatch;
        Name = name;
        PotentialSg = potentialSg;
        Timestamp = timestamp;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    public int getColorSrm() {
        return ColorSrm;
    }

    public void setColorSrm(int colorSrm) {
        ColorSrm = colorSrm;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCountryAbbreviation() {
        return CountryAbbreviation;
    }

    public void setCountryAbbreviation(String countryAbbreviation) {
        CountryAbbreviation = countryAbbreviation;
    }

    public String getGrainDescription() {
        return GrainDescription;
    }

    public void setGrainDescription(String grainDescription) {
        GrainDescription = grainDescription;
    }

    public int getGrainPk() {
        return GrainPk;
    }

    public void setGrainPk(int grainPk) {
        GrainPk = grainPk;
    }

    public int getGrainTypePk() {
        return GrainTypePk;
    }

    public void setGrainTypePk(int grainTypePk) {
        GrainTypePk = grainTypePk;
    }

    public boolean isMash() {
        return Mash;
    }

    public void setMash(boolean mash) {
        Mash = mash;
    }

    public double getMaxPercentageInBatch() {
        return MaxPercentageInBatch;
    }

    public void setMaxPercentageInBatch(double maxPercentageInBatch) {
        MaxPercentageInBatch = maxPercentageInBatch;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPotentialSg() {
        return PotentialSg;
    }

    public void setPotentialSg(String potentialSg) {
        PotentialSg = potentialSg;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }
}