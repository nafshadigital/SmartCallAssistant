package com.nafshadigital.smartcallassistant.activity;

import android.Manifest;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.adapter.ListAdapterViewActivity;
import com.nafshadigital.smartcallassistant.floatingAnimation.Direction;
import com.nafshadigital.smartcallassistant.floatingAnimation.TransparentActivity;
import com.nafshadigital.smartcallassistant.floatingAnimation.ZeroGravityAnimation;
import com.nafshadigital.smartcallassistant.helpers.AppRunning;
import com.nafshadigital.smartcallassistant.helpers.MyToast;
import com.nafshadigital.smartcallassistant.network.ApiInterface;
import com.nafshadigital.smartcallassistant.network.SmartCallAssistantApiClient;
import com.nafshadigital.smartcallassistant.service.SyncContactsService;
import com.nafshadigital.smartcallassistant.vo.ActivityVO;
import com.nafshadigital.smartcallassistant.vo.FCMNotificationVO;
import com.nafshadigital.smartcallassistant.vo.LastSeenVO;
import com.nafshadigital.smartcallassistant.vo.SendNotificationResponse;
import com.nafshadigital.smartcallassistant.vo.SettingsVO;
import com.nafshadigital.smartcallassistant.vo.SyncContactVO;
import com.nafshadigital.smartcallassistant.vo.UpdateFcmTokenResponse;
import com.nafshadigital.smartcallassistant.vo.UpdateLastSeenResponse;
import com.nafshadigital.smartcallassistant.webservice.ActivityService;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.MODIFY_PHONE_STATE;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.RECEIVE_BOOT_COMPLETED;

public class Dashboard extends AppCompatActivity {


    ImageView imgadd;
    ListView listactivity;
    TextView txtactcount;
    ArrayList<ActivityVO> actAL;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigation;
    public static final int RequestPermissionCode = 1;
    String selectedUserID;
    Intent intent;
    private Context context;
    private NotifyActivity remActivity;
    private String fcm_Token;
    private static final String TAG = "Dashboard";
    Intent syncContactsIntent;
    private SyncContactsService syncContactsService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        // Smart Call Assistant
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        imgadd = findViewById(R.id.btnaddactivity);
        listactivity = findViewById(R.id.listactivity);
       // txtactcount = (TextView) findViewById(R.id.tvtotnoact);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( Dashboard.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                fcm_Token = newToken;
                Log.e("newToken",newToken);

                // Save FCM Token into the context
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("fcmtoken", fcm_Token);
                editor.commit();

                String userid = sharedPreferences.getString("userID","");

                SyncContactVO users = new SyncContactVO(null);
                users.fcmtoken = fcm_Token;
                users.id = userid;

