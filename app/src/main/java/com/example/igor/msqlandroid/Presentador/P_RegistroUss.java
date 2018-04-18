package com.example.igor.msqlandroid.Presentador;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by Igor on 13/04/2018.
 */

public class P_RegistroUss extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    EditText txtnom;
    EditText txtapellidos;
    EditText txttelefono;
    EditText txtcorreo;
    EditText txtuss;
    EditText txtpass;

    ProgressDialog progreso;
    RequestQueue reques;
    JsonObjectRequest jsonObjectRequest;


     void CargarWebServices() {
        if (!validar()) return;
        progreso=new ProgressDialog(this);
        progreso.setMessage("Cargando...");
        progreso.show();
        String url="http://192.168.1.38/dbremota/WsJsonRegistroUSS.php?nombres="+txtnom.getText().toString()+
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
        Toast.makeText(this,"No se pudo registrar"+error.toString(), Toast.LENGTH_SHORT).show();
        Log.i("Error:",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(this,"Se añadió un registro exitosamente", Toast.LENGTH_SHORT).show();
        progreso.hide();
        txtnom.setText("");
        txtapellidos.setText("");
        txttelefono.setText("");
        txtcorreo.setText("");
        txtuss.setText("");
        txtpass.setText("");
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
