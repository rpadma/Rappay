package rappay.com.rohit.rapidgrid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private static final String TAG = "CREATEEVENT";
    public static final String MyPREFERENCES = "MyPrefs";
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Boolean isLoggedIn = prefs.getBoolean("isloggedin", false);


        if(SaveSharedPreference.getUserName(SplashScreen.this) && isLoggedIn)
        {
            i= new Intent(SplashScreen.this, MainActivity.class);
        }
        else
        {
            i= new Intent(SplashScreen.this, Login.class);
        }

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                startActivity(i);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
