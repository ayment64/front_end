package com.aymentlili.aamoomor.Fragments.Estate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aymentlili.aamoomor.Activitys.Home;
import com.aymentlili.aamoomor.Adapters.Biding_Adapter;
import com.aymentlili.aamoomor.Adapters.Custom_Adapter;
import com.aymentlili.aamoomor.Entitys.Biding;
import com.aymentlili.aamoomor.Entitys.Estate;
import com.aymentlili.aamoomor.R;
import com.aymentlili.aamoomor.Services.CircleTransform;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static androidx.recyclerview.widget.LinearLayoutManager.VERTICAL;

public class Estate_profile extends Fragment {
    Estate e = new Estate();
    ImageView image_select;
    TextView adress;
    TextView bathrooms;
    TextView bedrooms;
    TextView kitchens;
    TextView livingrooms;
    TextView name;
    TextView type;
    TextView Gardens;
    TextView Prix;
    TextView Forr;
    TextView owner;
    public static ArrayList<Biding> ListOfItems;
    public static RecyclerView riri;
    public static RecyclerView.Adapter adapter;
    private static final String base_url = "http://10.0.2.2:3000/bid/";
    public static boolean check;

    public Estate_profile(Estate e) {
        this.e = e;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.estate_profile, viewGroup, false);
        //binding views to variables
        Log.d("Estate", e.adress);
        this.adress = view.findViewById(R.id.Estate_Profile_Adress);
        this.name = view.findViewById(R.id.Estate_Profile_Name);
        this.bathrooms = view.findViewById(R.id.Estate_Profile_Bathrooms);
        this.bedrooms = view.findViewById(R.id.Estate_Profile_bedrooms);
        this.livingrooms = view.findViewById(R.id.Estate_Profile_Livingrooms);
        this.kitchens = view.findViewById(R.id.Estate_Profile_Kitchen);
        this.image_select = view.findViewById(R.id.Estate_Profile_Thumb_nail);
        this.Gardens = view.findViewById(R.id.Estate_Profile_Gardens);
        this.type = view.findViewById(R.id.Estate_Profile_Type);
        this.Prix = view.findViewById(R.id.Estate_Profile_Prix);
        this.Forr = view.findViewById(R.id.Estate_Profile_for);
        this.owner = view.findViewById(R.id.Estate_Profile_owner);
        //binding data to views
        this.adress.setText(e.adress);
        this.name.setText(e.name);
        this.bathrooms.setText(String.valueOf(e.bathrooms));
        this.bedrooms.setText(String.valueOf(e.bedrooms));
        this.livingrooms.setText(String.valueOf(e.livingrooms));
        this.kitchens.setText(String.valueOf(e.kitchens));
        this.Gardens.setText(String.valueOf(e.gardens));
        this.type.setText(e.type);
        this.Prix.setText(e.prix);
        this.Forr.setText(e.forr);
        this.owner.setText(e.owner);
        riri =view.findViewById(R.id.Estate_Profile_Receptor_All_bidding);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), VERTICAL, false);
        riri.setLayoutManager(layoutManager);
        riri.setHasFixedSize(true);
        Picasso.get().load("http://10.0.2.2:3000/test/" + e.image).into(image_select);
        Home h = (Home) getActivity();
        Log.d("1house_name",e.name);
        if(e.owner.equals(h.u.Username))
        {
            HttpGetRequest request = new HttpGetRequest();
            request.execute();
        }else{
            HttpGetRequest request = new HttpGetRequest();
            request.execute();




        }

        return view;
    }
    public class HttpGetRequest extends AsyncTask<String, Void, String> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;
        @Override
        protected String doInBackground(String... params){

            String result;
            String inputLine;
            try {
                //Create a URL object holding our url

                URL myUrl = new URL(base_url+e.name);
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
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
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
                ListOfItems = toArrayOfObject(mJsonArray);


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



        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onPostExecute(String result){
            Home h = (Home) getActivity();
            if(h.u.Username.equals(e.owner))
            {
                if(ListOfItems.size()>0){
                Log.d("lalala","lalallala");
                adapter = new Biding_Adapter(ListOfItems, getContext());
                riri.setAdapter(adapter);
                check = true;
                }
            }else { List<Biding> mybiding = ListOfItems.stream()
                    .filter(p -> p.Username.equals(h.u.Username))
                    .map(p -> new Biding(p.Username, p.House_name,p.the_bid,p.image_url ))
                    .collect(Collectors.toList());
                if(mybiding.size()>0)
                {
                    if(ListOfItems.size()>0){
                        Log.d("lalala","lalallala");
                        adapter = new Biding_Adapter(mybiding, getContext());
                        riri.setAdapter(adapter);
                        check = true;}else{

                    }
                }else{
                    h.addFragmentAddBiddingItem(e.name);
                }

            if (result != null)
            {


            }
            Log.d("check state", ""+check);
            super.onPostExecute(result);

        }
    }
    public Biding toObject(JSONObject jSONObject) throws JSONException {
        Biding b = new Biding();

        b.Username = jSONObject.getString("Username");
        b.House_name = jSONObject.getString("house_name");
        b.the_bid = jSONObject.getString("the_bid");
        b.image_url = jSONObject.getString("image_url");

        return b;
    }
    public ArrayList<Biding> toArrayOfObject(JSONArray jSONArray) throws JSONException {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); ++i) {
            new Estate();
            Biding b = this.toObject(jSONArray.getJSONObject(i));
            Log.d("toarry of objects", b.Username);
            arrayList.add(b);
        }
        return arrayList;
    }
}}