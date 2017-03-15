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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rappay.com.rohit.rapidgrid.Adapter.TransAdapter;
import rappay.com.rohit.rapidgrid.Http.ConnectionManager;
import rappay.com.rohit.rapidgrid.Http.HttpManager;
import rappay.com.rohit.rapidgrid.Http.HttpResponse;
import rappay.com.rohit.rapidgrid.Http.RequestParams;
import rappay.com.rohit.rapidgrid.Models.TransactionList;

public class Trans_MiniActivity extends AppCompatActivity {




    private static final String TAG = "Mini-Statement";
    public static final String MyPREFERENCES = "MyPrefs";


    TextView CB_tx1;
    TextView CB_tx2;
    TextView CB_tx3;
    TextView CB_tx4;
    TextView CB_tx5;
    TextView CB_tx6;
    TextView CB_tx7;


    TextView TA_tx1;
    TextView TA_tx2;
    TextView TA_tx3;
    TextView TA_tx4;
    TextView TA_tx5;
    TextView TA_tx6;
    TextView TA_tx7;


    TextView TD_tx1;
    TextView TD_tx2;
    TextView TD_tx3;
    TextView TD_tx4;
    TextView TD_tx5;
    TextView TD_tx6;
    TextView TD_tx7;


    TextView  CM_tx1;
    TextView  CM_tx2;
    TextView  CM_tx3;
    TextView  CM_tx4;
    TextView  CM_tx5;
    TextView  CM_tx6;
    TextView  CM_tx7;


    TextView  CD_tx1;
    TextView  CD_tx2;
    TextView  CD_tx3;
    TextView  CD_tx4;
    TextView  CD_tx5;
    TextView  CD_tx6;
    TextView  CD_tx7;
    String[] tdx = new String[7];
    String[] tax = new String[7];
    String[] ttx = new String[7];
    String[] tcx = new String[7];
    String[] csx = new String[7];



