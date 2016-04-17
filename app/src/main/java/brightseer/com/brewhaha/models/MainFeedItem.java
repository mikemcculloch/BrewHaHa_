package brightseer.com.brewhaha.models;

public class MainFeedItem {
    private String Key;
    private String Title;
    private String Author;
    private String ImageUrl;
    private String UserImageUrl;
    private String DateCreated;
    private String UserKey;

    public MainFeedItem() {

    }

    public MainFeedItem(String key, String title, String author, String imageUrl, String userImageUrl, String dateCreated, String userKey) {
        Key = key;
        Title = title;
        Author = author;
        ImageUrl = imageUrl;
        UserImageUrl = userImageUrl;
        DateCreated = dateCreated;
        UserKey = userKey;
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

    public String getUserKey() {
        return UserKey;
    }

    public void setUserKey(String userKey) {
        UserKey = userKey;
    }
}
