package brightseer.com.brewhaha.objects;

import java.io.Serializable;

/**
 * Created by Michael McCulloch on 10/26/2014.
 */

public class RecipeSummary implements Serializable {
    private String AlcoholByVol;
    private int BeerSummaryId;
    private String BitternessIbu;
    private String ColorSrm;
    private String FinalGravity;
    private String OriginalGravity;
    private int YieldByGallon;
    public String SrmHex;


    public RecipeSummary() {
    }

    public RecipeSummary(String alcoholByVol, int beerSummaryId, String bitternessIbu, String colorSrm, String finalGravity, String originalGravity, int yieldByGallon, String srmHex) {
        AlcoholByVol = alcoholByVol;
        BeerSummaryId = beerSummaryId;
        BitternessIbu = bitternessIbu;
        ColorSrm = colorSrm;
        FinalGravity = finalGravity;
        OriginalGravity = originalGravity;
        YieldByGallon = yieldByGallon;
        SrmHex = srmHex;
    }

    public String getAlcoholByVol() {
        return AlcoholByVol;
    }

    public void setAlcoholByVol(String alcoholByVol) {
        AlcoholByVol = alcoholByVol;
    }

    public int getBeerSummaryId() {
        return BeerSummaryId;
    }

    public void setBeerSummaryId(int beerSummaryId) {
        BeerSummaryId = beerSummaryId;
    }

    public String getBitternessIbu() {
        return BitternessIbu;
    }

    public void setBitternessIbu(String bitternessIbu) {
        BitternessIbu = bitternessIbu;
    }

    public String getColorSrm() {
        return ColorSrm;
    }

    public void setColorSrm(String colorSrm) {
        ColorSrm = colorSrm;
    }

    public String getFinalGravity() {
        return FinalGravity;
    }

    public void setFinalGravity(String finalGravity) {
        FinalGravity = finalGravity;
    }

    public String getOriginalGravity() {
        return OriginalGravity;
    }

    public void setOriginalGravity(String originalGravity) {
        OriginalGravity = originalGravity;
    }

    public int getYieldByGallon() {
        return YieldByGallon;
    }

    public void setYieldByGallon(int yieldByGallon) {
        YieldByGallon = yieldByGallon;
    }

    public String getSrmHex() {
        return SrmHex;
    }

    public void setSrmHex(String srmHex) {
        SrmHex = srmHex;
    }
}
