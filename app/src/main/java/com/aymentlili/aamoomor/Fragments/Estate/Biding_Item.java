package com.aymentlili.aamoomor.Fragments.Estate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.aymentlili.aamoomor.Activitys.Home;
import com.aymentlili.aamoomor.Entitys.User;
import com.aymentlili.aamoomor.R;
import com.aymentlili.aamoomor.Services.CircleTransform;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Biding_Item extends Fragment {
    public ImageView UserImage;
    public TextView  Usernamee;
    public EditText  The_bid;
    public ImageView Send;
    public static String House_name;
    private static String the_bid;
    public boolean check;
    private static final String base_url = "http://10.0.2.2:3000/bid";
    private static int responseCode;
    public Biding_Item(String house_name) {
        House_name = house_name;
    }



    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.biding_item, viewGroup, false);
        UserImage = view.findViewById(R.id.Estate_Profile_User_Image);
        Usernamee = view.findViewById(R.id.Estate_Profile_Username);
        The_bid = view.findViewById(R.id.The_Bid);
        Send = view.findViewById(R.id.Send);
        Log.d("3house_name",House_name);
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                the_bid = The_bid.getText().toString();
                HttpPostRequest request = new HttpPostRequest();
                request.execute();
                Home h = (Home) getActivity();
                h.addFragmentAddBiddingItemShow(the_bid);
            }
        });


        Home h = (Home) getActivity();
        Usernamee.setText(h.u.Username);
        Picasso.get().load("http://10.0.2.2:3000/test/"+h.u.image).transform(new CircleTransform()).into(UserImage);

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

                String jsonData = toJSON();
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

            Log.d("Response Code ////",String.valueOf(responseCode));
            if(result!=null)
            {
                check = true;
            }
        }
    }

    public String toJSON() {
        JSONObject jSONObject = new JSONObject();

        try {
            Home h = (Home) getActivity();
            Log.d("thebid",the_bid);
            jSONObject.put("Username", h.u.Username);
            jSONObject.put("image_url", h.u.image);
            jSONObject.put("house_name",this.House_name);
            jSONObject.put("the_bid", the_bid);
            String string2 = jSONObject.toString();
            return string2;
        }
        catch (JSONException jSONException) {
            jSONException.printStackTrace();
            return "";
        }
    }

}
