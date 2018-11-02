package com.app.android.sample.newsfeedapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.app.android.sample.newsfeedapp.Util.Constants;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class DataAdapter  extends RecyclerView.Adapter<DataAdapter.MyViewHolder>{

    public static ArrayList<DataModel> objs_arr=new ArrayList<>();
    public DataAdapter(ArrayList<DataModel> objs) {
        this.objs_arr = objs;
    }

    String imgName ;

    @Override
    public DataAdapter.MyViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View buysell_view=LayoutInflater.from(parent.getContext()).inflate(R.layout.news_layout,parent,false);
        buysell_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = NewsFeedActivity.lstView.getChildAdapterPosition(view);
                String img = objs_arr.get(pos).get_imageName();
                if(img.contains(".pdf"))
                {
                   view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.BASE_IMG_URL+objs_arr.get(pos).get_imageName())));
                }
            }
        });
        return new DataAdapter.MyViewHolder (buysell_view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.MyViewHolder holder, int position) {
        holder.locationName.setText(objs_arr.get(position).get_locationName ());
        Glide.with(holder.postImage.getContext()).load(Constants.BASE_IMG_URL+objs_arr.get(position).get_imageName()).into(holder.postImage);
        Log.d("asasasasasas", "onBindViewHolder: "+objs_arr.get(position).get_imageName());
         imgName = objs_arr.get(position).get_imageName();
       if(imgName.contains(".jpg"))
       {
           Glide.with(holder.postImage.getContext()).load(Constants.BASE_IMG_URL+objs_arr.get(position).get_imageName()).into(holder.postImage);
           Log.d("asasasasasas", "JPG : "+objs_arr.get(position).get_imageName() );
       }
       else if(imgName.contains(".pdf"))
       {
           Glide.with(holder.postImage.getContext()).load(R.drawable.pdf_icon).into(holder.postImage);
           Log.d("asasasasasas", "PDF : "+objs_arr.get(position).get_imageName() );
       }
    }
    @Override
    public int getItemCount() {
        return objs_arr.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView locationName;
        ImageView postImage;
        public MyViewHolder(View itemView) {
            super(itemView);
            locationName = itemView.findViewById(R.id.loc_view);
            postImage = itemView.findViewById(R.id.img_view);
        }
    }

}
