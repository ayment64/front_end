package com.aymentlili.aamoomor.Fragments.User;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.aymentlili.aamoomor.Activitys.Home;
import com.aymentlili.aamoomor.Activitys.Start_Activity;
import com.aymentlili.aamoomor.Entitys.User;
import com.aymentlili.aamoomor.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;


public class Chats extends Fragment {
 public User reciver = new User();
 String username;
 public static boolean check;
FirebaseUser firebaseUser;
DatabaseReference reference;
ImageButton btn_snd;
EditText text_send;
    public Chats(String username) {
        this.username = username;
    }

    private static final String base_url = "http://10.0.2.2:3000/Users/";
    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.chats, viewGroup, false);
        GetUserbyEmail getUserbyEmail = new GetUserbyEmail();
        getUserbyEmail.execute();
        btn_snd = view.findViewById(R.id.btn_send);
        text_send = view.findViewById(R.id.text_send);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        btn_snd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text_send.getText().toString();
                if(!isEmpty(text_send))
                {
                    sendMessage(firebaseUser.getUid(),reciver.fb_id,msg);

                }else{
                    Home h = (Home) getActivity();
                    Toast.makeText(getContext(),"you can't send an emplty msg",Toast.LENGTH_SHORT);
                }
            }
        });

    return view;
    }
    public class GetUserbyEmail extends AsyncTask<String, Void, String> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;
        @Override
        protected String doInBackground(String... params){

            String result;
            String inputLine;
            try {
                //Create a URL object holding our url

                URL myUrl = new URL(base_url +username);
                //Create a connection
                HttpURLConnection connection =(HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();
                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();

                Log.d("Result|||||||||",result);
                JSONArray mJsonArray = new JSONArray(result);
                JSONObject Userobject = mJsonArray.getJSONObject(0);
                String Username = Userobject.getString("Username");

                reciver = new User();
                reciver.Username=Userobject.getString("Username");
                reciver.Password=Userobject.getString("Password");
                reciver.Name=Userobject.getString("Name");
                reciver.First_name=Userobject.getString("FirstName");
                reciver.Email=Userobject.getString("Email");
                reciver.image=Userobject.getString("image");
                reciver.Description=Userobject.getString("description");
                reciver.Phone_Number=Userobject.getString("phone_number");
                reciver.Job=Userobject.getString("job");
                reciver.fb_id=Userobject.getString("fb_id");
                Log.d("fb_id",reciver.fb_id);
               


            }
            catch(IOException | JSONException e){
                e.printStackTrace();
                result = null;
            }
            if (result != null)
            {

            }
            return result;
        }
        protected void onPostExecute(String result){
            if (result != null)
            {
                check = true;
            }
            Log.d("check state", ""+check);
            super.onPostExecute(result);

        }
    }
    private void sendMessage(String sender,String receiver,String message)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);
        reference.child("Chats").push().setValue(hashMap);

    }

}