    private List<TransactionList> transactionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans__mini);



         CB_tx1= (TextView)findViewById(R.id.trans_ClosingBalance1);
         CB_tx2= (TextView)findViewById(R.id.trans_ClosingBalance2);
         CB_tx3= (TextView)findViewById(R.id.trans_ClosingBalance3);
         CB_tx4= (TextView)findViewById(R.id.trans_ClosingBalance4);
         CB_tx5= (TextView)findViewById(R.id.trans_ClosingBalance5);
         CB_tx6= (TextView)findViewById(R.id.trans_ClosingBalance6);
         CB_tx7= (TextView)findViewById(R.id.trans_ClosingBalance7);


        TA_tx1= (TextView)findViewById(R.id.trans_TransactionAmount1);
        TA_tx2= (TextView)findViewById(R.id.trans_TransactionAmount2);
         TA_tx3= (TextView)findViewById(R.id.trans_TransactionAmount3);
         TA_tx4= (TextView)findViewById(R.id.trans_TransactionAmount4);
         TA_tx5= (TextView)findViewById(R.id.trans_TransactionAmount5);
         TA_tx6= (TextView)findViewById(R.id.trans_TransactionAmount6);
         TA_tx7= (TextView)findViewById(R.id.trans_TransactionAmount7);


         TD_tx1= (TextView)findViewById(R.id.trans_TransactionDate1);
         TD_tx2= (TextView)findViewById(R.id.trans_TransactionDate2);
        TD_tx3= (TextView)findViewById(R.id.trans_TransactionDate3);
         TD_tx4= (TextView)findViewById(R.id.trans_TransactionDate4);
         TD_tx5= (TextView)findViewById(R.id.trans_TransactionDate5);
         TD_tx6= (TextView)findViewById(R.id.trans_TransactionDate6);
         TD_tx7= (TextView)findViewById(R.id.trans_TransactionDate7);


          CM_tx1= (TextView)findViewById(R.id.trans_Comments1);
         CM_tx2= (TextView)findViewById(R.id.trans_Comments2);
          CM_tx3= (TextView)findViewById(R.id.trans_Comments3);
         CM_tx4= (TextView)findViewById(R.id.trans_Comments4);
          CM_tx5= (TextView)findViewById(R.id.trans_Comments5);
         CM_tx6= (TextView)findViewById(R.id.trans_Comments6);
         CM_tx7= (TextView)findViewById(R.id.trans_Comments7);


         CD_tx1= (TextView)findViewById(R.id.trans_Type1);
          CD_tx2= (TextView)findViewById(R.id.trans_Type2);
          CD_tx3= (TextView)findViewById(R.id.trans_Type3);
          CD_tx4= (TextView)findViewById(R.id.trans_Type4);
         CD_tx5= (TextView)findViewById(R.id.trans_Type5);
          CD_tx6= (TextView)findViewById(R.id.trans_Type6);
         CD_tx7= (TextView)findViewById(R.id.trans_Type7);

        BindData();

       // Log.i(TAG, "lenth1" + TransJsonArray.toString());






    }


    public void BindData()
    {
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String loc_clientid = prefs.getString("sclientid", null);
        String loc_token = prefs.getString("stoken", null);
        String loc_Accountno = prefs.getString("saccountno", null);
        String loc_day = "2";
        Boolean isloc_clientid = false;
        Boolean isloc_token = false;
        Boolean isloc_accountno = false;

        if (loc_clientid != null && !loc_clientid.isEmpty()) {
            isloc_clientid = true;
        }
        if (loc_Accountno != null && !loc_Accountno.isEmpty()) {
            isloc_accountno = true;
        }
        if (loc_token != null && !loc_token.isEmpty()) {
            isloc_token = true;
        }

        if (isloc_clientid && isloc_accountno && isloc_token) {

            RequestParams request = new RequestParams();
            String urlParam = "ndaystransaction?client_id=" + loc_clientid + "&token=" + loc_token + "&accountno=" + loc_Accountno + "&days=" + loc_day;
            request.setUri(App.getRetailBank() + urlParam);


            Log.v(TAG, "Request being sent with params " + request.getUri() + request.getParams().toString());


            if (ConnectionManager.isConnected(Trans_MiniActivity.this)) {

                new AsyncTransactionRefresh(Trans_MiniActivity.this).execute(request);
            } else {
                Snackbar.make(findViewById(R.id.gettrans_coordinator), "Error! No Internet connection", Snackbar.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ministatement, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.airport_menuRefresh) {
            unbinddata();
            BindData();

            return true ;
        }



        return super.onOptionsItemSelected(item);
    }



    private class AsyncTransactionRefresh extends AsyncTask<RequestParams, Void, HttpResponse> {

        private ProgressDialog dialog;

        public AsyncTransactionRefresh(Activity activity) {
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
                httpResponse = HttpManager.getData(params[0]);



            } catch (Exception e) {
                e.printStackTrace();
            }

            return httpResponse;
        }

        @Override
        protected void onPostExecute(HttpResponse response) {

            dialog.dismiss();
            if (response == null) {
                Snackbar.make(findViewById(R.id.gettrans_coordinator),
                        "Error! Please Check your Internet Connection", Snackbar.LENGTH_SHORT).show();
            } else if (response.getStatusCode() == 200) {

                // Populate the edit text fields with the response
                Log.v(TAG, "The response is " + response.getBody());

                //JSONObject jsonObject = null;
                JSONArray jsonarray = null;
                // JSONObject details;
                try {
                    //jsonObject = new JSONObject(response.getBody());
                    transactionList = new ArrayList<>();
                    jsonarray = new JSONArray(response.getBody());

                    JSONObject jsonobject0 = jsonarray.getJSONObject(0);


                    if ((Integer.parseInt(jsonobject0.getString("code")) == 200)) {


                        Log.v(TAG, "The response inside " +jsonarray.toString());
                         for(int i=1;i<8;i++)
                         {

                             JSONObject jsonobject1 = jsonarray.getJSONObject(i);

                             tdx[i-1]=jsonobject1.getString("transactiondate");
                             tax[i-1]=jsonobject1.getString("transaction_amount");
                             ttx[i-1]=jsonobject1.getString("credit_debit_flag");
                             tcx[i-1]=jsonobject1.getString("remark");
                             csx[i-1]=jsonobject1.getString("closing_balance");



                         }



                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                binddata(tdx,ttx,tcx,csx,tax);
                //
                //changeProfilePic(avatar_tx);
            }
        }




        public void binddata(String [] tdx,String []ttx,String []tcx,String []csx,String []tax)
        {
                    CB_tx1.setText(CB_tx1.getText().toString()+csx[0]);
                    CB_tx2.setText(CB_tx2.getText().toString()+csx[1]);
                    CB_tx3.setText(CB_tx3.getText().toString()+csx[2]);
                    CB_tx4.setText(CB_tx4.getText().toString()+csx[3]);
                    CB_tx5.setText(CB_tx5.getText().toString()+csx[4]);
                    CB_tx6.setText(CB_tx6.getText().toString()+csx[5]);
                    CB_tx7.setText(CB_tx7.getText().toString()+csx[6]);


                    TA_tx1.setText(TA_tx1.getText().toString()+tax[0]);
                    TA_tx2.setText(TA_tx2.getText().toString()+tax[1]);
                    TA_tx3.setText(TA_tx3.getText().toString()+tax[2]);
                    TA_tx4.setText(TA_tx4.getText().toString()+tax[3]);
                    TA_tx5.setText(TA_tx5.getText().toString()+tax[4]);
                    TA_tx6.setText(TA_tx6.getText().toString()+tax[5]);
                    TA_tx7.setText(TA_tx7.getText().toString()+tax[6]);


                    TD_tx1.setText(TD_tx1.getText().toString()+tdx[0]);
                    TD_tx2.setText(TD_tx2.getText().toString()+tdx[1]);
                    TD_tx3.setText(TD_tx3.getText().toString()+tdx[2]);
                    TD_tx4.setText(TD_tx4.getText().toString()+tdx[3]);
                    TD_tx5.setText(TD_tx5.getText().toString()+tdx[4]);
                    TD_tx6.setText(TD_tx6.getText().toString()+tdx[5]);
                    TD_tx7.setText(TD_tx7.getText().toString()+tdx[6]);


                    CM_tx1.setText(CM_tx1.getText().toString()+tcx[0]);
                    CM_tx2.setText(CM_tx2.getText().toString()+tcx[1]);
                    CM_tx3.setText(CM_tx3.getText().toString()+tcx[2]);
                    CM_tx4.setText(CM_tx4.getText().toString()+tcx[3]);
                    CM_tx5.setText(CM_tx5.getText().toString()+tcx[4]);
                    CM_tx6.setText(CM_tx6.getText().toString()+tcx[5]);
                    CM_tx7.setText(CM_tx7.getText().toString()+tcx[6]);


                     CD_tx1.setText(CD_tx1.getText().toString()+ttx[0]);
                     CD_tx2.setText(CD_tx2.getText().toString()+ttx[1]);
                    CD_tx3.setText(CD_tx3.getText().toString()+ttx[2]);
                    CD_tx4.setText(CD_tx4.getText().toString()+ttx[3]);
                    CD_tx5.setText(CD_tx5.getText().toString()+ttx[4]);
                    CD_tx6.setText(CD_tx6.getText().toString()+ttx[5]);
                    CD_tx7.setText(CD_tx7.getText().toString()+ttx[6]);
        }
    }




    public void unbinddata()
    {
        CB_tx1.setText("Closing Balance:");
        CB_tx2.setText("Closing Balance:");
        CB_tx3.setText("Closing Balance:");
        CB_tx4.setText("Closing Balance:");
        CB_tx5.setText("Closing Balance:");
        CB_tx6.setText("Closing Balance:");
        CB_tx7.setText("Closing Balance:");


        TA_tx1.setText("Transaction Amount:");
        TA_tx2.setText("Transaction Amount:");
        TA_tx3.setText("Transaction Amount:");
        TA_tx4.setText("Transaction Amount:");
        TA_tx5.setText("Transaction Amount:");
        TA_tx6.setText("Transaction Amount:");
        TA_tx7.setText("Transaction Amount:");


        TD_tx1.setText("Transaction Date:");
        TD_tx2.setText("Transaction Date:");
        TD_tx3.setText("Transaction Date:");
        TD_tx4.setText("Transaction Date:");
        TD_tx5.setText("Transaction Date:");
        TD_tx6.setText("Transaction Date:");
        TD_tx7.setText("Transaction Date:");


        CM_tx1.setText("Transaction Type:");
        CM_tx2.setText("Transaction Type:");
        CM_tx3.setText("Transaction Type:");
        CM_tx4.setText("Transaction Type:");
        CM_tx5.setText("Transaction Type:");
        CM_tx6.setText("Transaction Type:");
        CM_tx7.setText("Transaction Type:");


        CD_tx1.setText("Type:");
        CD_tx2.setText("Type:");
        CD_tx3.setText("Type:");
        CD_tx4.setText("Type:");
        CD_tx5.setText("Type:");
        CD_tx6.setText("Type:");
        CD_tx7.setText("Type:");
    }


}