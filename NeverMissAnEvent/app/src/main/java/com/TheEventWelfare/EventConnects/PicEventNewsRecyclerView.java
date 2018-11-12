package com.TheEventWelfare.EventConnects;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class PicEventNewsRecyclerView extends RecyclerView.Adapter<PicEventNewsRecyclerView.PicHolder> {


    private Context context;
    private List<EventNewsGenericDataType<Bitmap>> imagedata;
    PicEventNewsRecyclerView(Context ctx , List<EventNewsGenericDataType<Bitmap>> imagedata)
        {

            context = ctx;
            this.imagedata = imagedata;
        }

    @Override
    public PicEventNewsRecyclerView.PicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View myviewforevent = LayoutInflater.from(context).inflate(R.layout.event_news_pic_recyclerview,parent,false);
        return new PicHolder(myviewforevent);
    }

    @Override
    public void onBindViewHolder(PicHolder holder, int position) {
//        holder.imageView.setImageBitmap(Bitmap.createScaledBitmap(imagedata.get(position).getData(), 100, 100, false));

        holder.imageView.setImageBitmap(ImageScalig.BITMAP_RESIZER(imagedata.get(position).getData(),100,100));
    }

    @Override
    public int getItemCount() {
        return imagedata.size();
    }

    public class PicHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Context context;
        ImageView imageView;

        public PicHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.imageforeventnews);

            imageView.setOnClickListener(this);
            context = itemView.getContext();
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Bitmap bitmap = imagedata.get(position).getData();

            Intent intent = new Intent(v.getContext(),FullScreenImageView.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

            byte[] byteArray ;
            try {
                byteArray = stream.toByteArray();
            }catch (Exception e){
                return;
            }
            intent.putExtra("image",byteArray);
            context.startActivity(intent);

        }
    }
}
