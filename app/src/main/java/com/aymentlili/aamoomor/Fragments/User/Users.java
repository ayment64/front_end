package com.aymentlili.aamoomor.Fragments.User;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aymentlili.aamoomor.Adapters.Custom_Adapter;
import com.aymentlili.aamoomor.Adapters.Users_Adapter;
import com.aymentlili.aamoomor.Entitys.Estate;
import com.aymentlili.aamoomor.Entitys.User;
import com.aymentlili.aamoomor.Fragments.Estate.Home_page;
import com.aymentlili.aamoomor.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static androidx.recyclerview.widget.LinearLayoutManager.VERTICAL;

public class Users extends Fragment {
    public static ArrayList<User> ListOfItems;
    public static RecyclerView riri;
    public static RecyclerView.Adapter adapter;
    private static final String base_url = "http://10.0.2.2:3000/Users";
    public static boolean check;
    public static String d;
    private void InitList() {
        ListOfItems = new ArrayList();
    }
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.users, viewGroup, false);
        riri = view.findViewById(R.id.users_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), VERTICAL, false);
        riri.setLayoutManager(layoutManager);
        riri.setHasFixedSize(true);
        HttpGetRequest request = new HttpGetRequest();
        request.execute();
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


                adapter = new Users_Adapter(ListOfItems, getContext());
                riri.setAdapter(adapter);
                check = true;
            }
            Log.d("check state", ""+check);
            super.onPostExecute(result);

        }
    }
    public User toObject(JSONObject jSONObject) throws JSONException {
        User u = new User();
        u.Username=jSONObject.getString("Username");
        u.Password=jSONObject.getString("Password");
        u.Name=jSONObject.getString("Name");
        u.First_name=jSONObject.getString("FirstName");
        u.Email=jSONObject.getString("Email");
        u.image=jSONObject.getString("image");
        u.Description=jSONObject.getString("description");
        u.Phone_Number=jSONObject.getString("phone_number");
        u.Job=jSONObject.getString("job");
        u.fb_id=jSONObject.getString("fb_id");
        Log.d("fb_id",u.fb_id);

        return u;
    }
    public ArrayList<User> toArrayOfObject(JSONArray jSONArray) throws JSONException {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); ++i) {
            User u =  toObject(jSONArray.getJSONObject(i));

            arrayList.add(u);
        }
        return arrayList;
    }
}
