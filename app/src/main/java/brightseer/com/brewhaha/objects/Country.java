package brightseer.com.brewhaha.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import brightseer.com.brewhaha.Constants;

/**
 * Created by Michael McCulloch on 3/6/2015.
 */
@DatabaseTable(tableName = Constants.country_Table)
public class Country {
    @DatabaseField
    private String Abbreviation;
    @DatabaseField
    private int CountryPk;
    @DatabaseField
    private String Name;

    public Country() {
    }

    public Country(String abbreviation, int countryPk, String name) {
        Abbreviation = abbreviation;
        CountryPk = countryPk;
        Name = name;
    }

    public String getAbbreviation() {
        return Abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        Abbreviation = abbreviation;
    }

    public int getCountryPk() {
        return CountryPk;
    }

    public void setCountryPk(int countryPk) {
        CountryPk = countryPk;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
