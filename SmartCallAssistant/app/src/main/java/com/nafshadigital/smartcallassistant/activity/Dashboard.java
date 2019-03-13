package com.nafshadigital.smartcallassistant.activity;

import android.Manifest;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.adapter.ListAdapterViewActivity;
import com.nafshadigital.smartcallassistant.helpers.AppRunning;
import com.nafshadigital.smartcallassistant.helpers.MyToast;
import com.nafshadigital.smartcallassistant.service.SyncContactsService;
import com.nafshadigital.smartcallassistant.vo.ActivityVO;
import com.nafshadigital.smartcallassistant.vo.ContactVO;
import com.nafshadigital.smartcallassistant.vo.FCMNotificationVO;
import com.nafshadigital.smartcallassistant.vo.FavoriteVO;
import com.nafshadigital.smartcallassistant.vo.SettingsVO;
import com.nafshadigital.smartcallassistant.vo.UsersVO;
import com.nafshadigital.smartcallassistant.webservice.ActivityService;
import com.nafshadigital.smartcallassistant.webservice.MyRestAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.SEND_SMS;

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
    private String fcmToken;

    Intent syncContactsIntent;
    private SyncContactsService syncContactsService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Smart Call Assistant
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        imgadd = (ImageView) findViewById(R.id.btnaddactivity);
        listactivity = (ListView) findViewById(R.id.listactivity);
       // txtactcount = (TextView) findViewById(R.id.tvtotnoact);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( Dashboard.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                fcmToken = newToken;
                Log.e("newToken",newToken);

            }
        });

        FCMNotificationVO fcmNotificationVO = new FCMNotificationVO();

        fcmNotificationVO.title = "Title";
        fcmNotificationVO.message = "Message";
        fcmNotificationVO.token = fcmToken;
        String res = MyRestAPI.PostCall("send_notification",fcmNotificationVO.toJSONObject());
        Log.v("newToken","Calling Notification sender PHP ");
        try {
            JSONObject json = new JSONObject(res);
            MyToast.show(getApplicationContext(),json.getString("message"));
        }catch (JSONException e) {

            Log.v("newToken","Trying to sending notification " + e.toString() + "\n" + e.getMessage());
        }


        this.context = this;

        SyncContactsService syncContactsService = new SyncContactsService(this.context);

        syncContactsIntent = new Intent(this.context, syncContactsService.getClass());
        startService(syncContactsIntent);

        if (!isMyServiceRunning(SyncContactsService.class)) {
            //    startService(syncContactsIntent);
        }

        intent = new Intent(this.context,BgPCICallService.class);
        ((AppRunning) context.getApplicationContext()).setBGServiceRunning(true);
        startService(intent);

        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        selectedUserID =  sharedPreferences.getString("userID", "");

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null && bundle.get("dashuserId")!=null) {
            selectedUserID = bundle.getString("dashuserId");
          //  MyToast.show(this,"dashuserid="+ selectedUserID);
        }

        MyAsyncTasks runner = new MyAsyncTasks();
        runner.execute();

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

    /*
    public void favoritecheck(View view){
          String phnnum = "+919787249698";
        /*if(phnnum.length() > 8) {
            phnnum = phnnum.substring(phnnum.length()-8, phnnum.length());
        }

        MyToast.show(this,"Phnnum="+new FavoriteVO(getApplicationContext()).checkFavorites(phnnum));
    }  */

    public void addactivty(View view) {
        Intent i = new Intent(this, AddActivity.class);
        startActivity(i);
    }

    public void displayActivity() {
        ActivityService activitySer = new ActivityService(getApplicationContext());
        actAL = activitySer.getAllActivity();
        // MyToast.show(actAL);
        ListAdapterViewActivity adapter = new ListAdapterViewActivity(this, actAL);
        listactivity.setAdapter(adapter);
        //listactivity.setClickable(true);
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

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(Dashboard.this, drawerLayout, R.string.hello_world, R.string.hello_world);
        drawerLayout.setDrawerListener(drawerToggle);

        navigation = (NavigationView) findViewById(R.id.navigation_view);

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
        ActivityCompat.requestPermissions(Dashboard.this, new String[]
                {
                        Manifest.permission.
                        READ_PHONE_STATE
                }, RequestPermissionCode);

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
        int PermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);

        return PermissionResult == PackageManager.PERMISSION_GRANTED ;

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //MyToast.show(this, "onActivityResult" + requestCode);
        if (requestCode == 1) {

                displayActivity();

        }
    }


    public class MyAsyncTasks extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(Dashboard.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            JSONObject jsonupdate = new JSONObject();
            try {
                jsonupdate.put("token", selectedUserID);
              //  MyToast.show(getApplicationContext(),selectedUserID);
                System.out.println("userId="+selectedUserID);
            }catch (JSONException e) {

            }
            String res = MyRestAPI.PostCall("updateLastSeen/",jsonupdate);

            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
        }

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

}


