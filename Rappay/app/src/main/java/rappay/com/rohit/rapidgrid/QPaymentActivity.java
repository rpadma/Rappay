package rappay.com.rohit.rapidgrid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class QPaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qpayment);

        Button btnqpay=(Button)findViewById(R.id.btnQpay);
        Button btnqcards=(Button)findViewById(R.id.btnQcards);
        Button btnqcardscan=(Button)findViewById(R.id.btnQcardscanner);
        Button btngbill=(Button)findViewById(R.id.btnGBill);


        btnqpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(QPaymentActivity.this,QPay_Activity.class);
                 startActivity(intent);
            }

        });

        btnqcards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(QPaymentActivity.this,QCards_Activity.class);
                startActivity(intent);
            }
        });

        btnqcardscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(QPaymentActivity.this,QCardsScan_Activity.class);
                startActivity(intent);
            }
        });

        btngbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(QPaymentActivity.this,GBill_Activity.class);
                startActivity(intent);
            }
        });
    }
}
