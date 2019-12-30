package com.aymentlili.aamoomor.Fragments.Estate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Toast;

import com.aymentlili.aamoomor.Activitys.Home;
import com.aymentlili.aamoomor.Adapters.Add_Estate_Adapter;
import com.aymentlili.aamoomor.Adapters.Custom_Adapter;
import com.aymentlili.aamoomor.Entitys.Estate;
import com.aymentlili.aamoomor.Entitys.User;
import com.aymentlili.aamoomor.R;
import com.aymentlili.aamoomor.Services.FileUploadService;
import com.aymentlili.aamoomor.Services.FileUtil;
import com.aymentlili.aamoomor.Services.ServiceGenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class Add_Estate extends Fragment {
    private static final String base_url = "http://10.0.2.2:3000/Estates";
    private int responseCode;
    public static RecyclerView riri;
    public static RecyclerView.Adapter adapter;
    public static boolean check;
    public static Estate e = new Estate();
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_IMAGE_MULTIPLE = 2;
    ImageView image_select;
    EditText adress;
    EditText bathrooms;
    EditText bedrooms;
    EditText kitchens;
    EditText livingrooms;
    EditText name;
    EditText type;
    EditText Gardens;
    EditText Prix;
    EditText Forr;
    Button Post;
    Button Add_photos;
    public static ArrayList<Uri> ListOfItems;
    String imageEncoded;
    public static List<String> imagesEncodedList;
    public static Uri im;
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ListOfItems = new ArrayList<>();
        View view = layoutInflater.inflate(R.layout.add__estate, viewGroup, false);
        this.adress =view.findViewById(R.id.Estate_Profile_Adress);
        this.name =view.findViewById(R.id.Estate_Profile_Name);
        this.bathrooms =view.findViewById(R.id.Estate_Profile_Bathrooms);
        this.bedrooms =view.findViewById(R.id.Estate_Profile_bedrooms);
        this.livingrooms =view.findViewById(R.id.Estate_Profile_Livingrooms);
        this.kitchens = view.findViewById(R.id.Estate_Profile_Kitchen);
        this.image_select =view.findViewById(R.id.Estate_Profile_Thumb_nail);
        this.Gardens = view.findViewById(R.id.Estate_Profile_Gardens);
        this.type = view.findViewById(R.id.Estate_Profile_Type);
        this.Prix = view.findViewById(R.id.Estate_Profile_Prix);
        this.Forr = view.findViewById(R.id.Estate_Profile_for);
        this.Post = view.findViewById(R.id.add_estate_button);
        this.Add_photos = view.findViewById(R.id.Add_Pictures);
        Add_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        image_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        riri =view.findViewById(R.id.Add_Estate_Receptor);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        riri.setLayoutManager(gridLayoutManager);
        this.Post.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Home home = (Home) getActivity();
                e.type =type.getText().toString();
                e.forr = Forr.getText().toString();
                e.owner = home.u.Username;
                e.name = name.getText().toString();
                e.adress = adress.getText().toString();
                e.livingrooms = Integer.parseInt(livingrooms.getText().toString());
                e.bedrooms = Integer.parseInt(bedrooms.getText().toString());
                e.bathrooms = Integer.parseInt(bathrooms.getText().toString());
                e.kitchens = Integer.parseInt(kitchens.getText().toString());
                e.gardens = Integer.parseInt(Gardens.getText().toString());
                e.prix = Prix.getText().toString();
                HttpPostRequest request = new HttpPostRequest();
                request.execute();
            }
        });
        return view;
    }
    private void showFileChooser() {
        Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        this.startActivityForResult(intent, this.PICK_IMAGE_REQUEST);
    }
    private void showFileChooserMultiple() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE);
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

                String jsonData = e.toJSON();
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
            Home  h = (Home) getActivity();
            uploadFile(im);
            h.addFragmentHomePage();
            if(result!=null)
            {
                check = true;
            }
        }
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
                MultipartBody.Part.createFormData("houseImage", file.getName(), requestFile);

        // add another part within the multipart request
        String descriptionString = e.name;
        RequestBody description =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString);

        // finally, execute the request
        Call<ResponseBody> call = service.uupload(description, body);
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            Home h = (Home) getActivity();
            // When an Image is picked
            if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                    && null != data)
            {
                im = data.getData();
                image_select.setImageURI(im);
            }
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                imagesEncodedList = new ArrayList<String>();
                if(data.getData()!=null){

                    Uri mImageUri=data.getData();
                    ListOfItems.add(mImageUri);
                    im = data.getData();
                    image_select.setImageURI(im);
                    // Get the cursor
                    Cursor cursor = h.getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded  = cursor.getString(columnIndex);
                    cursor.close();

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            ListOfItems.add(uri);
                            // Get the cursor
                            Cursor cursor = h.getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded  = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                        }

                        image_select.setImageURI(ListOfItems.get(0));
                        adapter = new Add_Estate_Adapter(ListOfItems, getContext());
                        riri.setAdapter(adapter);
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
            } else {
                Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            Log.d("error",e.getMessage());
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
