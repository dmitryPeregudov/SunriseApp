package com.dethdemonaexemple.sunriseapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    static double latitude, longitude;
    BroadcastReceiver broadcastReceiver;
    TextView sunrise, sunset, solarNoon, dayLength, civilTwilightBegin,
            civilTwilightEnd, nauticalTwilightBegin, nauticalTwilightEnd,
            astronomicalTwilightBegin, astronomicalTwilightEnd;
    DatePicker datePicker;
    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionCheck();
        datePicker=findViewById(R.id.datePicker);
        presenter=Presenter.getPresenter(this);
        presenter.registerGps();
        enableLocation();
        initiate();
        registerBroad();



    }


    public void update(MyResponse response) {
        sunrise.setText(response.sunrise);
        sunset.setText(response.sunset);
        solarNoon.setText(response.solarNoon);
        dayLength.setText(response.dayLength);
        civilTwilightBegin.setText(response.civilTwilightBegin);
        civilTwilightEnd.setText(response.civilTwilightEnd);
        nauticalTwilightBegin.setText(response.nauticalTwilightBegin);
        nauticalTwilightEnd.setText(response.nauticalTwilightEnd);
        astronomicalTwilightBegin.setText(response.astronomicalTwilightBegin);
        astronomicalTwilightEnd.setText(response.astronomicalTwilightEnd);

    }

    public void initiate() {
        sunrise = findViewById(R.id.sunrise);
        sunset = findViewById(R.id.sunset);
        solarNoon = findViewById(R.id.solarNoon);
        dayLength = findViewById(R.id.dayLength);
        civilTwilightBegin = findViewById(R.id.civilTwilightBegin);
        civilTwilightEnd = findViewById(R.id.civilTwilightEnd);
        nauticalTwilightBegin = findViewById(R.id.nauticalTwilightBegin);
        nauticalTwilightEnd = findViewById(R.id.nauticalTwilightEnd);
        astronomicalTwilightBegin = findViewById(R.id.astronomicalTwilightBegin);
        astronomicalTwilightEnd = findViewById(R.id.astronomicalTwilightEnd);
    }

    public void registerBroad() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String a = intent.getStringExtra("result");
                if (a.equals(getString(R.string.new_data))) {
                    MyResponse response = (MyResponse) intent.getSerializableExtra("data");
                    update(response);
                }
            }
        };
        IntentFilter intFilt = new IntentFilter(getString(R.string.receiver));
        registerReceiver(broadcastReceiver, intFilt);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    void permissionCheck() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_DENIED
        ||ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_DENIED
        ||ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED
        ||ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    0);


        }
    }

    @Override
    protected void onResume() {
        presenter.registerGps();
        super.onResume();
    }

    public void clickerCurrent(View v){
        presenter.registerGps();
        String date=datePicker.getYear()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getDayOfMonth();
        presenter.newData(latitude,longitude,date);
    }

    public void clickerAnother(View v){
    Intent intent=new Intent(this,MapsActivity.class);
    startActivity(intent);
    finish();
    }



   void enableLocation(){
        if (isLocationServiceEnabled()) {
            //DO what you need...
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Seems Like location service is off,   Enable this to show map")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    }).setNegativeButton("NO THANKS", null).create().show();

        }}




public boolean isLocationServiceEnabled(){
        LocationManager locationManager = null;
        boolean gps_enabled= false,network_enabled = false;

        if(locationManager ==null)
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try{
        gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }catch(Exception ex){
        //do nothing...
        }

        try{
        network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }catch(Exception ex){
        //do nothing...
        }

        return gps_enabled || network_enabled;
}
}
