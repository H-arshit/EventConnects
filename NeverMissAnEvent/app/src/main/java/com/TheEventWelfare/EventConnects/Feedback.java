package com.TheEventWelfare.EventConnects;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Feedback extends Fragment implements View.OnClickListener {

    EditText feedbackreport;
    Button feedbackbutton;
    static String report = "";
    static String userid= "";
    static boolean status_request;
    private String result = "";

    static ProgressDialog progressbar;
    public Feedback() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_feedback, container, false);


        progressbar=new ProgressDialog(getActivity());
        progressbar.setTitle("Processing");
        progressbar.setMessage("Please Wait");
        progressbar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressbar.setCancelable(false);

        feedbackreport=(EditText)myview.findViewById(R.id.feedbackreport);
        feedbackbutton=(Button)myview.findViewById(R.id.feedbackbutton);
        status_request = false;


        //getActivity().getSupportLoaderManager().initLoader(R.id.loader_for_feedback,null,loaderCallbacks);
        feedbackbutton.setOnClickListener(this);



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




    @Override
    public void onClick(View v) {

        if(feedbackreport.getText().toString().isEmpty())
        {
            Toast.makeText(getActivity(),"Please Fill In The Field",Toast.LENGTH_SHORT).show();
            status_request = false;
        }
        else {
            progressbar.show();

            report = feedbackreport.getText().toString();
            final String PREF_NAME = "com.data.workshop.event";
            SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            userid = sharedPreferences.getString("EMAIL_ID","");
            status_request = true;
            getActivity().getSupportLoaderManager().restartLoader(R.id.loader_for_feedback,null,loaderCallbacks);
        }
    }


    LoaderManager.LoaderCallbacks<String> loaderCallbacks = new LoaderManager.LoaderCallbacks<String>() {
        @Override
        public Loader<String> onCreateLoader(int id, Bundle args) {
            return new TaskOfFeedback(getActivity().getApplicationContext());
        }

        @Override
        public void onLoadFinished(Loader<String> loader, String data) {

            result = data;
            getActivity().getSupportLoaderManager().destroyLoader(R.id.loader_for_feedback);
            display();


        }

        @Override
        public void onLoaderReset(Loader<String> loader) {

        }
    };

    private void display()
    {
        progressbar.dismiss();
        if (result.equals(" "))
        {
            return;
        }

        Toast.makeText(getContext(),result,Toast.LENGTH_SHORT).show();
        feedbackreport.setText("");
        status_request = false;
    }




}



