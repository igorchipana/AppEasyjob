package com.example.igor.msqlandroid.View;

import android.Manifest;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.igor.msqlandroid.Entidades.Anuncios;
import com.example.igor.msqlandroid.Entidades.Persona;
import com.example.igor.msqlandroid.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapsAnuncios extends FragmentActivity implements OnMapReadyCallback {
    private boolean mScrollable = true;
    private GoogleMap mMap;
    TextView dato3;
    public final int MY_PERMISSIONS_REQUEST =500;
    private View popup=null;
    ArrayList<Anuncios> listaAnuncios;
    String valordesc;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_anuncios);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);



    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                  marker.setVisible(true);
                if(popup==null){
                    popup=getLayoutInflater().inflate(R.layout.popup,null);

                }

                TextView dato1=(TextView)popup.findViewById(R.id.titulo);
                 //LinearLayout context=(LinearLayout)popup.findViewById(R.id.madrugada) ;
                TextView dato2=(TextView)popup.findViewById(R.id.desc);
                dato2.setMovementMethod (LinkMovementMethod.getInstance());
                dato1.setText(marker.getTitle());
                dato2.setText(marker.getSnippet());

                return (popup);


            }
        });
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST);
        }
        mMap.setMyLocationEnabled(true);
        String ip = getApplicationContext().getString(R.string.ip);
        String urlServices2 = ip + "/dbremota/WsListAnunciosMaps.php";
        RequestQueue requestQueue2 = Volley.newRequestQueue(this);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlServices2, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Anuncios oAnuncios = null;
                JSONArray json = response.optJSONArray("RESULTADO");
                try {
                    JSONObject jsonObjectTest = null;
                    jsonObjectTest = json.getJSONObject(0);
                    if (jsonObjectTest.optString("personaid").trim().equals("noEntra")) {
                    } else {
                        for (int i = 0; i < json.length(); i++) {
                            oAnuncios = new Anuncios();
                            JSONObject jsonObject = null;
                            jsonObject = json.getJSONObject(i);
                            oAnuncios.setIdAnuncio(jsonObject.optInt("idAnuncio "));
                            oAnuncios.setIdCategoria(jsonObject.optInt("idPersona"));
                            oAnuncios.setIdCategoria(jsonObject.optInt("idCategoria"));
                            oAnuncios.setFecha(jsonObject.optString("fecha"));
                            oAnuncios.setTitulo(jsonObject.optString("titulo"));
                            oAnuncios.setDescripcion(jsonObject.optString("descripcion"));
                            oAnuncios.setLatitud(jsonObject.optString("latitud"));
                            oAnuncios.setLongitud(jsonObject.optString("longitud"));
                            oAnuncios.setDireccion(jsonObject.optString("direccion"));
                            oAnuncios.setTelefono(jsonObject.optString("nombres"));
                            oAnuncios.setCorreo(jsonObject.optString("apellidos"));
                            oAnuncios.setTelefono(jsonObject.optString("telefono"));
                            oAnuncios.setCorreo(jsonObject.optString("correo"));
                            listaAnuncios = new ArrayList<>();
                            listaAnuncios.add(oAnuncios);
                            LatLng ListaAnuncios = new LatLng(Double.parseDouble(listaAnuncios.get(0).getLatitud()), Double.parseDouble(listaAnuncios.get(0).getLongitud()));
                            mMap.addMarker(new MarkerOptions().position(ListaAnuncios).title(listaAnuncios.get(0).getTitulo())
                                    .snippet("Descripción: "+listaAnuncios.get(0).getDescripcion()
                                            +"\n"
                                            +"\nTelefono: "+listaAnuncios.get(0).getTelefono()
                                            +"\nDirección: "+listaAnuncios.get(0).getDireccion())) ;




                          //  valordesc= (new MarkerOptions().position(ListaAnuncios)(listaAnuncios.get(0).getDescripcion().toString()));
                           // Log.d("hola",valordesc);

                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ListaAnuncios,7));


                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                    StyleableToast.makeText(getApplicationContext(), "Error del json: " + e, R.style.exampletoast).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                StyleableToast.makeText(getApplicationContext(), "No se pudo conectar al JSON : " + error, R.style.exampletoast).show();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);
        requestQueue2.add(jsonObjectRequest);


    }

}
