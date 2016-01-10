package brightseer.com.brewhaha.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import brightseer.com.brewhaha.Constants;

/**
 * Created by Michael McCulloch on 3/6/2015.
 */
@DatabaseTable(tableName = Constants.srmColorKey_Table)
public class SrmColorKey {
    @DatabaseField
    private String HexColor;
    @DatabaseField
    private int ColorSrm;
    @DatabaseField
    private int SrmColorKeyPk;

    public SrmColorKey() {
    }

    public SrmColorKey(String hexColor, int colorSrm, int srmColorKeyPk) {
        HexColor = hexColor;
        ColorSrm = colorSrm;
        SrmColorKeyPk = srmColorKeyPk;
    }

    public String getHexColor() {
        return HexColor;
    }

    public void setHexColor(String hexColor) {
        HexColor = hexColor;
    }

    public int getColorSrm() {
        return ColorSrm;
    }

    public void setColorSrm(int colorSrm) {
        ColorSrm = colorSrm;
    }

    public int getSrmColorKeyPk() {
        return SrmColorKeyPk;
    }

    public void setSrmColorKeyPk(int srmColorKeyPk) {
        SrmColorKeyPk = srmColorKeyPk;
    }
}
