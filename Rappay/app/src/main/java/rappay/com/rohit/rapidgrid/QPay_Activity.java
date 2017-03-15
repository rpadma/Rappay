package rappay.com.rohit.rapidgrid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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

public class QPay_Activity extends AppCompatActivity {

    @Bind(R.id.qpay_BillAmount_et)EditText BillAmount;
    @Bind(R.id.qpay_Payeedesc_et)EditText PayeeDesc;
    @Bind(R.id.qpay_TransactionType_et) EditText Transaction;
    @Bind(R.id.ScanToPay) Button btnScantopay;

  //  EditText BillAmount=(EditText)findViewById(R.id.qpay_BillAmount_et);
   // EditText PayeeDesc=(EditText)findViewById(R.id.qpay_Payeedesc_et);
   // EditText Transaction=(EditText)findViewById(R.id.qpay_TransactionType_et);
   // Button btnScantopay=(Button)findViewById(R.id.ScanToPay);
 //   @Bind(R.id.Qpay_reponselayout) View v;


    String BillAmount_tx,PayeeDesc_tx,Transaction_tx="";

    public static String loc_vendoraccountno,loc_payeeid;

    private static final String TAG = "QPay";
    public static final String MyPREFERENCES = "MyPrefs" ;
    View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qpay);
       ButterKnife.bind(this);
        //Layout ll=new Layout(R.id.Qpay_reponselayout);
      v= (View) findViewById(R.id.Qpay_reponselayout);
        v.setVisibility(View.INVISIBLE);

       Button btnPayNow=(Button)findViewById(R.id.qpay_Paynow_btn);


        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                String srcAccount = prefs.getString("saccountno", null);
                String stoken=prefs.getString("stoken", null);
                String clientid=prefs.getString("sclientid", null);
                String desAccount=loc_vendoraccountno;
                String payeeid=loc_payeeid;
                String BillAmt=BillAmount.getText().toString();
                String Paydes=PayeeDesc.getText().toString();
                String Ttype=Transaction.getText().toString();

                Boolean isBillAmout=true;
                Boolean isSrcAccount=true;
                Boolean isstoken=true;
                Boolean isclientid=true;
                Boolean isdesAccount=true;

                if(srcAccount ==null && srcAccount.isEmpty())
                {
                    isSrcAccount=false;
                }

                if(BillAmt ==null && BillAmt.isEmpty())
                {
                    isBillAmout=false;
                }
                if(stoken ==null && stoken.isEmpty())
                {
                    isstoken=false;
                }
                if(clientid ==null && clientid.isEmpty())
                {
                    isclientid=false;
                }
                if(desAccount ==null && desAccount.isEmpty())
                {
                    isdesAccount=false;
                }

                if(isSrcAccount&&isdesAccount&&isclientid&&isstoken&&isBillAmout)
                {
                    RequestParams request = new RequestParams();
                    String urlParam="fundTransfer?client_id="+clientid+"&token="+stoken+"&srcAccount="+srcAccount+"&destAccount="+desAccount
                            +"&amt="+BillAmt+"&payeeDesc="+Paydes+"&payeeId="+payeeid+"&type_of_transaction="+Ttype;
                    request.setUri(App.getRetailBank()+urlParam );


                    if(ConnectionManager.isConnected(QPay_Activity.this)){
                        new QpayAsync(QPay_Activity.this ).execute(request);
                    }else{
                        Snackbar.make(findViewById(R.id.Qpay_coordinator), "Error! No Internet connection", Snackbar.LENGTH_SHORT).show();
                    }
                }




            }
        });

    }


    public void scanToolbar(View view) {
        new IntentIntegrator(this).setCaptureActivity(ToolbarCaptureActivity.class).initiateScan();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");

                String qcodereponse=result.getContents();

                BindBillDetails(qcodereponse);

              //  Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void BindBillDetails(String qcodereponse)
    {

        try {

            Log.i(TAG,"inside the method");

            JSONArray objjsonarray = new JSONArray(qcodereponse);

            JSONObject jsonobject0 = objjsonarray.getJSONObject(0);
            JSONObject jsonobject1 = objjsonarray.getJSONObject(1);
            Log.i(TAG,"inside the method0"+jsonobject0);
            Log.i(TAG,"inside the method1"+jsonobject1);

            if((Integer.parseInt(jsonobject0.getString("code"))==200))
            {
                BillAmount_tx = jsonobject1.getString("BillAmount");
                PayeeDesc_tx=jsonobject1.getString("Payeedesc");
                Transaction_tx=jsonobject1.getString("TransactionType");
                loc_vendoraccountno=jsonobject1.getString("VendorAccountno");
                loc_payeeid=jsonobject1.getString("PayeeId");

            }



        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        BillAmount.setText(BillAmount_tx);
        PayeeDesc.setText(PayeeDesc_tx);
        Transaction.setText(Transaction_tx);
        btnScantopay.setVisibility(View.INVISIBLE);
        v.setVisibility(View.VISIBLE);




    }


    private class QpayAsync extends AsyncTask<RequestParams, Void, HttpResponse> {

        private ProgressDialog dialog;

        public QpayAsync(Activity activity) {
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
                JSONArray  jsonArray=null;
                try {

                        jsonArray=new JSONArray(response.getBody());

                    Log.i(TAG, jsonArray.toString());

                    JSONObject  jsonObject0=jsonArray.getJSONObject(0);
                    JSONObject  jsonObject1=jsonArray.getJSONObject(1);

                    if((Integer.parseInt(jsonObject0.getString("code"))==200)&&(jsonObject1.getString("status").equals("SUCCESS")))
                    {
                        Log.i(TAG, "inside success message");

                        new AlertDialog.Builder(QPay_Activity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Confirmation")
                                .setMessage("Successfully Paid ! Rs "+jsonObject1.getString("transaction_amount")+" Go to Home")
                                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent intent = new Intent(QPay_Activity.this, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);

                                    }
                                }).setNegativeButton("no", null).show();
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }

    }

}
