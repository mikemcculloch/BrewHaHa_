package brightseer.com.brewhaha.models;

import java.io.Serializable;

/**
 * Created by mccul_000 on 11/23/2014.
 */
public class RecipeInstruction implements Serializable {
    private String Key;
    private String Description;
    private int Order;
    private boolean Active;

    public RecipeInstruction() {
    }

    public RecipeInstruction(String key, String userProfileKey, String description, int order, boolean active) {
        Key = key;
        Description = description;
        Order = order;
        Active = active;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getOrder() {
        return Order;
    }

    public void setOrder(int order) {
        Order = order;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }
}
