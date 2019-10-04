package edu.icesi.arithgo;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private Marker locationUser;
    private Geocoder geocoder;
    private TextView siteText;
    private Polygon libraryZone;
    private ArrayList<Polygon> reactiveZones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
        }, 11);

        ArrayList<Polygon> reactiveZones = new ArrayList<Polygon>();


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    @SuppressLint("MissingPermission")
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        geocoder = new Geocoder(this, Locale.getDefault());
        // Add a marker in Sydney and move the camera

        initializePolygons();

        LatLng icesi = new LatLng(3.342262, -76.529901);
        locationUser = mMap.addMarker(new MarkerOptions().position(icesi).title("Usted"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(icesi, 18));

        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,  1000, 0, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
        locationUser.setPosition(pos);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 18));

        boolean isInLibrary = PolyUtil.containsLocation(pos, libraryZone.getPoints(), true);

        if(isInLibrary){
            Intent i = new Intent(MapsActivity.this, ExchangeActivity.class);
            startActivity(i);

        }else {

            for (Polygon polygon : reactiveZones
                 ) {
                boolean isInZone = PolyUtil.containsLocation(pos, libraryZone.getPoints(), true);
                if(isInZone){
                    Intent i = new Intent(MapsActivity.this, QuestionActivity.class);
                    startActivity(i);
                }
            }
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void initializePolygons(){

        PolygonOptions poLibrary = new PolygonOptions().add(
                new LatLng(3.341962, -76.530078),
                new LatLng(3.341667, -76.530095),
                new LatLng(3.341662, -76.529783),
                new LatLng(3.341946, -76.529778)
        ).fillColor(R.color.colorPrimary);

        PolygonOptions poBuildingD = new PolygonOptions().add(
                new LatLng(3.341051, -76.530481),
                new LatLng(3.340826, -76.530492),
                new LatLng(3.340784, -76.529934),
                new LatLng(3.341003, -76.529934)
        ).fillColor(R.color.colorPrimary);

        PolygonOptions poBuildingL = new PolygonOptions().add(
                new LatLng(3.342524, -76.528963),
                new LatLng(3.342760, -76.528389),
                new LatLng(3.342637, -76.528212),
                new LatLng(3.341726, -76.528260),
                new LatLng(3.341748, -76.529032)
        ).fillColor(R.color.colorPrimary);


        libraryZone = mMap.addPolygon(poLibrary);

        Polygon polygonD = mMap.addPolygon(poBuildingD);
        reactiveZones.add(polygonD);

        Polygon polygonL = mMap.addPolygon(poBuildingL);
        reactiveZones.add(polygonL);

    }
}
