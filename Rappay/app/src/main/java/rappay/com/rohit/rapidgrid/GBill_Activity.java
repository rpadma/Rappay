package rappay.com.rohit.rapidgrid;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.*;
import javax.crypto.*;

import rappay.com.rohit.rapidgrid.Util.Contents;
import rappay.com.rohit.rapidgrid.Util.QRCodeEncoder;

public class GBill_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private String LOG_TAG="GenerateQRCode";
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gbill);

        Spinner spinner = (Spinner) findViewById(R.id.Gbill_Type_of_Transaction);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Type_of_Transaction, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        Button btnQrcodegenerator = (Button)findViewById(R.id.GenerateQrcode);

        btnQrcodegenerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onGenerateClick();
            }
        });
    }



    public void onGenerateClick()
    {

                 SharedPreferences prefs1 = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                 String VendorAccountno=prefs1.getString("saccountno", null);

                EditText BillAmount =(EditText)findViewById(R.id.GBill_BillAmount_ets);
                EditText PayeeDesc=(EditText)findViewById(R.id.GBill_PayeeDesc_ets);
                Spinner tt=(Spinner)findViewById(R.id.Gbill_Type_of_Transaction);

                String BillAmount_tx=BillAmount.getText().toString();
                String PayeeDesc_tx=PayeeDesc.getText().toString();
                String TransactionType_tx=tt.getSelectedItem().toString();

                 int randomPIN = (int)(Math.random()*9000)+1000;
                 String PayeeId_tx=String.valueOf(randomPIN);

                     Log.i(LOG_TAG, "transaction type:"+TransactionType_tx);

      String qrstring ="[{"+"\""+"code"+"\""+":200}," +
              "{"+"\""+"VendorAccountno"+"\""+":"+VendorAccountno+","+"\""+"BillAmount"+"\""+
              ":"+BillAmount_tx+","+"\""+"Payeedesc"+"\""+":"+"\""+PayeeDesc_tx+"\""+","+"\""+"TransactionType"+"\""+":"+"\""+TransactionType_tx+"\""+
              ","+"\""+"PayeeId"+"\""+":"+PayeeId_tx+"}]";

        try

        {
            JSONArray sample = new JSONArray(qrstring);

            JSONObject jsonobject0 = sample.getJSONObject(0);
            JSONObject jsonobject1 = sample.getJSONObject(1);
            Log.i(LOG_TAG, "The response0 is " + jsonobject0);
            Log.i(LOG_TAG, "The response1 is " + jsonobject1);

        }
        catch (Exception e)
        {
            Log.i(LOG_TAG, "Expection occured"+e.getMessage());
        }


                        Log.v(LOG_TAG, qrstring);

                WindowManager Wmanager=(WindowManager)getSystemService(WINDOW_SERVICE);
                Display display=Wmanager.getDefaultDisplay();
                Point point=new Point();
                display.getSize(point);
                int width= point.x;
                int heigth= point.y;
                int smalldimension=width<heigth?width:heigth;
                QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrstring,
                        null,
                        Contents.Type.TEXT,
                        BarcodeFormat.QR_CODE.toString(),
                        smalldimension);
                try {
                    Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
                    ImageView myImage = (ImageView) findViewById(R.id.QrCode1);
                    myImage.setImageBitmap(bitmap);

                } catch (WriterException e) {
                    e.printStackTrace();
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
