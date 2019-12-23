package com.aymentlili.aamoomor.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.aymentlili.aamoomor.Entitys.User;
import com.aymentlili.aamoomor.Fragments.User.Subscribe_c;
import com.aymentlili.aamoomor.Fragments.User.User_profile;
import com.aymentlili.aamoomor.R;

public class Home extends AppCompatActivity {
    public static User u = new User();
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
        addFragmentProfile();
    }
    public void addFragmentProfile() {
        User_profile user_profile = new User_profile();
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.home_page_receptor, user_profile);
        fragmentTransaction.commit();
    }
}
