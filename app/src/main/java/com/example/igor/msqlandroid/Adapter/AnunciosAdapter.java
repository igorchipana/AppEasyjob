package com.example.igor.msqlandroid.Adapter;

/**
 * Created by Igor on 7/04/2018.
 */
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.opengl.Visibility;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.igor.msqlandroid.Entidades.Anuncios;
import com.example.igor.msqlandroid.R;
import com.example.igor.msqlandroid.View.C_Dialog;
import com.example.igor.msqlandroid.View.ExampleComboBox;
import com.example.igor.msqlandroid.View.MainActivity;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.tooltip.Tooltip;

import android.app.Dialog;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AnunciosAdapter   extends RecyclerView.Adapter<AnunciosAdapter.AnunciosHolder> {
    TextView vtxtdatos, vtxtTitulo, vtxtDes, vtxtTelcorreo,vtxtuss,vtxtidanuncio;
    TextView textidanuncio,textidpersona;
    EditText vedtcomentario;
    JsonObjectRequest jsonObjectRequest;
    Button btndialog, btndialog1;
    Button acpetar;
    Button cancelar;
    List<Anuncios> listaAnuncios;
    String fechaHora;
    Tooltip toltip, toltip1;

    public AnunciosAdapter(List<Anuncios> listaAnuncios) {
        this.listaAnuncios = listaAnuncios;
    }
     @Override

    public AnunciosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.anuncios_list,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new AnunciosHolder(vista);


    }

    @Override
    public void onBindViewHolder(AnunciosHolder holder, int position) {
     vtxtdatos.setText(listaAnuncios.get(position).getNombres().toString()+"   "+listaAnuncios.get(position).getApellidos().toString());
     vtxtTitulo.setText(listaAnuncios.get(position).getTitulo().toString());
     vtxtDes.setText(listaAnuncios.get(position).getDescripcion().toString());
     vtxtTelcorreo.setText(listaAnuncios.get(position).getTelefono().toString()+"      --       "+listaAnuncios.get(position).getCorreo().toString());
     vtxtuss.setText(Integer.toString(listaAnuncios.get(position).getIdPersona()));
     vtxtidanuncio.setText(Integer.toString(listaAnuncios.get(position).getIdAnuncio()));

    }

    @Override
    public int getItemCount() {
        return listaAnuncios.size();
    }

    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


    public class AnunciosHolder extends RecyclerView.ViewHolder {

        final Context context = itemView.getContext();


        public AnunciosHolder(View itemView) {
            super(itemView);
            vtxtdatos = (TextView) itemView.findViewById(R.id.txtDatos);
            vtxtTitulo = (TextView) itemView.findViewById(R.id.txtTitulo);
            vtxtDes = (TextView) itemView.findViewById(R.id.txtDes);
            vtxtTelcorreo = (TextView) itemView.findViewById(R.id.txttelcorreo);
            btndialog = (Button) itemView.findViewById(R.id.btncomentar5);
            vtxtuss = (TextView) itemView.findViewById(R.id.idussss);
            vtxtidanuncio = (TextView) itemView.findViewById(R.id.idanuncio);
            btndialog1= (Button) itemView.findViewById(R.id.btncomentar6);
          /*  Tooltip tooltip=new Tooltip.Builder(btndialog)
                    .setText("Comentar")
                    .setTextColor(Color.WHITE)
                    .setCornerRadius(8f)
                    .setDismissOnClick(true)
                    .show();*/
            btndialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openDialog(context, vtxtidanuncio.getText().toString(), vtxtuss.getText().toString());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date date = new Date();
                    String fecha = dateFormat.format(date);
                    Calendar c = Calendar.getInstance();
                    int hora=c.get(Calendar.HOUR_OF_DAY);
                    int minuto=c.get(Calendar.MINUTE);
                    int secundo=c.get(Calendar.SECOND);
                    String horaactual=String.valueOf(hora+":"+minuto+":"+secundo);
                    fechaHora=fecha+" "+horaactual;
                    Log.i("Hora3::",fechaHora);
                }
            });
            btndialog1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openDialogo(context);
                }
            });
        }

        public void openDialogo(Context context) {
            final Dialog dialog2 = new Dialog(context);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            dialog2.setContentView(R.layout.lista_dialog);
            dialog2.setTitle("Comentarios de Anuncio:");



            dialog2.show();
            dialog2.setCanceledOnTouchOutside(true);

        }

        public void openDialog(Context context, String correo,String correo1) {
            final Dialog dialog1 = new Dialog(context);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            dialog1.setContentView(R.layout.comentar_dialog);
            dialog1.setTitle("Escriba su comentario:");
            textidanuncio = (TextView) dialog1.findViewById(R.id.txtEjemplo);
            textidanuncio.setText(correo);
            textidpersona = (TextView) dialog1.findViewById(R.id.txtEjemplo1);
            textidpersona.setText(correo1);
            vedtcomentario= (EditText) dialog1.findViewById(R.id.edtcomentario);
            acpetar = (Button) dialog1.findViewById(R.id.btncomentar);
            cancelar = (Button) dialog1.findViewById(R.id.btncancelar);
            cancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog1.cancel();
                }
            });
            acpetar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!validar()) return;
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(view.getContext());
                    builder.setMessage("¿Está seguro que quiere realizar este comentario?");
                    builder.setTitle("Mensaje de confirmación");
                    builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            RegComentario();
                            dialog1.cancel();
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
            dialog1.show();
            dialog1.setCanceledOnTouchOutside(true);
        }

        private void RegComentario() {
            String ip=itemView.getContext().getString(R.string.ip);
            String urlService = ip+"/dbremota/WsRegistroComentario.php?idanuncio="+textidanuncio.getText().toString() +
                    "&idpersona="+textidpersona.getText().toString()+
                    "&comentario="+vedtcomentario.getText().toString()+
                    "&fecha="+fechaHora.toString() ;
            urlService=urlService.replace(" ","%20");
            RequestQueue requestQueue3 = Volley.newRequestQueue(itemView.getContext());
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlService, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray json = response.optJSONArray("resultado");
                        JSONObject jsonObjectTest = null;
                        jsonObjectTest = json.getJSONObject(0);
                        if (jsonObjectTest.optString("valor").trim().equals("1")) {
                            StyleableToast.makeText(itemView.getContext(), "Se registró comentario",R.style.exito_toast).show();
                            vedtcomentario.setText("");
                        } else {
                            StyleableToast.makeText(itemView.getContext(), "Error al registrar", R.style.exampletoast).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        StyleableToast.makeText(itemView.getContext(),"Error del json: "+e,R.style.exampletoast).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    StyleableToast.makeText(itemView.getContext(),"No se pudo conectar al JSON : "+error,R.style.exampletoast).show();
                }
            });
            int socketTimeout = 30000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjectRequest.setRetryPolicy(policy);
            requestQueue3.add(jsonObjectRequest);
        }

        private boolean validar() {
            boolean valid = true;
            String scomentario = vedtcomentario.getText().toString();
            if (scomentario.isEmpty()) {
                vedtcomentario.setError("Ingrese algún comentario");
                valid = false;
            } else {
                vedtcomentario.setError(null);
            }
            return valid;
        }
    }




    }

