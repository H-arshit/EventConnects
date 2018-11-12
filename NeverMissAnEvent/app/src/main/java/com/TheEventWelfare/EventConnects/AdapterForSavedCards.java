package com.TheEventWelfare.EventConnects;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class AdapterForSavedCards extends RecyclerView.Adapter<AdapterForSavedCards.SavedHolder> {

    private Context context;
    private List<EventDetailsFromDatabase> mydatalist;
    public AdapterForSavedCards(Context context,List<EventDetailsFromDatabase> mydatalist)
    {

        this.context=context;
        this.mydatalist=mydatalist;
    }


    @Override

     public AdapterForSavedCards.SavedHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View myownview = LayoutInflater.from(context).inflate(R.layout.cardviewforregister,parent,false);
        return new SavedHolder(myownview);

     }

    @Override
    public void onBindViewHolder(AdapterForSavedCards.SavedHolder holder, int position) {
        holder.workshoptitle.setText(mydatalist.get(position).getTitle());
        holder.organizingcomitte.setText(mydatalist.get(position).getOrganizer());
        holder.workshopdate.setText(mydatalist.get(position).getDates());
    }

    @Override
    public int getItemCount() {
        return mydatalist.size();
    }

    public class SavedHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
        private Context context;
        TextView workshoptitle,workshopdate,organizingcomitte;
        CardView mycardview;

        public SavedHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();
            Typeface custom_font1 = Typeface.createFromAsset(context.getAssets(),  "fonts/NotoSerif-Regular.ttf");
            Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/BalooBhaijaan-Regular.ttf");


            workshoptitle=(TextView)itemView.findViewById(R.id.workshoptitle);
            workshoptitle.setTypeface(custom_font);

            workshopdate=(TextView)itemView.findViewById(R.id.workshopdate);
            workshopdate.setTypeface(custom_font1);
            organizingcomitte=(TextView)itemView.findViewById(R.id.organizingcomitte);
            organizingcomitte.setTypeface(custom_font1);

            mycardview=(CardView)itemView.findViewById(R.id.mycardview);
            mycardview.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }


        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            Bundle dataforcard=new Bundle();
            dataforcard.putString("Title",mydatalist.get(position).getTitle());
            dataforcard.putString("Type",mydatalist.get(position).getType());
            dataforcard.putString("Venue",mydatalist.get(position).getVenue());
            dataforcard.putString("Price",mydatalist.get(position).getPrice());
            dataforcard.putString("Dates",mydatalist.get(position).getDates());
            dataforcard.putString("Time",mydatalist.get(position).getTiming());
            dataforcard.putString("DirectLink",mydatalist.get(position).getDirectlink());
            dataforcard.putString("Organizer",mydatalist.get(position).getOrganizer());
            dataforcard.putString("Contact",mydatalist.get(position).getContact());
            dataforcard.putString("Emailid",mydatalist.get(position).getEmailid());
            dataforcard.putString("Weblink",mydatalist.get(position).getWeblink());
            dataforcard.putString("Description",mydatalist.get(position).getDescription());

            NavigationDrawerPage myActivity = (NavigationDrawerPage) context;
            InternallySavedCard fragment = new InternallySavedCard();
            fragment.setArguments(dataforcard);
            FragmentManager fragmentManager =myActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.commit();
        }

        
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Share = menu.add(Menu.NONE,R.id.sharecard_id, 1, "Share");
            MenuItem Delete = menu.add(Menu.NONE, R.id.deletesaved_card, 2, "Delete");
            Share.setOnMenuItemClickListener(contextualmenu);
            Delete.setOnMenuItemClickListener(contextualmenu);

        }
        private String getdataforsharecard()
        {

            int position = getAdapterPosition();
            EventDetailsFromDatabase eventDetailsFromDatabase = mydatalist.get(position);

            String dataforcard = "";

            String temp = eventDetailsFromDatabase.getTitle();
            dataforcard = dataforcard + temp + "\n";
            temp = "TYPE : " + eventDetailsFromDatabase.getType();
            dataforcard = dataforcard + temp + "\n";
            temp = "VENUE: " + eventDetailsFromDatabase.getVenue();
            dataforcard = dataforcard + temp + "\n";
            temp = "PRICE : Rs " + eventDetailsFromDatabase.getPrice();
            dataforcard = dataforcard + temp + "\n";
            temp = "TIME : " + eventDetailsFromDatabase.getTiming();
            dataforcard = dataforcard + temp + "\n";
            temp = "DATE : " + eventDetailsFromDatabase.getDates();
            dataforcard = dataforcard + temp + "\n";
            temp =  "ORGANIZER : " + eventDetailsFromDatabase.getOrganizer();
            dataforcard = dataforcard + temp + "\n";
            temp = "Contact : " + eventDetailsFromDatabase.getContact();
            dataforcard = dataforcard + temp + "\n";
            temp = "Email-Id : " + eventDetailsFromDatabase.getEmailid();
            dataforcard = dataforcard + temp + "\n";
            temp =  "Web Link : " + eventDetailsFromDatabase.getWeblink();
            dataforcard = dataforcard + temp + "\n";
            temp = "Description: " + eventDetailsFromDatabase.getDescription();
            dataforcard = dataforcard + temp + "\n";
            temp = "Booking Link : "+ eventDetailsFromDatabase.getDirectlink();
            dataforcard = dataforcard + temp + "\n";


            return dataforcard;
        }

        private final MenuItem.OnMenuItemClickListener contextualmenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                
                switch (item.getItemId()) {
                    case R.id.sharecard_id:
                        Intent sendcard=new Intent();
                        sendcard.setAction(Intent.ACTION_SEND);
                        sendcard.putExtra(Intent.EXTRA_TEXT,getdataforsharecard());
                        sendcard.setType("text/plain");
                        mycardview.getContext().startActivity(Intent.createChooser(sendcard,"Share card via"));
                        break;

                    case R.id.deletesaved_card:
                        int position = getAdapterPosition();
                        InternalDatabaseForSavedCards internalDatabaseForSavedCards = new InternalDatabaseForSavedCards(context);
                        boolean result = internalDatabaseForSavedCards.deletedata(String.valueOf( mydatalist.get(position).getId()));
                        if (result)
                        {
                            mydatalist.remove(position);
                            Toast.makeText(context,"Data Deleted",Toast.LENGTH_LONG).show();
                            notifyDataSetChanged();
                        }
                        else
                        {
                            Toast.makeText(context,"Unable to delete Please try again later",Toast.LENGTH_LONG).show();
                        }
                        break;
                }
                return true;
            }
        };
    }
}
