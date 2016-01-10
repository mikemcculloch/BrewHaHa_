package brightseer.com.brewhaha.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import brightseer.com.brewhaha.Constants;

@DatabaseTable(tableName = Constants.table_InstructionSelected)
public class InstructionSelected {
    @DatabaseField
    private String UserToken;
    @DatabaseField
    private int ContentItemPk;
    @DatabaseField
    private int InstructionsId;

    public InstructionSelected() {
    }

    public InstructionSelected(String userToken, int contentItemPk, int instructionsId) {
        UserToken = userToken;
        ContentItemPk = contentItemPk;
        InstructionsId = instructionsId;
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

    public int getInstructionsId() {
        return InstructionsId;
    }

    public void setInstructionsId(int instructionsId) {
        InstructionsId = instructionsId;
    }
}
