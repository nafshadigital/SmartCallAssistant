package com.nafshadigital.smartcallassistant.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class SmartCallAssistantSharedPrefs {

    public static final String ACCESS_TOKEN = "accessToken";
    public static final String TOKEN_TYPE = "accessToken";
    public static final String HUBUN_PREFS = "HubunPrefs";
    public static final String USER_LOGIN = "userLogin";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_PASSWORD = "userPassword";
    public static final String USER_PHONE_NUMBER = "userPhoneNumber";
    public static final String USER_TITLE = "userTitle";
    public static final String USER_FIRST_NAME = "userFirstName";
    public static final String USER_IS_REGISTERED = "isRegistered";

    public static final String USER_COUNTRY_CODE = "userCountryCode";
    public static final String ONBOARDING_SCREEN_SHOWN = "onBoardingScreenShown";
    public static final String IS_SETTINGS_FETCHED = "isSettingsFetched";
    public static final String PRIVACY_POLICY_URL = "privacyPolicyUrl";
    public static final String TERMS_OF_USE_URL = "termsOfUserUrl";
    public static final String FAQ_URL = "faqUrl";
    public static final String CONTACT_US_PHONE_NUMBER = "contactusPhoneNumber";
    public static final String CONTACT_US_EMAIL = "contactusEmail";
    public static void setAccessToken(Context ctx, String accessToken) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE).edit();
        editor.putString(ACCESS_TOKEN, accessToken);
        editor.apply();
    }

    public static String getAccessToken(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE);
        String accessToken = prefs.getString(ACCESS_TOKEN, "");
        return accessToken;
    }

    public static void setIsUserLoggedIn(Context ctx, boolean isUserLoggedIn) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE).edit();
        editor.putBoolean(USER_LOGIN, isUserLoggedIn);
        editor.apply();
    }

    public static boolean getIsUserLoggedIn(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE);
        boolean isUserLoggedIn = prefs.getBoolean(USER_LOGIN, false);
        return isUserLoggedIn;
    }

    public static void setIsOnBoardingShown(Context ctx, boolean isUserLoggedIn) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE).edit();
        editor.putBoolean(ONBOARDING_SCREEN_SHOWN, isUserLoggedIn);
        editor.apply();
    }

    public static boolean getIsOnBoardingShown(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE);
        boolean isOnBoardingShown = prefs.getBoolean(ONBOARDING_SCREEN_SHOWN, false);
        return isOnBoardingShown;
    }

    public static void setIsUserRegistered(Context ctx, boolean isUserRegistered) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE).edit();
        editor.putBoolean(USER_IS_REGISTERED, isUserRegistered);
        editor.apply();
    }

    public static boolean getIsUserRegistered(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE);
        boolean isUserRegistered = prefs.getBoolean(USER_IS_REGISTERED, false);
        return isUserRegistered;
    }

    public static void setPhoneNumberInPrefs(Context ctx, String phoneNumber) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE).edit();
        editor.putString(USER_PHONE_NUMBER, phoneNumber);
        editor.apply();
    }

    public static String getPhoneFromPrefs(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE);
        String phoneNumber = prefs.getString(USER_PHONE_NUMBER, "");
        return phoneNumber;
    }

    public static void setUserEmailPrefs(Context ctx, String email) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE).edit();
        editor.putString(USER_EMAIL, email);
        editor.apply();
    }

    public static String getEmailFromPrefs(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE);
        String userEmail = prefs.getString(USER_EMAIL, "");
        return userEmail;
    }

    public static void setUserPasswordPrefs(Context ctx, String userPassword) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE).edit();
        editor.putString(USER_PASSWORD, userPassword);
        editor.apply();
    }

    public static String getPasswordFromPrefs(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE);
        String userPassword = prefs.getString(USER_PASSWORD, "");
        return userPassword;
    }

    public static void setUserFirstName(Context ctx, String firstName) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE).edit();
        editor.putString(USER_FIRST_NAME, firstName);
        editor.apply();
    }

    public static String getUserFirstNameFromPrefs(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE);
        String firstName = prefs.getString(USER_FIRST_NAME, "");
        return firstName;
    }

    public static void setUserTitlePrefs(Context ctx, String firstName) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE).edit();
        editor.putString(USER_TITLE, firstName);
        editor.apply();
    }

    public static String getUserTitlePrefs(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE);
        String firstName = prefs.getString(USER_TITLE, "");
        return firstName;
    }

    public static void setPrivacyPolicyUrl(Context ctx, String url) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE).edit();
        editor.putString(PRIVACY_POLICY_URL, url);
        editor.apply();
    }

    public static String getPrivacyPolicyUrl(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE);
        return prefs.getString(PRIVACY_POLICY_URL, "");
    }
    public static void setTermsOfUseUrl(Context ctx, String url) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE).edit();
        editor.putString(TERMS_OF_USE_URL, url);
        editor.apply();
    }

    public static String getTermsOfUseUrl(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE);
        return prefs.getString(TERMS_OF_USE_URL, "");
    }

    public static void setFaqUrl(Context ctx, String url) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE).edit();
        editor.putString(FAQ_URL, url);
        editor.apply();
    }

    public static String getFaqUrl(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE);
        return prefs.getString(FAQ_URL, "");
    }

    public static void setContactUsPhoneNumber(Context ctx, String phone) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE).edit();
        editor.putString(CONTACT_US_PHONE_NUMBER, phone);
        editor.apply();
    }

    public static String getContactUsPhoneNumber(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE);
        return prefs.getString(CONTACT_US_PHONE_NUMBER, "");
    }
    public static void setContactUsEmail(Context ctx, String email) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE).edit();
        editor.putString(CONTACT_US_EMAIL, email);
        editor.apply();
    }

    public static String getContactUsEmail(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE);
        return prefs.getString(CONTACT_US_EMAIL, "");
    }
    public static void setIsSettingsFetched(Context ctx, boolean isSettingsFetched) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE).edit();
        editor.putBoolean(IS_SETTINGS_FETCHED, isSettingsFetched);
        editor.apply();
    }

    public static boolean isSettingsFetched(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE);
        return prefs.getBoolean(IS_SETTINGS_FETCHED, false);
    }
    public static void clearPrefs(Context ctx){
        SharedPreferences.Editor editor = ctx.getSharedPreferences(HUBUN_PREFS, ctx.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }
}
