package com.aymentlili.aamoomor.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.aymentlili.aamoomor.Entitys.User;
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
    }
}
