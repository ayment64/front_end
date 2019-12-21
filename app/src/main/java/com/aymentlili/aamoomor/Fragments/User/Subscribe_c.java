package com.aymentlili.aamoomor.Fragments.User;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aymentlili.aamoomor.Activitys.MainActivity;
import com.aymentlili.aamoomor.Entitys.User;
import com.aymentlili.aamoomor.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Subscribe_c extends Fragment {
    public Button next;
    public boolean check;
    private static final String base_url = "http://192.168.43.80:3000/Users";
    private static int responseCode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_subcribe_c, container, false);
        next = view.findViewById(R.id.BU_Subscribe_c_Next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity m = (MainActivity) getActivity();
                HttpPostRequest request = new HttpPostRequest();
                request.u = m.u;
                request.execute();
                Log.d(String.valueOf(check),"Check state");
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


}
