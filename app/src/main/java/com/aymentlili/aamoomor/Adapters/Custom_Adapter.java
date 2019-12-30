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

import com.aymentlili.aamoomor.Entitys.Estate;
import com.aymentlili.aamoomor.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Custom_Adapter
        extends RecyclerView.Adapter<Custom_Adapter.ViewHolder> {
    private List<Estate> ListOfItems = new ArrayList();
    private Context context;

    public Custom_Adapter(List<Estate> list, Context context) {
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

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_item_home_page, parent, false));

    }

    public void onBindViewHolder(ViewHolder viewHolder, int n) {
        Estate estate = (Estate)this.ListOfItems.get(n);
        viewHolder.bedrooms.setText((CharSequence)String.valueOf((int)estate.bedrooms));
        TextView textView = viewHolder.adress;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  Adresse : ");
        stringBuilder.append(estate.adress);
        textView.setText((CharSequence)stringBuilder.toString());
        TextView textView2 = viewHolder.name;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("  Name :");
        stringBuilder2.append(estate.name);
        textView2.setText((CharSequence)stringBuilder2.toString());
        viewHolder.kitchens.setText((CharSequence)String.valueOf((int)estate.kitchens));
        viewHolder.bathrooms.setText((CharSequence)String.valueOf((int)estate.bathrooms));
        viewHolder.livingrooms.setText((CharSequence)String.valueOf((int)estate.livingrooms));
        Picasso picasso = Picasso.get();
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("http://10.0.2.2:3000/test/");
        stringBuilder3.append(estate.image);
        picasso.load(stringBuilder3.toString()).into(viewHolder.image_select);



    }



    public class ViewHolder
            extends RecyclerView.ViewHolder {
        TextView adress;
        TextView bathrooms;
        TextView bedrooms;
        ImageView image_select;
        TextView kitchens;
        TextView livingrooms;
        TextView name;
        ImageView type;

        public ViewHolder(View view) {
            super(view);

            this.adress =view.findViewById(R.id.Card_view_Item_adresse);
            this.name =view.findViewById(R.id.Card_view_Item_Name);
            this.bathrooms =view.findViewById(R.id.Card_view_Item_Bath_rooms);
            this.bedrooms =view.findViewById(R.id.Card_view_Item_Bed_room);
            this.livingrooms =view.findViewById(R.id.Card_view_Item_Living_room);
            this.kitchens = view.findViewById(R.id.Card_view_Item_Kitchen);
            this.image_select =view.findViewById(R.id.Card_view_Item_House_Image);
        }
    }

}

