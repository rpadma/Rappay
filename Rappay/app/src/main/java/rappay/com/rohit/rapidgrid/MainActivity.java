package rappay.com.rohit.rapidgrid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homelayout);

       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // toolbar.setTitle("Home");

        Button  btnprofile=(Button)findViewById(R.id.btnprofile);
        Button  btnaccountsummary=(Button)findViewById(R.id.btnAccountsummary);
        Button  btntranscation=(Button)findViewById(R.id.btnTransaction);
        Button  btnQPay=(Button)findViewById(R.id.btnQpay);
        Button btnEcheque=(Button)findViewById(R.id.btnEcheque);

        //Profile

        btnprofile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);

            }
        });

        //Account Summary

        btnaccountsummary.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AccountActivity.class);
                startActivity(intent);

            }
        });

        //Transaction

        btntranscation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,TransactionActivity.class);
                startActivity(intent);

            }
        });


        // QPayment
        btnQPay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,QPaymentActivity.class);
                startActivity(intent);

            }
        });


        btnEcheque.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,EChequeActivity.class);
                startActivity(intent);

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_signout) {

            SaveSharedPreference ssp=new SaveSharedPreference();
            ssp.setUserName(MainActivity.this,false);
            finish();
            return true;
        }
        else if(id==R.id.action_settings)
        {
            startActivity(new Intent(MainActivity.this,SettingsActivity.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onBackPressed() {
/*
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
        */


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.home_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
/*

        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        finish();
                        System.exit(0);

                    }
                }).setNegativeButton("no", null).show();
                */

    }
}
