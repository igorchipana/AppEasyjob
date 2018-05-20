/**
 * Created by Igor on 7/04/2018.
 */
package com.example.igor.msqlandroid.Adapter;

/**
 * Created by Igor on 7/04/2018.
 */
import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.example.igor.msqlandroid.Entidades.Categorias;
import com.example.igor.msqlandroid.Entidades.Persona;
import com.example.igor.msqlandroid.Entidades.calificacion;
import com.example.igor.msqlandroid.R;
import com.example.igor.msqlandroid.View.llamar;
import com.like.OnAnimationEndListener;
import com.like.OnLikeListener;
import com.mikepenz.iconics.IconicsDrawable;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.tooltip.Tooltip;

import android.app.Dialog;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.Inet4Address;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

import com.like.LikeButton;



public class AnunciosAdapter   extends RecyclerView.Adapter<AnunciosAdapter.AnunciosHolder> {
    int pruebav;

    RecyclerView recyclerAnuncios;
    TextView vtxtdatos, vtxtTitulo, vtxtDes, vtxtTelcorreo, vtxtTelcorreo1, vtxtuss, vtxtidanuncio, textidanuncio1,idanuncios;
    TextView vcategoria, vnombres, vcorreo, telefono, vdescripcion, vtitulo, txtResultado;
    TextView textidanuncio, textidpersona,edtprueba,edtprueba1,edtprueba2;
    EditText vedtcomentario;
    TextView domingo,domingo1;
    JsonObjectRequest jsonObjectRequest;
    Button btndialog, btndialog1;
    Button acpetar;
    Button acpetar1,cerrarmodal;
    Button cancelar,b1;
    Button llamar;
    List<Anuncios> listaAnuncios;
    List<calificacion> listaAnuncios5;
    ArrayList<Anuncios> listaAnuncios1;
    ArrayList<Anuncios> listaAnuncios2;
    ArrayList<calificacion> listacalificacion;
    ArrayList<String> listaAnunciosFinal;
    String fechaHora;
    ProgressDialog progreso;
    Tooltip toltip, toltip1;
    String valornumerotel;
    int contador = 0;
    String a,b;
    ArrayList<String> lisCategoriasCombo;
    ArrayList<calificacion> listaprueba;
    private static final int REQUEST_CALL = 1;
    private Button llamada,llama;
    String xx;
    String id1;
    private static long LAST_CLICK_TIME = 0;
    private final int mDoubleClickInterval = 400;
    //LikeButton thumbButton;


    LikeButton thumbButton;


    public AnunciosAdapter(List<Anuncios> listaAnuncios) {
        this.listaAnuncios = listaAnuncios;

    }



    @Override

