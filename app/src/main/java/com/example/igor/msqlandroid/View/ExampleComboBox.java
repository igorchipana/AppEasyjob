package com.example.igor.msqlandroid.View;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.igor.msqlandroid.*;
import com.example.igor.msqlandroid.Adapter.AnunciosAdapter;
import com.example.igor.msqlandroid.Entidades.Categorias;
import com.example.igor.msqlandroid.Entidades.Anuncios;
import com.example.igor.msqlandroid.R;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ExampleComboBox extends Fragment {
      RecyclerView recyclerAnuncios;
    Spinner comboCategorias;
    TextView txtResultado;
    //RequestQueue reques;
    JsonObjectRequest jsonObjectRequest;
    ArrayList<Categorias> listcategorias;
    ArrayList<Anuncios> listaAnuncios;
    ArrayList<String>lisCategoriasCombo;
    ArrayList<String>listaAnunciosFinal;
    String capturarIdCategoria;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vista= inflater.inflate(com.example.igor.msqlandroid.R.layout.fragment_example_combo_box, container, false);
        comboCategorias=(Spinner)vista.findViewById(R.id.idComboCategoria);
        txtResultado=(TextView)vista.findViewById(R.id.idTxtResultado);
        recyclerAnuncios=(RecyclerView)vista.findViewById(R.id.idRecycler);
        recyclerAnuncios.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerAnuncios.setHasFixedSize(true);
        //reques= Volley.newRequestQueue(getContext());
        listcategorias=new ArrayList<>();
        listaAnuncios= new ArrayList<>();
      //  btndialog = (Button)vista.findViewById(R.id.btncomentar);
        String ip=getString(R.string.ip);
        String urlServices1=ip+"/dbremota/WsJsonSelectCategorias.php";
        loadSpinnerData(urlServices1);
        //loadSpinnerData(urlServices2);


        comboCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!= 0) {
                    capturarIdCategoria= (listcategorias.get(i-1).getId_categoria().toString());
                  //  Toast.makeText(getContext(),capturarIdCategoria,Toast.LENGTH_SHORT).show();
                    String ip=getString(R.string.ip);
                    String urlServices2=ip+"/dbremota/WsPrueba.php";
                    loadAnuncioData(urlServices2+"?IDCATEGORIA="+capturarIdCategoria);
                }else{
                   StyleableToast.makeText(getContext(),"Selecione alguna Categoría",R.style.exampletoast).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
        return vista;


    }

    private void loadAnuncioData (String url) {
        RequestQueue requestQueue2=Volley.newRequestQueue(getContext());
        jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Anuncios oAnuncios=null;

                        JSONArray json=response.optJSONArray("RESULTADO");
                        Log.i("tamaño array:",String.valueOf(json.length()));

                        // if(jsonObject.getInt("success")==1){
                        txtResultado.setText("");
                        listaAnuncios.clear();
                        try {
                            JSONObject jsonObjectTest = null;
                            jsonObjectTest = json.getJSONObject(0);
                                if (jsonObjectTest.optString("idAnuncio").trim().equals("-9999999999")) {
                                   // txtResultado.clearComposingText();
                                    recyclerAnuncios.removeAllViewsInLayout();
                                    txtResultado.setText("  No hay resultados para la categoria seleccionada :(");
                                   // recyclerAnuncios.setAdapter("No hay resultados para la categoria seleccionada");
                                }else{

                                    for(int i=0;i<json.length();i++) {
                                        oAnuncios = new Anuncios();
                                        JSONObject jsonObject = null;
                                        jsonObject = json.getJSONObject(i);

                                        oAnuncios.setNombres (jsonObject.optString("NOMBRES"));
                                        oAnuncios.setApellidos(jsonObject.optString("APELLIDOS"));
                                        oAnuncios.setTelefono(jsonObject.optString("TELEFONO"));
                                        oAnuncios.setCorreo(jsonObject.optString("CORREO"));
                                        oAnuncios.setFecha(jsonObject.optString("fechA"));
                                        oAnuncios.setTitulo(jsonObject.optString("titulo"));
                                        oAnuncios.setDescripcion(jsonObject.optString("descripcion"));
                                        oAnuncios.setCalificacion(jsonObject.optString("calificacion"));
                                        oAnuncios.setIdAnuncio(jsonObject.optInt("idAnuncio"));
                                        oAnuncios.setIdPersona(jsonObject.optInt("idPersona"));


                                        listaAnuncios.add(oAnuncios);
                                    }
                                    Log.i("tamaño array:",String.valueOf(listaAnuncios.size()));

                                    listaAnunciosFinal= new ArrayList<>();
                                    listaAnunciosFinal.add("Categorias:");

                                    for(int i=0;i<listaAnuncios.size();i++){
                                        AnunciosAdapter adapter= new AnunciosAdapter(listaAnuncios);
                                        recyclerAnuncios.setAdapter(adapter);

                                    }
                                }
                            //progreso.hide();
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
                requestQueue2.add(jsonObjectRequest);
    }



    private void  loadSpinnerData (String url) {
        RequestQueue requestQueue=Volley.newRequestQueue(getContext());
        jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Categorias categorias=null;

                JSONArray json=response.optJSONArray("categorias");
                Log.i("tamaño array:",String.valueOf(json.length()));

                // if(jsonObject.getInt("success")==1){

                try {
                    for(int i=0;i<json.length();i++) {
                        categorias = new Categorias();
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);

                        categorias.setId_categoria(jsonObject.optInt("idcategoria"));
                        categorias.setNomCategoria(jsonObject.optString("nomcategoria"));

                        Log.i("Error SSSSSS:",String.valueOf(categorias.getId_categoria()));
                        Log.i("Error SSSSSS:",String.valueOf(categorias.getNomCategoria()));

                        listcategorias.add(categorias);
                    }
                    Log.i("tamaño array:",String.valueOf(listcategorias.size()));
                    Log.i("Error SSSSSS:",String.valueOf(listcategorias.get(0).getId_categoria()));
                    Log.i("Error SSSSSS:",String.valueOf(listcategorias.get(0).getNomCategoria()));
                    Log.i("Error SSSSSS:",String.valueOf(listcategorias.get(1).getId_categoria()));
                    Log.i("Error SSSSSS:",String.valueOf(listcategorias.get(1).getNomCategoria()));
                    // a = new ArrayAdapter<Categorias>(getContext(),android.R.layout.simple_spinner_item,listcategorias);
                    lisCategoriasCombo= new ArrayList<>();
                    lisCategoriasCombo.add("Categorias:");
                    for(int i=0;i<listcategorias.size();i++){
                        lisCategoriasCombo.add(listcategorias.get(i).getNomCategoria());
                    }
                    ArrayAdapter<CharSequence> adaptador= new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,lisCategoriasCombo);
                    comboCategorias.setAdapter(adaptador);
                    //progreso.hide();
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
        requestQueue.add(jsonObjectRequest);
    }
}
