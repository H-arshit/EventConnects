package com.TheEventWelfare.EventConnects;


import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class InternallySavedCard extends Fragment implements View.OnClickListener {

    TextView registertitle,registertype,registerVenue,registerPrice,registertime,registerdate,registerorg,registercont,registeremail,registerlink,registerdescript;
    Button linkbutton , imagebutton , callbutton ;
    EventDetailsFromDatabase eventDetailsFromDatabase;
    public InternallySavedCard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview=  inflater.inflate(R.layout.fragment_view_selected_card_register, container, false);

        Bundle bundle =this.getArguments();
        eventDetailsFromDatabase = new EventDetailsFromDatabase();
        eventDetailsFromDatabase.setType(bundle.getString("Type"));
        eventDetailsFromDatabase.setTitle(bundle.getString("Title"));
        eventDetailsFromDatabase.setDates(bundle.getString("Dates"));
        eventDetailsFromDatabase.setTiming(bundle.getString("Time"));
        eventDetailsFromDatabase.setPrice(bundle.getString("Price"));
        eventDetailsFromDatabase.setVenue(bundle.getString("Venue"));
        eventDetailsFromDatabase.setDirectlink(bundle.getString("DirectLink"));
        eventDetailsFromDatabase.setWeblink(bundle.getString("Weblink"));
        eventDetailsFromDatabase.setContact(bundle.getString("Contact"));
        eventDetailsFromDatabase.setEmailid(bundle.getString("Emailid"));
        eventDetailsFromDatabase.setDescription(bundle.getString("Description"));
        eventDetailsFromDatabase.setOrganizer(bundle.getString("Organizer"));


        Typeface custom_font1 = Typeface.createFromAsset(getContext().getAssets(),  "fonts/NotoSerif-Regular.ttf");
        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(),  "fonts/BalooBhaijaan-Regular.ttf");


        linkbutton = (Button)myview.findViewById(R.id.linkbutton);
        imagebutton = (Button)myview.findViewById(R.id.imagebutton);
        callbutton = (Button)myview.findViewById(R.id.callbutton);
        imagebutton.setVisibility(View.GONE);

        registertitle = (TextView)myview.findViewById(R.id.registertitle);
        registertitle.setTypeface(custom_font);


        registertype= (TextView)myview.findViewById(R.id.registertype);
        registertype.setTypeface(custom_font1);


        registerVenue= (TextView)myview.findViewById(R.id.registerVenue);
        registerVenue.setTypeface(custom_font1);


        registerPrice= (TextView)myview.findViewById(R.id.registerPrice);
        registerPrice.setTypeface(custom_font1);

        registertime= (TextView)myview.findViewById(R.id.registertime);
        registertime.setTypeface(custom_font1);

        registerdate= (TextView)myview.findViewById(R.id.registerdate);
        registerdate.setTypeface(custom_font1);

        registerorg= (TextView)myview.findViewById(R.id.registerorg);
        registerorg.setTypeface(custom_font1);

        registercont= (TextView)myview.findViewById(R.id.registercont);
        registercont.setTypeface(custom_font1);

        registeremail= (TextView)myview.findViewById(R.id.registeremail);
        registeremail.setTypeface(custom_font1);

        registerlink= (TextView)myview.findViewById(R.id.registerlink);
        registerlink.setTypeface(custom_font1);

        registerdescript= (TextView)myview.findViewById(R.id.registerdescript);
        registerdescript.setTypeface(custom_font1);

        if (eventDetailsFromDatabase.getDirectlink().equals("N.A") || eventDetailsFromDatabase.getDirectlink().isEmpty())
        {
            linkbutton.setVisibility(View.GONE);
        }
        if (eventDetailsFromDatabase.getContact().equals("N.A") || eventDetailsFromDatabase.getContact().isEmpty())
        {
            callbutton.setVisibility(View.GONE);
        }
        String temp;
        registertitle.setText(eventDetailsFromDatabase.getTitle());
        temp = "TYPE : " + eventDetailsFromDatabase.getType();
        registertype.setText(temp);
        temp = "VENUE: " + eventDetailsFromDatabase.getVenue();
        registerVenue.setText(temp);
        temp = "PRICE : Rs " + eventDetailsFromDatabase.getPrice();
        registerPrice.setText(temp);
        temp = "TIME : " + eventDetailsFromDatabase.getTiming();
        registertime.setText(temp);
        temp = "DATE : " + eventDetailsFromDatabase.getDates();
        registerdate.setText(temp);
        temp =  "ORGANIZER : " + eventDetailsFromDatabase.getOrganizer();
        registerorg.setText(temp);
        temp = "Contact : " + eventDetailsFromDatabase.getContact();
        registercont.setText(temp);
        temp = "Email-Id : " + eventDetailsFromDatabase.getEmailid();
        registeremail.setText(temp);
        temp =  "Web Link : " + eventDetailsFromDatabase.getWeblink();
        registerlink.setText(temp);
        temp = "Description: " + eventDetailsFromDatabase.getDescription();
        registerdescript.setText(temp);


            linkbutton.setOnClickListener(this);
            callbutton.setOnClickListener(this);
        return myview;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.callbutton)
        {
            Uri uri;

            String phoneno = eventDetailsFromDatabase.getContact();
            phoneno = "tel:"+phoneno;
            uri=Uri.parse(phoneno);
            Intent i3=new Intent(Intent.ACTION_VIEW, uri);
            startActivity(i3);

        }
        else if (v.getId()==R.id.linkbutton)
        {
            Uri uri;
           String directbookinglink = eventDetailsFromDatabase.getDirectlink();
//            if (!directbookinglink.contains("http://"))
//            {
//                directbookinglink = "http://" + directbookinglink;
//            }

            uri=Uri.parse(directbookinglink);
            Intent i=new Intent(Intent.ACTION_VIEW, uri);
            startActivity(i);
        }
    }
}
