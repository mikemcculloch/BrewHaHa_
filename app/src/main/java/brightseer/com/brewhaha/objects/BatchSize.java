package brightseer.com.brewhaha.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

import brightseer.com.brewhaha.Constants;

@DatabaseTable(tableName = Constants.batchSize_TableName)
public class BatchSize {

    @DatabaseField
    private int BatchSizePk;
    @DatabaseField
    private String Description;
    @DatabaseField
    private int Value;
    @DatabaseField
    private Date Timestamp;

    public BatchSize() {
    }

    public BatchSize(int batchSizePk, String description, int value, Date timestamp) {
        BatchSizePk = batchSizePk;
        Description = description;
        Value = value;
        Timestamp = timestamp;
    }

    public int getBatchSizePk() {
        return BatchSizePk;
    }

    public void setBatchSizePk(int batchSizePk) {
        BatchSizePk = batchSizePk;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getValue() {
        return Value;
    }

    public void setValue(int value) {
        Value = value;
    }

    public Date getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(Date timestamp) {
        Timestamp = timestamp;
    }
}
