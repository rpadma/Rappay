package rappay.com.rohit.rapidgrid;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        Button btnViewTrans=(Button)findViewById(R.id.btnViewTransactions);
        Button btnViewGraphsTrans=(Button)findViewById(R.id.btnGraphs);
        Button btnViewPlannerTrans=(Button)findViewById(R.id.btnPlanner);
        Button btnSuggestionTrans=(Button)findViewById(R.id.btnSuggestion);


         btnViewTrans.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 startActivity(new Intent(TransactionActivity.this,Trans_MiniActivity.class));

             }
         });


        btnViewGraphsTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TransactionActivity.this,Trans_ViewGraphsActivity.class));
            }
        });


        btnViewPlannerTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TransactionActivity.this,Trans_PlannerActivity.class));
            }
        });

        btnSuggestionTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TransactionActivity.this,Trans_Suggestions.class));
            }
        });

    }
}
