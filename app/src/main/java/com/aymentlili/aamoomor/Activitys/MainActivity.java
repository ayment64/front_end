package com.aymentlili.aamoomor.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.aymentlili.aamoomor.Entitys.User;
import com.aymentlili.aamoomor.Fragments.User.Log_in;
import com.aymentlili.aamoomor.Fragments.User.Subscribe;
import com.aymentlili.aamoomor.Fragments.User.Subscribe_b;
import com.aymentlili.aamoomor.Fragments.User.Subscribe_c;
import com.aymentlili.aamoomor.R;

public class MainActivity extends AppCompatActivity {
    public static User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFragmentLogIn();
    }
    public void addFragmentLogIn() {
        Log_in log_in = new Log_in();
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Frame_layout_Main_activity,log_in);
        fragmentTransaction.commit();
    }
    public void addFragmentSubscribe() {
        Subscribe subscribe = new Subscribe();
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.Frame_layout_Main_activity, subscribe);
        fragmentTransaction.commit();
      }
    public void addFragmentSubscribe_b() {
        Subscribe_b subscribe = new Subscribe_b();
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.Frame_layout_Main_activity, subscribe);
        fragmentTransaction.commit();
    }
    public void addFragmentSubscribe_c() {
        Subscribe_c subscribe = new Subscribe_c();
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.Frame_layout_Main_activity, subscribe);
        fragmentTransaction.commit();
    }
}