    public AnunciosHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.anuncios_list, parent, false);
        thumbButton = (LikeButton) vista.findViewById(R.id.thumb_button);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        listaAnuncios2 = new ArrayList<>();
        listacalificacion = new ArrayList<>();
        return new AnunciosHolder(vista);



    }


    @Override
    public void onBindViewHolder(AnunciosHolder holder, int position){
        vtxtdatos.setText(listaAnuncios.get(position).getNombres().toString() + " " + listaAnuncios.get(position).getApellidos().toString());
        vtxtTitulo.setText(listaAnuncios.get(position).getTitulo().toString());
        vtxtDes.setText(listaAnuncios.get(position).getDescripcion().toString());
        vtxtTelcorreo.setText(listaAnuncios.get(position).getCorreo().toString());
        vtxtTelcorreo1.setText(listaAnuncios.get(position).getTelefono().toString());
        vtxtuss.setText(Integer.toString(listaAnuncios.get(position).getIdPersona()));
        vtxtidanuncio.setText(Integer.toString(listaAnuncios.get(position).getIdAnuncio()));
       // idanuncios.setText(Integer.toString(listaAnuncios.get(position).getValoridcali()));
       // edtprueba2.setText(xx);.




        if ((listaAnuncios.get(position).getValoridcali()) != 0) {
            thumbButton.setLiked(true);
        }

    }



    @Override
    public int getItemCount() {
        return listaAnuncios.size();
    }

    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public class AnunciosHolder extends RecyclerView.ViewHolder  implements OnLikeListener{
        final Context context = itemView.getContext();
        public AnunciosHolder(View itemView) {
            super(itemView);
            vtxtdatos = (TextView) itemView.findViewById(R.id.txtDatos);
            vtxtTitulo = (TextView) itemView.findViewById(R.id.txtTitulo);
            vtxtDes = (TextView) itemView.findViewById(R.id.txtDes);
            vtxtDes.setMovementMethod (LinkMovementMethod.getInstance());
            vtxtTelcorreo = (TextView) itemView.findViewById(R.id.txttelcorreo);
            vtxtTelcorreo1=(TextView)itemView.findViewById(R.id.txttelcorreo1) ;
            thumbButton.setOnLikeListener(this);
            btndialog = (Button) itemView.findViewById(R.id.btncomentar5);
            vtxtuss = (TextView) itemView.findViewById(R.id.idussss);
            vtxtidanuncio = (TextView) itemView.findViewById(R.id.idanuncio);
            btndialog1 = (Button) itemView.findViewById(R.id.btncomentar6);
            llamada = (Button)itemView.findViewById(R.id.btncomentarcall);
            llama = (Button)itemView.findViewById(R.id.algo);



                llama.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                        }
                        String valorid = String.valueOf(listaAnuncios.get(pos).getIdAnuncio());
                         listaPrueba(valorid);
                    }
                });


            btndialog.setOnClickListener(new View.OnClickListener() {
                int position;
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
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

            llamada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                    }
                        valornumerotel = (listaAnuncios.get(pos).getTelefono());
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(view.getContext());
                        builder.setMessage("¿Estás seguro que desea llamar a este número?");
                        builder.setTitle("Mensaje de confirmación");
                        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String phoneNo = valornumerotel.toString();
                                if (!TextUtils.isEmpty(phoneNo)) {
                                    String dial = "tel:" + phoneNo;
                                    context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                                } else {
                                    Toast.makeText(context, "LLamando...", Toast.LENGTH_SHORT).show();
                                }
                                Toast.makeText(context, "Seleccionando telefono", Toast.LENGTH_SHORT).show();

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


            btndialog1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                    }

                    String id3;
                    id3 = String.valueOf(listaAnuncios.get(pos).getIdAnuncio());
                    openDialogo(context, id3);
                }
            });
        }


        @Override
        public void liked(LikeButton likeButton) {
            Regcali();
        }

        @Override
        public void unLiked(LikeButton likeButton) {
            deletelike();
        }


        private void listaPrueba(String valor) {
            progreso=new ProgressDialog(itemView.getContext());
            progreso.setMessage("Cargando...");
            progreso.show();
            String ip = context.getString(R.string.ip);
            String urlServices2 = ip + "/dbremota/hola.php?idanuncio="+valor;
            RequestQueue requestQueue2 = Volley.newRequestQueue(itemView.getContext());
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlServices2, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    calificacion oPersona = null;
                    JSONArray json = response.optJSONArray("RESULTADO");
                    try {
                        JSONObject jsonObjectTest = null;
                        jsonObjectTest = json.getJSONObject(0);
                        if (jsonObjectTest.optString("idAnuncio").trim().equals("-9999999999")) {
                        } else {
                            for (int i = 0; i < json.length(); i++) {
                                oPersona = new calificacion();
                                JSONObject jsonObject = null;
                                jsonObject = json.getJSONObject(i);
                                oPersona.setValor(jsonObject.optInt("valor"));
                                listaprueba = new ArrayList<>();
                                listaprueba.add(oPersona);
                                xx=String.valueOf(listaprueba.get(i).getValor());
                                Log.d("datoxx=",xx);
                                Log.d("datovalor=",String.valueOf(listaprueba.get(i).getValor()));
                                openDialogcoment(context, xx);

                            }
                        }
                        progreso.hide();
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
            int socketTimeout = 10000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjectRequest.setRetryPolicy(policy);
            requestQueue2.add(jsonObjectRequest);

        }




        private void deletelike(){
            int pos = getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
            }
            pruebav= (listaAnuncios.get(pos).getIdPersonas());
            String ip=itemView.getContext().getString(R.string.ip);
            String urlService2=ip+"/dbremota/WsDeleteComent.php?idpersona="+ (listaAnuncios.get(pos).getIdPersonas())+
                    "&idanuncios="+ (listaAnuncios.get(pos).getIdAnuncio());

            urlService2=urlService2.replace(" ","%20");
            // Log.i("Latitud1::",valorLat);

            RequestQueue requestQueue3=Volley.newRequestQueue(itemView.getContext());
            jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, urlService2,null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Anuncios oAnuncios=null;

                    try{
                        JSONArray json=response.optJSONArray("resultado");
                        JSONObject jsonObjectTest = null;
                        jsonObjectTest = json.getJSONObject(0);
                        if (jsonObjectTest.optString("valor").trim().equals("1")){
                            StyleableToast.makeText(itemView.getContext(),"Dislike",R.style.exampletoast).show();
                            // listaPerfilUss();
                        }else{
                            StyleableToast.makeText(itemView.getContext(),"Error al modificar",R.style.exampletoast).show();
                        }
                    }catch (JSONException e) {
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


        private void Regcali() {
            int pos = getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
            }
            int valorLike=1;
            //   Toast.makeText(context, "Recycle" +valorLike, Toast.LENGTH_SHORT).show();
            String ip = itemView.getContext().getString(R.string.ip);
            String urlService = ip + "/dbremota/WsRegistroCalificacion.php?idanuncio=" + (listaAnuncios.get(pos).getIdAnuncio()) +
                    "&idpersona=" + (listaAnuncios.get(pos).getIdPersonas()) +
                    "&calificacion=" + valorLike;

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
                            StyleableToast.makeText(itemView.getContext(), "Se registró like", R.style.exito_toast).show();
                        } else {
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

        private void openDialogcoment(Context context, String xx1) {
            final Dialog dialogc = new Dialog(context);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.create();
            dialogc.setContentView(R.layout.comentario_modal);
            edtprueba1 = (TextView) dialogc.findViewById(R.id.porfavor);
            edtprueba1.setText(xx1);
            cerrarmodal=(Button)dialogc.findViewById(R.id.idcerrarmodal);
            cerrarmodal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogc.cancel();
                }
            });

            dialogc.show();
            dialogc.setCanceledOnTouchOutside(true);

        }

        public void openDialogo(Context context, String dato) {
            progreso=new ProgressDialog(itemView.getContext());
            progreso.setMessage("Cargando...");
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
            progreso.show();
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
            int pos = getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
            }
            String ip = itemView.getContext().getString(R.string.ip);
            String urlService = ip + "/dbremota/WsRegistroComentario.php?idanuncio=" + textidanuncio.getText().toString() +
                    "&idpersona=" +(listaAnuncios.get(pos).getIdPersonas()) +
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
                        progreso.hide();
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
           // progreso=new ProgressDialog(context);
           // progreso.setMessage("Cargando...");
          //  progreso.show();
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
                      //  progreso.hide();
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

    @Override
    public void onViewRecycled(AnunciosHolder holder) {
        super.onViewRecycled(holder);
    }
}

