package rappay.com.rohit.rapidgrid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Trans_PlannerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener   {

    private String [] SaveCategory={"Food ","Entertainment","Travelling","OthersBills -mobile,electricity","Savings","Insurance & medical expenses"};
    Spinner ssaving;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans__planner);
         ssaving=(Spinner)findViewById(R.id.sg_savingcategory);

        ArrayAdapter<String> adapter_type1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,SaveCategory);
        ssaving.setAdapter(adapter_type1);
        ssaving.setOnItemSelectedListener(this);

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





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_savegoal, menu);
        return true;
    }





}



