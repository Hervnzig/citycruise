package com.example.mycityguideapp.Databases;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    // Variables
    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;        //Context simply means which activity is trying to call this java class

    // session names
    public static final String SESSION_USER_SESSION = "userLogInSession";
    public static final String SESSION_REMEMBER_ME_SESSION = "rememberMe";


    // User session variables
    private static final String IS_LOGIN = "isLoggedIn";
    public static final String KEY_FULL_NAME = "fullName";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE_NUMBER = "phoneNo";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_DATE = "date";
    public static final String KEY_GENDER = "gender";

    // Remember me varibales
    private static final String IS_REMEMBER_ME = "IsRememberMe";
    public static final String KEY_SESSION_PHONE_NUMBER = "phoneNo";
    public static final String KEY_SESSION_PASSWORD = "password";

    // Constructor
    public SessionManager(Context _context, String sessionName) {
        context = _context;
        userSession = _context.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        editor = userSession.edit();
    }

    /*
    Users
    Login Session
     */
    public void createLoginSession(String fullName, String username, String email,
                                   String phoneNo, String password, String age, String gender) {
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_FULL_NAME, fullName);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PHONE_NUMBER, phoneNo);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_DATE, age);
        editor.putString(KEY_GENDER, gender);

        editor.commit();
    }

    public HashMap<String, String> getUsesrDetailFromSession() {
        HashMap<String, String> userData = new HashMap<>();

        userData.put(KEY_FULL_NAME, userSession.getString(KEY_FULL_NAME, null));
        userData.put(KEY_USERNAME, userSession.getString(KEY_USERNAME, null));
        userData.put(KEY_EMAIL, userSession.getString(KEY_EMAIL, null));
        userData.put(KEY_PHONE_NUMBER, userSession.getString(KEY_PHONE_NUMBER, null));
        userData.put(KEY_PASSWORD, userSession.getString(KEY_PASSWORD, null));
        userData.put(KEY_DATE, userSession.getString(KEY_DATE, null));
        userData.put(KEY_GENDER, userSession.getString(KEY_GENDER, null));

        return userData;
    }

    public boolean checkLogin() {
        if (userSession.getBoolean(IS_LOGIN, false)) {
            return true;
        } else {
            return false;
        }
    }

    public void logOutUserFromSession() {
        editor.clear();
        editor.commit();
    }


    /*
    Remember Me
    Session Functions
     */
    public void createRememberMeSession(String phoneNo, String password) {
        editor.putBoolean(IS_REMEMBER_ME, true);

        editor.putString(KEY_SESSION_PHONE_NUMBER, phoneNo);
        editor.putString(KEY_SESSION_PASSWORD, password);

        editor.commit();
    }

    public HashMap<String, String> getRememberMeDetailFromSession() {
        HashMap<String, String> userData = new HashMap<>();

        userData.put(KEY_SESSION_PHONE_NUMBER, userSession.getString(KEY_SESSION_PHONE_NUMBER, null));
        userData.put(KEY_SESSION_PASSWORD, userSession.getString(KEY_SESSION_PASSWORD, null));

        return userData;
    }

    public boolean checkRememberMe() {
        if (userSession.getBoolean(IS_REMEMBER_ME, false)) {
            return true;
        } else {
            return false;
        }
    }

}
