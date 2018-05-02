package com.example.igor.msqlandroid.View;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
//import android.app.FragmentTransaction;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.igor.msqlandroid.FregistroAnuncio;
import com.example.igor.msqlandroid.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends android.support.v4.app.FragmentActivity {
    Button enviarD;
    String valorLong;
    String valorLat;
    String dato;
    private Marker marcador;
    String punto;
    private GoogleMap mMap;
    private TextView mTapTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        enviarD = (Button) findViewById(R.id.enviardatos);
        final Context context = this;

       /* FregistroAnuncio vistafragment= new FregistroAnuncio();
        Bundle args = new Bundle();
        args.putString("dato",dato);
        vistafragment.setArguments(args);*/

        enviarD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.putExtra("message_return", "This data is returned when user click button in target activity.");
                intent.putExtra("dato",dato);
                intent.putExtra("dato1",valorLat);
                intent.putExtra("dato2",valorLong);
                //startActivityForResult(intent,REQUEST_SECOND);
                setResult(Activity.RESULT_OK, intent);
                finish();
                Log.i("Direccion:1:", "aaaaaaaaaaaaaaaaaaa");
              /*  FregistroAnuncio fragment = new FregistroAnuncio();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.map, fragment);
                fragmentTransaction.commit();*/

              //onBackPressed();
               /* FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                //agrega el Fragment en el contenedor, en este caso el FrameLayout con id `FrameLayout`.
                ft.add(R.id.contenedor, new FregistroAnuncio());
                ft.commit();*/

                // FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
                // fragTransaction.replace(R.id.contenedor, vistafragment, null);
                //fragTransaction.commit();

            }
        });
        //comprobacionesMapa();



    mMap =((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
        @Override
        public void onMapClick (LatLng latLng){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        if (latLng.latitude != 0.0 && latLng.longitude != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        latLng.latitude, latLng.longitude, 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    dato = DirCalle.getAddressLine(0);
                    valorLat = String.valueOf(latLng.latitude);
                    valorLong = String.valueOf(latLng.longitude);
                    Log.i("Direccion:1:", dato);
                    Log.i("Longitud:1:", valorLong);
                    Log.i("Latitud:1:", valorLat);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        markerOptions.title(dato);
        mMap.clear();
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.addMarker(markerOptions);
    }
    });




}


   /* private void configurarMapa()
    {
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
    }

    @Override
    public void onMapClick(LatLng punto) {
        mMap.addMarker(new MarkerOptions()
                        .position(punto)
                        .title("You are here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        MarkerOptions options = new MarkerOptions()
                .title(addres.getLocality())
                .position(new LatLng(punto.latitude,
                        punto.longitude));

        marker = mMap.addMarker(options);

        mTapTextView.setText("Punto marcado=" + punto);
    }

    @Override
    public void onMapLongClick(LatLng punto1) {
        mTapTextView.setText("Punto, presionado=" + punto1);


    }



    public void onLocationChanged(Location location) {

        valorLat = (location.getLatitude()) + "";
        valorLong = (location.getLongitude()) + "";
        Log.i("SLatitud::", valorLat);
        Log.i("SLongitud::", valorLong);

        if (location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        location.getLatitude(), location.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    dato = DirCalle.getAddressLine(0);
                    Log.i("SDireccion::", dato.toString());
                    Toast.makeText(this,dato.toString(),Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }





    private void comprobacionesMapa() {
        // Hacer una comprobación nula para confirmar que ya no hemos instanciado el mapa.
        if (mMap == null) {
            // Intenta obtener el mapa desde el SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Comprueba si hemos tenido éxito en la obtención del mapa.
            if (mMap != null) {
                configurarMapa();
            }
        }
    }*/
       @Override
       public void onBackPressed() {
       if (getFragmentManager().getBackStackEntryCount() == 0) {
         Intent i =  new Intent();
         i.putExtra("dato",dato);
         //  i.putExtra("longitud",valorLong);
          // i.putExtra("latitud",valorLat);
           setResult(Activity.RESULT_OK, i);
          /* Bundle args = new Bundle();
           args.putString("dato",dato.toString());
           vistafragment.setArguments(args);
           FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
           fragTransaction.commit();*/
           Toast.makeText(this,"se envió la data",Toast.LENGTH_LONG).show();


           this.finish();
           //super.onBackPressed();
       } else {
           getFragmentManager().popBackStack();
           Toast.makeText(this,"ERROR AL ENVIAR DATA",Toast.LENGTH_LONG).show();
       }
   }
        }
