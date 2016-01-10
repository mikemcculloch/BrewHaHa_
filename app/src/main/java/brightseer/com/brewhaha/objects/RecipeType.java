package brightseer.com.brewhaha.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

import brightseer.com.brewhaha.Constants;

@DatabaseTable(tableName = Constants.recipeType_TableName)
public class RecipeType {
    @DatabaseField
    private int RecipeTypePk;
    @DatabaseField
    private String Description;
    @DatabaseField
    private Date Timestamp;

    public RecipeType() {
    }

    public RecipeType(int recipeTypePk, String description, Date timestamp) {
        RecipeTypePk = recipeTypePk;
        Description = description;
        Timestamp = timestamp;
    }

    public int getRecipeTypePk() {
        return RecipeTypePk;
    }

    public void setRecipeTypePk(int recipeTypePk) {
        RecipeTypePk = recipeTypePk;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Date getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(Date timestamp) {
        Timestamp = timestamp;
    }
}
