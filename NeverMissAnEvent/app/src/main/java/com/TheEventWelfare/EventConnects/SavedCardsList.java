package com.TheEventWelfare.EventConnects;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SavedCardsList extends Fragment {

    RecyclerView recyclerviewforsavedcards;
    AdapterForSavedCards adapterForSavedCards;
    Context context;
    List<EventDetailsFromDatabase> mydatalist;
    LinearLayoutManager linearLayoutManager;
    InternalDatabaseForSavedCards internalDatabaseForSavedCards;




    public SavedCardsList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_saved_cards_list, container, false);

        mydatalist=new ArrayList<>();

        context = getContext();
        internalDatabaseForSavedCards = new InternalDatabaseForSavedCards(context);



        recyclerviewforsavedcards=(RecyclerView)myview.findViewById(R.id.recyclerviewforsavedcards);
        adapterForSavedCards = new AdapterForSavedCards(context,mydatalist);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerviewforsavedcards.setAdapter(adapterForSavedCards);
        recyclerviewforsavedcards.setLayoutManager(linearLayoutManager);
        workofgettingdata();
        return myview;
    }

    private void workofgettingdata() {
        Cursor alldata = internalDatabaseForSavedCards.getalldata();

        if (alldata.getCount() == 0)
        {
            Toast.makeText(context,"No Data Saved",Toast.LENGTH_LONG).show();
        }
        else
        {
            EventDetailsFromDatabase eventdata;
            while(alldata.moveToNext())
            {
                eventdata = new EventDetailsFromDatabase();
                eventdata.setId(alldata.getInt(0));
                eventdata.setType(alldata.getString(1));
                eventdata.setTitle(alldata.getString(2));
                eventdata.setVenue(alldata.getString(3));
                eventdata.setPrice(alldata.getString(4));
                eventdata.setDates(alldata.getString(5));
                eventdata.setTiming(alldata.getString(6));
                eventdata.setDirectlink(alldata.getString(7));
                eventdata.setOrganizer(alldata.getString(8));
                eventdata.setContact(alldata.getString(9));
                eventdata.setEmailid(alldata.getString(10));
                eventdata.setWeblink(alldata.getString(11));
                eventdata.setDescription(alldata.getString(12));

                mydatalist.add(eventdata);
            }
            adapterForSavedCards.notifyDataSetChanged();
        }

    }

}
