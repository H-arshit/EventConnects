package com.TheEventWelfare.EventConnects;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    EditText forgotemail , forgotcontact , resentpasswprd , resetconfirmpassword ;
    Button forgotbutton;
    static boolean request_change_password;
    static ProgressDialog progressbar;
    static UserDetails userDetails;
    private String result="";
    TextView logoreset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getWindow().setBackgroundDrawableResource(R.drawable.backgound);

        logoreset = (TextView)findViewById(R.id.logoreset);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Pacifico-Regular.ttf");
        logoreset.setTypeface(custom_font);


        progressbar=new ProgressDialog(this);
        progressbar.setTitle("Authenticating");
        progressbar.setMessage("Please Wait");
        progressbar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressbar.setCancelable(false);


        request_change_password = false;
        forgotbutton = (Button)findViewById(R.id.forgotbutton);
        userDetails = new UserDetails();

        forgotemail = (EditText)findViewById(R.id.forgotemail);
        forgotcontact = (EditText)findViewById(R.id.forgotcontact);
        resentpasswprd = (EditText)findViewById(R.id.resentpasswprd);
        resetconfirmpassword = (EditText)findViewById(R.id.resetconfirmpassword);




      //  getSupportLoaderManager().initLoader(R.id.loader_resetpassword,null,loaderCallbacks);

        forgotbutton.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {


        if (primarycheck())
        {
                progressbar.show();
                filliserdetails();
                getSupportLoaderManager().restartLoader(R.id.loader_resetpassword,null,loaderCallbacks);
        }

    }

    private void filliserdetails()
    {
        userDetails = new UserDetails();

        String passwordenc = MDEncryptionCode.md5(resentpasswprd.getText().toString());

        if (passwordenc.isEmpty())
        {
            userDetails.setPassword(resentpasswprd.getText().toString());
        }

        else
        {
            userDetails.setPassword(passwordenc);

        }
        userDetails.setEmailid(forgotemail.getText().toString());
        userDetails.setContact(forgotcontact.getText().toString());
        request_change_password = true;
    }

    private boolean primarycheck() {
        boolean validate = true ;

        if (forgotemail.getText().toString().length() == 0 || forgotcontact.getText().toString().length() == 0 
                || resentpasswprd.getText().toString().length() == 0 || resetconfirmpassword.getText().toString().length() == 0)
        {
            Toast.makeText(this,"Incomplete Fields",Toast.LENGTH_SHORT).show();
            validate = false;
        }
        else if (resentpasswprd.getText().toString().length() >10 || resentpasswprd.getText().toString().length() < 4 )
        {
            Toast.makeText(this,"Password length 4 to 10 alphanumeric characters",Toast.LENGTH_SHORT).show();
            validate = false;

        }
        else if (!resentpasswprd.getText().toString().equals(resetconfirmpassword.getText().toString()))
        {
            validate = false;
            Toast.makeText(this,"Passwords Do Not Match",Toast.LENGTH_SHORT).show();

        }


        return validate;

    }


    LoaderManager.LoaderCallbacks<String> loaderCallbacks = new LoaderManager.LoaderCallbacks<String>() {
        @Override
        public Loader<String> onCreateLoader(int id, Bundle args) {
            return new TaskToResetPassword(getApplicationContext());
        }

        @Override
        public void onLoadFinished(Loader<String> loader, String data) {


            result = data;

            getSupportLoaderManager().destroyLoader(R.id.loader_resetpassword);


            displayresult();

        }

        @Override
        public void onLoaderReset(Loader<String> loader) {

        }
    };

    private void displayresult()
    {
        progressbar.dismiss();
        if (result.equals(" "))
        {
            return;
        }
        Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
        if (result.equals("Password Reset Successful"))
        {
            Intent signin=new Intent(ForgotPassword.this,SigninPage.class);
            startActivity(signin);

        }

    }

}
