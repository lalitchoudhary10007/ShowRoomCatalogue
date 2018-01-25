package nubella.purplecommerce.com.showroomcatalogue.SessionManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "LoginPrefrences";
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String USER_ID_LOCAL = "user_id";
    public static final String USER_ID_BACKEND = "user_id_backend";
    public static final String USER_NAME = "user_name";
    public static final String USER_PHONE = "user_phone_number";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_PASSWORD = "user_password";


    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


//    public void setDeviceId(String device_id){
//        editor.putString(USER_DEVICE_ID, device_id);
//        editor.commit();
//    }

    public void createLoginSession(String user_id_local, String user_id_backend ,
                                   String user_name,String user_phone ,  String user_email,
                                   String user_pass) {


        editor.putBoolean(IS_LOGIN, true);
        editor.putString(USER_ID_LOCAL, user_id_local);
        editor.putString(USER_ID_BACKEND, user_id_backend);
        editor.putString(USER_NAME, user_name);
        editor.putString(USER_PHONE, user_phone);
        editor.putString(USER_EMAIL, user_email);
        editor.putString(USER_PASSWORD, user_pass);

        editor.commit();
    }





    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(USER_ID_LOCAL, pref.getString(USER_ID_LOCAL, ""));
        user.put(USER_ID_BACKEND, pref.getString(USER_ID_BACKEND, ""));
        user.put(USER_NAME, pref.getString(USER_NAME, ""));
        user.put(USER_EMAIL, pref.getString(USER_EMAIL, "default@gmail.com"));
        user.put(USER_PHONE, pref.getString(USER_PHONE, ""));
        user.put(USER_PASSWORD, pref.getString(USER_PASSWORD, ""));


        return user;
    }





    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
    }
}
