package com.TheEventWelfare.EventConnects;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class AdapterForCompanyName extends RecyclerView.Adapter<AdapterForCompanyName.MyAdapter> {

    private Context context;
    private List<EventNewsGenericDataType<String>> companyname;
    AdapterForCompanyName(Context context , List<EventNewsGenericDataType<String>> companyname)
    {
        this.companyname = companyname;
        this.context = context ;

    }


    @Override
    public AdapterForCompanyName.MyAdapter onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.layoutforcompanyname,parent,false);
        return new MyAdapter(view);


    }

    @Override
    public void onBindViewHolder(MyAdapter holder, int position) {
        holder.companynameforeventnews.setText(companyname.get(position).getData());
        holder.description.setText(companyname.get(position).getDescription());
        holder.image.setImageBitmap(ImageScalig.BITMAP_RESIZER(companyname.get(position).getImage(),100,100));

    }

    @Override
    public int getItemCount() {
        return companyname.size();
    }



    public class MyAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView companynameforeventnews;
        TextView description;
        ImageView image;
        CardView cardView;

        public MyAdapter(View itemView) {
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.company_logo);
            description = (TextView)itemView.findViewById(R.id.description_company);
            Typeface custom_font1 = Typeface.createFromAsset(context.getAssets(),  "fonts/NotoSerif-Regular.ttf");
            description.setTypeface(custom_font1);
            companynameforeventnews = (TextView)itemView.findViewById(R.id.companynameforeventnews);
            Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/BalooBhaijaan-Regular.ttf");
            companynameforeventnews.setTypeface(custom_font);
            cardView = (CardView)itemView.findViewById(R.id.company_card);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (description.getVisibility()== View.GONE)
            {
                description.setVisibility(View.VISIBLE);
            }else{
                description.setVisibility(View.GONE);
            }
        }
    }
}
