package brightseer.com.brewhaha.objects;

import java.util.List;

import brightseer.com.brewhaha.models.MainFeedItem;

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
