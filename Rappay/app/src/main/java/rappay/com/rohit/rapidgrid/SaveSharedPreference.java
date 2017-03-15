package rappay.com.rohit.rapidgrid;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Rohit on 3/22/2016.
 */
public class SaveSharedPreference {
    static final String PREF_IsLoggedin= "MyPrefs";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, Boolean Isloggedin)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(PREF_IsLoggedin, Isloggedin);
        editor.commit();
    }

    public static Boolean getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getBoolean(PREF_IsLoggedin, false);


    }
}
