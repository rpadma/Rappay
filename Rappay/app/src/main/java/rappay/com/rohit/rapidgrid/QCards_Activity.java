package rappay.com.rohit.rapidgrid;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import rappay.com.rohit.rapidgrid.Adapter.RecyclerViewAdapter;
import rappay.com.rohit.rapidgrid.Listeners.RecyclerViewOnItemClickListener;

public class QCards_Activity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qcards);

        initializeActivity();
    }

    public void GetQrCardDetails(View view) {
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
               // Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
              // if (resultCode == Activity.RESULT_OK) {
                String cardnumber="";
                try
                {

                    JSONArray jsonarrayobj=new JSONArray(result.getContents());

                    JSONObject objjsonobj=new JSONObject();

                    objjsonobj=jsonarrayobj.getJSONObject(1);

                    cardnumber=objjsonobj.getString("ccardnumber");


                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }


                   addDecodedStringToAdapter("QCardNumber:"+cardnumber);
               // }
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void initializeActivity() {
        initializeClickListeners();
        initializeRecyclerLayoutManager();
        initializeRecyclerAdapter();
        initializeRecycler();
        setNoDataVisibility();
    }

    private void addDecodedStringToAdapter(String decodedValue) {
        adapter.addItem(decodedValue);
        setNoDataVisibility();
    }
    private void initializeRecycler() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //This flag is used to improve the performance of the Recycler. This should only
        //be set to true if you know that changes in content does not change the size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    private void initializeRecyclerAdapter() {
        adapter = new RecyclerViewAdapter(recyclerViewOnItemClickListener);
    }

    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener = new RecyclerViewOnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
                  removeItemFromAdapter(position);
        }
    };
    private void removeItemFromAdapter(int position) {
        adapter.removeItem(position);
       setNoDataVisibility();
    }

    private void initializeRecyclerLayoutManager() {
        layoutManager = new LinearLayoutManager(this);
    }
    private void initializeClickListeners() {
        findViewById(R.id.addActionButton).setOnClickListener(fabClickListener);
    }

    private View.OnClickListener fabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           // startActivityForResult(new Intent(QCards_Activity.this, CaptureActivity.class), REQUEST_CODE);
            new IntentIntegrator(QCards_Activity.this).setCaptureActivity(ToolbarCaptureActivity.class).initiateScan();
        }
    };


    private void setNoDataVisibility() {
        View noDataLayout = findViewById(R.id.no_data_layout);

        if (adapter.hasData()) {
            noDataLayout.setVisibility(View.INVISIBLE);
        } else {
            noDataLayout.setVisibility(View.VISIBLE);
        }
    }
}
