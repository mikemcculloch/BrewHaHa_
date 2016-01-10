package brightseer.com.brewhaha.objects;

/**
 * Created by mmcculloch on 9/26/2014.
 */
public class NavDrawerItem {

    private String title;
    private int icon;
    private String count = "0";
    private String imageUrl;
    private String userName;

    // boolean to set visiblity of the counter
    private boolean isCounterVisible = false;

    public NavDrawerItem() {
    }

    public NavDrawerItem(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public NavDrawerItem(String title, int icon, boolean isCounterVisible, String count) {
        this.title = title;
        this.icon = icon;
        this.isCounterVisible = isCounterVisible;
        this.count = count;
    }

    public NavDrawerItem(String title, int icon, String count, String imageUrl, String userName, boolean isCounterVisible) {
        this.title = title;
        this.icon = icon;
        this.count = count;
        this.imageUrl = imageUrl;
        this.userName = userName;
        this.isCounterVisible = isCounterVisible;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isCounterVisible() {
        return isCounterVisible;
    }

    public void setCounterVisible(boolean isCounterVisible) {
        this.isCounterVisible = isCounterVisible;
    }

    public boolean getCounterVisibility() {
        return this.isCounterVisible;
    }
}