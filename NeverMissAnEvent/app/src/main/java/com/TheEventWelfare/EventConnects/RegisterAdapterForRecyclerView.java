package com.TheEventWelfare.EventConnects;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class RegisterAdapterForRecyclerView extends RecyclerView.Adapter<RegisterAdapterForRecyclerView.RegisterHolder> {

    private  Context context;
    private List<DataForRegisterPage> mydatalist;
    RegisterAdapterForRecyclerView(Context ctx, List<DataForRegisterPage> mydatalist)
    {

        context=ctx;
        this.mydatalist=mydatalist;

    }


    @Override
    public RegisterAdapterForRecyclerView.RegisterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View myownview= LayoutInflater.from(context).inflate(R.layout.cardviewforregister,parent,false);
        return new RegisterHolder(myownview);
    }

    @Override
    public void onBindViewHolder(RegisterAdapterForRecyclerView.RegisterHolder holder, int position) {
        holder.workshoptitle.setText(mydatalist.get(position).getTitlename());
        holder.organizingcomitte.setText(mydatalist.get(position).getOrgname());
        holder.workshopdate.setText(mydatalist.get(position).getDates());
    }

    @Override
    public int getItemCount() {
        return mydatalist.size();
    }

    public class RegisterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;
        TextView workshoptitle,workshopdate,organizingcomitte;
        CardView mycardview;

        public RegisterHolder(View itemView) {
            super(itemView);
            context=itemView.getContext();
            workshoptitle=(TextView)itemView.findViewById(R.id.workshoptitle);
            Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/BalooBhaijaan-Regular.ttf");
            workshoptitle.setTypeface(custom_font);

            Typeface custom_font1 = Typeface.createFromAsset(context.getAssets(),  "fonts/NotoSerif-Regular.ttf");

            workshopdate=(TextView)itemView.findViewById(R.id.workshopdate);
            workshopdate.setTypeface(custom_font1);

            organizingcomitte=(TextView)itemView.findViewById(R.id.organizingcomitte);
            organizingcomitte.setTypeface(custom_font1);

            mycardview=(CardView)itemView.findViewById(R.id.mycardview);
            mycardview.setOnClickListener(this);
          //  mycardview.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int position=getAdapterPosition();
            Bundle idnumber=new Bundle();
            idnumber.putInt("IDNUMBER",mydatalist.get(position).getId());

                NavigationDrawerPage myActivity = (NavigationDrawerPage) context;
                ViewSelectedCardRegister fragment = new ViewSelectedCardRegister();
                fragment.setArguments(idnumber);
                FragmentManager fragmentManager = myActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commit();


        }

//        @Override
//        public boolean onLongClick(View v) {
//            PopupMenu popup = new PopupMenu(v.getContext(), v);
//            popup.inflate(R.menu.sharecard);
//            popup.setOnMenuItemClickListener(this);
//            popup.show();
//            return true;
//        }

//        @Override
//        public boolean onMenuItemClick(MenuItem item) {
//            Intent sendcard=new Intent();
//            sendcard.setAction(Intent.ACTION_SEND);
//            sendcard.putExtra(Intent.EXTRA_TEXT,"This is my card");
//            sendcard.setType("text/plain");
//            mycardview.getContext().startActivity(Intent.createChooser(sendcard,"Share card via"));
//
//            return true;
//        }



    }

    public void setFilter(List<DataForRegisterPage> filteredlist)
    {

        mydatalist=new ArrayList<>();
        mydatalist.addAll(filteredlist);
        notifyDataSetChanged();
    }
}
