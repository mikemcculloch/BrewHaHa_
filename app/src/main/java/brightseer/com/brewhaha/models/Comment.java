package brightseer.com.brewhaha.models;

import java.io.Serializable;

/**
 * Created by mccul_000 on 10/26/2014.
 */
public class Comment implements Serializable {
    private String FeedKey;
    private String AuthorName;
    private String AuthorImageUrl;
    private String Body;
    private String DateCreated;
    private Long DateCreatedInverse;
    private String Key;
    private String AuthorEmail;

    public Comment() {
    }

    public Comment(String feedKey, String authorName, String authorImageUrl, String body, String dateCreated, Long dateCreatedInverse, String key, String authorEmail) {
        FeedKey = feedKey;
        AuthorName = authorName;
        AuthorImageUrl = authorImageUrl;
        Body = body;
        DateCreated = dateCreated;
        DateCreatedInverse = dateCreatedInverse;
        Key = key;
        AuthorEmail = authorEmail;
    }

    public String getFeedKey() {
        return FeedKey;
    }

    public void setFeedKey(String feedKey) {
        FeedKey = feedKey;
    }

    public String getAuthorName() {
        return AuthorName;
    }

    public void setAuthorName(String authorName) {
        AuthorName = authorName;
    }

    public String getAuthorImageUrl() {
        return AuthorImageUrl;
    }

    public void setAuthorImageUrl(String authorImageUrl) {
        AuthorImageUrl = authorImageUrl;
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

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getAuthorEmail() {
        return AuthorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        AuthorEmail = authorEmail;
    }

    public Long getDateCreatedInverse() {
        return DateCreatedInverse;
    }

    public void setDateCreatedInverse(Long dateCreatedInverse) {
        DateCreatedInverse = dateCreatedInverse;
    }
}
