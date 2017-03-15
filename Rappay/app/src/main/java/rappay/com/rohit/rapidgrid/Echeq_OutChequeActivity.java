package rappay.com.rohit.rapidgrid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.andexert.expandablelayout.library.ExpandableLayoutListView;

public class Echeq_OutChequeActivity extends AppCompatActivity {


    private final String[] array = {"Cheque Number:X9853       Issued to:rohit", "Cheque Number:X8962       Issued to:Krishna", "Cheque Number:X5689      Issued to:ram"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echeq__out_cheque);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.zout_row, R.id.header_text1, array);
        final ExpandableLayoutListView expandableLayoutListView = (ExpandableLayoutListView) findViewById(R.id.outchk_listview);

        expandableLayoutListView.setAdapter(arrayAdapter);
    }
}
