package brightseer.com.brewhaha.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import brightseer.com.brewhaha.Constants;

/**
 * Created by Michael McCulloch on 3/6/2015.
 */
@DatabaseTable(tableName = Constants.grainUse_Table)
public class GrainUse {
    @DatabaseField
    private String Description;
    @DatabaseField
    private int GrainUsePk;

    public GrainUse() {
    }

    public GrainUse(String description, int grainUsePk) {
        Description = description;
        GrainUsePk = grainUsePk;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getGrainUsePk() {
        return GrainUsePk;
    }

    public void setGrainUsePk(int grainUsePk) {
        GrainUsePk = grainUsePk;
    }
}
