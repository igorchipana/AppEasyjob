package com.example.igor.msqlandroid.View;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.igor.msqlandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity  implements Response.Listener<JSONObject>,Response.ErrorListener {
  Button reg;
  Button Login;

     EditText usuario;
     EditText contrasenia;
        TextView gg;
    RequestQueue reques;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usuario=(EditText)findViewById(R.id.txtUsuario);
        contrasenia=(EditText)findViewById(R.id.txtpass);
        gg=(TextView)findViewById(R.id.hola);

        //reg=(Button)findViewById(R.id.hola);
        gg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RegistroUss.class);
                startActivity(i);
            }
        });

        Login=(Button)findViewById(R.id.btningresar);
        Login.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                validarUsuario();
            }
        });


    }
    public void validarUsuario(){
        String ip=getString(R.string.ip);
       reques= Volley.newRequestQueue(this);
        String url=ip+"/dbremota/WsLogin.php?USER="+usuario.getText().toString()+"&PASS="+contrasenia.getText().toString();

        url=url.replace(" ","%20");
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        reques.add(jsonObjectRequest);
    }


    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(this,"Error en lectura del json"+error.toString(), Toast.LENGTH_SHORT).show();
       Log.d("Error:",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        //
        JSONArray JSON_retorno= response.optJSONArray("RESULTADO");
        JSONObject JSON_Objeto=null;
        if (!validar()) return;
        try {
            JSON_Objeto = JSON_retorno.getJSONObject(0);

            if ((JSON_Objeto.optString("PERSONAID")).trim().equals("-9999999999")) {

                Log.i("MENSAJE 1:",JSON_Objeto.optString("PERSONAID"));
                Toast.makeText(getApplicationContext(),"Usuario y Password incorrectos",Toast.LENGTH_SHORT).show();;
                //Toast.makeText(getApplicationContext(),"Login incorrecto: "+JSON_Objeto.optString("PERSONAID"),Toast.LENGTH_LONG).show();

            }else{
                /*Log.i("MENSAJE 2:",JSON_Objeto.optString("PERSONAID"));
                Log.i("MENSAJE 2:",JSON_Objeto.optString("NOMBRES"));
                Log.i("MENSAJE 2:",JSON_Objeto.optString("APELLIDOS"));
                Log.i("MENSAJE 2:",JSON_Objeto.optString("TELEFONO"));
                Log.i("MENSAJE 2:",JSON_Objeto.optString("CORREO"));
                Log.i("MENSAJE 2:",JSON_Objeto.optString("USS"));
                Log.i("MENSAJE 2:",JSON_Objeto.optString("PASS"));*/

                String vPERSONAID= JSON_Objeto.optString("PERSONAID");
                String vNOMBRES= JSON_Objeto.optString("NOMBRES");
                String vAPELLIDOS= JSON_Objeto.optString("APELLIDOS");
                String vTELEFONO= JSON_Objeto.optString("TELEFONO");
                String vCORREO= JSON_Objeto.optString("CORREO");
                String vUSS= JSON_Objeto.optString("USS");
                String vPASS= JSON_Objeto.optString("PASS");
              //  Toast.makeText(getApplicationContext(),"Login correcto: "+JSON_Objeto.optString("PERSONAID"),Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), MainActivity2.class);
                i.putExtra("PERSONAID",vPERSONAID );
                i.putExtra("NOMBRES", vNOMBRES);
                i.putExtra("APELLIDOS", vAPELLIDOS);
                i.putExtra("TELEFONO", vTELEFONO);
                i.putExtra("CORREO", vCORREO);
                i.putExtra("USS", vUSS);
                i.putExtra("PASS", vPASS);
                startActivity(i);
            }
            usuario.setText("");
            contrasenia.setText("");
            usuario.findFocus();
        }catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("LOG Correcto:","No hubo error en leer el json");

    }
    private boolean validar() {
        boolean valid = true;

        String sUsser = usuario.getText().toString();
        String sPassword = contrasenia.getText().toString();

        if (sUsser.isEmpty()) {
            usuario.setError("Debe ingresar usuario");
            valid = false;
        } else {
            contrasenia.setError(null);
        }

        if (sPassword.isEmpty()) {
            contrasenia.setError("Debe ingresar contraseña");
            valid = false;
        } else {
            contrasenia.setError(null);
        }

        return valid;
    }
    @Override
    public void onBackPressed() {}

}
