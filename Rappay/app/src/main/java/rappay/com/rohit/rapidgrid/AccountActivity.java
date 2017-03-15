package rappay.com.rohit.rapidgrid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import rappay.com.rohit.rapidgrid.Http.ConnectionManager;
import rappay.com.rohit.rapidgrid.Http.HttpManager;
import rappay.com.rohit.rapidgrid.Http.HttpResponse;
import rappay.com.rohit.rapidgrid.Http.RequestParams;


public class AccountActivity extends AppCompatActivity {

    @Bind(R.id.account_Accountnumber_et)EditText Accountnumber;
    @Bind(R.id.account_CardNumber_et)EditText Cardnumber;
    @Bind(R.id.account_CustomerId_et) EditText CustomerId;
    @Bind(R.id.account_AccountType_et) EditText AccountType;
    @Bind(R.id.account_AccountBalance_et) EditText AccountBalance;
    @Bind(R.id.account_AccountBalanceDate_et) EditText AccountBalanceDate;

    private static final String TAG = "Account";
    public static final String MyPREFERENCES = "MyPrefs" ;
    String Accountnumber_tx,Cardnumber_tx,CustomerId_tx , AccountType_tx, AccountBalance_tx, AccountBalanceDate_tx = "";
    private String clientid,token,passcode="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);

        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String restoredusername = prefs.getString("username", null);
        RequestParams requestParams = new RequestParams();
        requestParams.setUri(App.getIp() + "api/getuseraccountsummary/" + restoredusername);


        if(ConnectionManager.isConnected(AccountActivity.this)){
            new GetAccountAsync(AccountActivity.this).execute(requestParams);
        }
        else
        {
            Snackbar.make(findViewById(R.id.Account_coordinator), "Error !No Internet connection", Snackbar.LENGTH_SHORT).show();
        }









        AccountRefresh();

        Button btnrefresh=(Button)findViewById(R.id.account_refresh_btn);

        btnrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AccountRefresh();

            }
        });


    }

    private class GetAccountAsync extends AsyncTask<RequestParams, Void, HttpResponse> {

        private ProgressDialog dialog;

        public GetAccountAsync(Activity activity) {
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
                Snackbar.make(findViewById(R.id.Account_coordinator),
                        "Error! Please Check your Internet Connection", Snackbar.LENGTH_SHORT).show();
            }else if(response.getStatusCode() == 200){

                // Populate the edit text fields with the response
                Log.v(TAG, "The response is " + response.getBody());

                JSONObject jsonObject = null;
                JSONObject details;
                try {
                    jsonObject = new JSONObject(response.getBody());
                    //username_tx = jsonObject.getString("username");

                    details = jsonObject.getJSONObject("details");
                    Accountnumber_tx= details.getString("Accountno");
                    Cardnumber_tx = details.getString("Cardno");
                    CustomerId_tx = details.getString("CustID");
                    clientid = details.getString("ClientID");
                    passcode=details.getString("Passcode");
                    token =details.getString("Token");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Accountnumber.setText(Accountnumber_tx);
                CustomerId.setText(CustomerId_tx);
                Cardnumber.setText(Cardnumber_tx);



                updateSharedpreference(Accountnumber_tx, clientid, token, passcode, CustomerId_tx);

            }
        }

    }

    public void updateSharedpreference(String Accountno,String Clientid, String token,String passcode,String customerid )
    {


        SharedPreferences Accountpref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //String CurrentToken = Accountpref.getString("stoken", null);

        SharedPreferences.Editor editor = Accountpref.edit();

        editor.putString("stoken", token);
        editor.putString("saccountno", Accountno);
        editor.putString("scustomerid",customerid);
        editor.putString("spasscode", passcode);
        editor.putString("sclientid",  Clientid);
        editor.commit();

    }



    public void AccountRefresh() {

        Log.i(TAG, "The AccountRefresg started");

        SharedPreferences prefs1 = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String loc_clientid = prefs1.getString("sclientid", null);
        String loc_token=prefs1.getString("stoken", null);
        String loc_accountno=prefs1.getString("saccountno", null);
        Boolean isloc_clientid=false;
        Boolean isloc_token=false;
        Boolean isloc_accountno=false;

      //  Log.i(TAG, "The AccountRefresg started"+loc_clientid+"client"+loc_accountno+"account"+loc_token+"token");
        if(loc_clientid != null && !loc_clientid.isEmpty())
        {
            isloc_clientid=true;
        }
        if(loc_accountno != null && !loc_accountno.isEmpty())
        {
            isloc_accountno=true;
        }
        if(loc_token != null && !loc_token.isEmpty())
        {
            isloc_token=true;
        }
        if(isloc_clientid && isloc_accountno && isloc_token ){

            RequestParams request = new RequestParams();
            String urlParam="balanceenquiry?client_id="+loc_clientid+"&token="+loc_token+"&accountno="+loc_accountno;
            request.setUri(App.getRetailBank()+urlParam );
            //request.setUri(urlsample);
            //request.setParam("client_id", loc_clientid);
            //request.setParam("token", loc_token);
            //request.setParam("accountno", loc_accountno);



            Log.v(TAG, "Request being sent with params " + request.getUri() + request.getParams().toString());


            if(ConnectionManager.isConnected(AccountActivity.this)){
                new AsyncAccountRefresh(AccountActivity.this ).execute(request);
            }else{
                Snackbar.make(findViewById(R.id.Account_coordinator), "Error! No Internet connection", Snackbar.LENGTH_SHORT).show();
            }
        }



    }


    private class AsyncAccountRefresh extends AsyncTask<RequestParams, Void, HttpResponse> {

        private ProgressDialog dialog;

        public AsyncAccountRefresh(Activity activity) {
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

                //JSONObject jsonObject = null;
                JSONArray jsonarray=null;
               // JSONObject details;
                try {
                    //jsonObject = new JSONObject(response.getBody());

                     jsonarray = new JSONArray(response.getBody());

                    JSONObject jsonobject0 = jsonarray.getJSONObject(0);
                    JSONObject jsonobject1 = jsonarray.getJSONObject(1);

                    Log.i(TAG, "The response0 is " + jsonobject0);
                    Log.i(TAG, "The response1 is " + jsonobject1);

                    if((Integer.parseInt(jsonobject0.getString("code"))==200))
                    {
                        AccountBalance_tx = jsonobject1.getString("balance");
                        AccountType_tx=jsonobject1.getString("accounttype");
                        AccountBalanceDate_tx=jsonobject1.getString("balancetime");

                    }






                } catch (JSONException e) {
                    e.printStackTrace();
                }

                AccountBalance.setText(AccountBalance_tx);
                AccountType.setText(AccountType_tx);
                AccountBalanceDate.setText(AccountBalanceDate_tx);

                //changeProfilePic(avatar_tx);
            }
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_account, menu);
        return true;
    }
}
