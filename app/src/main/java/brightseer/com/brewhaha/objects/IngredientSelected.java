package brightseer.com.brewhaha.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import brightseer.com.brewhaha.Constants;

@DatabaseTable(tableName = Constants.table_IngredientSelected)
public class IngredientSelected {
    @DatabaseField
    private String UserToken;
    @DatabaseField
    private int ContentItemPk;
    @DatabaseField
    private int IngredientId;
    @DatabaseField
    private int Type;

    public IngredientSelected() {
    }

    public IngredientSelected(String userToken, int contentItemPk, int ingredientId, int type) {
        UserToken = userToken;
        ContentItemPk = contentItemPk;
        IngredientId = ingredientId;
        Type = type;
    }

    public String getUserToken() {
        return UserToken;
    }

    public void setUserToken(String userToken) {
        UserToken = userToken;
    }

    public int getContentItemPk() {
        return ContentItemPk;
    }

    public void setContentItemPk(int contentItemPk) {
        ContentItemPk = contentItemPk;
    }

    public int getIngredientId() {
        return IngredientId;
    }

    public void setIngredientId(int ingredientId) {
        IngredientId = ingredientId;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }
}
