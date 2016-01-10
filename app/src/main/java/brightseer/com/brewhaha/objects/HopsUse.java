package brightseer.com.brewhaha.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import brightseer.com.brewhaha.Constants;

/**
 * Created by Michael McCulloch on 3/6/2015.
 */
@DatabaseTable(tableName = Constants.hopsUse_Table)
public class HopsUse {
    @DatabaseField
    private String Description;
    @DatabaseField
    private int HopsUsePk;

    public HopsUse() {
    }

    public HopsUse(String description, int hopsUsePk) {
        Description = description;
        HopsUsePk = hopsUsePk;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getHopsUsePk() {
        return HopsUsePk;
    }

    public void setHopsUsePk(int hopsUsePk) {
        HopsUsePk = hopsUsePk;
    }
}
