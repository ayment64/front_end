package com.aymentlili.aamoomor.Adapters;

import android.content.Context;
import android.net.Uri;
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

public class Add_Estate_Adapter
        extends RecyclerView.Adapter<Add_Estate_Adapter.ViewHolder> {
    private List<Uri> ListOfItems = new ArrayList();
    private Context context;

    public Add_Estate_Adapter(List<Uri> list, Context context) {
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

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false));

    }

    public void onBindViewHolder(ViewHolder viewHolder, int n) {
        Uri im = (Uri)this.ListOfItems.get(n);
       viewHolder.picture.setImageURI(im);


    }



    public class ViewHolder
            extends RecyclerView.ViewHolder {

        ImageView picture;

        public ViewHolder(View view) {
            super(view);

            this.picture =view.findViewById(R.id.Image_Item_Picture);

        }
    }

}

