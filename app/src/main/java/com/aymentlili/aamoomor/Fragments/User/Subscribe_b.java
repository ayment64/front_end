package com.aymentlili.aamoomor.Fragments.User;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.aymentlili.aamoomor.Activitys.Start_Activity;
import com.aymentlili.aamoomor.R;

public class Subscribe_b extends Fragment {
    public EditText Description;
    public EditText Job;
    public int count;
    public Button next;

    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view        = layoutInflater.inflate(R.layout.fragment_m_subscribe_b, viewGroup, false);
        this.next        = view.findViewById(R.id.BU_Subscribe_b_Next);
        this.Job         = view.findViewById(R.id.T_E_Subscribe_b_Job);
        this.Description = view.findViewById(R.id.T_E_Subscribe_b_Description);
        if (isEmpty(Job)) {
            Job.setError("it's true that job is optional but it will help");
        }
        if (isEmpty(Description)) {
            Description.setError("it's true that Description is optional but it will help");
        }
        this.count = 0;
        this.next.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {

              count++;
                if (count > 1) {
                    Start_Activity startActivity = (Start_Activity)getActivity();
                    startActivity.u.setJob(Job.getText().toString());
                    startActivity.u.setDescription(Description.getText().toString());
                    Log.d((String)"Json State at Email ", startActivity.u.toJSON());
                    startActivity.addFragmentSubscribe_c();
                }
            }
        });
        return view;
    }

}