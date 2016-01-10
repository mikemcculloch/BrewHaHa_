package brightseer.com.brewhaha.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import brightseer.com.brewhaha.Constants;

/**
 * Created by wooan_000 on 1/6/2015.
 */
@DatabaseTable(tableName = Constants.hops_TableName)
public class Hops {
    @DatabaseField
    private boolean Active;
    @DatabaseField
    private double AlphaAcidPercentage;
    @DatabaseField
    private String Aroma;
    @DatabaseField
    private String BetaAcidPercentage;
    @DatabaseField
    private String CohumulonePercentage;
    @DatabaseField
    private String Description;
    @DatabaseField
    private int HopsPk;
    @DatabaseField
    private String Name;
    @DatabaseField
    private String TotalOil;
    @DatabaseField
    private String Timestamp;

    public Hops() {
    }

    public Hops(boolean active, double alphaAcidPercentage, String aroma, String betaAcidPercentage, String cohumulonePercentage, String description, int hopsPk, String name, String totalOil, String timestamp) {
        Active = active;
        AlphaAcidPercentage = alphaAcidPercentage;
        Aroma = aroma;
        BetaAcidPercentage = betaAcidPercentage;
        CohumulonePercentage = cohumulonePercentage;
        Description = description;
        HopsPk = hopsPk;
        Name = name;
        TotalOil = totalOil;
        Timestamp = timestamp;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    public double getAlphaAcidPercentage() {
        return AlphaAcidPercentage;
    }

    public void setAlphaAcidPercentage(double alphaAcidPercentage) {
        AlphaAcidPercentage = alphaAcidPercentage;
    }

    public String getAroma() {
        return Aroma;
    }

    public void setAroma(String aroma) {
        Aroma = aroma;
    }

    public String getBetaAcidPercentage() {
        return BetaAcidPercentage;
    }

    public void setBetaAcidPercentage(String betaAcidPercentage) {
        BetaAcidPercentage = betaAcidPercentage;
    }

    public String getCohumulonePercentage() {
        return CohumulonePercentage;
    }

    public void setCohumulonePercentage(String cohumulonePercentage) {
        CohumulonePercentage = cohumulonePercentage;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getHopsPk() {
        return HopsPk;
    }

    public void setHopsPk(int hopsPk) {
        HopsPk = hopsPk;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTotalOil() {
        return TotalOil;
    }

    public void setTotalOil(String totalOil) {
        TotalOil = totalOil;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }
}