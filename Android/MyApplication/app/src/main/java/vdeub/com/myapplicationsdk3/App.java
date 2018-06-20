package vdeub.com.myapplicationsdk3;

import android.app.Application;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by loicvdb on 22/03/2018.
 */

public class App extends Application {

    public void onCreate(){
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess (this)){
            return;
        }

        LeakCanary.install(this);


    }
}
