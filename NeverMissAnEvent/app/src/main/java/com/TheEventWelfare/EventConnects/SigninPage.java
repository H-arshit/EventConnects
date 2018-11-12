package com.TheEventWelfare.EventConnects;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class SigninPage extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CHECK_CONDITION_TAG";
    EditText usernameinput,passwordinput;
    Button signinbutton;
    TextView logosignin;
    TextView createidtext,forgotpaswd;
    static ProgressDialog progressbar;
    static boolean READY_TO_CHECK = false;
    UserDetails userDetails;
    static String emailid = "" , passwordchech = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_page);
        userDetails = new UserDetails();
        getWindow().setBackgroundDrawableResource(R.drawable.backgound);


        logosignin = (TextView)findViewById(R.id.logosignin);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Pacifico-Regular.ttf");
        logosignin.setTypeface(custom_font);



        progressbar=new ProgressDialog(this);
        progressbar.setTitle("Authenticating");
        progressbar.setMessage("Please Wait");
        progressbar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressbar.setCancelable(false);

        passwordinput=(EditText)findViewById(R.id.passwordinput);
        usernameinput=(EditText)findViewById(R.id.usernameinput);

        signinbutton=(Button) findViewById(R.id.signinbutton);

        createidtext=(TextView) findViewById(R.id.createidtext);
        forgotpaswd=(TextView)findViewById(R.id.forgotpaswd);

        usernameinput.setText("");
        passwordinput.setText("");
        emailid = usernameinput.getText().toString();
        passwordchech = passwordinput.getText().toString();



//        getSupportLoaderManager().initLoader(R.id.loaderforlogin,null,loaderCallbacks);


        signinbutton.setOnClickListener(SigninPage.this);
        createidtext.setOnClickListener(SigninPage.this);
        forgotpaswd.setOnClickListener(SigninPage.this);



    }


    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.signinbutton:
            {
                progressbar.show();


                emailid = usernameinput.getText().toString();
                passwordchech = passwordinput.getText().toString();

                passwordchech = MDEncryptionCode.md5(passwordchech);

                if (passwordchech.isEmpty())
                {
                    passwordchech = passwordinput.getText().toString();

                }

                if (emailid.isEmpty() || passwordchech.isEmpty())
                {
                    Toast.makeText(this,"Fill all the Fields",Toast.LENGTH_LONG).show();
                    progressbar.dismiss();
                    break;
                }
                getSupportLoaderManager().restartLoader(R.id.loaderforlogin,null,loaderCallbacks);

//                Intent intent = new Intent(TaskToLogin.ACTION_BROADCAST_LOGIN);
//                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);


                break;
            }
                case R.id.createidtext:
                Intent createid=new Intent(SigninPage.this,CreateUserId.class);
                    createid.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    createid.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    createid.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(createid);
                break;
            case R.id.forgotpaswd:

                Intent forgotpassword=new Intent(SigninPage.this,ForgotPassword.class);
                startActivity(forgotpassword);
                break;
        }


    }
    public boolean signindataiscorrect()
    {


        boolean validdata;
        if (userDetails == null)
        {
            validdata = false;
            passwordinput.setText("");
            Toast.makeText(this,"Network Error",Toast.LENGTH_LONG).show();
        }
        else if (userDetails.getUsername().isEmpty())
        {
            validdata = false;
            passwordinput.setText("");

            Toast.makeText(this,"Incorrect Email-Id or Password",Toast.LENGTH_LONG).show();

        }
        else {
            validdata = true;
        }
        return validdata;
    }


    private void authenticationprocess()
    {
//        if (usernameinput.getText().toString().isEmpty())
//        {
//            READY_TO_CHECK = false;
//            return ;
//        }
        if(READY_TO_CHECK)
        {

            READY_TO_CHECK = false;
            if (signindataiscorrect()) {
                progressbar.dismiss();


                final String PREF_NAME = "com.data.workshop.event";
                SharedPreferences sharedPreferences = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editorffordata = sharedPreferences.edit();

                editorffordata.putString("USERNAME",userDetails.getUsername());
                editorffordata.putString("PASSWORD",userDetails.getPassword());
                editorffordata.putString("CONTACT",userDetails.getContact());
                editorffordata.putString("EMAIL_ID",userDetails.getEmailid());
                editorffordata.commit();



                Intent navpage = new Intent(SigninPage.this, NavigationDrawerPage.class);

                navpage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                navpage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                navpage.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(navpage);
            }
            else {
                progressbar.dismiss();

            }
        }

    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            moveTaskToBack(true);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this,"Press Back Again To Exit",Toast.LENGTH_LONG).show();


        //moveTaskToBack(true);
    }

    private LoaderManager.LoaderCallbacks<UserDetails> loaderCallbacks = new LoaderManager.LoaderCallbacks<UserDetails>() {
        @Override
        public Loader<UserDetails> onCreateLoader(int id, Bundle args) {

            return new TaskToLogin(getApplicationContext());
        }

        @Override
        public void onLoadFinished(Loader<UserDetails> loader, UserDetails data) {


                        userDetails = data;
                        READY_TO_CHECK = true;

                        getSupportLoaderManager().destroyLoader(R.id.loaderforlogin);

                    authenticationprocess();
        }

        @Override
        public void onLoaderReset(Loader<UserDetails> loader) {

        }
    };
}
