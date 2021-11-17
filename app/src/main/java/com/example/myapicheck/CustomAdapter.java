package com.example.myapicheck;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<Data> wdata;

    public CustomAdapter(Context context, ArrayList<Data> wdata) {
        this.context= context;
        this.wdata= wdata;

    }

    @Override
    public int getCount() {
        return wdata.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.sample_layout,parent,false);

        }

        TextView title= convertView.findViewById(R.id.TextViewTitle_id);
        TextView details= convertView.findViewById(R.id.textviewDetail_id);
        ImageView newsimg= convertView.findViewById(R.id.imgFlag_id);


            title.setText(wdata.get(position).head);
            details.setText(wdata.get(position).descib);
               String s=wdata.get(position).imgurl;
        Picasso.get().load(s).into(newsimg);

        return convertView;
    }
}
