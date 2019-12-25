package com.aymentlili.aamoomor.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aymentlili.aamoomor.Entitys.Estate;
import com.aymentlili.aamoomor.Entitys.User;
import com.aymentlili.aamoomor.Fragments.Estate.Add_Estate;
import com.aymentlili.aamoomor.Fragments.Estate.Estate_profile;
import com.aymentlili.aamoomor.Fragments.Estate.Home_page;
import com.aymentlili.aamoomor.Fragments.User.Subscribe_c;
import com.aymentlili.aamoomor.Fragments.User.User_profile;
import com.aymentlili.aamoomor.R;
import com.aymentlili.aamoomor.Services.CircleTransform;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity {
    public static User u = new User();
    private DrawerLayout L_Drawer;
    private TextView Usename;
    private ImageView Image_view;
    private NavigationView mainNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = this.getIntent();
        this.u.Username = intent.getStringExtra("Username");
        Log.d("from home activity",this.u.Username);
        this.u.Password = intent.getStringExtra("Password");
        this.u.First_name = intent.getStringExtra("First_name");
        this.u.Name = intent.getStringExtra("Name");
        this.u.Email = intent.getStringExtra("Email");
        this.u.image = intent.getStringExtra("image");
        this.u.Description = intent.getStringExtra("description");
        this.u.Phone_Number = intent.getStringExtra("phone_number");
        this.u.Job = intent.getStringExtra("job");


        this.mainNavigationView = this.findViewById(R.id.navigation_menu);

        View hView =  mainNavigationView.getHeaderView(0);
        Usename = hView.findViewById(R.id.Navigation_Header_Username);
        this.Usename.setText(u.Username);
        Image_view = hView.findViewById(R.id.Navigation_Header_Image_Profile);
        Picasso.get().load("http://192.168.43.80:3000/test/"+u.image).transform(new CircleTransform()).into(Image_view);

        addFragmentAddEsatate();
    }
    public void addFragmentProfile() {
        User_profile user_profile = new User_profile();
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.home_page_receptor, user_profile);
        fragmentTransaction.commit();
    }
    public void addFragmentHomePage() {
        Home_page user_profile = new Home_page();
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.home_page_receptor, user_profile);
        fragmentTransaction.commit();
    }
    public void addFragmentEstateProfile(Estate e) {

        Estate_profile user_profile = new Estate_profile(e);
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.home_page_receptor, user_profile);
        fragmentTransaction.commit();
    }
    public void addFragmentAddEsatate() {

        Add_Estate user_profile = new Add_Estate();
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.home_page_receptor, user_profile);
        fragmentTransaction.commit();
    }
}
