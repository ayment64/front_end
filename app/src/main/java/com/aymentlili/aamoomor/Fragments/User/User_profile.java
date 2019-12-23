package com.aymentlili.aamoomor.Fragments.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aymentlili.aamoomor.Activitys.Home;
import com.aymentlili.aamoomor.Activitys.MainActivity;
import com.aymentlili.aamoomor.R;
import com.mikhaellopez.circularimageview.CircularImageView;

public class User_profile extends Fragment {
    public TextView Email;
    public TextView Firstname;
    public TextView Name;
    public TextView Job;
    public TextView Description;
    public TextView PhoneNumber;
    public TextView Username;
   public CircularImageView circularImageView;
    private boolean isEmpty(TextView TextView) {
        return TextView.getText().toString().trim().length() == 0;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view        = layoutInflater.inflate(R.layout.user_profile, viewGroup, false);
        CircularImageView circularImageView = view.findViewById(R.id.circularImageView);
// Set Color
        circularImageView.setCircleColor(Color.WHITE);
// or with gradient
        circularImageView.setCircleColorStart(Color.BLACK);
        circularImageView.setCircleColorEnd(Color.RED);
        circularImageView.setCircleColorDirection(CircularImageView.GradientDirection.TOP_TO_BOTTOM);

// Set Border
        circularImageView.setBorderWidth(10f);
        circularImageView.setBorderColor(Color.BLACK);
// or with gradient
        circularImageView.setBorderColorStart(Color.BLACK);
        circularImageView.setBorderColorEnd(Color.RED);
        circularImageView.setBorderColorDirection(CircularImageView.GradientDirection.TOP_TO_BOTTOM);

// Add Shadow with default param
        circularImageView.setShadowEnable(true);
// or with custom param
        circularImageView.setShadowRadius(15f);
        circularImageView.setShadowColor(Color.RED);
        circularImageView.setShadowGravity(CircularImageView.ShadowGravity.CENTER);

        Email = view.findViewById(R.id.T_V_Profile_Email);
        Firstname = view.findViewById(R.id.T_V_Profile_First_name);
        Name = view.findViewById(R.id.T_V_Profile_Last_name);
        Job = view.findViewById(R.id.T_V_Profile_Job);
        PhoneNumber = view.findViewById(R.id.T_V_Profile_Phone_Number);
        Username = view.findViewById(R.id.T_V_Profile_Username);
        Email = view.findViewById(R.id.T_V_Profile_Email);
        Description = view.findViewById(R.id.T_V_Profile_Desccription);
        Home h = (Home) getActivity();
        Email.setText(h.u.Email);
        Username.setText(h.u.Username);
        Name.setText(h.u.Name);
        Firstname.setText(h.u.First_name);
        Job.setText(h.u.Job);
        Description.setText(h.u.Description);
        PhoneNumber.setText(h.u.Phone_Number);
        return view;
    }

}