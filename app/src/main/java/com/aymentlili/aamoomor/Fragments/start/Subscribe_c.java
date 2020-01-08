package com.aymentlili.aamoomor.Fragments.start;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aymentlili.aamoomor.Activitys.Start_Activity;
import com.aymentlili.aamoomor.Entitys.User;
import com.aymentlili.aamoomor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class Subscribe_c extends Fragment {
    public Button next;
    public boolean check;
    private static final String base_url = "http://10.0.2.2:3000/Users";
    private static int responseCode;
    FirebaseAuth auth;
    DatabaseReference reference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_subcribe_c, container, false);
        next = view.findViewById(R.id.BU_Subscribe_c_Next);
        auth = FirebaseAuth.getInstance();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Start_Activity m = (Start_Activity) getActivity();
                HttpPostRequest request = new HttpPostRequest();
                request.u = m.u;
                request.execute();
                Log.d(String.valueOf(check),"Check state");
                register();

                if(check) {
                 /*   Intent i = new Intent(getContext() , home.class);
                    startActivityForResult(i,0);
                    m.replaceFragmentLogIn();*/
                 Log.d("yeeey","yeeu");

                }


            }
        });
        return view;
    }

    public class HttpPostRequest extends AsyncTask<Void, Void, String> {

        static final String REQUEST_METHOD = "POST";
        static final int READ_TIMEOUT = 15000;
        static final int CONNECTION_TIMEOUT = 15000;
        User u;


        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(Void... params){
            String result;
            String inputLine;
            Log.d("state inside request", u.toJSON());



            try {
                // connect to the server
                URL myUrl = new URL(base_url);
                HttpURLConnection connection =(HttpURLConnection) myUrl.openConnection();
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");

                connection.connect();

                // get the string from the input stream
                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                String jsonData = u.toJSON();
                writer.write(jsonData);
                writer.close();


                os.flush();
                responseCode = connection.getResponseCode();
                StringBuilder stringBuilder = new StringBuilder();
                result = stringBuilder.toString();

            } catch(IOException e) {
                e.printStackTrace();
                result = "error";
            }

            return result;
        }

        protected void onPostExecute(String result){
            super.onPostExecute(result);

            Log.d("///bytes//",u.toJSON().getBytes().toString());
            Log.d("Response Code ////",String.valueOf(responseCode));
            if(result!=null)
            {
                check = true;
            }
        }
    }
public void register(){
        Start_Activity m = (Start_Activity) getActivity();
    auth.createUserWithEmailAndPassword(m.u.Email,m.u.Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful())
            {
                FirebaseUser firebaseUser = auth.getCurrentUser();
                String userid = firebaseUser.getUid();
                reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("username",m.u.Username);
                hashMap.put("imageURL","default");
                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            m.addFragmentLogIn();
                        }
                    }
                });

            }else{
                Toast.makeText(getContext(),"you can't register with this email or password",Toast.LENGTH_SHORT).show();
                m.addFragmentLogIn();
            }
        }
    });
}

}
