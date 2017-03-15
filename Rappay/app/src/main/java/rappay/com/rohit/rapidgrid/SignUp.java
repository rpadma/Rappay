package rappay.com.rohit.rapidgrid;

import android.app.ProgressDialog;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import rappay.com.rohit.rapidgrid.Http.ConnectionManager;
import rappay.com.rohit.rapidgrid.Http.HttpManager;
import rappay.com.rohit.rapidgrid.Http.HttpResponse;
import rappay.com.rohit.rapidgrid.Http.RequestParams;
import rappay.com.rohit.rapidgrid.Util.RegexValidator;

public class SignUp extends AppCompatActivity {

    protected final static String TAG = "LoginActivity";
    private EditText username,useremail,phone, password, repassword,fullname;
    private Button register;

    private TextView signup,oldUser;
    @Bind(R.id.signup_ipl_username) TextInputLayout ipl_username;
    @Bind(R.id.signup_ipl_fullname) TextInputLayout ipl_fullname;
    @Bind(R.id.signup_ipl_email) TextInputLayout ipl_email;
    @Bind(R.id.signup_ipl_phonenumber) TextInputLayout ipl_phone;
    @Bind(R.id.signup_ipl_pass) TextInputLayout ipl_password;
    @Bind(R.id.signup_ipl_repass) TextInputLayout ipl_repass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        init();

    }

    private void init(){
        username    = (EditText) findViewById(R.id.sign_up_username_et);
        phone       = (EditText) findViewById(R.id.sign_up_phone_et);
        fullname   = (EditText)findViewById(R.id.sign_up_fullname);
        useremail  =(EditText)findViewById(R.id.sign_up_email_et);
        password    = (EditText) findViewById(R.id.sign_up_password_et);
        repassword  = (EditText) findViewById(R.id.sign_up_repassword_et);
        oldUser     = (TextView) findViewById(R.id.sign_up_old_user_tv);
        signup      = (TextView) findViewById(R.id.sign_up_text_view);
        register    = (Button) findViewById(R.id.sign_up_register_btn);

        oldUser.setPaintFlags(oldUser.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        Linkify.addLinks(oldUser, Linkify.ALL);

        Typeface roboto_light = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
        username.setTypeface(roboto_light);
        fullname.setTypeface(roboto_light);
        phone.setTypeface(roboto_light);
        password.setTypeface(roboto_light);
        repassword.setTypeface(roboto_light);
        useremail.setTypeface(roboto_light);

        ipl_username.setTypeface(roboto_light);
        ipl_fullname.setTypeface(roboto_light);
        ipl_email.setTypeface(roboto_light);
        ipl_phone.setTypeface(roboto_light);
        ipl_password.setTypeface(roboto_light);
        ipl_repass.setTypeface(roboto_light);


        Typeface roboto_thin = Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf");
        signup.setTypeface(roboto_thin);
        oldUser.setTypeface(roboto_thin);

    }

    public void register(View view){

        String username_text, phone_text, pass_text, repass_text, fullname_text, useremail_text;

        username_text   = username.getText().toString();
        fullname_text  =  fullname.getText().toString();
        useremail_text  = useremail.getText().toString();
        phone_text      = phone.getText().toString();
        pass_text       = password.getText().toString();
        repass_text     = repassword.getText().toString();

        boolean validUsername   = RegexValidator.validateName(username_text);
        boolean validFullName  = RegexValidator.validateName(fullname_text);
        boolean validEmail   = RegexValidator.validateEmail(useremail_text);
        boolean validPhone      = RegexValidator.validatePhoneNumber(phone_text);
        boolean validPassword   = RegexValidator.validatePassword(pass_text);
        boolean passwordsMatch  = pass_text.equals(repass_text);


        if(!validUsername){
            username.setError("Invalid UserName");
        }

        if(!validPhone){
            phone.setError("Invalid Phone number");
        }

        if(!validPassword){
            password.setError("Invalid");
        }
        if(!passwordsMatch){
            repassword.setError("Doesn't Match");
        }

        if(!validFullName){
            fullname.setError("Invalid full Name");
        }

        if(!validEmail){
            useremail.setError("Invalid Email");
        }


        if(validUsername && validPassword && validPhone && passwordsMatch && validFullName && validEmail){
            // TODO
            //call async task to register the user
            RequestParams request = new RequestParams();
            request.setUri(App.getIp() + "api/register");
            request.setParam("Username", username_text);
            request.setParam("hashed_password", pass_text);
            request.setParam("mobile_number", phone_text);
            request.setParam("fullname", fullname_text);
            request.setParam("email",useremail_text);


            Log.v(TAG, "Request being sent with params " + request.getUri() + request.getParams().toString());


            if(ConnectionManager.isConnected(SignUp.this)){
                new AsyncSignUp().execute(request);
            }else{
                Snackbar.make(findViewById(R.id.sign_up_coordinator), "No Internet connection", Snackbar.LENGTH_SHORT).show();
            }
        }

    }


    public void backToLogin(View view){
        finish();
    }

    private class AsyncSignUp extends AsyncTask<RequestParams, Void, HttpResponse> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(SignUp.this );
            progressDialog.setTitle("Registering");
            progressDialog.show();
        }

        protected HttpResponse doInBackground(RequestParams... params) {

            HttpResponse response = null;
            try {
                response = HttpManager.postData(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.v(TAG, "Do background, async call goes to the server");
            return response;
        }

        protected void onPostExecute(HttpResponse result) {

            progressDialog.dismiss();
            if(result == null)
            {
                Snackbar.make(findViewById(R.id.sign_up_coordinator), "Error! Couldn't register", Snackbar.LENGTH_SHORT).show();
            }
            else if(result.getStatusCode() == 200){
                Snackbar.make(findViewById(R.id.sign_up_coordinator), "Successfully registered", Snackbar.LENGTH_LONG).show();
                Log.v(TAG, "Result is "+ result.getBody());
                finish();
            }else
            {
                Snackbar.make(findViewById(R.id.sign_up_coordinator), "Error! Couldn't register", Snackbar.LENGTH_SHORT).show();
            }
        }
    }




}
