package rappay.com.rohit.rapidgrid;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class Trans_ViewGraphsActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private String [] montlytype={"Earning Vs Expenditure","Savings","Category level","Saving Allocation"};
    private String[] yearlytype={"Categories","Earning Vs Expenditure","Saving level","Vendor level"};
    private String[] Types={"Yearly","Montly","Weekly"};
    private String [] weeklytype={"Weekly Expenditure"};

    Spinner Type1,Type2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans__view_graphs);

        Type1=(Spinner)findViewById(R.id.graphs_duration);


        ArrayAdapter<String> adapter_type1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Types);
        Type1.setAdapter(adapter_type1);
        Type1.setOnItemSelectedListener(this);


        Button btnshowgraphs =(Button)findViewById(R.id.showgraph);

        btnshowgraphs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int postype1=Type1.getSelectedItemPosition();
                int postype2=Type2.getSelectedItemPosition();

                Intent i;

                if(postype1==0)                           //yearly
                {
                    if(postype2==0) //yearly category
                    {
                        startActivity(new Intent(Trans_ViewGraphsActivity.this,YearlySavings.class));
                    }
                    else if(postype2==1)
                    {
                        startActivity(new Intent(Trans_ViewGraphsActivity.this,YearlyEarningVsExp.class));
                    }else if(postype2==2)
                    {

                    }
                     else
                    {
                        startActivity(new Intent(Trans_ViewGraphsActivity.this,YearlyVendors.class));
                    }

                }
                else if(postype1==1)     //montly
                    {
                          if(postype2==0)
                          {

                          } else if(postype2==1)             //Savings
                          {
                              startActivity(new Intent(Trans_ViewGraphsActivity.this,MontlySaving.class));


                          } else if(postype2==2)
                          {

                          } else
                          {

                          }

                    }
                else {

                    //weekly

                    }



            }
        });




    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        String [] ArrayType2name=new String[4];

switch(item)
        {
            case "Yearly" :ArrayType2name=yearlytype;
                           break;
            case "Montly" :ArrayType2name=montlytype;
                           break;
            case "Weekly" : ArrayType2name=weeklytype;
                default:
                    ArrayType2name=new String[]{"--None--"};
        }

        Type2=(Spinner)findViewById(R.id.graphs_type);
        ArrayAdapter<String> adapter_type2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, ArrayType2name);
        Type2.setAdapter(adapter_type2);
      //  Type1.setOnItemSelectedListener(this);


    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }





}
