package brightseer.com.brewhaha.objects;

/**
 * Created by mccul_000 on 10/26/2014.
 */
public class Ingredient {
    private int IngredientId;
    private String Type;
    private int ContentItemPk;
    private String Description;
    private String HexColor;
    private String Timestamp;

    public Ingredient() {
    }

    public Ingredient(int ingredientId, String type, int contentItemPk, String description, String hexColor, String timestamp) {
        IngredientId = ingredientId;
        Type = type;
        ContentItemPk = contentItemPk;
        Description = description;
        HexColor = hexColor;
        Timestamp = timestamp;
    }

    public int getIngredientId() {
        return IngredientId;
    }

    public void setIngredientId(int ingredientId) {
        IngredientId = ingredientId;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getContentItemPk() {
        return ContentItemPk;
    }

    public void setContentItemPk(int contentItemPk) {
        ContentItemPk = contentItemPk;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getHexColor() {
        return HexColor;
    }

    public void setHexColor(String hexColor) {
        HexColor = hexColor;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }
}