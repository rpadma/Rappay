package rappay.com.rohit.rapidgrid;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Trans_Suggestions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans__suggestions);

        Button btninterested=(Button)findViewById(R.id.show_suggestions);



        btninterested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(Trans_Suggestions.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Thanks for showing Interest!")
                        .setMessage("Our Agent will contact you shortly")
                        .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(Trans_Suggestions.this, TransactionActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            }
                        }).setNegativeButton("Stay here", null).show();
            }
        });

    }
}
