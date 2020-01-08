package com.aymentlili.aamoomor.Activitys;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aymentlili.aamoomor.Adapters.Message_Adapter;
import com.aymentlili.aamoomor.Adapters.Users_Adapter;
import com.aymentlili.aamoomor.Entitys.Chat;
import com.aymentlili.aamoomor.Entitys.Estate;
import com.aymentlili.aamoomor.Entitys.User;
import com.aymentlili.aamoomor.Fragments.Home.Add_Estate;
import com.aymentlili.aamoomor.Fragments.Home.Biddding_item_show;
import com.aymentlili.aamoomor.Fragments.Home.Biding_Item;
import com.aymentlili.aamoomor.Fragments.Home.Estate_profile;
import com.aymentlili.aamoomor.Fragments.Home.Estateee;
import com.aymentlili.aamoomor.Fragments.Home.Home_page;
import com.aymentlili.aamoomor.Fragments.Home.Chats;
import com.aymentlili.aamoomor.Fragments.Home.ProfileViewPager;
import com.aymentlili.aamoomor.Fragments.Home.User_profile;
import com.aymentlili.aamoomor.R;
import com.aymentlili.aamoomor.Services.CircleTransform;
import com.aymentlili.aamoomor.Services.MyService;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
import java.util.Optional;
import java.util.stream.Collectors;

public class Home extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {
    public static User u = new User();
    private static final String base_url = "http://10.0.2.2:3000/Users";
    private TextView Usename;
    private ImageView Image_view;
    private NavigationView mainNavigationView;
    public static FirebaseAuth firebaseAuth;
    public DrawerLayout mdrower;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HttpGetRequest request = new HttpGetRequest();
        request.execute();
        setContentView(R.layout.activity_home);
        Intent intent = this.getIntent();
        this.u.Username = intent.getStringExtra("Username");
        this.u.Password = intent.getStringExtra("Password");
        this.u.First_name = intent.getStringExtra("First_name");
        this.u.Name = intent.getStringExtra("Name");
        this.u.Email = intent.getStringExtra("Email");
        this.u.image = intent.getStringExtra("image");
        this.u.Description = intent.getStringExtra("description");
        this.u.Phone_Number = intent.getStringExtra("phone_number");
        this.u.Job = intent.getStringExtra("job");
        mdrower = findViewById(R.id.Drawer_layaout);




        this.mainNavigationView = this.findViewById(R.id.navigation_menu);
        if (mainNavigationView != null) {
            mainNavigationView.setNavigationItemSelectedListener(this);

        }
        View hView =  mainNavigationView.getHeaderView(0);
        Usename = hView.findViewById(R.id.Navigation_Header_Username);
        this.Usename.setText(u.Username);
        Image_view = hView.findViewById(R.id.Navigation_Header_Image_Profile);
        Picasso.get().load("http://10.0.2.2:3000/test/"+u.image).transform(new CircleTransform()).into(Image_view);

        addFragmentAddEsatate();
    }
    public void addFragmentProfile() {
        User_profile user_profile = new User_profile();
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.home_page_receptor, user_profile);
        fragmentTransaction.commit();
    }
    public void addFragmentChats(String username) {
        Chats user_profile = new Chats(username);
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.home_page_receptor, user_profile);
        fragmentTransaction.commit();
    }
    public void addFragmentProfileViewPager() {
        ProfileViewPager user_profile = new ProfileViewPager();
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.home_page_receptor, user_profile);
        fragmentTransaction.commit();
    }
    public void addFragmentHomePage() {
        Home_page user_profile = new Home_page();
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.home_page_receptor, user_profile);
        fragmentTransaction.commit();
    }
    public void addFragmentEstateProfile(Estate e) {

        Estate_profile user_profile = new Estate_profile(e);
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.home_page_receptor, user_profile);
        fragmentTransaction.commit();
    }
    public void addFragmentAddEsatate() {

        Add_Estate user_profile = new Add_Estate();
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.home_page_receptor, user_profile);
        fragmentTransaction.commit();
    }
    public void addFragmentAddBiddingItem(String housename) {
        Log.d("2house_name",housename);
        Biding_Item user_profile = new Biding_Item(housename);
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.Estate_Profile_biding_Receptor, user_profile);
        fragmentTransaction.commit();
    }
    public void addFragmentAddBiddingItemShow(String housename) {
        Log.d("home thebid",housename);
        Biddding_item_show user_profile = new Biddding_item_show(housename);
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.Estate_Profile_biding_Receptor, user_profile);
        fragmentTransaction.commit();
    }

    public void addFragmentAddEstateee(Estate e) {
        Estateee user_profile = new Estateee(e);
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.home_page_receptor, user_profile);
        fragmentTransaction.commit();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.Navigation_item_Profile)
        {
            addFragmentProfileViewPager();
            mdrower.closeDrawers();
        }
        if (id == R.id.Navigation_item_Home_Page)
        {
            addFragmentHomePage();
            mdrower.closeDrawers();
        }
        if (id == R.id.Navigation_item_Add_Estate)
        {
            addFragmentAddEsatate();
            mdrower.closeDrawers();
        }
        if (id == R.id.Navigation_item_Logout)
        {
            mdrower.closeDrawers();
            firebaseAuth.getInstance().signOut();
            Intent i = new Intent(this,Start_Activity.class);
            startActivity(i);
            finish();
        }

        return true;
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


            }
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
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String myid = firebaseUser.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Chat chat = snapshot.getValue(Chat.class);
                    if(chat.getReceiver().equals(myid))
                    {
                        for(User u : arrayList)
                        {

                            if(chat.State.equals("Unread"))
                            {
                                User sender = arrayList.stream().
                                        filter(p -> p.fb_id.equals(chat.sender)).
                                        findAny().orElse(null);
                                try {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        addNotification(chat,sender);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                }



            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addNotification(Chat msg, User sender) throws IOException {
        // create the notification
        Bitmap bmp = null;
        int NOTIFICATION_ID = 234;
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String CHANNEL_ID = "my_channel_01";
            CharSequence name = "my_channel";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 40});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }


        Notification.Builder m_notificationBuilder;

            m_notificationBuilder = new Notification.Builder(this,"my_channel_01")
                    .setContentTitle("new message")
                    .setContentText(msg.message)
                    .setSmallIcon(R.drawable.user_icon)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));


///////////////
        Intent resultIntent = new Intent(this, Start_Activity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(Start_Activity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        m_notificationBuilder.setContentIntent(resultPendingIntent);
        notificationManager.notify(NOTIFICATION_ID, m_notificationBuilder.build());

        // send the notification

    }
}
