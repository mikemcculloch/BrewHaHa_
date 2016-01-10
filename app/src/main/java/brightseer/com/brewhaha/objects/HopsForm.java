package brightseer.com.brewhaha.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import brightseer.com.brewhaha.Constants;

/**
 * Created by Michael McCulloch on 3/6/2015.
 */

@DatabaseTable(tableName = Constants.hopsForm_Table)
public class HopsForm {
    @DatabaseField
    private String Description;
    @DatabaseField
    private int HopsFormPk;

    public HopsForm() {
    }

    public HopsForm(String description, int hopsFormPk) {
        Description = description;
        HopsFormPk = hopsFormPk;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getHopsFormPk() {
        return HopsFormPk;
    }

    public void setHopsFormPk(int hopsFormPk) {
        HopsFormPk = hopsFormPk;
    }
}
