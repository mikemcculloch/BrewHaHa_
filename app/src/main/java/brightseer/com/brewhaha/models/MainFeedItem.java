package brightseer.com.brewhaha.models;

public class MainFeedItem {
    private String Key;
    private String Title;
    private String Author;
    private String ImageUrl;
    private String UserImageUrl;
    private String DateCreated;
    private String Style;
    private String CloneKey;
    private String SharedKey;

    public MainFeedItem() {

    }

    public MainFeedItem(String key, String title, String author, String imageUrl, String userImageUrl, String dateCreated, String style, String cloneKey, String sharedKey) {
        Key = key;
        Title = title;
        Author = author;
        ImageUrl = imageUrl;
        UserImageUrl = userImageUrl;
        DateCreated = dateCreated;
        Style = style;
        CloneKey = cloneKey;
        SharedKey = sharedKey;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getUserImageUrl() {
        return UserImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        UserImageUrl = userImageUrl;
    }

    public String getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(String dateCreated) {
        DateCreated = dateCreated;
    }

    public String getStyle() {
        return Style;
    }

    public void setStyle(String style) {
        Style = style;
    }

    public String getCloneKey() {
        return CloneKey;
    }

    public void setCloneKey(String cloneKey) {
        CloneKey = cloneKey;
    }

    public String getSharedKey() {
        return SharedKey;
    }

    public void setSharedKey(String sharedKey) {
        SharedKey = sharedKey;
    }
}
