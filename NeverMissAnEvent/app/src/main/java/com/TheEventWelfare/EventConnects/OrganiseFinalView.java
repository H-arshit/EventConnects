package com.TheEventWelfare.EventConnects;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrganiseFinalView extends Fragment implements View.OnClickListener {
    Context context;
    TextView outtype,outtitle,outvenue,outprice,outfromdate,outtodate,outfromtime,bookinglinkdirect,outtotime,outorg,outphone,outid,outlink,outdescript;
    Button savebutton,backbuttonreck;
    String typename="",Titlename="",Ven="",Price="",dates="",time="",orgname="",phonenum="",email="",weblink="",bookinglink="",descript="",pastimagecode="",upcomingimagecode="";


    static ProgressDialog progressbar;

    private String result;

    static UploadDetails uploadDetails;

    public OrganiseFinalView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myview= inflater.inflate(R.layout.fragment_organise_final_view, container, false);



        progressbar=new ProgressDialog(getActivity());
        progressbar.setTitle("Uploading");
        progressbar.setMessage("Please Wait");
        progressbar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressbar.setCancelable(false);


        result = " ";
        context=getContext();

        Bundle bundle=this.getArguments();

        outtype=(TextView)myview.findViewById(R.id.outtype);
        typename=bundle.getString("WorkType");
        if((typename==null) || typename.equals(""))
        {
            typename="N.A";
        }
        outtype.setText(typename);

        outtitle=(TextView)myview.findViewById(R.id.outtitle);
        Titlename=bundle.getString("TitleName");
        if(Titlename==null ||Titlename.equals(""))
        {
            Titlename="N.A";
        }
        outtitle.setText(Titlename);

        outvenue=(TextView)myview.findViewById(R.id.outvenue);
        Ven=bundle.getString("Venue");
        if(Ven==null || Ven.equals(""))
        {
            Ven="N.A";
        }
        outvenue.setText(Ven);

        outprice=(TextView)myview.findViewById(R.id.outprice);
        Price=bundle.getString("Price");
        if(Price==null || Price.equals(""))
        {
            Price="N.A";
        }
        outprice.setText(Price);

        outfromdate=(TextView) myview.findViewById(R.id.outfromdate);
        String fromdte=bundle.getString("fromdate");
        if(fromdte==null || fromdte.equals(""))
        {
            fromdte="N.A";
        }
        outfromdate.setText(fromdte);
        outtodate=(TextView) myview.findViewById(R.id.outtodate);
        String todte=bundle.getString("todate");
        if(todte==null || todte.equals(""))
        {
            todte="N.A";
        }
        outtodate.setText(todte);
        dates= fromdte + " - " + todte;

        outfromtime=(TextView)myview.findViewById(R.id.outfromtime);
        String fromtime=bundle.getString("fromtime");
        if(fromtime==null || fromtime.equals(""))
        {
            fromtime="N.A";
        }
        outfromtime.setText(fromtime);
        outtotime=(TextView)myview.findViewById(R.id.outtotime);
        String totime=bundle.getString("totime");
        if(totime==null || totime.equals(""))
        {
            totime="N.A";
        }
        outtotime.setText(totime);

        time=fromtime+" - "+totime;

        outorg=(TextView)myview.findViewById(R.id.outorg);
        orgname=bundle.getString("orgname");
        if(orgname==null || orgname.equals(""))
        {
            orgname="N.A";
        }
        outorg.setText(orgname);

        outphone=(TextView)myview.findViewById(R.id.outphone);
        phonenum=bundle.getString("phone");
        if(phonenum==null || phonenum.equals(""))
        {
            phonenum="N.A";
        }
        outphone.setText(phonenum);

        outid=(TextView)myview.findViewById(R.id.outid);
        email=bundle.getString("email");
        if(email == null || email.equals(""))
        {
           email="N.A";
        }
        outid.setText(email);

        outlink=(TextView)myview.findViewById(R.id.outlink);
        weblink=bundle.getString("weblink");
        if(weblink==null || weblink.equals(""))
        {
            weblink="N.A";
        }
        outlink.setText(weblink);

        bookinglinkdirect=(TextView)myview.findViewById(R.id.bookinglinkdirect);
        bookinglink=bundle.getString("directbookinglink");
        if(bookinglink==null || bookinglink.equals(""))
        {
            bookinglink="N.A";
        }
        bookinglinkdirect.setText(bookinglink);


            // base 64 strigns for the images if present


            pastimagecode=bundle.getString("pastimage");


            upcomingimagecode = bundle.getString("upcomingimage");





        outdescript=(TextView)myview.findViewById(R.id.outdescript);
        descript=bundle.getString("description");
        if(descript==null || descript.equals(""))
        {
            descript="N.A";
        }
        outdescript.setText(descript);

        backbuttonreck=(Button)myview.findViewById(R.id.backbuttonreck);
        backbuttonreck.setOnClickListener(this);

        savebutton=(Button)myview.findViewById(R.id.savebutton);
        savebutton.setOnClickListener(this);

        return myview;
    }


    public void performuploadtask()
    {




        final String PREF_NAME = "com.data.workshop.event";

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        String userid = sharedPreferences.getString("EMAIL_ID","");


        uploadDetails = new UploadDetails();
        uploadDetails.setTypename(typename);
        uploadDetails.setTitlename(Titlename);
        uploadDetails.setVen(Ven);
        uploadDetails.setPrice(Price);
        uploadDetails.setDates(dates);
        uploadDetails.setTime(time);
        uploadDetails.setOrgname(orgname);
        uploadDetails.setPhonenum(phonenum);
        uploadDetails.setEmail(email);
        uploadDetails.setWeblink(weblink);
        uploadDetails.setBookinglink(bookinglink);
        uploadDetails.setDescript(descript);
        uploadDetails.setPastimagecode(pastimagecode);
        uploadDetails.setUpcomingimagecode(upcomingimagecode);
        uploadDetails.setUserid(userid);




        getActivity().getSupportLoaderManager().restartLoader(R.id.loader_for_advertisement_request,null,loaderCallbacks);




    }



    LoaderManager.LoaderCallbacks<String> loaderCallbacks = new LoaderManager.LoaderCallbacks<String>() {
        @Override
        public Loader<String> onCreateLoader(int id, Bundle args) {
            return new TaskToUploadAdvertiserRequest(getActivity().getApplicationContext());
        }

        @Override
        public void onLoadFinished(Loader<String> loader, String data) {

            if (data!=null) {
                result = data;

                getActivity().getSupportLoaderManager().destroyLoader(R.id.loader_for_advertisement_request);

                displayresult();

            }
        }

        @Override
        public void onLoaderReset(Loader<String> loader) {

        }
    };


    private void transact()
    {
        if (result.equals("Upload Successful. We will have a short verification . You will be notified within 24 Hours"))
        {


            savebutton.setVisibility(View.GONE);
            backbuttonreck.setVisibility(View.GONE);

//            FragmentHolderForEventNews eventfrag = new FragmentHolderForEventNews();
//            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.frame, eventfrag);
//            fragmentTransaction.commit();


        }
    }


    private void displayresult()
    {

            progressbar.dismiss();

        Toast.makeText(getContext(),result, Toast.LENGTH_LONG).show();


        transact();

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.savebutton) {
            AlertDialog.Builder saveasker = new AlertDialog.Builder(getActivity());
            saveasker.setMessage("Are you Sure want to save the data.");
            saveasker.setCancelable(false);
            saveasker.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    dialog.cancel();

                    progressbar.show();

                    performuploadtask();


                }
            });
            saveasker.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(context, "Recheck your data", Toast.LENGTH_SHORT).show();
                    dialog.cancel();

                }
            });
            AlertDialog mysavealert = saveasker.create();
            mysavealert.setTitle("Confirm to Save");
            mysavealert.show();
        }
        else{
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.popBackStack();
        }
    }

}
