package com.app.android.sample.newsfeedapp;

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

    @Override
    public DataAdapter.MyViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View buysell_view=LayoutInflater.from(parent.getContext()).inflate(R.layout.news_layout,parent,false);
        buysell_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                int buy_position = Rental_Buysell.buysell_list.getChildAdapterPosition(view);
//                Intent in = new Intent(view.getContext(), Preview_Property.class);
//                in.putExtra("value", objs_arr.get(buy_position).getID() + "#buysell");
//                // Toast.makeText (view.getContext (), ""+objs_arr.get (buy_position).getID (), Toast.LENGTH_SHORT).show ();
//                view.getContext().startActivity(in);

            }
        });

        return new DataAdapter.MyViewHolder (buysell_view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.MyViewHolder holder, int position) {
        holder.locationName.setText(objs_arr.get(position).get_locationName ());
        Glide.with(holder.postImage.getContext()).load(Constants.BASE_IMG_URL+objs_arr.get(position).get_imageName()).into(holder.postImage);
        Log.d("asasasasasas", "onBindViewHolder: "+objs_arr.get(position).get_imageName());
//        String[] separated =objs_arr.get(position).get_imageName().split(".");
//        String sss = separated[0];
//        String fileFormat = separated[1];
      //  Log.d("asasasasasas", "onBindViewHolder: "+sss +"   " + fileFormat);
//        if (fileFormat.equalsIgnoreCase("pdf"))
//        {
//            Glide.with(holder.postImage.getContext()).load(R.drawable.pdf_icon).into(holder.postImage);
//        }
//        String[] separated =objs_arr.get(position).get_imageName().split(".");
//        String fileFormat = separated[1];

//        if(objs_arr.get(position).get_imageName().split(".").toString().contains("jpg") ||
//                objs_arr.get(position).get_imageName().split(".").toString().contains("png"))
//        {
//            Glide.with(holder.postImage.getContext()).load(Constants.BASE_IMG_URL+objs_arr.get(position).get_imageName()).into(holder.postImage);
//        }
//        else if (objs_arr.get(position).get_imageName().split(".").toString().contains("pdf"))
//        {
//            Glide.with(holder.postImage.getContext()).load(R.drawable.pdf_icon).into(holder.postImage);
//        }



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
