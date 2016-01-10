package brightseer.com.brewhaha.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import brightseer.com.brewhaha.Constants;

/**
 * Created by wooan_000 on 1/6/2015.
 */
@DatabaseTable(tableName = Constants.yeast_TableName)
public class Yeast {
    @DatabaseField
    private int YeastPk;
    @DatabaseField
    private boolean Active;
    @DatabaseField
    private String AlcoholTolerance;
    @DatabaseField
    private String ApparantAttenuationPercentageRange;
    @DatabaseField
    private String FermentationTempRangeF;
    @DatabaseField
    private String Flocculation;
    @DatabaseField
    private String Laboratory;
    @DatabaseField
    private String Name;
    @DatabaseField
    private String Strain;
    @DatabaseField
    private String Timestamp;
    @DatabaseField
    private int YeastSpeciesPk;
    @DatabaseField
    private String YeastSpecies;

    public Yeast() {
    }

    public Yeast(int yeastPk, boolean active, String alcoholTolerance, String apparantAttenuationPercentageRange, String fermentationTempRangeF, String flocculation, String laboratory, String name, String strain, String timestamp, int yeastSpeciesPk, String yeastSpecies) {
        YeastPk = yeastPk;
        Active = active;
        AlcoholTolerance = alcoholTolerance;
        ApparantAttenuationPercentageRange = apparantAttenuationPercentageRange;
        FermentationTempRangeF = fermentationTempRangeF;
        Flocculation = flocculation;
        Laboratory = laboratory;
        Name = name;
        Strain = strain;
        Timestamp = timestamp;
        YeastSpeciesPk = yeastSpeciesPk;
        YeastSpecies = yeastSpecies;
    }

    public int getYeastPk() {
        return YeastPk;
    }

    public void setYeastPk(int yeastPk) {
        YeastPk = yeastPk;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    public String getAlcoholTolerance() {
        return AlcoholTolerance;
    }

    public void setAlcoholTolerance(String alcoholTolerance) {
        AlcoholTolerance = alcoholTolerance;
    }

    public String getApparantAttenuationPercentageRange() {
        return ApparantAttenuationPercentageRange;
    }

    public void setApparantAttenuationPercentageRange(String apparantAttenuationPercentageRange) {
        ApparantAttenuationPercentageRange = apparantAttenuationPercentageRange;
    }

    public String getFermentationTempRangeF() {
        return FermentationTempRangeF;
    }

    public void setFermentationTempRangeF(String fermentationTempRangeF) {
        FermentationTempRangeF = fermentationTempRangeF;
    }

    public String getFlocculation() {
        return Flocculation;
    }

    public void setFlocculation(String flocculation) {
        Flocculation = flocculation;
    }

    public String getLaboratory() {
        return Laboratory;
    }

    public void setLaboratory(String laboratory) {
        Laboratory = laboratory;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStrain() {
        return Strain;
    }

    public void setStrain(String strain) {
        Strain = strain;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public int getYeastSpeciesPk() {
        return YeastSpeciesPk;
    }

    public void setYeastSpeciesPk(int yeastSpeciesPk) {
        YeastSpeciesPk = yeastSpeciesPk;
    }

    public String getYeastSpecies() {
        return YeastSpecies;
    }

    public void setYeastSpecies(String yeastSpecies) {
        YeastSpecies = yeastSpecies;
    }
}