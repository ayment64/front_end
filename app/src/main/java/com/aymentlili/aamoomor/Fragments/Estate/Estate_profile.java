package com.aymentlili.aamoomor.Fragments.Estate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aymentlili.aamoomor.Entitys.Estate;
import com.aymentlili.aamoomor.R;
import com.aymentlili.aamoomor.Services.CircleTransform;
import com.squareup.picasso.Picasso;

public class Estate_profile extends Fragment {
    Estate e = new Estate();
    ImageView image_select;
    TextView adress;
    TextView bathrooms;
    TextView bedrooms;
    TextView kitchens;
    TextView livingrooms;
    TextView name;
    TextView type;
    TextView Gardens;
    TextView Prix;
    TextView Forr;
    TextView owner;

    public Estate_profile(Estate e) {
        this.e = e;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.estate_profile, viewGroup, false);
        //binding views to variables
        Log.d("Estate",e.adress);
        this.adress =view.findViewById(R.id.Estate_Profile_Adress);
        this.name =view.findViewById(R.id.Estate_Profile_Name);
        this.bathrooms =view.findViewById(R.id.Estate_Profile_Bathrooms);
        this.bedrooms =view.findViewById(R.id.Estate_Profile_bedrooms);
        this.livingrooms =view.findViewById(R.id.Estate_Profile_Livingrooms);
        this.kitchens = view.findViewById(R.id.Estate_Profile_Kitchen);
        this.image_select =view.findViewById(R.id.Estate_Profile_Thumb_nail);
        this.Gardens = view.findViewById(R.id.Estate_Profile_Gardens);
        this.type = view.findViewById(R.id.Estate_Profile_Type);
        this.Prix = view.findViewById(R.id.Estate_Profile_Prix);
        this.Forr = view.findViewById(R.id.Estate_Profile_for);
        this.owner = view.findViewById(R.id.Estate_Profile_owner);
        //binding data to views
        this.adress.setText(e.adress);
        this.name.setText(e.name);
        this.bathrooms .setText(String.valueOf(e.bathrooms));
        this.bedrooms .setText(String.valueOf(e.bedrooms));
        this.livingrooms.setText(String.valueOf(e.livingrooms));
        this.kitchens.setText(String.valueOf(e.kitchens));
        this.Gardens.setText(String.valueOf(e.gardens));
        this.type .setText(e.type);
        this.Prix.setText(e.prix);
        this.Forr.setText(e.forr);
        this.owner.setText(e.owner);
        Picasso.get().load("http://192.168.43.80:3000/test/"+e.image).into(image_select);

        return view;
    }
}
