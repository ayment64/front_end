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

import com.aymentlili.aamoomor.Activitys.Home;
import com.aymentlili.aamoomor.Entitys.Chat;
import com.aymentlili.aamoomor.R;
import com.aymentlili.aamoomor.Services.CircleTransform;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Message_Adapter extends RecyclerView.Adapter<Message_Adapter.ViewHolder> {
    public final static int MSG_TYPE_LEFT = 0;
    public final static int MSG_TYPE_RIGHT = 1;
    private List<Chat> ListOfItems = new ArrayList();
    private String image_url;
    private Context context;
    FirebaseUser firebaseUser;
    private String my_image;


    public Message_Adapter(List<Chat> list, Context context, String image_url,String my_image) {
        this.ListOfItems = list;
        this.context = context;
        this.image_url = image_url;
        this.my_image = my_image;
    }

    public int getItemCount() {


        return ListOfItems.size();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType== MSG_TYPE_RIGHT) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right, parent, false));
        }
        else{
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left, parent, false));

        }
    }

    public void onBindViewHolder(ViewHolder viewHolder, int n) {

        Log.d("i m in","walah");
        Chat B = ListOfItems.get(n);
        viewHolder.Message.setText(B.message);
        if(getItemViewType(n)== MSG_TYPE_LEFT)
        {
            Picasso.get().load("http://10.0.2.2:3000/test/"+image_url).transform(new CircleTransform()).into(viewHolder.User_Image);
        }
        if(getItemViewType(n)== MSG_TYPE_RIGHT)
        {
            Picasso.get().load("http://10.0.2.2:3000/test/"+my_image).transform(new CircleTransform()).into(viewHolder.User_Image);
        }
    }

    public class ViewHolder
            extends RecyclerView.ViewHolder {

        TextView Message;
        ImageView User_Image;
        public ViewHolder(View view) {
            super(view);

            this.Message     =view.findViewById(R.id.message_chat);
            this.User_Image =view.findViewById(R.id.buble_pic_him);
        }
    }

    @Override
    public int getItemViewType(int position) {
      firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
      Log.d("sender", ListOfItems.get(position).sender);
      if(ListOfItems.get(position).sender.equals(firebaseUser.getUid()))
        {
            return MSG_TYPE_RIGHT;
        }else {
          return MSG_TYPE_LEFT;
      }
    }
}

