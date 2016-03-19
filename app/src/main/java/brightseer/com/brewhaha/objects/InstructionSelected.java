package brightseer.com.brewhaha.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import brightseer.com.brewhaha.Constants;

@DatabaseTable(tableName = Constants.table_InstructionSelected)
public class InstructionSelected {
    @DatabaseField
    private String UserToken;
    @DatabaseField
    private int RecipeContentId;
    @DatabaseField
    private int InstructionsId;

    public InstructionSelected() {
    }

    public InstructionSelected(String userToken, int recipeContentId, int instructionsId) {
        UserToken = userToken;
        RecipeContentId = recipeContentId;
        InstructionsId = instructionsId;
    }

    public String getUserToken() {
        return UserToken;
    }

    public void setUserToken(String userToken) {
        UserToken = userToken;
    }

    public int getRecipeContentId() {
        return RecipeContentId;
    }

    public void setRecipeContentId(int recipeContentId) {
        RecipeContentId = recipeContentId;
    }

    public int getInstructionsId() {
        return InstructionsId;
    }

    public void setInstructionsId(int instructionsId) {
        InstructionsId = instructionsId;
    }
}
