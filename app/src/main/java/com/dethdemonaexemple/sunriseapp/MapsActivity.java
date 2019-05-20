package com.dethdemonaexemple.sunriseapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.DatePicker;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private GoogleMap mMap;
    private DatePicker datePicker;
    Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
       datePicker=findViewById(R.id.datePicker2);
        datePicker.setCalendarViewShown(false);
        presenter=Presenter.getPresenter(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
       try {
           mapFragment.getMapAsync(this);
       }catch (NullPointerException e){}

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.clear();



    }

    @Override
    protected void onStop() {
        finish();
        super.onStop();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng).title("YOUR CITY"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        String date=datePicker.getYear()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getDayOfMonth();
        presenter.newData(latLng.latitude,latLng.longitude,date);
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();

    }
}