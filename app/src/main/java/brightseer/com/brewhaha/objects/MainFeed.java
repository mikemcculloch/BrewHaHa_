package brightseer.com.brewhaha.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

import brightseer.com.brewhaha.Constants;

public class MainFeed {
    private List<MainFeedItem> MainFeedItems;

    public MainFeed() {
    }

    public MainFeed(List<MainFeedItem> mainFeedItems) {
        MainFeedItems = mainFeedItems;
    }

    public List<MainFeedItem> getMainFeedItems() {
        return MainFeedItems;
    }

    public void setMainFeedItems(List<MainFeedItem> mainFeedItems) {
        MainFeedItems = mainFeedItems;
    }
}
