package brightseer.com.brewhaha.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import brightseer.com.brewhaha.Constants;

/**
 * Created by Michael McCulloch on 3/6/2015.
 */
@DatabaseTable(tableName = Constants.laboratory_Table)
public class Laboratory {
    @DatabaseField
    private String Name;
    @DatabaseField
    private int LaboratoryPk;

    public Laboratory() {
    }

    public Laboratory(String name, int laboratoryPk) {
        Name = name;
        LaboratoryPk = laboratoryPk;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getLaboratoryPk() {
        return LaboratoryPk;
    }

    public void setLaboratoryPk(int laboratoryPk) {
        LaboratoryPk = laboratoryPk;
    }
}
