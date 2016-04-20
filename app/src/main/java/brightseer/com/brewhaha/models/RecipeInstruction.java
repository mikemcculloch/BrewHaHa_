package brightseer.com.brewhaha.models;

import java.io.Serializable;

/**
 * Created by mccul_000 on 11/23/2014.
 */
public class RecipeInstruction implements Serializable {
    private String Key;
    private String FeedKey;
    private String UserProfileKey;
    private String Description;
    private int Order;
    private boolean Active;

    public RecipeInstruction() {
    }

    public RecipeInstruction(String key, String feedKey, String userProfileKey, String description, int order, boolean active) {
        Key = key;
        FeedKey = feedKey;
        UserProfileKey = userProfileKey;
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

    public String getFeedKey() {
        return FeedKey;
    }

    public void setFeedKey(String feedKey) {
        FeedKey = feedKey;
    }

    public String getUserProfileKey() {
        return UserProfileKey;
    }

    public void setUserProfileKey(String userProfileKey) {
        UserProfileKey = userProfileKey;
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
