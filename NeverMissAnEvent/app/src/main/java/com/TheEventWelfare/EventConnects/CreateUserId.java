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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateUserId extends AppCompatActivity implements View.OnClickListener {

    private EditText createidfirstname,createduserid,createduserpassword,reenteredpassword,createphoneno;
    private Button createidbutton;
    private  TextView alreadyregistered;
    static UserDetails userDetailsforId;
    private String resultstring;
    static ProgressDialog progressbar;
    TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_id);

        userDetailsforId = new UserDetails();

        getWindow().setBackgroundDrawableResource(R.drawable.backgound);


        logo = (TextView)findViewById(R.id.logo);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Pacifico-Regular.ttf");
        logo.setTypeface(custom_font);



        progressbar=new ProgressDialog(this);
        progressbar.setTitle("Authenticating");
        progressbar.setMessage("Please Wait");
        progressbar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressbar.setCancelable(false);


        resultstring = "";
        createidfirstname=(EditText)findViewById(R.id.createidfirstname);
        createduserid=(EditText)findViewById(R.id.createduserid);
        createduserpassword=(EditText)findViewById(R.id.createduserpassword);
        reenteredpassword=(EditText)findViewById(R.id.reenteredpassword);
        createphoneno=(EditText)findViewById(R.id.createphoneno);
        alreadyregistered=(TextView)findViewById(R.id.alreadyregistered);
        createidbutton=(Button)findViewById(R.id.createidbutton);
    //// TODO: 19-06-2017  
//        getSupportLoaderManager().initLoader(R.id.loader_for_createid,null,loaderCallbacks);

        taskToAutoLogin();

        alreadyregistered.setOnClickListener(CreateUserId.this);
        createidbutton.setOnClickListener(CreateUserId.this);
    }


    private void taskToAutoLogin()
    {
        final String PREF_NAME = "com.data.workshop.event";

        SharedPreferences sharedPreferences = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String loginId = sharedPreferences.getString("EMAIL_ID","");
        String password = sharedPreferences.getString("PASSWORD","");
        if (loginId.length()>0 && password.length()>0)
        {

            Intent navpage = new Intent(CreateUserId.this, NavigationDrawerPage.class);
            navpage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            navpage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            navpage.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

            startActivity(navpage);
        }


    }



    private boolean isvalidemailid(String email)
    {

        String validemail = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +

                "\\@" +

                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +

                "(" +

                "\\." +

                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +

                ")+";



        Matcher matcher= Pattern.compile(validemail).matcher(email);


        if (!matcher.matches()){

            Toast.makeText(getApplicationContext(),"Enter Valid Email-Id",Toast.LENGTH_LONG).show();
            return false;
        }

            return true;

    }



    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.createidbutton:
              if (createidconstraints())
              {


                  progressbar.show();

                     userDetailsforId.setUsername(createidfirstname.getText().toString());

                  String passwenc = MDEncryptionCode.md5(createduserpassword.getText().toString());
                  if (passwenc.isEmpty())
                  {
                      userDetailsforId.setPassword(createduserpassword.getText().toString());
                  }
                  else {
                      userDetailsforId.setPassword(passwenc);
                  }

                     userDetailsforId.setContact(createphoneno.getText().toString());
                     userDetailsforId.setEmailid(createduserid.getText().toString());
                  getSupportLoaderManager().restartLoader(R.id.loader_for_createid,null,loaderCallbacks);
              }
                break;
            case R.id.alreadyregistered:

                Intent signin=new Intent(CreateUserId.this,SigninPage.class);
                signin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                signin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                signin.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(signin);

                break;
        }


    }
    private boolean createidconstraints()
    {
        Boolean isvalid=true;
        String name=createidfirstname.getText().toString();
        if(name.isEmpty())
        {
            isvalid=false;
            createidfirstname.setError("Enter a Valid Name");
        }
        else
        {
            createidfirstname.setError(null);
        }

        String id=createduserid.getText().toString();

        isvalid = isvalidemailid(id);

        if(id.isEmpty())
        {
            isvalid=false;
            createduserid.setError("Enter a valid Email-Id");
        }
        else
        {
            createduserid.setError(null);
        }
        String password=createduserpassword.getText().toString();
        if (password.isEmpty() || password.length() > 10 || password.length() < 4) {
            isvalid=false;
            createduserpassword.setError("Between 4 and 10 Alphanumeric Characters");
        }
        else
        {
            createduserpassword.setError(null);
        }
        String repaswd=reenteredpassword.getText().toString();
        if(repaswd.isEmpty() || repaswd.length()!=password.length()|| !repaswd.equals(password))
        {

            isvalid=false;
            reenteredpassword.setError("Entered password does not match");

        }
        else {
            reenteredpassword.setError(null);

        }

        String phonenochk=createphoneno.getText().toString();
        if(phonenochk.isEmpty())
        {
            isvalid=false;

            createphoneno.setError("Enter A Valid Number");
        }
        else {
            createphoneno.setError(null);

        }

        return isvalid;


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

    private LoaderManager.LoaderCallbacks<String> loaderCallbacks = new LoaderManager.LoaderCallbacks<String>() {
        @Override
        public Loader<String> onCreateLoader(int id, Bundle args) {
            return new TaskToCreateId(getApplicationContext());
        }

        @Override
        public void onLoadFinished(Loader<String> loader, String data) {

             resultstring = data;
            getSupportLoaderManager().destroyLoader(R.id.loader_for_createid);

            authenticationprocess();
        }

        @Override
        public void onLoaderReset(Loader<String> loader) {

        }
    };

    private void authenticationprocess()
    {
        progressbar.dismiss();

        if(resultstring.equals("Correct Data")) {

            final String PREF_NAME = "com.data.workshop.event";
            SharedPreferences sharedPreferences = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editorffordata = sharedPreferences.edit();
            editorffordata.putString("USERNAME",userDetailsforId.getUsername());
            editorffordata.putString("PASSWORD",userDetailsforId.getPassword());
            editorffordata.putString("CONTACT",userDetailsforId.getContact());
            editorffordata.putString("EMAIL_ID",userDetailsforId.getEmailid());
            editorffordata.commit();



            Intent navpage = new Intent(CreateUserId.this, NavigationDrawerPage.class);
            navpage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            navpage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            navpage.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(navpage);
        }
        else{
            if (!resultstring.equals(" "))
            Toast.makeText(this,resultstring,Toast.LENGTH_LONG).show();

        }
    }


}
