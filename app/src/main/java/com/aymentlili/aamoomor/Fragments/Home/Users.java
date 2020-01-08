package com.aymentlili.aamoomor.Fragments.Home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aymentlili.aamoomor.Activitys.Home;
import com.aymentlili.aamoomor.Adapters.Message_Adapter;
import com.aymentlili.aamoomor.Adapters.Users_Adapter;
import com.aymentlili.aamoomor.Entitys.Biding;
import com.aymentlili.aamoomor.Entitys.Chat;
import com.aymentlili.aamoomor.Entitys.User;
import com.aymentlili.aamoomor.R;
import com.aymentlili.aamoomor.Services.MyTouchListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

import dmax.dialog.SpotsDialog;

import static androidx.recyclerview.widget.LinearLayoutManager.VERTICAL;

public class Users extends Fragment {
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    AlertDialog nDialog;
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
        InitList();
        View view = layoutInflater.inflate(R.layout.users, viewGroup, false);
        riri = view.findViewById(R.id.users_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), VERTICAL, false);
        riri.setLayoutManager(layoutManager);
        riri.setHasFixedSize(true);
        HttpGetRequest request = new HttpGetRequest();
        request.execute();
        showprogressbar();
        riri.addOnItemTouchListener(new MyTouchListener(this.getContext(), riri, new MyTouchListener.OnTouchActionListener(){

            @Override
            public void onClick(View view, int n) {
                Home h = (Home) getActivity();
                h.addFragmentChats(ListOfItems.get(n).Username);
            }

            @Override
            public void onLeftSwipe(View view, int n) {

            }

            @Override
            public void onRightSwipe(View view, int n) {
            }
        }));

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
                toArrayOfObject(mJsonArray);


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
    public void toArrayOfObject(JSONArray jSONArray) throws JSONException {
        ArrayList<User> arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); ++i) {
            User u =  toObject(jSONArray.getJSONObject(i));

            arrayList.add(u);

        }
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String myid = firebaseUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ListOfItems.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Chat chat = snapshot.getValue(Chat.class);
                    if(chat.getReceiver().equals(myid))
                    {
                        for(User u : arrayList)
                        {
                            Log.d("getting reciever",u.fb_id);
                            if(u.fb_id.equals(chat.getSender()))
                            {  Log.d("got","sender");
                                ListOfItems.add(u);
                            }
                        }
                    }
                    if(chat.getSender().equals(myid))
                    {
                        for(User u : arrayList)
                        {
                            Log.d("getting reciever",u.fb_id);
                            if(u.fb_id.equals(chat.getReceiver()))
                            {Log.d("got ","reciever");
                                Log.d("getting reciever","");
                                ListOfItems.add(u);
                            }
                        }
                    }
                }

                List<User> listWithoutDuplicates = ListOfItems.stream().distinct().collect(Collectors.toList());
                Log.d("list of contacts", String.valueOf(ListOfItems.size()));
                adapter = new Users_Adapter(listWithoutDuplicates, getContext());
                riri.setAdapter(adapter);
                nDialog.dismiss();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
public void showprogressbar()
{
    nDialog = new SpotsDialog(getContext(),R.style.Custom);
    nDialog.setMessage("Loading..");
    nDialog.setTitle("Get Data");

    nDialog.setCancelable(true);
    nDialog.show();
}


}
