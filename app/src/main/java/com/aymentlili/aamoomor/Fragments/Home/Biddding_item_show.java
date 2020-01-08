package com.aymentlili.aamoomor.Fragments.Home;


import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aymentlili.aamoomor.Activitys.Home;
import com.aymentlili.aamoomor.R;
import com.aymentlili.aamoomor.Services.CircleTransform;
import com.squareup.picasso.Picasso;

public class Biddding_item_show extends Fragment {
    public ImageView UserImage;
    public TextView  Usernamee;
    public TextView  The_bid;
    public static String thebid;

    public boolean check;
    private static final String base_url = "http://10.0.2.2:3000/bid";
    private static int responseCode;
    public Biddding_item_show(String thebid) {
        this.thebid = thebid;
    }



    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.biding_item_show, viewGroup, false);
        UserImage = view.findViewById(R.id.S_Estate_Profile_User_Image);
        Usernamee = view.findViewById(R.id.S_Estate_Profile_Username);
        The_bid = view.findViewById(R.id.S_The_Bid);



        Home h = (Home) getActivity();
        Usernamee.setText(h.u.Username);
        The_bid.setText(thebid);
        Picasso.get().load("http://10.0.2.2:3000/test/"+h.u.image).transform(new CircleTransform()).into(UserImage);

        return view;
    }



}
