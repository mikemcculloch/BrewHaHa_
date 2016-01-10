package brightseer.com.brewhaha.objects;

/**
 * Created by mccul_000 on 11/18/2014.
 */
public class UserProfile {
    private String FirstName;
    private String LastName;
    private String FullName;
    private String EmailAddress;
    private String ImageUrl;
    private Boolean Disclaimer;
    private String ScreenName;
    private String Type;
    private String Token;
    private String SignupDate;

    public UserProfile() {
    }

    public UserProfile(String firstName, String lastName, String fullName, String emailAddress, String imageUrl, Boolean disclaimer, String screenName, String type, String token, String signupDate) {
        FirstName = firstName;
        LastName = lastName;
        FullName = fullName;
        EmailAddress = emailAddress;
        ImageUrl = imageUrl;
        Disclaimer = disclaimer;
        ScreenName = screenName;
        Type = type;
        Token = token;
        SignupDate = signupDate;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
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

    public Boolean getDisclaimer() {
        return Disclaimer;
    }

    public void setDisclaimer(Boolean disclaimer) {
        Disclaimer = disclaimer;
    }

    public String getScreenName() {
        return ScreenName;
    }

    public void setScreenName(String screenName) {
        ScreenName = screenName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getSignupDate() {
        return SignupDate;
    }

    public void setSignupDate(String signupDate) {
        SignupDate = signupDate;
    }
}




