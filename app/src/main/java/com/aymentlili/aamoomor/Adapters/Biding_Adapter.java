package com.aymentlili.aamoomor.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aymentlili.aamoomor.Entitys.Biding;
import com.aymentlili.aamoomor.Entitys.Estate;
import com.aymentlili.aamoomor.R;
import com.aymentlili.aamoomor.Services.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Biding_Adapter extends RecyclerView.Adapter<Biding_Adapter.ViewHolder> {
    private List<Biding> ListOfItems = new ArrayList();
    private Context context;

    public Biding_Adapter(List<Biding> list, Context context) {
        this.ListOfItems = list;
        this.context = context;
    }

    public int getItemCount() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this.ListOfItems.size());

        return this.ListOfItems.size();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.biding_item_show, parent, false));

    }

    public void onBindViewHolder(ViewHolder viewHolder, int n) {
        Log.d("i m in","walah");
        Biding B = ListOfItems.get(n);
        viewHolder.Username.setText(B.Username);
        viewHolder.Thebid.setText(B.the_bid);
        Picasso.get().load("http://10.0.2.2:3000/test/"+B.image_url).transform(new CircleTransform()).into(viewHolder.User_Image);


    }

    public class ViewHolder
            extends RecyclerView.ViewHolder {
        TextView Username;
        TextView Thebid;
        ImageView User_Image;
        public ViewHolder(View view) {
            super(view);
            this.Username   =view.findViewById(R.id.S_Estate_Profile_Username);
            this.Thebid     =view.findViewById(R.id.S_The_Bid);
            this.User_Image =view.findViewById(R.id.S_Estate_Profile_User_Image);
        }
    }

}

