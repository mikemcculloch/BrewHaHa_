package brightseer.com.brewhaha.Depricated;

import android.content.SearchRecentSuggestionsProvider;

import brightseer.com.brewhaha.BuildConfig;

public class MySuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider() {
//        String test = AUTHORITY;
        setupSuggestions(AUTHORITY, MODE);
    }
}
