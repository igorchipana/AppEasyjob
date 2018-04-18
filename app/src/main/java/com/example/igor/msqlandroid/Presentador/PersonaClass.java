

package com.example.igor.msqlandroid.Presentador;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.Toast;

/**
 * Created by Igor on 5/04/2018.
 */

public class PersonaClass  {

    RequestQueue reques;
    JsonObjectRequest jsonObjectRequest;

    private String PERSONAID;
    private String NOMBRES;
    private String APELLIDOS;
    private String TELEFONO;
    private String CORREO;
    private String USS;
    private String PASS;
    private String FLAG_LOGIN;



    public int LogearUsuario(String usuario, String password, Context context){

        reques= Volley.newRequestQueue(context);
        String url="http://192.168.1.37/dbremota/WsLogin.php?USER=dante27&PASS=123";
        url=url.replace(" ","%20");
        //jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        //reques.add(jsonObjectRequest);
        //String FLAG_LOGIN;

        jsonObjectRequest =new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


            JSONArray JSON_retorno= response.optJSONArray("RESULTADO");
            JSONObject JSON_Objeto=null;
        try {
                JSON_Objeto = JSON_retorno.getJSONObject(0);

                if ((JSON_Objeto.optString("PERSONAID")).trim().equals("-9999999999")) {
                    FLAG_LOGIN="0";
                    Log.i("MENSAJE 1:",JSON_Objeto.optString("PERSONAID"));
                }else{
                    Log.i("MENSAJE 2:",JSON_Objeto.optString("PERSONAID"));
                    cargarUsuario(JSON_Objeto.optString("PERSONAID"),
                            JSON_Objeto.optString("NOMBRES"),
                            JSON_Objeto.optString("APELLIDOS"),
                            JSON_Objeto.optString("TELEFONO"),
                            JSON_Objeto.optString("CORREO"),
                            JSON_Objeto.optString("USS"),
                            JSON_Objeto.optString("PASS"));
                }

            }catch (JSONException e) {
                e.printStackTrace();
            }

        Log.i("LOG COrrecto:","No hubo error en leer el json");

            }
        }, new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            // TODO Auto-generated method stub
            Log.i("LOG Error:",error.toString());
        }
    });

        reques.add(jsonObjectRequest);
        return 0;
}

    public void cargarUsuario(String pPERSONAID, String pNOMBRES, String pAPELLIDOS,String pTELEFONO, String pCORREO, String pUSS, String pPASS){
        this.FLAG_LOGIN="1";
        this.PERSONAID=pPERSONAID;
        this.NOMBRES=pNOMBRES;
        this.APELLIDOS=pAPELLIDOS;
        this.TELEFONO=pTELEFONO;
        this.CORREO=pCORREO;
        this.USS=pUSS;
        this.PASS=pPASS;
        Log.i("MENSAJE 1:",PERSONAID);
        Log.i("MENSAJE 1:",NOMBRES);
        Log.i("MENSAJE 1:",APELLIDOS);
        Log.i("MENSAJE 1:",TELEFONO);
    }

    /*@Override
    public void onErrorResponse(VolleyError error) {

        //Toast.makeText(this,"No se logeo errorr "+error.toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResponse(JSONObject response) {
        //
        JSONArray JSON_retorno= response.optJSONArray("RESULTADO");
        JSONObject JSON_Objeto=null;
        try {
            JSON_Objeto = JSON_retorno.getJSONObject(0);

            if ((JSON_Objeto.optString("PERSONAID")).trim().equals("-9999999999")) {
                this.FLAG_LOGIN="0";
                Log.i("MENSAJE 1:",JSON_Objeto.optString("PERSONAID"));
            }else{
                Log.i("MENSAJE 2:",JSON_Objeto.optString("PERSONAID"));
                this.cargarUsuario(JSON_Objeto.optString("PERSONAID"),
                        JSON_Objeto.optString("NOMBRES"),
                        JSON_Objeto.optString("APELLIDOS"),
                        JSON_Objeto.optString("TELEFONO"),
                        JSON_Objeto.optString("CORREO"),
                        JSON_Objeto.optString("USS"),
                JSON_Objeto.optString("PASS"));
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("LOG COrrecto:","No hubo error en leer el json");

    }*/

}
