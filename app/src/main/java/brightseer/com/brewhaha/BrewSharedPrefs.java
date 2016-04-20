package brightseer.com.brewhaha;

import android.content.Context;
import android.content.SharedPreferences;

import org.joda.time.DateTime;

public class BrewSharedPrefs {
    public static void clearAllPrefs() {
        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }



//    public static void setIsUserLoggedIn(boolean loggedIn) {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putBoolean(Constants.spLoggedIn, loggedIn);
//        editor.apply();
//    }
//
//    public static boolean getIsUserLoggedIn() {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        return preferences.getBoolean(Constants.spLoggedIn, false);
//    }
//
//    public static void setUserToken(String token) {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(Constants.spUserToken, token);
//        editor.apply();
//    }
//
//    public static String getUserToken() {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        return preferences.getString(Constants.spUserToken, "na");
//    }
//
//    public static void setShowWelcome(Boolean showWelcome) {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putBoolean(Constants.spShowWelcome, showWelcome);
//        editor.apply();
//    }
//
//    public static boolean getShowWelcome() {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        return preferences.getBoolean(Constants.spShowWelcome, false);
//    }
//
//    public static void setScreenName(String screenName) {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(Constants.spScreenName, screenName);
//        editor.apply();
//    }
//
//    public static String getScreenName() {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        return preferences.getString(Constants.spScreenName, "");
//    }
//
//    public static void setCurrentToken(String contentToken) {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(Constants.spContentToken, contentToken);
//        editor.apply();
//    }
//
//    public static String getCurrentToken() {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        return preferences.getString(Constants.spContentToken, "");
//    }
//
//
//    public static void setCurrentContentTitle(String contentTitle) {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(Constants.spContentTitle, contentTitle);
//        editor.apply();
//    }
//
//    public static String getCurrentContentTitle() {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        return preferences.getString(Constants.spContentTitle, "");
//    }
//
//    public static void setLastContentItemPk(int contentItemPk) {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putInt(Constants.spContentItemPk, contentItemPk);
//        editor.apply();
//    }
//
//    public static int getLastContentItemPk() {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        return preferences.getInt(Constants.spContentItemPk, 0);
//    }
//

//
//    public static void setFullName(String fullName) {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(Constants.spFullName, fullName);
//        editor.apply();
//    }
//
//    public static String getFullName() {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        return preferences.getString(Constants.spFullName, "");
//    }
//
//    public static void setEmailAddress(String emailAddress) {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(Constants.spEmailAddress, emailAddress);
//        editor.apply();
//    }
//
//    public static String getEmailAddress() {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        return preferences.getString(Constants.spEmailAddress, "");
//    }
//
//    public static void setUserProfileImageUrl(String userImageUrl) {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(Constants.spUserProfileImageUrl, userImageUrl);
//        editor.apply();
//    }
//
//    public static String getUserProfileImageUrl() {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        return preferences.getString(Constants.spUserProfileImageUrl, "");
//    }
//
//    public static void setLastForcedUpdateDate(String referenceLastUpdated) {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(Constants.spReferenceLastUpdated, referenceLastUpdated);
//        editor.apply();
//    }
//
//    public static String getLastForcedUpdateDate() {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        return preferences.getString(Constants.spReferenceLastUpdated, new DateTime().plusDays(-1).toString());
//    }
//
//    public static void setUserProfileDate(String userProfileDate) {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(Constants.spUserProfileDate, userProfileDate);
//        editor.apply();
//    }
//
//    public static String getUserProfileDate() {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        return preferences.getString(Constants.spUserProfileDate, new DateTime().plusDays(-1).toString());
//    }
//
//
//    public static void setNextContentItemId(int nextContentItemId) {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putInt(Constants.spNextContentItemId, nextContentItemId);
//        editor.apply();
//    }
//
//    public static int getNextContentItemId() {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        return preferences.getInt(Constants.spNextContentItemId, 0);
//    }
//
//    public static void setAcceptedTerms(boolean acceptedTerms) {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putBoolean(Constants.spAcceptedTerms, acceptedTerms);
//        editor.apply();
//    }
//
//    public static boolean getAcceptedTerms() {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        return preferences.getBoolean(Constants.spAcceptedTerms, false);
//    }
//
//
//    public static void setGoolgePlusProfileUrl(String goolgePlusProfileUrl) {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(Constants.spGoolgePlusProfileUrl, goolgePlusProfileUrl);
//        editor.apply();
//    }
//
//    public static String getGoolgePlusProfileUrl() {
//        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(MainActivity.getInstance().getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
//        return preferences.getString(Constants.spGoolgePlusProfileUrl, "");
//    }
}
