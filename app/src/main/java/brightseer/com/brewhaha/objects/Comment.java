package brightseer.com.brewhaha.objects;

import java.io.Serializable;

/**
 * Created by mccul_000 on 10/26/2014.
 */
public class Comment  implements Serializable {
    private int CommentPk;
    private int ContentItemPk;
    private String AuthorName;
    private String ImageUrl;
    private String Body;
    private int RatingValue;
    private String RatingDescription;
    private String Timestamp;
    private String UserToken;

    public Comment() {
    }

    public Comment(int commentPk, int contentItemPk, String authorName, String imageUrl, String body, int ratingValue, String ratingDescription, String timestamp, String userToken) {
        CommentPk = commentPk;
        ContentItemPk = contentItemPk;
        AuthorName = authorName;
        ImageUrl = imageUrl;
        Body = body;
        RatingValue = ratingValue;
        RatingDescription = ratingDescription;
        Timestamp = timestamp;
        UserToken = userToken;
    }

    public int getCommentPk() {
        return CommentPk;
    }

    public void setCommentPk(int commentPk) {
        CommentPk = commentPk;
    }

    public int getContentItemPk() {
        return ContentItemPk;
    }

    public void setContentItemPk(int contentItemPk) {
        ContentItemPk = contentItemPk;
    }

    public String getAuthorName() {
        return AuthorName;
    }

    public void setAuthorName(String authorName) {
        AuthorName = authorName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public int getRatingValue() {
        return RatingValue;
    }

    public void setRatingValue(int ratingValue) {
        RatingValue = ratingValue;
    }

    public String getRatingDescription() {
        return RatingDescription;
    }

    public void setRatingDescription(String ratingDescription) {
        RatingDescription = ratingDescription;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public String getUserToken() {
        return UserToken;
    }

    public void setUserToken(String userToken) {
        UserToken = userToken;
    }
}
