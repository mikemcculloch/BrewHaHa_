package brightseer.com.brewhaha.objects;

import java.io.Serializable;

/**
 * Created by mccul_000 on 10/26/2014.
 */
public class Comment implements Serializable {
    private int CommentId;
    private int RecipeContentId;

    private String AuthorName;
    private String ImageUrl;
    private String Body;
    private String DateCreated;
    private String UserToken;
    private String Key;

    public Comment() {
    }

    public Comment(int commentId, int recipeContentId, String authorName, String imageUrl, String body, String dateCreated, String userToken, String key) {
        CommentId = commentId;
        RecipeContentId = recipeContentId;
        AuthorName = authorName;
        ImageUrl = imageUrl;
        Body = body;
        DateCreated = dateCreated;
        UserToken = userToken;
        Key = key;
    }

    public int getCommentId() {
        return CommentId;
    }

    public void setCommentId(int commentId) {
        CommentId = commentId;
    }

    public int getRecipeContentId() {
        return RecipeContentId;
    }

    public void setRecipeContentId(int recipeContentId) {
        RecipeContentId = recipeContentId;
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

    public String getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(String dateCreated) {
        DateCreated = dateCreated;
    }

    public String getUserToken() {
        return UserToken;
    }

    public void setUserToken(String userToken) {
        UserToken = userToken;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }
}