                Call<UpdateFcmTokenResponse> call= SmartCallAssistantApiClient.getClient()
                        .create(ApiInterface.class).updateFCM(users);
                call.enqueue(new Callback<UpdateFcmTokenResponse>() {
                    @Override
                    public void onResponse(Call<UpdateFcmTokenResponse> call, Response<UpdateFcmTokenResponse> response) {
                        UpdateFcmTokenResponse updateFcmTokenResponse=response.body();
                        if(updateFcmTokenResponse!=null )
                        Log.d(TAG, "onResponse: "+updateFcmTokenResponse.getMessage());
                    }

                    @Override
                    public void onFailure(Call<UpdateFcmTokenResponse> call, Throwable t) {

                    }
                });

            }
        });

        FCMNotificationVO fcmNotificationVO = new FCMNotificationVO();

        fcmNotificationVO.title = "Title";
        fcmNotificationVO.message = "Message";
        fcmNotificationVO.token = fcm_Token;


        Call<SendNotificationResponse> call= SmartCallAssistantApiClient.getClient()
                .create(ApiInterface.class).sendNotification(fcmNotificationVO);
        call.enqueue(new Callback<SendNotificationResponse>() {
            @Override
            public void onResponse(Call<SendNotificationResponse> call, Response<SendNotificationResponse> response) {
                Log.v("newToken","Calling Notification sender PHP ");
                try {
                    SendNotificationResponse sendNotificationResponse=response.body();
                    if(sendNotificationResponse!=null && sendNotificationResponse.getMessage()!=null)
                    MyToast.show(getApplicationContext(),sendNotificationResponse.getMessage());
                }catch (Exception e) {
                    Log.v("newToken","Trying to sending notification " + e.toString() + "\n" + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<SendNotificationResponse> call, Throwable t) {

            }
        });

        this.context = this;

        SyncContactsService syncContactsService = new SyncContactsService(this.context);

        syncContactsIntent = new Intent(this.context, syncContactsService.getClass());
        startService(syncContactsIntent);

        if (!isMyServiceRunning(SyncContactsService.class)) {
                startService(syncContactsIntent);
        }

        intent = new Intent(this.context,BgPCICallService.class);
        ((AppRunning) context.getApplicationContext()).setBGServiceRunning(true);
        startService(intent);

        sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        selectedUserID =  sharedPreferences.getString("userID", "");

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null && bundle.get("dashuserId")!=null) {
            selectedUserID = bundle.getString("dashuserId");
        }

        updateLastSeen();

      /*  JSONObject jsonupdate = new JSONObject();
        try {
            jsonupdate.put("token", selectedUserID);
            MyToast.show(getApplicationContext(),selectedUserID);
        }catch (JSONException e) {

        }
        String res = MyRestAPI.PostCall("updateLastSeen/",jsonupdate); */

        manifestPermission();

        ActivityService actService = new ActivityService(getApplicationContext());
        int actcount = actService.getActivityCount();
       // txtactcount.setText(""+actcount);
       // MyToast.show(this,""+actcount);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        initInstances();
        displayActivity();


       // SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("bgservice", Context.MODE_PRIVATE);
        SettingsVO settingsVO = new SettingsVO(getApplicationContext());
        settingsVO.getSettings();
        String isMobileMute =settingsVO.ismobilemute;


        JSONObject jsonObject = new JSONObject();
        //String res = MyRestAPI.PostCall("updateLastSeen"+"/"+ + "/",jsonObject);

        /*AudioManager am =  (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE); */

        remActivity = (NotifyActivity) findViewById(R.id.remactivity);
        remActivity.isCompact = true;
        remActivity.init();

    }

    public void addactivty(View view) {

        Intent i = new Intent(this, AddActivity.class);
        startActivity(i);
    }

    public void flowHearts()
    {
        Intent i = new Intent(this, TransparentActivity.class);
        startActivity(i);
    }

    public void displayActivity() {
        ActivityService activitySer = new ActivityService(getApplicationContext());
        actAL = activitySer.getAllActivity();
        ListAdapterViewActivity adapter = new ListAdapterViewActivity(this, actAL);
        listactivity.setAdapter(adapter);
        listactivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Your code here
                //int position is the index of the list item you clicked
                //use it to manipulate the item for each click
                Toast.makeText(getApplicationContext(), "selected Item Name is " + position, Toast.LENGTH_LONG).show();
            }

        });

    }

    private void initInstances() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(Dashboard.this, drawerLayout, R.string.hello_world, R.string.hello_world);
        drawerLayout.setDrawerListener(drawerToggle);

        navigation = findViewById(R.id.navigation_view);

        View hView = navigation.getHeaderView(0);

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()

        {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {

                    case R.id.manageact:
                        Intent indash = new Intent(Dashboard.this, Dashboard.class);
                        startActivity(indash);
                        break;

                    case R.id.myprofile:
                        Intent in = new Intent(Dashboard.this, MyProfile.class);
                        startActivity(in);
                        break;

                 /*   case R.id.contacts:
                        Intent i = new Intent(Dashboard.this, Contacts.class);
                        startActivity(i);
                        break; */

                    case R.id.callog:
                        Intent calllog = new Intent(Dashboard.this, CallLogs.class);
                        startActivity(calllog);
                        break;

                    case R.id.favourite:
                        Intent fav = new Intent(Dashboard.this, FavouriteActivity.class);
                        startActivity(fav);
                        break;

                    case R.id.heart:
                        Intent sendHeart = new Intent(Dashboard.this, SendHeartActivity.class);
                        startActivity(sendHeart);
                        break;

                    case R.id.shareheart:
                        Intent shareHeart = new Intent(Dashboard.this, ShareHeartsActivity.class);
                        startActivity(shareHeart);
                        break;

                    case R.id.help:
                        Intent help = new Intent(Dashboard.this, Help.class);
                        startActivity(help);
                        break;
                 /*   case R.id.settings:
                        Intent set = new Intent(Dashboard.this,Settings.class);
                        startActivity(set);
                        break; */
                }
                return false;
            }
        });

    }


    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
            return true;

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.action_settings) {
            return true;
        } */

        return super.onOptionsItemSelected(item);
    }





    public void manifestPermission(){
        if (checkPermission()) {
           // Toast.makeText(Dashboard.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();
        } else {
            requestPermission();
        }
    }
    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{CALL_PHONE, READ_PHONE_STATE,READ_CONTACTS, MODIFY_PHONE_STATE, INTERNET,   RECEIVE_BOOT_COMPLETED},
                RequestPermissionCode);

        /*
        ActivityCompat.requestPermissions(Dashboard.this, new String[]
                {
                        Manifest.permission.
                        READ_PHONE_STATE
                }, RequestPermissionCode);


        ActivityCompat.requestPermissions(Dashboard.this, new String[]
                {
                        READ_CONTACTS
                }, RequestPermissionCode);

               */


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0) {
                  //  boolean SmsPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean PhonestatePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (PhonestatePermission) {
                   //     Toast.makeText(Dashboard.this, "Permission has been Successfully Granted", Toast.LENGTH_LONG).show();
                    } else {
                   //     Toast.makeText(Dashboard.this, "Permission has been Denied", Toast.LENGTH_LONG).show();
                    }

                }
                break;
        }
    }

    public boolean checkPermission() {
     //   int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS);
        int PermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE);

        return PermissionResult == PackageManager.PERMISSION_GRANTED ;

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //MyToast.show(this, "onActivityResult" + requestCode);
        if (requestCode == 1) {

                displayActivity();

        }
    }

    private void updateLastSeen(){
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(Dashboard.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<UpdateLastSeenResponse> call= SmartCallAssistantApiClient.getClient()
                .create(ApiInterface.class).updateLastSeen(new LastSeenVO(selectedUserID));
        call.enqueue(new Callback<UpdateLastSeenResponse>() {
            @Override
            public void onResponse(Call<UpdateLastSeenResponse> call, Response<UpdateLastSeenResponse> response) {
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UpdateLastSeenResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }



    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }

    public void flyEmoji(final int resId) {
        ZeroGravityAnimation animation = new ZeroGravityAnimation();
        animation.setCount(1);
        animation.setScalingFactor(1f);
        animation.setOriginationDirection(Direction.BOTTOM);
        animation.setDestinationDirection(Direction.TOP);
        animation.setImage(resId);
        animation.setAnimationListener(new Animation.AnimationListener() {
                                           @Override
                                           public void onAnimationStart(Animation animation) {

                                           }
                                           @Override
                                           public void onAnimationEnd(Animation animation) {

                                           }

                                           @Override
                                           public void onAnimationRepeat(Animation animation) {

                                           }
                                       }
        );

        ViewGroup container = findViewById(R.id.animation_holder);
        animation.play(this,container);

    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = this.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String message =  sharedPreferences.getString("message", "");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("message", "0");
        editor.commit();

        if(message.equals("1"))
        {
            flowHearts();
        }
    }
}



