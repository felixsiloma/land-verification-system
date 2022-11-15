package com.oyopizmanga.landownerverification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class mapsfinalActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    TextView txtbro;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapsfinal);
        txtbro = findViewById(R.id.txtbro);
        txtbro.setText(getIntent().getStringExtra("mapslcn") + "");
        // initializing our search view.
        searchView = findViewById(R.id.idSearchView);
        check();
        String voke = txtbro.getText().toString();
        searchView.setQuery(voke, false);
        // Obtain the SupportMapFragment and get notified
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        // adding on query listener for our search view.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        // at last we calling our map fragment to update.
        mapFragment.getMapAsync(this);
    }

    private void check() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String location = searchView.getQuery().toString();

        List<Address> addressList = null;
        if (Geocoder.isPresent()) {
            if (location != null || location.equals("")) {
                // on below line we are creating and initializing a geo coder.
                Geocoder geocoder = new Geocoder(mapsfinalActivity.this);
                try {
                    try {
                        List<Address> geoResults = geocoder.getFromLocationName(location, 1);
                        while (geoResults.size() == 0) {
                            geoResults = geocoder.getFromLocationName(location, 1);
                        }
                        if (geoResults.size() > 0) {
                            Address addr = geoResults.get(0);
                            LatLng latLng = new LatLng(addr.getLatitude(), addr.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(latLng).title(location));

                            // below line is to animate camera to that position.
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                        }
                    } catch (Exception e) {
                        Toast.makeText(this, "Location Not found", Toast.LENGTH_SHORT).show();
                    }
                    // on below line we are getting the location
                    // from our list a first position.
                    //Address address = addressList.get(0);
                    // on below line we are creating a variable for our location
                    // where we will add our locations latitude and longitude.
                    //LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                    // on below line we are adding marker to that position.

                } finally {

                }
                // checking if the entered location is null or not.

            } else {
                Toast.makeText(this, "geocoder not present", Toast.LENGTH_SHORT).show();
            }
        }
    }
}