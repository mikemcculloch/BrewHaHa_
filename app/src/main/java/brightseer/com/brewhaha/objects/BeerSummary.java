package brightseer.com.brewhaha.objects;

/**
 * Created by Michael McCulloch on 10/26/2014.
 */
public class BeerSummary {
    private String AlcoholByVol;
    private int BeerSummaryId;
    private String BitternessIbu;
    private String ColorSrm;
    private String FinalGravity;
    private String OriginalGravity;
    private int YieldByGallon;

    public BeerSummary() {
    }

    public BeerSummary(String alcoholByVol, int beerSummaryId, String bitternessIbu, String colorSrm, String finalGravity, String originalGravity, int yieldByGallon) {
        AlcoholByVol = alcoholByVol;
        BeerSummaryId = beerSummaryId;
        BitternessIbu = bitternessIbu;
        ColorSrm = colorSrm;
        FinalGravity = finalGravity;
        OriginalGravity = originalGravity;
        YieldByGallon = yieldByGallon;
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
}
