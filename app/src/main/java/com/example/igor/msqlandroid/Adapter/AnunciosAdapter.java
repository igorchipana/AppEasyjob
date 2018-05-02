/**
 * Created by Igor on 7/04/2018.
 */
package com.example.igor.msqlandroid.Adapter;

/**
 * Created by Igor on 7/04/2018.
 */
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.tooltip.Tooltip;

import android.app.Dialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AnunciosAdapter   extends RecyclerView.Adapter<AnunciosAdapter.AnunciosHolder> {
    RecyclerView recyclerAnuncios;
    TextView vtxtdatos, vtxtTitulo, vtxtDes, vtxtTelcorreo,vtxtTelcorreo1, vtxtuss, vtxtidanuncio, textidanuncio1;
    TextView vcategoria, vnombres, vcorreo, telefono, vdescripcion, vtitulo, txtResultado;
    TextView textidanuncio, textidpersona;
    EditText vedtcomentario;
    JsonObjectRequest jsonObjectRequest;
    Button btndialog, btndialog1;
    Button acpetar;
    Button acpetar1;
    Button cancelar;
    List<Anuncios> listaAnuncios;
    ArrayList<Anuncios> listaAnuncios1;
    ArrayList<Anuncios> listaAnuncios2;
    ArrayList<String> listaAnunciosFinal;
    String fechaHora;
    ProgressDialog progreso;
    Tooltip toltip, toltip1;

    public AnunciosAdapter(List<Anuncios> listaAnuncios) {
        this.listaAnuncios = listaAnuncios;
    }

    @Override

    public AnunciosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.anuncios_list, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        listaAnuncios2 = new ArrayList<>();

        return new AnunciosHolder(vista);


    }

    @Override
    public void onBindViewHolder(AnunciosHolder holder, int position) {
        vtxtdatos.setText(listaAnuncios.get(position).getNombres().toString() + " " + listaAnuncios.get(position).getApellidos().toString());
        vtxtTitulo.setText(listaAnuncios.get(position).getTitulo().toString());
        vtxtDes.setText(listaAnuncios.get(position).getDescripcion().toString());
        vtxtTelcorreo.setText(listaAnuncios.get(position).getCorreo().toString());
        vtxtTelcorreo1.setText(listaAnuncios.get(position).getTelefono().toString());
        vtxtuss.setText(Integer.toString(listaAnuncios.get(position).getIdPersona()));
        vtxtidanuncio.setText(Integer.toString(listaAnuncios.get(position).getIdAnuncio()));
        // vcorreo.setText(listaAnuncios1.get(position).getCorreo());

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
            vtxtDes.setMovementMethod (LinkMovementMethod.getInstance());
            vtxtTelcorreo = (TextView) itemView.findViewById(R.id.txttelcorreo);
            vtxtTelcorreo1=(TextView)itemView.findViewById(R.id.txttelcorreo1) ;
            btndialog = (Button) itemView.findViewById(R.id.btncomentar5);
            vtxtuss = (TextView) itemView.findViewById(R.id.idussss);
            vtxtidanuncio = (TextView) itemView.findViewById(R.id.idanuncio);
            btndialog1 = (Button) itemView.findViewById(R.id.btncomentar6);
          /*  Tooltip tooltip=new Tooltip.Builder(btndialog)
                    .setText("Comentar")
                    .setTextColor(Color.WHITE)
                    .setCornerRadius(8f)
                    .setDismissOnClick(true)
                    .show();*/


            btndialog.setOnClickListener(new View.OnClickListener() {
                int position;

                @Override
                public void onClick(View view) {

                    int pos = getAdapterPosition();
                    // check if item still exists

                    if (pos != RecyclerView.NO_POSITION) {
                       // Toast.makeText(context, "Recycle" + (listaAnuncios.get(pos).getIdAnuncio()), Toast.LENGTH_SHORT).show();
                      //  Toast.makeText(context, "Recycle" + (listaAnuncios.get(pos).getIdPersona()), Toast.LENGTH_SHORT).show();
                    }

                    String id1;
                    String id2;
                    id1 = String.valueOf(listaAnuncios.get(pos).getIdAnuncio());
                    id2 = String.valueOf(listaAnuncios.get(pos).getIdPersona());
                    openDialog(context, id1, id2);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date date = new Date();
                    String fecha = dateFormat.format(date);
                    Calendar c = Calendar.getInstance();
                    int hora = c.get(Calendar.HOUR_OF_DAY);
                    int minuto = c.get(Calendar.MINUTE);
                    int secundo = c.get(Calendar.SECOND);
                    String horaactual = String.valueOf(hora + ":" + minuto + ":" + secundo);
                    fechaHora = fecha + " " + horaactual;
                    Log.i("Hora3::", fechaHora);
                }
            });
            btndialog1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    // check if item still exists

                    if (pos != RecyclerView.NO_POSITION) {
                      //  Toast.makeText(context, "Recycle" + (listaAnuncios.get(pos).getIdAnuncio()), Toast.LENGTH_SHORT).show();
                    }
                    String id3;
                    id3 = String.valueOf(listaAnuncios.get(pos).getIdAnuncio());
                    openDialogo(context, id3);
                }
            });
        }

        public void openDialogo(Context context, String dato) {

            final Dialog dialog2 = new Dialog(context);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            dialog2.setContentView(R.layout.lista_dialog);
            dialog2.setTitle("Comentarios de Anuncio:");
            acpetar1 = (Button) dialog2.findViewById(R.id.acpet);
            vcategoria = (TextView) dialog2.findViewById(R.id.acategoria);
            vnombres = (TextView) dialog2.findViewById(R.id.apublicador);
            vcorreo = (TextView) dialog2.findViewById(R.id.acorreo);
            telefono = (TextView) dialog2.findViewById(R.id.atelefono);
            vdescripcion = (TextView) dialog2.findViewById(R.id.adescripcion);
            vdescripcion.setMovementMethod (LinkMovementMethod.getInstance());
            vtitulo = (TextView) dialog2.findViewById(R.id.atitulo);
            txtResultado = (TextView) dialog2.findViewById(R.id.idTxtResultado1);

            textidanuncio1 = (TextView) dialog2.findViewById(R.id.idsalv);
            textidanuncio1.setText(dato);
            recyclerAnuncios = (RecyclerView) dialog2.findViewById(R.id.idRecycler1);
            recyclerAnuncios.setLayoutManager(new LinearLayoutManager(context));
            recyclerAnuncios.setHasFixedSize(true);
            loadAnuncioData1();
            loadAnuncioData2();


            acpetar1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog2.cancel();
                }
            });
            dialog2.show();
            dialog2.setCanceledOnTouchOutside(true);

        }


        public void openDialog(Context context, String correo, String correo1) {
            final Dialog dialog1 = new Dialog(context);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            dialog1.setContentView(R.layout.comentar_dialog);
            dialog1.setTitle(R.string.titulo);
            builder.create();

            textidanuncio = (TextView) dialog1.findViewById(R.id.txtEjemplo);
            textidanuncio.setText(correo);
            textidpersona = (TextView) dialog1.findViewById(R.id.txtEjemplo1);
            textidpersona.setText(correo1);
            vedtcomentario = (EditText) dialog1.findViewById(R.id.edtcomentario);
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
                    progreso=new ProgressDialog(itemView.getContext());
                    progreso.setMessage("Cargando...");
                    progreso.show();
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
                            progreso.hide();
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
            String ip = itemView.getContext().getString(R.string.ip);
            String urlService = ip + "/dbremota/WsRegistroComentario.php?idanuncio=" + textidanuncio.getText().toString() +
                    "&idpersona=" + textidpersona.getText().toString() +
                    "&comentario=" + vedtcomentario.getText().toString() +
                    "&fecha=" + fechaHora.toString();
            urlService = urlService.replace(" ", "%20");
            RequestQueue requestQueue3 = Volley.newRequestQueue(itemView.getContext());
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlService, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray json = response.optJSONArray("resultado");
                        JSONObject jsonObjectTest = null;
                        jsonObjectTest = json.getJSONObject(0);
                        if (jsonObjectTest.optString("valor").trim().equals("1")) {
                            StyleableToast.makeText(itemView.getContext(), "Se registró comentario", R.style.exito_toast).show();
                            progreso.hide();
                            vedtcomentario.setText("");
                            progreso.hide();
                        } else {
                            progreso.hide();
                            StyleableToast.makeText(itemView.getContext(), "Error al registrar", R.style.exampletoast).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        StyleableToast.makeText(itemView.getContext(), "Error del json: " + e, R.style.exampletoast).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    StyleableToast.makeText(itemView.getContext(), "No se pudo conectar al JSON : " + error, R.style.exampletoast).show();
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


        private void loadAnuncioData1() {
            Log.i("Cargo:", "aqui");
            String ip = itemView.getContext().getString(R.string.ip);
            String urlServices2 = ip + "/dbremota/WsListComent.php?idanuncio=" + textidanuncio1.getText().toString();
            RequestQueue requestQueue2 = Volley.newRequestQueue(itemView.getContext());
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlServices2, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Anuncios oAnuncios = null;
                    JSONArray json = response.optJSONArray("RESULTADO");
                    try {
                        JSONObject jsonObjectTest = null;
                        jsonObjectTest = json.getJSONObject(0);
                        if (jsonObjectTest.optString("idAnuncio").trim().equals("-9999999999")) {
                            Log.i("Cargo:", "aqui2");
                        } else {
                            for (int i = 0; i < json.length(); i++) {
                                oAnuncios = new Anuncios();
                                JSONObject jsonObject = null;
                                jsonObject = json.getJSONObject(i);
                                oAnuncios.setCategoria(jsonObject.optString("nomcategoria"));
                                oAnuncios.setNombres(jsonObject.optString("nombres"));
                                oAnuncios.setApellidos(jsonObject.optString("apellidos"));
                                oAnuncios.setTelefono(jsonObject.optString("telefono"));
                                oAnuncios.setCorreo(jsonObject.optString("correo"));
                                oAnuncios.setFecha(jsonObject.optString("fechA"));
                                oAnuncios.setTitulo(jsonObject.optString("titulo"));
                                oAnuncios.setDescripcion(jsonObject.optString("descripcion"));
                                oAnuncios.setIdAnuncio(jsonObject.optInt("idAnuncio"));
                                oAnuncios.setIdCategoria(jsonObject.optInt("idCategoria"));
                                oAnuncios.setIdPersona(jsonObject.optInt("idPersona"));
                                oAnuncios.setCalificacion(jsonObject.optString("calificacion"));
                                listaAnuncios1 = new ArrayList<>();
                                listaAnuncios1.add(oAnuncios);
                                vcorreo.setText(listaAnuncios1.get(i).getCorreo().toString());
                                vtitulo.setText(listaAnuncios1.get(i).getTitulo().toString());
                                vcategoria.setText(listaAnuncios1.get(i).getCategoria().toString());
                                vnombres.setText(listaAnuncios1.get(i).getNombres().toString() + "  " + listaAnuncios1.get(i).getApellidos().toString());
                                telefono.setText(listaAnuncios1.get(i).getTelefono().toString());
                                vdescripcion.setText(listaAnuncios1.get(i).getDescripcion().toString());
                            }
                        }
                        //progreso.hide();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        StyleableToast.makeText(itemView.getContext(), "Error del json: " + e, R.style.exampletoast).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    StyleableToast.makeText(itemView.getContext(), "No se pudo conectar al JSON : " + error, R.style.exampletoast).show();
                }
            });
            int socketTimeout = 30000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjectRequest.setRetryPolicy(policy);
            requestQueue2.add(jsonObjectRequest);

        }

        private void loadAnuncioData2() {

            String ip = itemView.getContext().getString(R.string.ip);
            String urlServices3 = ip + "/dbremota/WsListComentAdapter.php?idanuncio=" + textidanuncio1.getText().toString();
            RequestQueue requestQueue2 = Volley.newRequestQueue(itemView.getContext());

            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlServices3, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Anuncios oComentarios = null;
                    JSONArray json = response.optJSONArray("RESULTADO");
                    txtResultado.setText("");
                    listaAnuncios2.clear();
                    try {
                        JSONObject jsonObjectTest = null;
                        jsonObjectTest = json.getJSONObject(0);
                        if (jsonObjectTest.optString("idAnuncio").trim().equals("Error_al_listar")) {

                            txtResultado.setText(" Aún no se han registrado comentarios :(");
                            //recyclerAnuncios.setAdapter("No hay resultados para la categoria seleccionada");

                        } else {
                            for (int i = 0; i < json.length(); i++) {
                                oComentarios = new Anuncios();
                                JSONObject jsonObject = null;
                                jsonObject = json.getJSONObject(i);
                                oComentarios.setNombres(jsonObject.optString("nombres"));
                                oComentarios.setApellidos(jsonObject.optString("apellidos"));
                                oComentarios.setFecha(jsonObject.optString("fecha"));
                                oComentarios.setComentario(jsonObject.optString("comentario"));
                                oComentarios.setIdAnuncio(jsonObject.optInt("idAnuncio"));
                                oComentarios.setIdPersona(jsonObject.optInt("idPersona"));

                                listaAnuncios2.add(oComentarios);
                            }


                            for (int i = 0; i < listaAnuncios2.size(); i++) {
                                Log.i("recorre?:", String.valueOf(listaAnuncios2.get(0).getApellidos()));
                                ComentariosAdapter adapter = new ComentariosAdapter(listaAnuncios2);
                                recyclerAnuncios.setAdapter(adapter);
                            }
                        }
                       // progreso.hide();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        StyleableToast.makeText(itemView.getContext(), "Error del json: " + e, R.style.exampletoast).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    StyleableToast.makeText(itemView.getContext(), "No se pudo conectar al JSON : " + error, R.style.exampletoast).show();
                }
            });
            int socketTimeout = 30000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjectRequest.setRetryPolicy(policy);
            requestQueue2.add(jsonObjectRequest);
        }


    }



}

