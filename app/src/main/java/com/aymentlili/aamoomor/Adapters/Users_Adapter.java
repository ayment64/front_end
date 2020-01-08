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
import com.aymentlili.aamoomor.Entitys.User;
import com.aymentlili.aamoomor.R;
import com.aymentlili.aamoomor.Services.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Users_Adapter extends RecyclerView.Adapter<Users_Adapter.ViewHolder> {
    private List<User> ListOfItems = new ArrayList();
    private Context context;

    public Users_Adapter(List<User> list, Context context) {
        this.ListOfItems = list;
        this.context = context;
    }

    public int getItemCount() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(ListOfItems.size());

        return this.ListOfItems.size();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.users_item, parent, false));

    }

    public void onBindViewHolder(ViewHolder viewHolder, int n) {
        Log.d("i m in","walah");
        User B = ListOfItems.get(n);
        viewHolder.Username.setText(B.Username);
       // viewHolder.State.setText(B.the_bid);
        Picasso.get().load("http://10.0.2.2:3000/test/"+B.image).transform(new CircleTransform()).into(viewHolder.User_Image);


    }

    public class ViewHolder
            extends RecyclerView.ViewHolder {
        TextView Username;
        TextView State;
        ImageView User_Image;
        public ViewHolder(View view) {
            super(view);
            this.Username   =view.findViewById(R.id.Username);
            this.State     =view.findViewById(R.id.State);
            this.User_Image =view.findViewById(R.id.user_image);
        }
    }

}

