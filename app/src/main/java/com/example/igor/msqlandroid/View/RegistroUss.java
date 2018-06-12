package com.example.igor.msqlandroid.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.igor.msqlandroid.Presentador.P_RegistroUss;
import com.example.igor.msqlandroid.R;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import org.json.JSONObject;

public class RegistroUss extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{
    RelativeLayout rellay1, rellay2;
   EditText txtnom;
    EditText txtapellidos;
    EditText txttelefono;
    EditText txtcorreo;
    EditText txtuss;
    EditText txtpass;
    Button registrar;
    ProgressDialog progreso;
    RequestQueue reques;
    JsonObjectRequest jsonObjectRequest;
    P_RegistroUss hola = new P_RegistroUss();
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)  {

        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_registro_uss);
        txtnom=(EditText)findViewById(R.id.txtnombres);
        txtapellidos=(EditText)findViewById(R.id.txtapepa);
        txttelefono=(EditText)findViewById(R.id.txttele);
        txtcorreo=(EditText)findViewById(R.id.txtcorreo);
        txtuss=(EditText)findViewById(R.id.iduss);
        txtpass=(EditText)findViewById(R.id.txtpass);
       registrar=(Button)findViewById(R.id.button);
      // P_RegistroUss hola = new P_RegistroUss();

        rellay1 = (RelativeLayout) findViewById(R.id.rellay1);
        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);

        handler.postDelayed(runnable, 1000);

        reques= Volley.newRequestQueue(this);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CargarWebServices();
            }
        });

    }


   private void CargarWebServices() {
       if (!validar()) return;
        progreso=new ProgressDialog(this);
        progreso.setMessage("Cargando...");
        progreso.show();
        String ip=getString(R.string.ip);
        String url=ip+"/dbremota/WsJsonRegistroUSS.php?nombres="+txtnom.getText().toString()+
                "&apellidos="+txtapellidos.getText().toString()+
                "&telefono="+txttelefono.getText().toString()+
                "&correo="+txtcorreo.getText().toString()+
                "&uss="+txtuss.getText().toString()+
                "&pass="+txtpass.getText().toString();
        url=url.replace(" ","%20");
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        reques.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        StyleableToast.makeText(this,"No se pudo registrar"+error.toString(),R.style.exampletoast).show();
        Log.i("Error:",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        StyleableToast.makeText(this,"Se añadió un registro",R.style.exito_toast).show();
        progreso.hide();
        txtnom.setText("");
        txtapellidos.setText("");
        txttelefono.setText("");
        txtcorreo.setText("");
        txtuss.setText("");
        txtpass.setText("");
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    private boolean validar() {
        boolean valid = true;

        String sNombre = txtnom.getText().toString();
        String sPassword = txtpass.getText().toString();
        String sEmail = txtcorreo.getText().toString();
        String aPater = txtapellidos.getText().toString();
        String aTele = txttelefono.getText().toString();
        String Ussu = txtuss.getText().toString();


        if (sNombre.isEmpty() || sNombre.length() < 3) {
            txtnom.setError("Ingrese al menos 3 caracteres");
            valid = false;
        } else {
            txtnom.setError(null);
        }
        if (aPater.isEmpty() || aPater.length() < 3) {
            txtapellidos.setError("Ingrese al menos 3 caracteres");
            valid = false;
        } else {
            txtapellidos.setError(null);
        } if (aTele.isEmpty() || aTele.length() < 3 ){
            txttelefono.setError("Direccion telefónica no válida");
            valid = false;
        } else {
            txttelefono.setError(null);
        }
        if (sEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(sEmail).matches()) {
            txtcorreo.setError("Dirección de correo electrónico no válida");
            valid = false;
        } else {
            txtcorreo.setError(null);
        }
        if (Ussu.isEmpty()) {
            txtuss.setError("Ingrese un usuario");
            valid = false;
        } else {
            txtuss.setError(null);
        }
        if (sPassword.isEmpty()) {
            txtpass.setError("Ingrese una contraseña");
            valid = false;
        } else {
            txtpass.setError(null);
        }

        return valid;
    }


}
