package com.example.lab8;

import androidx.fragment.app.FragmentActivity;

import org.joda.time.DateTime;
import android.os.Bundle;

import android.widget.Toast;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;

import java.io.IOException;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double departureLatitude;
    private double departureLongitude;
    private double arrivalLatitude;
    private double arrivalLongitude;
    private Bundle arguments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        arguments = getIntent().getExtras();
        departureLatitude = arguments.getDouble("DepartureLatitude");
        departureLongitude = arguments.getDouble("DepartureLongitude");
        arrivalLatitude = arguments.getDouble("ArrivalLatitude");
        arrivalLongitude = arguments.getDouble("ArrivalLongitude");
        Toast.makeText(this,arrivalLatitude + "," + arrivalLongitude,Toast.LENGTH_SHORT);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(
                new LatLng(arguments.getDouble("DepartureLatitude"),
                        arguments.getDouble("DepartureLongitude"))));
        mMap.addMarker(new MarkerOptions().position(
                new LatLng(arrivalLatitude,arrivalLongitude)));
        GeoApiContext geoApiContext = new GeoApiContext.Builder()
                .apiKey("KEY")
                .build();
        DirectionsResult result = null;
        try {
            result = DirectionsApi.newRequest(geoApiContext)
                    .mode(TravelMode.WALKING)
                    .origin(new com.google.maps.model.LatLng(departureLatitude,departureLongitude))
                    .destination(new com.google.maps.model.LatLng(arrivalLatitude,arrivalLongitude))
                    .departureTime(new DateTime())
                    .await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<com.google.maps.model.LatLng> path = result.routes[0].overviewPolyline.decodePath();

        LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();
        PolylineOptions line = new PolylineOptions();
        for (int i = 0; i < path.size(); i++) {
            line.add(new com.google.android.gms.maps.model.LatLng(path.get(i).lat, path.get(i).lng));
            latLngBuilder.include(new com.google.android.gms.maps.model.LatLng(path.get(i).lat, path.get(i).lng));
        }
        googleMap.addPolyline(line);

    }



}
