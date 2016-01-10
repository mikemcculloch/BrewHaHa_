package brightseer.com.brewhaha.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

import brightseer.com.brewhaha.Constants;

@DatabaseTable(tableName = Constants.difficulty_TableName)
public class Difficulty {
    @DatabaseField
    private int DifficultyPk;
    @DatabaseField
    private String Description;
    @DatabaseField
    private int Value;
    @DatabaseField
    private Date Timestamp;

    public Difficulty() {
    }

    public Difficulty(int difficultyPk, String description, int value, Date timestamp) {
        DifficultyPk = difficultyPk;
        Description = description;
        Value = value;
        Timestamp = timestamp;
    }

    public int getDifficultyPk() {
        return DifficultyPk;
    }

    public void setDifficultyPk(int difficultyPk) {
        DifficultyPk = difficultyPk;
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