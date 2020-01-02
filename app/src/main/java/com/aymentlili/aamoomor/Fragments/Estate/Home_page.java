package com.aymentlili.aamoomor.Fragments.Estate;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aymentlili.aamoomor.Activitys.Home;
import com.aymentlili.aamoomor.Adapters.Custom_Adapter;
import com.aymentlili.aamoomor.Entitys.Estate;
import com.aymentlili.aamoomor.R;
import com.aymentlili.aamoomor.Services.MyTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Home_page extends Fragment {
    public static ArrayList<Estate> ListOfItems;
    public static RecyclerView riri;
    public static RecyclerView.Adapter adapter;
    private static final String base_url = "http://10.0.2.2:3000/Estates";
    public static boolean check;
    public static String d;
    private void InitList() {
        ListOfItems = new ArrayList();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.home_page, viewGroup, false);
        this.InitList();
        riri =view.findViewById(R.id.Home_Page_Recycler_View);
        HttpGetRequest request =new HttpGetRequest();
        request.execute();
        while (ListOfItems.size() < 1) {
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        riri.setLayoutManager((RecyclerView.LayoutManager)gridLayoutManager);
        Home_page.adapter = new Custom_Adapter(Home_page.ListOfItems, getContext());
        riri.setAdapter(adapter);
        riri.addOnItemTouchListener((RecyclerView.OnItemTouchListener)new MyTouchListener(this.getContext(), riri, new MyTouchListener.OnTouchActionListener(){

            @Override
            public void onClick(View view, int n) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(n);
                Log.d((String)"position", (String)stringBuilder.toString());
                Home home = (Home)Home_page.this.getActivity();
                Log.d((String)"inside item click", (String)((Estate)Home_page.ListOfItems.get((int)n)).name);
                home.addFragmentAddEstateee(ListOfItems.get(n));
            }

            @Override
            public void onLeftSwipe(View view, int n) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(n);
                Log.d((String)"position", (String)stringBuilder.toString());
            }

            @Override
            public void onRightSwipe(View view, int n) {
            }
        }));

        return view;
    }

    public Estate toObject(JSONObject jSONObject) throws JSONException {
        Estate estate = new Estate();
        estate.adress = jSONObject.getString("adresse");
        estate.bathrooms = jSONObject.getInt("bathrooms");
        estate.bedrooms = jSONObject.getInt("bedrooms");
        estate.livingrooms = jSONObject.getInt("livingrooms");
        estate.kitchens = jSONObject.getInt("kitchens");
        estate.gardens = jSONObject.getInt("gardens");
        estate.image = jSONObject.getString("picture");
        estate.name = jSONObject.getString("name");
        estate.type = jSONObject.getString("type");
        estate.forr = jSONObject.getString("forr");
        estate.prix = jSONObject.getString("prix");
        estate.owner = jSONObject.getString("owner");
        Log.d((String)"toObject", (String)estate.adress);
        return estate;
    }
    public ArrayList<Estate> toArrayOfObject(JSONArray jSONArray) throws JSONException {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); ++i) {
            new Estate();
            Estate estate = this.toObject(jSONArray.getJSONObject(i));
            Log.d("toarry of objects", estate.adress);
            arrayList.add((Object)estate);
        }
        return arrayList;
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

                URL myUrl = new URL(base_url);
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
                JSONObject Userobject = mJsonArray.getJSONObject(0);
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



        protected void onPostExecute(String result){
            if (result != null)
            {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                riri.setLayoutManager((RecyclerView.LayoutManager)gridLayoutManager);
                Home_page.adapter = new Custom_Adapter(Home_page.ListOfItems, getContext());
                riri.setAdapter(adapter);
                check = true;
            }
            Log.d("check state", ""+check);
            super.onPostExecute(result);

        }
    }
}
