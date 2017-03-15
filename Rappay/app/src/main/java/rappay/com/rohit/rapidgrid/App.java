package rappay.com.rohit.rapidgrid;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;

/**
 * Created by Rohit on 3/22/2016.
 */
public class App extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);
        //  JodaTimeAndroid.init(this);

        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

    }

    public static String getIp(){

        return "http://192.168.1.45:3000/";
    }

    public static String getRetailBank()
    {

        return "http://retailbanking.mybluemix.net/banking/icicibank/";
     }


}
