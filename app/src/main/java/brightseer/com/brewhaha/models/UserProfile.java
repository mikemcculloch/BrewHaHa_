package brightseer.com.brewhaha.models;

import java.io.Serializable;

/**
 * Created by mccul_000 on 11/18/2014.
 */
public class UserProfile implements Serializable {
    private String Key;
    private String Uid;
    private String EmailAddress;
    private String ImageUrl;
    private String DisplayName;
    private String DateCreated;
    private boolean Admin; //GET ONLY

    public UserProfile() {
    }

    public UserProfile(String key, String uid, String emailAddress, String imageUrl, String displayName, String dateCreated, boolean admin) {
        Key = key;
        Uid = uid;
        EmailAddress = emailAddress;
        ImageUrl = imageUrl;
        DisplayName = displayName;
        DateCreated = dateCreated;
        Admin = admin;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(String dateCreated) {
        DateCreated = dateCreated;
    }

    public boolean isAdmin() {
        return Admin;
    }

    public void setAdmin(boolean admin) {
        Admin = admin;
    }
}




