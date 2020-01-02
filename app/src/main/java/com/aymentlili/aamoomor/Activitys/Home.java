package com.aymentlili.aamoomor.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aymentlili.aamoomor.Entitys.Estate;
import com.aymentlili.aamoomor.Entitys.User;
import com.aymentlili.aamoomor.Fragments.Estate.Add_Estate;
import com.aymentlili.aamoomor.Fragments.Estate.Biddding_item_show;
import com.aymentlili.aamoomor.Fragments.Estate.Biding_Item;
import com.aymentlili.aamoomor.Fragments.Estate.Estate_profile;
import com.aymentlili.aamoomor.Fragments.Estate.Estateee;
import com.aymentlili.aamoomor.Fragments.Estate.Home_page;
import com.aymentlili.aamoomor.Fragments.User.ProfileViewPager;
import com.aymentlili.aamoomor.Fragments.User.User_profile;
import com.aymentlili.aamoomor.R;
import com.aymentlili.aamoomor.Services.CircleTransform;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {
    public static User u = new User();

    private TextView Usename;
    private ImageView Image_view;
    private NavigationView mainNavigationView;
    public static FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = this.getIntent();
        this.u.Username = intent.getStringExtra("Username");

        this.u.Password = intent.getStringExtra("Password");
        this.u.First_name = intent.getStringExtra("First_name");
        this.u.Name = intent.getStringExtra("Name");
        this.u.Email = intent.getStringExtra("Email");
        this.u.image = intent.getStringExtra("image");
        this.u.Description = intent.getStringExtra("description");
        this.u.Phone_Number = intent.getStringExtra("phone_number");
        this.u.Job = intent.getStringExtra("job");




        this.mainNavigationView = this.findViewById(R.id.navigation_menu);
        if (mainNavigationView != null) {
            mainNavigationView.setNavigationItemSelectedListener(this);
        }
        View hView =  mainNavigationView.getHeaderView(0);
        Usename = hView.findViewById(R.id.Navigation_Header_Username);
        this.Usename.setText(u.Username);
        Image_view = hView.findViewById(R.id.Navigation_Header_Image_Profile);
        Picasso.get().load("http://10.0.2.2:3000/test/"+u.image).transform(new CircleTransform()).into(Image_view);

        addFragmentAddEsatate();
    }
    public void addFragmentProfile() {
        User_profile user_profile = new User_profile();
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.home_page_receptor, user_profile);
        fragmentTransaction.commit();
    }
    public void addFragmentProfileViewPager() {
        ProfileViewPager user_profile = new ProfileViewPager();
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
    public void addFragmentAddBiddingItem(String housename) {
        Log.d("2house_name",housename);
        Biding_Item user_profile = new Biding_Item(housename);
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.Estate_Profile_biding_Receptor, user_profile);
        fragmentTransaction.commit();
    }
    public void addFragmentAddBiddingItemShow(String housename) {
        Log.d("home thebid",housename);
        Biddding_item_show user_profile = new Biddding_item_show(housename);
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.Estate_Profile_biding_Receptor, user_profile);
        fragmentTransaction.commit();
    }

    public void addFragmentAddEstateee(Estate e) {
        Estateee user_profile = new Estateee(e);
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.home_page_receptor, user_profile);
        fragmentTransaction.commit();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.Navigation_item_Profile)
        {
            addFragmentProfileViewPager();
        }
        if (id == R.id.Navigation_item_Home_Page)
        {
            addFragmentHomePage();
        }
        if (id == R.id.Navigation_item_Logout)
        {
            firebaseAuth.getInstance().signOut();
            Intent i = new Intent(this,Start_Activity.class);
            startActivity(i);
            finish();
        }

        return true;
    }
}
