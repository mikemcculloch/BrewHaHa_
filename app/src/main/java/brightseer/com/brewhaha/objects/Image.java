package brightseer.com.brewhaha.objects;

/**
 * Created by mccul_000 on 10/26/2014.
 */
public class Image {
    private int ContentItemPk;
    private String ImageName;
    private int ImagePk;
    private String ImageUrl;
    //    private Date Timestamp;
    private String Caption;

    public Image() {
    }

    public Image(int contentItemPk, String imageName, int imagePk, String imageUrl, String caption) {
        ContentItemPk = contentItemPk;
        ImageName = imageName;
        ImagePk = imagePk;
        ImageUrl = imageUrl;
//        Timestamp = timestamp;
        Caption = caption;
    }

    public int getContentItemPk() {
        return ContentItemPk;
    }

    public void setContentItemPk(int contentItemPk) {
        ContentItemPk = contentItemPk;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public int getImagePk() {
        return ImagePk;
    }

    public void setImagePk(int imagePk) {
        ImagePk = imagePk;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

//    public Date getTimestamp() {
//        return Timestamp;
//    }
//
//    public void setTimestamp(Date timestamp) {
//        Timestamp = timestamp;
//    }

    public String getCaption() {
        return Caption;
    }

    public void setCaption(String caption) {
        Caption = caption;
    }
}
