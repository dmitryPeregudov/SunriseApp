package com.dethdemonaexemple.sunriseapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Presenter implements LocationListener {
    private static Context context;
    private static Presenter presenter;
    private LocationManager gps;
    private LocationManager agps;
    private boolean firstTime = true;


    private Presenter(Context context) {
        this.context = context;
        gps = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        agps = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

    }
@SuppressLint("MissingPermission")
void registerGps(){
    try{gps.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        agps.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);}
    catch (Exception e){Log.d("MyLog","Catch");}
}
    public static Presenter getPresenter(Context context) {
        if (presenter == null) {
            presenter = new Presenter(context);
        }
        return presenter;
    }

    public void newData(final double latitude, final double longitude, String data) {

        NetworkService.getInstance()
                .getJSONApi()
                .getDataCity(latitude, longitude, 0, data)
                .enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(@NonNull Call<Data> call, @NonNull Response<Data> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            MyResponse myResponse = new MyResponse(response.body().getResults());
                            Intent intent = new Intent(context.getString(R.string.receiver));
                            intent.putExtra("result", context.getString(R.string.new_data));
                            intent.putExtra("data", myResponse);

                            context.sendBroadcast(intent);


                        } else {
                            if (response.body() == null) {
                                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();Log.d("MyLog",latitude+" "+longitude);
                            } else {
                                switch (response.code()) {
                                    case 404:
                                        Toast.makeText(context, "Error 404", Toast.LENGTH_LONG).show();
                                        break;
                                    case 500:
                                        Toast.makeText(context, "Error 500", Toast.LENGTH_LONG).show();
                                        break;
                                }
                            }

                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Data> call, @NonNull Throwable t) {
                        Toast.makeText(context, "Response failure", Toast.LENGTH_LONG).show();

                        t.printStackTrace();
                    }
                });


    }

    @Override
    public void onLocationChanged(Location location) {
        MainActivity.latitude = location.getLatitude();
        MainActivity.longitude = location.getLongitude();

    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }


    @Override
    public void onProviderEnabled(String provider) {
            }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
