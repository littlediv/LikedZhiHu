package name.littlediv.likezhihu;

import android.app.Application;
import android.content.Context;

/**
 * Created by win7 on 2016/6/12.
 */
public class MyApplication extends Application {

    private static MyApplication myApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();

        myApplication = this;
    }


    public static Context getContext() {
        return myApplication;
    }
}
