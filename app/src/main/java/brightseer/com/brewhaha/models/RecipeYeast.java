package brightseer.com.brewhaha.models;

import java.io.Serializable;

/**
 * Created by Michael McCulloch on 3/2/2015.
 */
public class RecipeYeast  implements Serializable {
    private String Key;
    private String Name;
    private String Laboratory;
    private double AttenuationPercentage;

    public RecipeYeast() {
    }

    public RecipeYeast(String key, String name, String laboratory, double attenuationPercentage) {
        Key = key;
        Name = name;
        Laboratory = laboratory;
        AttenuationPercentage = attenuationPercentage;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLaboratory() {
        return Laboratory;
    }

    public void setLaboratory(String laboratory) {
        Laboratory = laboratory;
    }

    public double getAttenuationPercentage() {
        return AttenuationPercentage;
    }

    public void setAttenuationPercentage(double attenuationPercentage) {
        AttenuationPercentage = attenuationPercentage;
    }
}
