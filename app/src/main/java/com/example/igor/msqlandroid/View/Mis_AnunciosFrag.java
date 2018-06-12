package com.example.igor.msqlandroid.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.igor.msqlandroid.Adapter.ComentariosAdapter;
import com.example.igor.msqlandroid.Adapter.MisAnunciosAdapter;
import com.example.igor.msqlandroid.Entidades.Anuncios;
import com.example.igor.msqlandroid.Entidades.Persona;
import com.example.igor.msqlandroid.R;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Mis_AnunciosFrag extends Fragment {
    String valorid;
    TextView valorid1;
    TextView txtResultado;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue requestQueue2;
    ProgressDialog progreso;
    ArrayList<Anuncios> listaMisAnuncios;
    RecyclerView recyclerAnuncios;
    Button eleminiar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_mis__anuncios, container, false);
        txtResultado = (TextView) vista.findViewById(R.id.resultadono);

        if (getArguments() != null) {
            valorid = getArguments().getString("idpersona");
            Log.d("IDcapturadouss", valorid);
        } else {
            Log.d("msg", "llegonullo");
        }


        recyclerAnuncios = (RecyclerView) vista.findViewById(R.id.misrecycler);
        recyclerAnuncios.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerAnuncios.setHasFixedSize(true);
        listaMisAnuncios = new ArrayList<>();
       String ip = getString(R.string.ip);
       String url = ip + "/dbremota/WsListMisAnuncios.php";
       listaMisAnuncioss(url + "?personaid=" + valorid.toString());


        return vista;


    }


    public void listaMisAnuncioss(String url) {
        progreso = new ProgressDialog(getActivity());
        progreso.setMessage("Cargando...");
        progreso.show();
        String urlServices = url;
        Log.i("llegó aqui:", "asas");
        requestQueue2 = Volley.newRequestQueue(getActivity());
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlServices, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Anuncios oAnuncios = null;
                JSONArray json = response.optJSONArray("RESULTADO");
                try {
                    JSONObject jsonObjectTest = null;
                    jsonObjectTest = json.getJSONObject(0);
                    if (jsonObjectTest.optString("personaid").trim().equals("noEntra")) {
                       txtResultado.setText("No ha realizado ninguna publicación :(");
                    } else {
                        for (int i = 0; i < json.length(); i++) {
                            oAnuncios = new Anuncios();
                            JSONObject jsonObject = null;
                            jsonObject = json.getJSONObject(i);
                            oAnuncios.setIdPersona(jsonObject.optInt("personaid"));
                            oAnuncios.setIdCategoria(jsonObject.optInt("idCategoria"));
                            oAnuncios.setIdAnuncio(jsonObject.optInt("idAnuncio"));
                            oAnuncios.setFecha(jsonObject.optString("fecha"));
                            oAnuncios.setTitulo(jsonObject.optString("titulo"));
                            oAnuncios.setDescripcion(jsonObject.optString("descripcion"));
                            oAnuncios.setCalificacion(jsonObject.optString("calificacion"));
                            oAnuncios.setDireccion(jsonObject.optString("direccion"));
                            listaMisAnuncios.add(oAnuncios);
                        }
                        for (int ii = 0; ii < listaMisAnuncios.size(); ii++) {
                            Log.i("recorrehoy?:", String.valueOf(listaMisAnuncios.get(0).getTitulo()));
                            MisAnunciosAdapter adapter = new MisAnunciosAdapter(listaMisAnuncios);
                            recyclerAnuncios.setAdapter(adapter);
                        }
                    }
                   progreso.hide();
                } catch (JSONException e) {
                    e.printStackTrace();
                    StyleableToast.makeText(getActivity(), "Error del json: " + e, R.style.exampletoast).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                StyleableToast.makeText(getActivity(), "No se pudo conectar al JSON : " + error, R.style.exampletoast).show();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);
        requestQueue2.add(jsonObjectRequest);

    }


    public void HOLA(Context context) {
        Toast.makeText(context, "Please check the number you entered",
                Toast.LENGTH_LONG).show();

    }
}
