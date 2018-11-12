package com.TheEventWelfare.EventConnects;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountSettings extends Fragment implements View.OnClickListener {

    TextView settingemail;
    EditText settingname,settingcontact,settingpasswordold,settingpassword,settingconfirmpassword;
    Button updateprofilebutton;
    private String oldpswd = "",result = "";
    static UserDetails userDetails;
    static boolean status_request;
    static ProgressDialog progressbar;
    
    public AccountSettings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_account_settings, container, false);
        Snackbar.make(container,"Fill in the fields to be updated and the password to make changes",Snackbar.LENGTH_LONG).show();
        status_request = false;
        userDetails = new UserDetails();
        progressbar=new ProgressDialog(getActivity());
        progressbar.setTitle("Processing");
        progressbar.setMessage("Please Wait");
        progressbar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressbar.setCancelable(false);
        settingemail = (TextView)myview.findViewById(R.id.settingemail);
        settingname = (EditText)myview.findViewById(R.id.settingname);
        settingcontact = (EditText)myview.findViewById(R.id.settingcontact);
        settingpasswordold = (EditText)myview.findViewById(R.id.settingpasswordold);
        settingpassword = (EditText)myview.findViewById(R.id.settingpassword);
        settingconfirmpassword = (EditText)myview.findViewById(R.id.settingconfirmpassword);
        updateprofilebutton = (Button)myview.findViewById(R.id.updateprofilebutton);

        autofillthedata();

//// TODO: 19-06-2017
//        getActivity().getSupportLoaderManager().initLoader(R.id.loader_settings,null,loaderCallbacks);
        updateprofilebutton.setOnClickListener(this);

        if (!isNetworkAvailable())
        {
            shaowalertdialog();
        }

        return myview;
    }

    private void shaowalertdialog() {


        AlertDialog.Builder nointernet = new AlertDialog.Builder(getActivity());
        nointernet.setMessage("No Internet Available . \n Internet Connection Needed To Complete The Task");
        nointernet.setCancelable(false);
        nointernet.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

            }
        });
        AlertDialog nointernetalert = nointernet.create();
        nointernetalert.setTitle("Alert!!");
        nointernetalert.show();

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




    private void autofillthedata()
    {

        final String PREF_NAME = "com.data.workshop.event";

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        settingemail.setText(sharedPreferences.getString("EMAIL_ID",""));
        settingname.setText(sharedPreferences.getString("USERNAME",""));
        settingcontact.setText(sharedPreferences.getString("CONTACT",""));
        oldpswd = sharedPreferences.getString("PASSWORD","");
    }

    @Override
    public void onClick(View v) {


        if (chechupdates())
        {

            datatoupdate();
            progressbar.show();
            getActivity().getSupportLoaderManager().restartLoader(R.id.loader_settings,null,loaderCallbacks);

        }

    }

    private void datatoupdate() {
        userDetails = new UserDetails();

        userDetails.setEmailid(settingemail.getText().toString());
        userDetails.setContact(settingcontact.getText().toString());
        userDetails.setUsername(settingname.getText().toString());
        if (settingpassword.getText().toString().length()==0)
        {
            userDetails.setPassword(oldpswd);
        }
        else {


            String passwordenc = MDEncryptionCode.md5(settingpassword.getText().toString());

            if (passwordenc.isEmpty())
            {
                userDetails.setPassword(settingpassword.getText().toString());            }

            else
            {
                userDetails.setPassword(passwordenc);

            }

        }

        status_request = true;
    }

    private boolean chechupdates()
    {
        boolean validate = true;

        String passenc = MDEncryptionCode.md5(settingpasswordold.getText().toString());
        if (passenc.isEmpty())
        {
            passenc = settingpasswordold.getText().toString();
        }


        if (settingname.getText().toString().length()==0 || settingcontact.getText().toString().length() == 0)
        {
            validate = false;
            Toast.makeText(getContext(),"Incomplete Field For Username Or Contact",Toast.LENGTH_SHORT).show();
        }
        if (settingpasswordold.getText().toString().length() == 0 || !passenc.equals(oldpswd))
        {
            Toast.makeText(getContext(),"Invalid Password",Toast.LENGTH_SHORT).show();
            validate = false;
        }
        if (settingpassword.getText().toString().length()!=0 || settingconfirmpassword.getText().toString().length()!=0)
        {

            if (settingpassword.getText().toString().length() > 10 || settingpassword.getText().toString().length()< 4 )
            {
                Toast.makeText(getContext(),"Expected Password length between 4 to 10 alphanumeric characters",Toast.LENGTH_SHORT).show();
                validate = false;
            }

            else if (!settingconfirmpassword.getText().toString().equals(settingpassword.getText().toString()))
            {
                validate = false;
                Toast.makeText(getContext(),"New Passwords Do Not Match",Toast.LENGTH_SHORT).show();

            }

        }
        
        return validate;
    }



    LoaderManager.LoaderCallbacks<String> loaderCallbacks = new LoaderManager.LoaderCallbacks<String>() {
        @Override
        public Loader<String> onCreateLoader(int id, Bundle args) {
            return new TaskToChangeProfile(getActivity().getApplicationContext());
        }

        @Override
        public void onLoadFinished(Loader<String> loader, String data) {

            result = data;
            displayresult();

            getActivity().getSupportLoaderManager().destroyLoader(R.id.loader_settings);


        }

        @Override
        public void onLoaderReset(Loader<String> loader) {



        }
    };



    private void displayresult() {
        progressbar.dismiss();
        if (result.equals(" "))
        {
            return;
        }
        Toast.makeText(getContext(),result,Toast.LENGTH_SHORT).show();
        if (result.equals("Update Successful"))
        {
            updateinternaldatabase();
        }
    }

    private void updateinternaldatabase()
    {
        final String PREF_NAME = "com.data.workshop.event";
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorffordata = sharedPreferences.edit();
        editorffordata.putString("USERNAME",userDetails.getUsername());
        editorffordata.putString("PASSWORD",userDetails.getPassword());
        editorffordata.putString("CONTACT",userDetails.getContact());
        editorffordata.putString("EMAIL_ID",userDetails.getEmailid());

        NavigationDrawerPage.usernamenav.setText(userDetails.getUsername());
        TextDrawable drawable = TextDrawable.builder().beginConfig()
                .bold()
                .toUpperCase()
                .endConfig()
                .buildRound(userDetails.getUsername().substring(0,1),  Color.parseColor("#338099"));
        NavigationDrawerPage.image.setImageDrawable(drawable);

        editorffordata.commit();


    }


}
