package com.example.igor.msqlandroid.Entidades;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ArrayAdapter;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Igor on 13/04/2018.
 */

public class Prueba extends Fragment {
    ArrayList<Categorias> listcategorias;
    Spinner comboCategorias;
    JsonObjectRequest jsonObjectRequest;
    ArrayList<String> lisCategoriasCombo;
    String capturarIdCategoria;
    String urlServices1 = "http://192.168.1.38/dbremota/WsJsonSelectCategorias.php";

    public void loadSpinnerData(String url) {

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Categorias categorias = null;
                JSONArray json = response.optJSONArray("categorias");
                Log.i("tamaño array:", String.valueOf(json.length()));
                try {
                    for (int i = 0; i < json.length(); i++) {
                        categorias = new Categorias();
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);
                        categorias.setId_categoria(jsonObject.optInt("idcategoria"));
                        categorias.setNomCategoria(jsonObject.optString("nomcategoria"));
                        listcategorias.add(categorias);
                    }
                    lisCategoriasCombo = new ArrayList<>();
                    lisCategoriasCombo.add("Categorias:");
                    for (int i = 0; i < listcategorias.size(); i++) {
                        lisCategoriasCombo.add(listcategorias.get(i).getNomCategoria());
                    }
                    ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, lisCategoriasCombo);
                    comboCategorias.setAdapter(adaptador);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error" + e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se pudo conectar al JSON : " + error, Toast.LENGTH_SHORT).show();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);
        requestQueue.add(jsonObjectRequest);
    }


}
