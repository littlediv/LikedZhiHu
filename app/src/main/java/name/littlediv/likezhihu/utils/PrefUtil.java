package name.littlediv.likezhihu.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by win7 on 2016/6/13.
 */
public class PrefUtil {

    private static String PREF_NAME = "themes";
    private static SharedPreferences sp;

    public static void putStringPref(Context context, String key, String value) {
        if (sp == null) {
            sp = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }

        sp.edit().putString(key, value).commit();
    }

    public static String getStringPref(Context context, String key, String value) {
        if (sp == null) {
            sp = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }

        return sp.getString(key, value);
    }
}
