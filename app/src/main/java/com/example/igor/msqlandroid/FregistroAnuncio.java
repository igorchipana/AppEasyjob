package com.example.igor.msqlandroid;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
//import com.google.android.gms.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
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
import com.example.igor.msqlandroid.View.MapsActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;


public class FregistroAnuncio extends Fragment {
    private  int dia,mes,ano;
    Button fecha;
    EditText date;
    EditText titulo;
    EditText descripcion;
    String valorLong;
    String valorLat;
    String dato;
    String valorLong1;
    String valorLat1;
    String dato1;
    Button acpetar1;
    String locality;
    ArrayList<Categorias> listcategorias;
    Spinner comboCategorias;
    JsonObjectRequest jsonObjectRequest;
    ArrayList<String>lisCategoriasCombo;
    String capturarIdCategoria;
    Switch sswitch;
    String valordireccion;
    TextView edt;
    private GoogleMap mMap;
    //private LocationManager locationManager;
    Button regcapturar;
    String TAG = "PermisoAPP";
    ProgressDialog progreso;
    String valorid;
    public final int MY_PERMISSIONS_REQUEST =1;
    //LocationListener contexto = this;
    Button cargarubicacion,cargarubicacion1;
    private SupportMapFragment mapFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Context context =getContext();
        View vista=  inflater.inflate(R.layout.fragment_fregistro_anuncio, container, false);


        comboCategorias=(Spinner)vista.findViewById(R.id.idComboCategoria1);
        listcategorias=new ArrayList<>();
        fecha=(Button)vista.findViewById(R.id.btnfecha);
        regcapturar=(Button)vista.findViewById(R.id.button2);
        date=(EditText)vista.findViewById(R.id.fecha);
        titulo=(EditText)vista.findViewById(R.id.edttitulo);
        descripcion=(EditText)vista.findViewById(R.id.edtdescripcion);
        sswitch=(Switch)vista.findViewById(R.id.switch1);


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

        cargarubicacion = (Button) vista.findViewById(R.id.llamar);
        cargarubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intentfff = new Intent(getContext(), MapsActivity.class);
                startActivityForResult(intentfff, 12345);
            }
        });


        sswitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.switch1) {
                    if (sswitch.isChecked()) {
                        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            Log.d(TAG, "Faltan permisos");
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                                    MY_PERMISSIONS_REQUEST);
                            return;
                        }
                        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            builAlertMessageNoGps();
                        } else {
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, new LocationListener() {
                                @Override
                                public void onLocationChanged(Location location) {
                                    valorLat = (location.getLatitude()) + "";
                                    valorLong = (location.getLongitude()) + "";
                                    Log.i("Latitud1::", valorLat);
                                    Log.i("Longitud1::", valorLong);
                                    if (location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
                                        try {
                                            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                                            List<Address> list = geocoder.getFromLocation(
                                                    location.getLatitude(), location.getLongitude(), 1);
                                            if (!list.isEmpty()) {
                                                Address DirCalle = list.get(0);
                                                dato = DirCalle.getAddressLine(0);
                                                Log.i("SDireccion::", dato);
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                //    Toast.makeText(getContext(), dato, Toast.LENGTH_SHORT).show();
                                  //  Toast.makeText(getContext(), valorLong, Toast.LENGTH_SHORT).show();
                                   // Toast.makeText(getContext(), valorLat, Toast.LENGTH_SHORT).show();
                                    // RegAnuncio();
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
                            });
                        }
                        cargarubicacion.setEnabled(false);
                        cargarubicacion.getBackground().setAlpha(45);
                    } else {

                        cargarubicacion.setEnabled(true);
                        cargarubicacion.getBackground().setAlpha(255);
                    }

                }
            }
        });

        regcapturar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validar()) return;
                progreso=new ProgressDialog(getContext());
                progreso.setMessage("Cargando...");
                progreso.show();
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(view.getContext());
                builder.setMessage("¿Está seguro que quiere realizar este comentario?");
                builder.setTitle("Mensaje de confirmación");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(dato==null && valorLong==null && valorLat==null){
                            progreso.hide();
                            Toast.makeText(getContext(),"Tiene que selecionar ubicación", Toast.LENGTH_SHORT).show();
                        }else{
                            RegAnuncio();
                        }

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progreso.hide();
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

                    final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            date.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);

                        }
                    }
                            ,dia,mes,ano);
                   // datePickerDialog.getDatePicker().setMaxDate(new DatePicker(getContext()).getMaxDate());
                    datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                    datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());

                    datePickerDialog.show();
                }
            }
        });




        return vista;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //FregistroAnuncio fragment = (FregistroAnuncio) getFragmentManager().findFragmentById(R.id.rrr);
        //fragment.onActivityResult(requestCode, resultCode, data);
        Log.d("valor","dddddddddddddddddddddddddddddddddddddddd");
        Toast.makeText(getContext(),"Se seleccionó ubicación",Toast.LENGTH_SHORT).show();
        dato = data.getStringExtra("dato");
        valorLat = data.getStringExtra("dato1");
        valorLong = data.getStringExtra("dato2");
      //  Toast.makeText(getContext(),dato,Toast.LENGTH_SHORT).show();
     //   Toast.makeText(getContext(),valorLat,Toast.LENGTH_SHORT).show();
      //  Toast.makeText(getContext(),valorLong,Toast.LENGTH_SHORT).show();
        //getActivity().RESULT_OK
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                 valordireccion = data.getStringExtra("dato");
                 Log.d("valor",valordireccion.toString());
                 Toast.makeText(getContext(),"entro al recibidor1",Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == 0) {
                Log.d("msgerror","No llego nada");
            Toast.makeText(getContext(),"NO entro al recibidor1",Toast.LENGTH_SHORT).show();
            }
    }
    public void openDialogo1(Context context) {
        final Dialog dialog3 = new Dialog(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        dialog3.show();
        dialog3.setCanceledOnTouchOutside(true);



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
                "&longitud="+valorLong.toString()+
                "&direccion="+dato.toString();

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
                    progreso.hide();
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
    private boolean validar1() {
        boolean valid1 = true;
        String sDate1 = dato;
        String sTitulo1= valorLat;
        String sDescripcion1 = valorLong;
      if(sDate1==null && sTitulo1==null && sDescripcion1==null){
          Toast.makeText(getContext(),"Error", Toast.LENGTH_SHORT).show();

      }else{


      }return  valid1;
    }
}
