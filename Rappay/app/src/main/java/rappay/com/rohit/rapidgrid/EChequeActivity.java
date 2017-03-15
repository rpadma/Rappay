package rappay.com.rohit.rapidgrid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EChequeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echeque);


        Button btnGenerateChk=(Button)findViewById(R.id.btnGcheque);
        Button btnShareChk=(Button)findViewById(R.id.btnShareCheque);
        Button btnInChk=(Button)findViewById(R.id.btninCheque);
        Button btnOutChk=(Button)findViewById(R.id.btnoutCheque);


        btnGenerateChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(EChequeActivity.this,Echeq_GenerateActivity.class));
            }
        });


        btnInChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EChequeActivity.this,Echeq_InChequeActivity.class));
            }
        });

        btnOutChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EChequeActivity.this ,Echeq_OutChequeActivity.class));
            }
        });

        btnShareChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EChequeActivity.this,Echeq_ShareActivity.class
                ));
            }
        });

    }
}
