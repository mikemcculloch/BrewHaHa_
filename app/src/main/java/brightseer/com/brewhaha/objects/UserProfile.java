package brightseer.com.brewhaha.objects;

import java.io.Serializable;

/**
 * Created by mccul_000 on 11/18/2014.
 */
public class UserProfile implements Serializable {
    private String Key;
    private String EmailAddress;
    private String ImageUrl;
    private String ScreenName;
    private String DateCreated;
    private boolean Admin;

    public UserProfile() {
    }

    public UserProfile(String key, String emailAddress, String imageUrl, String screenName, String dateCreated, boolean admin) {
        Key = key;
        EmailAddress = emailAddress;
        ImageUrl = imageUrl;
        ScreenName = screenName;
        DateCreated = dateCreated;
        Admin = admin;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
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

    public String getScreenName() {
        return ScreenName;
    }

    public void setScreenName(String screenName) {
        ScreenName = screenName;
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




