package brightseer.com.brewhaha.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import brightseer.com.brewhaha.Constants;

@DatabaseTable(tableName = Constants.table_IngredientSelected)
public class IngredientSelected {
    @DatabaseField
    private String UserKey;
    @DatabaseField
    private String FeedKey;
    @DatabaseField
    private String Key;

    public IngredientSelected() {
    }

    public IngredientSelected(String userKey, String feedKey, String key) {
        UserKey = userKey;
        FeedKey = feedKey;
        Key = key;
    }

    public String getUserKey() {
        return UserKey;
    }

    public void setUserKey(String userKey) {
        UserKey = userKey;
    }

    public String getFeedKey() {
        return FeedKey;
    }

    public void setFeedKey(String feedKey) {
        FeedKey = feedKey;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }
}
