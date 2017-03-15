package rappay.com.rohit.rapidgrid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Echeq_GenerateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echeq__generate);

        Button btnshowcheck=(Button)findViewById(R.id.GenerateECheque);
        final EditText edittxt=(EditText)findViewById(R.id.gchk_Amount_ets);


        btnshowcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edittxt.getText().toString().equals("5000")) {
                    startActivity(new Intent(Echeq_GenerateActivity.this, ShowGcheck2.class));
                }
                else {
                    startActivity(new Intent(Echeq_GenerateActivity.this,ShowGcheque.class));
                }

                }
        });
    }
}
