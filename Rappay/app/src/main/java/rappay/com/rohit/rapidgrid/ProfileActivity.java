package rappay.com.rohit.rapidgrid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import rappay.com.rohit.rapidgrid.Http.HttpManager;
import rappay.com.rohit.rapidgrid.Http.HttpResponse;
import rappay.com.rohit.rapidgrid.Http.RequestParams;

public class ProfileActivity extends AppCompatActivity {

    @Bind(R.id.profile_fullname)EditText fullname;
    @Bind(R.id.profile_username_ets)EditText username;
    @Bind(R.id.profile_email_et) EditText email;
    @Bind(R.id.profile_phonenumber) EditText phonenumber;
    @Bind(R.id.profile_contactnumber) EditText contactnumber;
    @Bind(R.id.profile_username) TextView avatartext;

    private static final String TAG = "Profile";
    public static final String MyPREFERENCES = "MyPrefs" ;

    String username_tx, fullname_tx,phone_tx, avatar_tx,contact_tx,email_tx= "";


    @Bind(R.id.profile_image) CircleImageView profile_pic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String restoredusername = prefs.getString("username", null);
        RequestParams requestParams = new RequestParams();

        requestParams.setUri(App.getIp()+"api/getuser/"+restoredusername);

        new GetProfileAsync(ProfileActivity.this).execute(requestParams);

    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String restoredusername = prefs.getString("username", null);
        RequestParams requestParams = new RequestParams();

        requestParams.setUri(App.getIp()+"api/getuser/"+restoredusername);

        new GetProfileAsync(ProfileActivity.this).execute(requestParams);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String restoredusername = prefs.getString("username", null);
        RequestParams requestParams = new RequestParams();

        requestParams.setUri(App.getIp()+"api/getuser/"+restoredusername);

        new GetProfileAsync(ProfileActivity.this).execute(requestParams);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
/*
        //noinspection SimplifiableIfStatement
        if (id == R.id.edit_profile) {
            Toast.makeText(getApplicationContext(),
                    "Here I'll intent to another activity, where you can edit", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Profile.this, UpdateProfile.class);
            intent.putExtra("username", username_tx);
            intent.putExtra("avatar", avatar_tx);
            intent.putExtra("firstname", firstname_tx);
            intent.putExtra("lastname", lastname_tx);
            intent.putExtra("phonenumber", phone_tx);

            intent.putExtra("emergencycnt1", emergencycnt1_tx);
            intent.putExtra("emergencycnt2", emergencycnt2_tx);
            intent.putExtra("emergencycntnum1", emergencycntnum1_tx);
            intent.putExtra("emergencycntnum2", emergencycntnum2_tx);
            startActivity(intent);


            return true;
        }
        */

        return super.onOptionsItemSelected(item);
    }





    private class GetProfileAsync extends AsyncTask<RequestParams, Void, HttpResponse> {

        private ProgressDialog dialog;

        public GetProfileAsync(Activity activity) {
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Retrieving your details, please wait.");
            dialog.show();
        }


        @Override
        protected HttpResponse doInBackground(RequestParams... params) {

            HttpResponse httpResponse = null;
            try {
                httpResponse =  HttpManager.getData(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return httpResponse;
        }
        @Override
        protected void onPostExecute(HttpResponse response) {

            dialog.dismiss();
            if(response == null){
                Snackbar.make(findViewById(R.id.getprofile_coordinator),
                        "Error! Please Check your Internet Connection", Snackbar.LENGTH_SHORT).show();
            }else if(response.getStatusCode() == 200){

                // Populate the edit text fields with the response
                Log.v(TAG, "The response is " + response.getBody());

                JSONObject jsonObject = null;
                JSONObject details;
                try {
                    jsonObject = new JSONObject(response.getBody());
                    username_tx = jsonObject.getString("username");

                    details = jsonObject.getJSONObject("details");
                    username_tx= details.getString("Username");
                    fullname_tx = details.getString("fullname");
                    phone_tx = details.getString("mobile_number");
                    avatar_tx = "Welcome!" +details.getString("fullname");
                    email_tx=details.getString("email");
                    contact_tx ="9700917471";


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                username.setText(username_tx);
                fullname.setText(fullname_tx);
                phonenumber.setText(phone_tx);

                email.setText(email_tx);

                avatartext.setText(avatar_tx);
                contactnumber.setText(contact_tx);

                //changeProfilePic(avatar_tx);
            }
        }

    }



}
