package com.aymentlili.aamoomor.Fragments.User;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aymentlili.aamoomor.Activitys.Home;
import com.aymentlili.aamoomor.Adapters.Custom_Adapter;
import com.aymentlili.aamoomor.Entitys.Estate;
import com.aymentlili.aamoomor.R;
import com.aymentlili.aamoomor.Services.CircleTransform;
import com.aymentlili.aamoomor.Services.FileUploadService;
import com.aymentlili.aamoomor.Services.FileUtil;
import com.aymentlili.aamoomor.Services.ServiceGenerator;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_profile extends Fragment {
    private static final String base_url = "http://10.0.2.2:3000/Estates/";
    private static final int PICK_IMAGE_REQUEST = 1;
    public static RecyclerView riri;
    public static RecyclerView.Adapter adapter;
    public TextView Email;
    public TextView Firstname;
    public TextView Name;
    public TextView Job;
    public TextView Description;
    public TextView PhoneNumber;
    public TextView Username;
   public ImageView circularImageView;
    ImageView i ;
    public static ArrayList<Estate> ListOfItems;
    private boolean isEmpty(TextView TextView) {
        return TextView.getText().toString().trim().length() == 0;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view        = layoutInflater.inflate(R.layout.user_profile, viewGroup, false);
        circularImageView = view.findViewById(R.id.C_I_Image_Profile);

// Set Color
      /*  circularImageView.setCircleColor(Color.WHITE);
// or with gradient
        circularImageView.setCircleColorStart(Color.WHITE);
        circularImageView.setCircleColorEnd(Color.BLUE);
        circularImageView.setCircleColorDirection(CircularImageView.GradientDirection.TOP_TO_BOTTOM);

// Set Border
        circularImageView.setBorderWidth(10f);
        circularImageView.setBorderColor(Color.WHITE);
// or with gradient
        circularImageView.setBorderColorStart(Color.WHITE);
        circularImageView.setBorderColorEnd(Color.WHITE);
        circularImageView.setBorderColorDirection(CircularImageView.GradientDirection.TOP_TO_BOTTOM);*/

// Add Shadow with default param

// or with custom param

        circularImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();

            }
        });
        Email = view.findViewById(R.id.T_V_Profile_Email);
        Firstname = view.findViewById(R.id.T_V_Profile_First_name);
        Name = view.findViewById(R.id.T_V_Profile_Last_name);
        Job = view.findViewById(R.id.T_V_Profile_Job);
        PhoneNumber = view.findViewById(R.id.T_V_Profile_Phone_Number);
        Username = view.findViewById(R.id.T_V_Profile_Username);
        Email = view.findViewById(R.id.T_V_Profile_Email);
        Description = view.findViewById(R.id.T_V_Profile_Desccription);
        Home h = (Home) getActivity();
        Email.setText(h.u.Email);
        Username.setText(h.u.Username);
        Name.setText(h.u.Name);
        Firstname.setText(h.u.First_name);
        Job.setText(h.u.Job);
        Description.setText(h.u.Description);
        PhoneNumber.setText(h.u.Phone_Number);
        Picasso.get().load("http:/10.0.2.2:3000/test/"+h.u.image).transform(new CircleTransform()).into(circularImageView);
        riri =view.findViewById(R.id.Profile_Receptor);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        riri.setLayoutManager(gridLayoutManager);
        HttpGetRequest request =new HttpGetRequest();
        request.execute();

        return view;
    }
    private void showFileChooser() {
        Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        this.startActivityForResult(intent, this.PICK_IMAGE_REQUEST);
    }

    private void uploadFile(Uri fileUri) {
        Home h = (Home) getActivity();
        // create upload service client
        FileUploadService service =
                ServiceGenerator.createService(FileUploadService.class);

        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = new File(FileUtil.getPath( getContext(),fileUri));
        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(h.getContentResolver().getType(fileUri)),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("profileImage", file.getName(), requestFile);

        // add another part within the multipart request
        String descriptionString = h.u.Username;
        RequestBody description =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString);

        // finally, execute the request
        Call<ResponseBody> call = service.upload(description, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                Log.v("Upload", "success");
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data!=null)
        {
            Uri uri = data.getData();
            circularImageView.setImageURI(uri);
            uploadFile(uri);

        }
        switch(requestCode)
        {
            case PICK_IMAGE_REQUEST:
                if(resultCode==1)
                {

                    Uri uri = data.getData();
                   circularImageView.setImageURI(uri);
                }
                break;

        }
    }
    public class HttpGetRequest extends AsyncTask<String, Void, String> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;
        @Override
        protected String doInBackground(String... params){
            Home h = (Home) getActivity();

            String result;
            String inputLine;
            try {
                //Create a URL object holding our url

                URL myUrl = new URL(base_url+h.u.Username);
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



        protected void onPostExecute(String result){

            if (result != null)
            {
                if(ListOfItems.size()>1)
                {Log.d("listofitems",ListOfItems.get(0).adress);
                adapter = new Custom_Adapter(ListOfItems, getContext());
                riri.setAdapter(adapter);}
            }

            super.onPostExecute(result);

        }
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
        Log.d("toObject", estate.adress);
        return estate;
    }
    public ArrayList<Estate> toArrayOfObject(JSONArray jSONArray) throws JSONException {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            new Estate();
            Estate estate = this.toObject(jSONArray.getJSONObject(i));
            arrayList.add(estate);
        }
        return arrayList;
    }
}