package com.aymentlili.aamoomor.Fragments.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aymentlili.aamoomor.Activitys.Home;
import com.aymentlili.aamoomor.Activitys.MainActivity;
import com.aymentlili.aamoomor.R;
import com.aymentlili.aamoomor.Services.FileUploadService;
import com.aymentlili.aamoomor.Services.FileUtil;
import com.aymentlili.aamoomor.Services.ServiceGenerator;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.apache.commons.io.FileUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_profile extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    public TextView Email;
    public TextView Firstname;
    public TextView Name;
    public TextView Job;
    public TextView Description;
    public TextView PhoneNumber;
    public TextView Username;
   public CircularImageView circularImageView;
    private boolean isEmpty(TextView TextView) {
        return TextView.getText().toString().trim().length() == 0;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view        = layoutInflater.inflate(R.layout.user_profile, viewGroup, false);
         circularImageView = view.findViewById(R.id.C_I_Image_Profile);
// Set Color
        circularImageView.setCircleColor(Color.WHITE);
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
        circularImageView.setBorderColorDirection(CircularImageView.GradientDirection.TOP_TO_BOTTOM);

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
        Picasso.get().load("http://192.168.43.80:3000/test/"+h.u.image).into(circularImageView);
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
}