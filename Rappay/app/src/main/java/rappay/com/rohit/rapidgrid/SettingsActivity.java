package rappay.com.rohit.rapidgrid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        RadioButton qcardnone = (RadioButton) findViewById(R.id.radioButton4);
        RadioButton qpaynone = (RadioButton) findViewById(R.id.radioButtonp3);
        RadioButton qpaymenttype = (RadioButton) findViewById(R.id.radioButtonpm);

        qcardnone.setChecked(true);
        qpaynone.setChecked(true);
        qpaymenttype.setChecked(true);
    }

}
