package com.example.igor.msqlandroid;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
//import com.google.android.gms.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.example.igor.msqlandroid.Entidades.Categorias;
import com.example.igor.msqlandroid.Entidades.Prueba;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;


public class FregistroAnuncio extends Fragment {
    private  int dia,mes,ano;
    Button fecha;
    EditText date;
    EditText titulo;
    EditText descripcion;
    String valorLong;
    String valorLat;

    ArrayList<Categorias> listcategorias;
    Spinner comboCategorias;
    JsonObjectRequest jsonObjectRequest;
    ArrayList<String>lisCategoriasCombo;
    String capturarIdCategoria;


    //private LocationManager locationManager;
    Button regcapturar;
    String TAG = "PermisoAPP";
    String valorid;
    public final int MY_PERMISSIONS_REQUEST =1;
    //LocationListener contexto = this;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
          //LocationListener contexto = this;
        // Inflate the layout for this fragment
        View vista=  inflater.inflate(R.layout.fragment_fregistro_anuncio, container, false);
        comboCategorias=(Spinner)vista.findViewById(R.id.idComboCategoria1);
        listcategorias=new ArrayList<>();
        fecha=(Button)vista.findViewById(R.id.btnfecha);
        regcapturar=(Button)vista.findViewById(R.id.button2);

        date=(EditText)vista.findViewById(R.id.fecha);
        titulo=(EditText)vista.findViewById(R.id.edttitulo);
        descripcion=(EditText)vista.findViewById(R.id.edtdescripcion);


            if(getArguments()!=null){
                valorid=getArguments().getString("idpersona");
                Log.d("IDcapturado",valorid);
            }else{
                Log.d("msg","llegonullo");
            }

        String ip=getString(R.string.ip);
        String urlServices1=ip+"/dbremota/WsJsonSelectCategorias.php";
        loadSpinnerData(urlServices1);
       // obtenerCoordenadas();




