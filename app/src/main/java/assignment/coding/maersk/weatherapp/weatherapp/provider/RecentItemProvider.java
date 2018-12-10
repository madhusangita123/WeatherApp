package assignment.coding.maersk.weatherapp.weatherapp.provider;

import android.content.SearchRecentSuggestionsProvider;

public class RecentItemProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "assignment.coding.maersk.weatherapp.weatherapp.provider.RecentItemProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public RecentItemProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
