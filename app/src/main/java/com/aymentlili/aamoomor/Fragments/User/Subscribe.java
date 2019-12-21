package com.aymentlili.aamoomor.Fragments.User;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.aymentlili.aamoomor.Activitys.MainActivity;
import com.aymentlili.aamoomor.Entitys.User;
import com.aymentlili.aamoomor.R;

public class Subscribe
        extends Fragment {
    public EditText Email;
    public EditText Firstname;
    public EditText Name;
    public EditText Password;
    public EditText PhoneNumber;
    public EditText Username;
    public Button next;

    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.fragment_m_subscribe, viewGroup, false);
        this.next        = view.findViewById(R.id.BU_Subscribe_Next);
        this.Username    = view.findViewById(R.id.T_E_Subscribe_Username);
        this.Password    = view.findViewById(R.id.T_E_Subscribe_Password);
        this.Firstname   = view.findViewById(R.id.T_E_Subscribe_First_Name);
        this.Name        = view.findViewById(R.id.T_E_Subscribe_Last_Name);
        this.PhoneNumber = view.findViewById(R.id.T_E_Subscribe_Phone_Number);
        this.Email       = view.findViewById(R.id.T_E_Subscribe_Email);
        this.next.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {

                if (isEmpty(Username)) {
                    Username.setError( "Username can not be Empty ");
                }
                if (isEmpty(Password)) {
                    Password.setError( "Password can not be Empty ");
                }
                if (isEmpty(Firstname)) {
                    Subscribe.this.Firstname.setError( "First name can not be Empty ");
                }
                if (isEmpty(Name)) {
                    Subscribe.this.Name.setError( "Name can not be Empty ");
                }
                if (isEmpty(PhoneNumber)) {
                    Subscribe.this.PhoneNumber.setError( "Phone Number can not be Empty ");
                }
                if (isEmpty(Email)) {
                    Subscribe.this.Email.setError( "Email can not be Empty ");
                }
                if (!isEmpty(Username) || !isEmpty(Password) || !isEmpty(Firstname) || !isEmpty(Name) || !isEmpty(PhoneNumber) || !isEmpty(Email)) {
                    MainActivity mainActivity = (MainActivity)Subscribe.this.getActivity();
                    mainActivity.u = new User(Username.getText().toString(), Password.getText().toString(), Firstname.getText().toString(), Name.getText().toString());
                    mainActivity.u.Phone_Number = Subscribe.this.PhoneNumber.getText().toString();
                    mainActivity.u.Email = Subscribe.this.Email.getText().toString();
                    Log.d("Json State at Subscribe",mainActivity.u.toJSON());
                    mainActivity.addFragmentSubscribe_b();
                }
            }
        });
        return view;
    }

}