        regcapturar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!validar()) return;
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(view.getContext());
                builder.setMessage("¿Está seguro que quiere realizar este comentario?");
                builder.setTitle("Mensaje de confirmación");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            Log.d(TAG,"Faltan permisos");
                            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION},
                            MY_PERMISSIONS_REQUEST);
                            return;
                        }
                        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                            builAlertMessageNoGps();
                        } else{
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1 ,  new LocationListener() {
                                @Override
                                public void onLocationChanged(Location location) {
                                    valorLat=(location.getLatitude())+"";
                                    valorLong=(location.getLongitude())+"";
                                    Log.i("Latitud1::",valorLat);
                                    Log.i("Longitud1::", valorLong);
                                    RegAnuncio();
                                }
                                @Override
                                public void onStatusChanged(String s, int i, Bundle bundle) {}
                                @Override
                                public void onProviderEnabled(String s) {}
                                @Override
                                public void onProviderDisabled(String s) {}
                            });
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                android.support.v7.app.AlertDialog dialog = builder.create();
                dialog.show();
            }


        });


        comboCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!= 0) {
                    capturarIdCategoria= (listcategorias.get(i-1).getId_categoria().toString());
                   // Toast.makeText(getContext(),capturarIdCategoria,Toast.LENGTH_SHORT).show();
                    regcapturar.setEnabled(true);
                    regcapturar.getBackground().setAlpha(255);
                }else{
                    StyleableToast.makeText(getContext(),"Tiene que seleccionar categoría",R.style.exampletoast).show();
                   regcapturar.setEnabled(false);
                    regcapturar.getBackground().setAlpha(45);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view==fecha){
                    final Calendar c= Calendar.getInstance();
                    dia=c.get(Calendar.DAY_OF_MONTH);
                    mes=c.get(Calendar.MONTH);
                    ano=c.get(Calendar.YEAR);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            date.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                        }
                    }
                            ,dia,mes,ano);
                    datePickerDialog.show();
                }
            }
        });
        return vista;
    }



    private void  loadSpinnerData (String url) {
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Categorias categorias=null;
                JSONArray json=response.optJSONArray("categorias");
                Log.i("tamaño array:",String.valueOf(json.length()));
                try {
                    for(int i=0;i<json.length();i++) {
                        categorias = new Categorias();
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);
                        categorias.setId_categoria(jsonObject.optInt("idcategoria"));
                        categorias.setNomCategoria(jsonObject.optString("nomcategoria"));
                        listcategorias.add(categorias);
                    }
                    lisCategoriasCombo= new ArrayList<>();
                    lisCategoriasCombo.add("Categorias:");
                    for(int i=0;i<listcategorias.size();i++){
                        lisCategoriasCombo.add(listcategorias.get(i).getNomCategoria());
                    }
                    ArrayAdapter<CharSequence> adaptador= new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,lisCategoriasCombo);
                    comboCategorias.setAdapter(adaptador);
                }catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),"Error"+e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"No se pudo conectar al JSON : "+error, Toast.LENGTH_SHORT).show();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);
        requestQueue.add(jsonObjectRequest);
    }


    private void builAlertMessageNoGps() {
        final AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
        builder.setMessage("Su Gps esta desabilitado, desea habilitarlo?")
                .setCancelable(false)
                 .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                     public void onClick(@SuppressWarnings("Unussed")final DialogInterface dialog, @SuppressWarnings ("Unussed") final int id){
                         startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                     }
                 })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, @SuppressWarnings ("Unussed") final int id) {
                            dialog.cancel();
                    }
                });
        final AlertDialog alert=builder.create();
        alert.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
           Log.d(TAG,"onRequestresul"+requestCode);
           switch (requestCode){
               case  MY_PERMISSIONS_REQUEST:{
                   if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                       StyleableToast.makeText(getContext(),"Perfecto",R.style.exito_toast).show();
                   }else{
                       StyleableToast.makeText(getContext(),"Error",R.style.exampletoast).show();
                   }
                   return;
               }
           }
    }


    private void RegAnuncio () {
        //if (!validar()) return;
        Log.i("Latitud1::",capturarIdCategoria);
        Log.i("Latitud1::",valorid);
        Log.i("Latitud1::",date.getText().toString());
        Log.i("Latitud1::",titulo.getText().toString());
        Log.i("Latitud1::",descripcion.getText().toString());
        Log.i("Latitud1::",valorLat);
        Log.i("Latitud1::",valorLong);
       String ip=getString(R.string.ip);
        String urlService2=ip+"/dbremota/WsRegistroAnuncios.php?idcategoria="+capturarIdCategoria.toString()+
                "&idpersona="+valorid.toString()+
                "&fecha="+date.getText().toString()+
                "&titulo="+titulo.getText().toString()+
                "&descripcion="+descripcion.getText().toString()+
                "&latitud="+valorLat.toString()+
                "&longitud="+valorLong.toString();
        urlService2=urlService2.replace(" ","%20");
       // Log.i("Latitud1::",valorLat);

        RequestQueue requestQueue3=Volley.newRequestQueue(getContext());
        jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, urlService2,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Anuncios oAnuncios=null;

                try{

                JSONArray json=response.optJSONArray("resultado");
                JSONObject jsonObjectTest = null;
                jsonObjectTest = json.getJSONObject(0);
                if (jsonObjectTest.optString("valor").trim().equals("1")){
                    StyleableToast.makeText(getContext(),"Se registro un anuncio",R.style.exito_toast).show();
                    date.setText("");
                    titulo.setText("");
                    descripcion.setText("");

                }else{
                    StyleableToast.makeText(getContext(),"Error al registrar",R.style.exampletoast).show();
                }

            }catch (JSONException e) {
                e.printStackTrace();
                    StyleableToast.makeText(getContext(),"Error del json: "+e,R.style.exampletoast).show();
            }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                StyleableToast.makeText(getContext(),"No se pudo conectar al JSON : "+error,R.style.exampletoast).show();
                //Toast.makeText(getContext(),"No se pudo conectar al JSON : "+error, Toast.LENGTH_SHORT).show();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);
        requestQueue3.add(jsonObjectRequest);

    }
    private boolean validar() {
        boolean valid = true;
        String sDate = date.getText().toString();
        String sTitulo= titulo.getText().toString();
        String sDescripcion = descripcion.getText().toString();
        String vidcategoria=capturarIdCategoria.toString();
        String c= comboCategorias.toString();


        if (sDate.isEmpty()) {
            date.setError("Ingrese una fecha");
            valid = false;
        } else {
            date.setError(null);
        }
        if (sTitulo.isEmpty()) {
            titulo.setError("Ingrese un título");
            valid = false;
        } else {
            titulo.setError(null);
        } if (sDescripcion.isEmpty() ){
            descripcion.setError("Ingrese una descripción");
            valid = false;
        } else {
            descripcion.setError(null);
        }
        return valid;
    }
}
