package rappay.com.rohit.rapidgrid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class QCardsScan_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @Bind(R.id.qpay_cardr_BillAmount_et)EditText BillAmount;
    @Bind(R.id.qpay_cardr_Payeedesc_et)EditText PayeeDesc;
    @Bind(R.id.qpay_cardr_TransactionType_et) Spinner spinner;
    @Bind(R.id.qpay_cardr_Pin_et) EditText Securepin;
    @Bind(R.id.qpay_cardr_Paynow_btn) Button btncardPaynow;
    @Bind(R.id.Qcard_scan_btn) Button btncardscan;

String BillAmount_tx,PayeeDesc_tx,Transaction_tx,Securepin_tx;

    public static String loc_Customeraccountno,loc_payeeid,ccardno;
    private static final String TAG = "QCard_Pay";
    public static final String MyPREFERENCES = "MyPrefs" ;
    View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qcards_scan);
        ButterKnife.bind(this);
        v= (View) findViewById(R.id.Qcard_reponselayout);
        v.setVisibility(View.INVISIBLE);

        spinner = (Spinner) findViewById(R.id.qpay_cardr_TransactionType_et);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Type_of_Transaction, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        btncardPaynow=(Button)findViewById(R.id.qpay_cardr_Paynow_btn);
         btncardscan=(Button)findViewById(R.id.Qcard_scan_btn);

        btncardPaynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                String desAccount = prefs.getString("saccountno", null);
                String stoken=prefs.getString("stoken", null);
                String clientid=prefs.getString("sclientid", null);
                String srcAccount=loc_Customeraccountno;
                String payeeid=loc_payeeid;
                String BillAmt=BillAmount.getText().toString();
                String Paydes=PayeeDesc.getText().toString()+"Payed using cardno:"+ccardno;
                String Ttype=spinner.getSelectedItem().toString();

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


                    if(ConnectionManager.isConnected(QCardsScan_Activity.this)){
                        new QCardPayAsync(QCardsScan_Activity.this ).execute(request);
                    }else{
                        Snackbar.make(findViewById(R.id.QCard_pay_coordinator), "Error! No Internet connection", Snackbar.LENGTH_SHORT).show();
                    }
                }



            }
        });


    }
    public void scanQrCard(View view) {
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
              //  Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();

                try{
                    JSONArray objJsonArray=new JSONArray(result.getContents());
                    JSONObject jsonobj1= objJsonArray.getJSONObject(1);

                    loc_Customeraccountno=jsonobj1.getString("caccountno");
                    ccardno=jsonobj1.getString("ccardnumber");

                    int randomPIN = (int)(Math.random()*9000)+1000;
                     loc_payeeid=String.valueOf(randomPIN);

                    btncardscan.setVisibility(View.INVISIBLE);
                    v.setVisibility(View.VISIBLE);

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }



            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    private class QCardPayAsync extends AsyncTask<RequestParams, Void, HttpResponse> {

        private ProgressDialog dialog;

        public QCardPayAsync(Activity activity) {
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
                Snackbar.make(findViewById(R.id.QCard_pay_coordinator),
                        "Error! Please Check your Internet Connection", Snackbar.LENGTH_SHORT).show();
            }else if(response.getStatusCode() == 200){

                // Populate the edit text fields with the response
                Log.v(TAG, "The response is " + response.getBody());

                JSONObject jsonObject = null;
                JSONArray  jsonArray=null;
                try {

                    jsonArray=new JSONArray(response.getBody());

                    JSONObject  jsonObject0=jsonArray.getJSONObject(0);
                    JSONObject  jsonObject1=jsonArray.getJSONObject(1);

                    if((Integer.parseInt(jsonObject0.getString("code"))==200)&&(jsonObject1.getString("status").equals("SUCCESS")))
                    {
                        Log.i(TAG, "inside success message");

                        new AlertDialog.Builder(QCardsScan_Activity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Confirmation")
                                .setMessage("Successfully Received ! Rs " + jsonObject1.getString("transaction_amount") + " Go to Home")
                                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent intent = new Intent(QCardsScan_Activity.this, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);

                                    }
                                }).show();
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        //  Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}
