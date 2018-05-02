package com.example.igor.msqlandroid.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

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
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PerfilUss extends Fragment {
    String valorid;
    JsonObjectRequest jsonObjectRequest;
    ArrayList<Persona> listaUsuarios;
    TextView uUss,uPass,ucorreo,utelefono,cargardatos1;
    EditText nombre,apellidos,telefono,correo,usuario,pass;
    ToggleButton acccion;
    Button cancelar,acpetar,edit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista= inflater.inflate(R.layout.fragment_perfil_uss, container, false);
        uUss=(TextView)vista.findViewById(R.id.utxuss);
        uPass=(TextView)vista.findViewById(R.id.utxtpass);
        ucorreo=(TextView)vista.findViewById(R.id.utxcorreo);
        utelefono=(TextView)vista.findViewById(R.id.utxttelefono);
        cargardatos1=(TextView)vista.findViewById(R.id.cargardatos);


        edit=(Button)vista.findViewById(R.id.edtperfil);

        if(getArguments()!=null){
            valorid=getArguments().getString("idpersona");
            Log.d("IDcapturadouss",valorid);
        }else{
            Log.d("msg","llegonullo");
        }
        listaPerfilUss();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogoedit(getContext());
            }
        });






        return vista;
    }

    public void openDialogoedit(Context context) {
        int i=0;
        final Dialog dialog = new Dialog(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialog.setContentView(R.layout.edit_uss_dialog);
       // dialog.setTitle("Editar dato:");
        acpetar = (Button) dialog.findViewById(R.id.btnacep);
        cancelar = (Button) dialog.findViewById(R.id.btncancel);
        nombre=(EditText) dialog.findViewById(R.id.edtvalornom);

        apellidos=(EditText)dialog.findViewById(R.id.edtapellidouss);
        pass=(EditText)dialog.findViewById(R.id.edtpassUss);
        telefono=(EditText)dialog.findViewById(R.id.edtteluss);
        correo=(EditText)dialog.findViewById(R.id.edtcorreouss);
        usuario=(EditText)dialog.findViewById(R.id.edtuss);

        nombre.setText(listaUsuarios.get(i).getNombres().toString());
        apellidos.setText(listaUsuarios.get(i).getApellidos().toString());
        pass.setText(listaUsuarios.get(i).getPassword().toString());
        telefono.setText(listaUsuarios.get(i).getTelefono().toString());
        correo.setText(listaUsuarios.get(i).getCorreo().toString());
        usuario.setText(listaUsuarios.get(i).getUsuario().toString());

        acpetar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();
        dialog.setCanceledOnTouchOutside(true);

    }






    private void listaPerfilUss() {
        String ip = getContext().getString(R.string.ip);
       String urlServices2 = ip + "/dbremota/Ws_listUsuarios.php?personaid="+ valorid.toString();
        RequestQueue requestQueue2 = Volley.newRequestQueue(getContext());
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlServices2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Persona oPersona = null;
                JSONArray json = response.optJSONArray("RESULTADO");
                try {
                    JSONObject jsonObjectTest = null;
                    jsonObjectTest = json.getJSONObject(0);
                    if (jsonObjectTest.optString("idAnuncio").trim().equals("-9999999999")) {
                    } else {
                        for (int i = 0; i < json.length(); i++) {
                            oPersona = new Persona();
                            JSONObject jsonObject = null;
                            jsonObject = json.getJSONObject(i);
                            oPersona.setIdPersona(jsonObject.optInt("personaid"));
                            oPersona.setNombres(jsonObject.optString("nombres"));
                            oPersona.setApellidos(jsonObject.optString("apellidos"));
                            oPersona.setTelefono(jsonObject.optString("telefono"));
                            oPersona.setCorreo(jsonObject.optString("correo"));
                            oPersona.setUsuario(jsonObject.optString("uss"));
                            oPersona.setPassword(jsonObject.optString("pass"));
                            listaUsuarios = new ArrayList<>();
                            listaUsuarios.add(oPersona);
                            uUss.setText(listaUsuarios.get(i).getUsuario().toString());
                            uPass.setText(listaUsuarios.get(i).getPassword().toString());
                            ucorreo.setText(listaUsuarios.get(i).getCorreo().toString());
                            utelefono.setText(listaUsuarios.get(i).getTelefono().toString());
                            cargardatos1.setText(listaUsuarios.get(i).getNombres().toString()+" "+listaUsuarios.get(i).getApellidos());

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    StyleableToast.makeText(getContext(), "Error del json: " + e, R.style.exampletoast).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                StyleableToast.makeText(getContext(), "No se pudo conectar al JSON : " + error, R.style.exampletoast).show();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);
        requestQueue2.add(jsonObjectRequest);

    }
}