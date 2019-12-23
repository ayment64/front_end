package com.aymentlili.aamoomor.Fragments.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aymentlili.aamoomor.Activitys.Home;
import com.aymentlili.aamoomor.Activitys.MainActivity;
import com.aymentlili.aamoomor.Entitys.User;
import com.aymentlili.aamoomor.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Log_in extends Fragment
{
    private static final String base_url = "http://192.168.43.80:3000/Users/";
    private static int responseCode;
    public static String s;
    public static String t;
    public EditText Password;
    public EditText Username;
    public boolean check;
    public TextView fp;
    public Button next;
    public TextView sub;

    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }


    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.check = false;
        View view = layoutInflater.inflate(R.layout.fragment_m_log_in, viewGroup, false);

        this.Username =  view.findViewById(R.id.T_E_Log_in_Username);
        this.Password =  view.findViewById(R.id.T_E_Log_in_Password);
        this.next     =  view.findViewById(R.id.BU_Log_in_Next);
        this.next.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if (isEmpty(Username)) {
                    Username.setError("Username can not be Empty ");
                }
                if (isEmpty(Password)) {
                    Password.setError( "Password is empty");
                }
                if (!isEmpty(Username) && !isEmpty(Password)) {
                  s = Username.getText().toString();
                  t = Password.getText().toString();

                  MainActivity m = (MainActivity) getActivity();
                    HttpGetRequest request =new HttpGetRequest();
                    request.execute();

                }
                Log.d("Satate of check", String.valueOf(check));
            }
        });

       this.sub =view.findViewById(R.id.T_V_Log_in_To_Subscribe);
        this.sub.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                ((MainActivity)Log_in.this.getActivity()).addFragmentSubscribe();
            }
        });
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

                URL myUrl = new URL(base_url +s+"/"+t);
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
                MainActivity m = (MainActivity) getActivity();
                m.u = new User();
                m.u.Username=Userobject.getString("Username");
                m.u.Password=Userobject.getString("Password");
                m.u.Name=Userobject.getString("Name");
                m.u.First_name=Userobject.getString("FirstName");
                m.u.Email=Userobject.getString("Email");
                m.u.image=Userobject.getString("image");
                m.u.Description=Userobject.getString("description");
                m.u.Phone_Number=Userobject.getString("phone_number");
                m.u.Job=Userobject.getString("job");
                Intent i = new Intent(getContext(),Home.class);
                i.putExtra("Username",m.u.Username);
                Log.d("username // ",m.u.Username );
                i.putExtra("Password",m.u.Password);
                i.putExtra("First_name",m.u.First_name);
                i.putExtra("Name",m.u.Name);
                i.putExtra("Email",m.u.Email);
                i.putExtra("image",m.u.image);
                i.putExtra("description",m.u.Description);
                i.putExtra("phone_number",m.u.Phone_Number);
                i.putExtra("job",m.u.Job);
                startActivityForResult(i,1);

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
    }    }