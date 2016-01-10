package brightseer.com.brewhaha.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

import brightseer.com.brewhaha.Constants;

@DatabaseTable(tableName = Constants.style_Table)
public class Style {
    @DatabaseField
    private int StylePk;
    @DatabaseField
    private String Description;
    @DatabaseField
    private Date Timestamp;

    public Style() {
    }

    public Style(int stylePk, String description, Date timestamp) {
        StylePk = stylePk;
        Description = description;
        Timestamp = timestamp;
    }

    public int getStylePk() {
        return StylePk;
    }

    public void setStylePk(int stylePk) {
        StylePk = stylePk;
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
