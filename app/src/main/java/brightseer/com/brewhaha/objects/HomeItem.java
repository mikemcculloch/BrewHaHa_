package brightseer.com.brewhaha.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import brightseer.com.brewhaha.Constants;

@DatabaseTable(tableName = Constants.table_HomeItem)
public class HomeItem {
    @DatabaseField
    private int ContentItemPk;
    @DatabaseField
    private String Title;
    @DatabaseField
    private String Author;
    @DatabaseField
    private String ImageUrl;
    @DatabaseField
    private String UserImageUrl;
    @DatabaseField
    private String Timestamp;
    @DatabaseField
    private int ItemTypePk;
    @DatabaseField
    private boolean Favorite;
    @DatabaseField
    private String Token;
    @DatabaseField
    private boolean Submitted;
    @DatabaseField
    private boolean Approved;

    public HomeItem() {

    }

    public HomeItem(int contentItemPk, String title, String author, String imageUrl, String userImageUrl, String timestamp, int itemTypePk, boolean favorite, String token, boolean submitted, boolean approved) {
        ContentItemPk = contentItemPk;
        Title = title;
        Author = author;
        ImageUrl = imageUrl;
        UserImageUrl = userImageUrl;
        Timestamp = timestamp;
        ItemTypePk = itemTypePk;
        Favorite = favorite;
        Token = token;
        Submitted = submitted;
        Approved = approved;
    }

    public int getContentItemPk() {
        return ContentItemPk;
    }

    public void setContentItemPk(int contentItemPk) {
        ContentItemPk = contentItemPk;
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

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public int getItemTypePk() {
        return ItemTypePk;
    }

    public void setItemTypePk(int itemTypePk) {
        ItemTypePk = itemTypePk;
    }

    public boolean isFavorite() {
        return Favorite;
    }

    public void setFavorite(boolean favorite) {
        Favorite = favorite;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public boolean isSubmitted() {
        return Submitted;
    }

    public void setSubmitted(boolean submitted) {
        Submitted = submitted;
    }

    public boolean isApproved() {
        return Approved;
    }

    public void setApproved(boolean approved) {
        Approved = approved;
    }
}
