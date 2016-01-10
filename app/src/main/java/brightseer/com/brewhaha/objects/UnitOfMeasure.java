package brightseer.com.brewhaha.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import brightseer.com.brewhaha.Constants;

/**
 * Created by Michael McCulloch on 3/5/2015.
 */
@DatabaseTable(tableName = Constants.unitOfMeasure_Table)
public class UnitOfMeasure {
    @DatabaseField
    private String Description;
    @DatabaseField
    private int Type;
    @DatabaseField
    private String TypeDescription;
    @DatabaseField
    private int UnitOfMeasurePk;

    public UnitOfMeasure() {
    }

    public UnitOfMeasure(String description, int type, String typeDescription, int unitOfMeasurePk) {
        Description = description;
        Type = type;
        TypeDescription = typeDescription;
        UnitOfMeasurePk = unitOfMeasurePk;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getTypeDescription() {
        return TypeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        TypeDescription = typeDescription;
    }

    public int getUnitOfMeasurePk() {
        return UnitOfMeasurePk;
    }

    public void setUnitOfMeasurePk(int unitOfMeasurePk) {
        UnitOfMeasurePk = unitOfMeasurePk;
    }
}
