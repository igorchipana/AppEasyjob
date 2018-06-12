package com.example.igor.msqlandroid.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.igor.msqlandroid.Entidades.Anuncios;
import com.example.igor.msqlandroid.Entidades.Persona;
import com.example.igor.msqlandroid.FregistroAnuncio;
import com.example.igor.msqlandroid.R;
import com.example.igor.msqlandroid.View.ExampleComboBox;
import com.example.igor.msqlandroid.View.Mis_AnunciosFrag;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 4/06/2018.
 */

public class MisAnunciosAdapter extends RecyclerView.Adapter<MisAnunciosAdapter.HolderAnuncios> {
    String valorid1;
    String id1;
    String valorid;
    List<Anuncios> listaMisAnuncios;
    ArrayList<Anuncios> listaAnuncios1;
    TextView textidanuncio;
    ProgressDialog progreso;
    Button acpetar,cancelar;
    EditText vdescripcion,vtitulo;
    public MisAnunciosAdapter(List<Anuncios> listanuncio) {
        this.listaMisAnuncios = listanuncio;
    }
    JsonObjectRequest jsonObjectRequest;


    Mis_AnunciosFrag Mis_AnunciosFrag= new Mis_AnunciosFrag();
    @Override
    public MisAnunciosAdapter.HolderAnuncios onCreateViewHolder( ViewGroup parent, int viewType) {
        View vista=LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_misanuncios,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);




        return new HolderAnuncios(vista);
    }

    @Override
    public void onBindViewHolder(MisAnunciosAdapter.HolderAnuncios holder, int position) {
        holder.desc.setText(listaMisAnuncios.get(position).getDescripcion().toString());
        holder.titulo.setText(listaMisAnuncios.get(position).getTitulo().toString());
    }

    @Override
    public int getItemCount() {
        return listaMisAnuncios.size();

    }

    public class HolderAnuncios extends RecyclerView.ViewHolder{
        TextView titulo,desc;
        Button eleminiar;
        ImageView imagen;
        final Context context = itemView.getContext();
        public HolderAnuncios(View itemView){

            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.txttitulo);
            eleminiar=(Button)itemView.findViewById(R.id.btndelete);
            imagen=(ImageView)itemView.findViewById(R.id.imgedit);
            desc = (TextView) itemView.findViewById(R.id.txtDescripcion);
            desc.setMovementMethod (LinkMovementMethod.getInstance());


            eleminiar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                    }
                    valorid = String.valueOf(listaMisAnuncios.get(pos).getIdAnuncio());
                    valorid1 = String.valueOf(listaMisAnuncios.get(pos).getIdPersona());
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                    builder.setMessage("¿Está seguro que quiere eliminar este anuncio?");
                    builder.setTitle("Mensaje de confirmación");
                    builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String ip=context.getString(R.string.ip);
                            String urlServices2=ip+"/dbremota/WsDeleteAnuncios.php";
                            delete(urlServices2+"?idanuncio="+valorid);
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


            imagen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                    }
                    id1 = String.valueOf(listaMisAnuncios.get(pos).getIdAnuncio());
                    valorid1 = String.valueOf(listaMisAnuncios.get(pos).getIdPersona());
                    openDialog(context);
                }
            });

        }
        public void openDialog(final Context context) {
            listamisanuciosV1();
            final Dialog dialog = new Dialog(context);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            dialog.setContentView(R.layout.dialog_edit_misanuncios);
            builder.create();
            vtitulo = (EditText) dialog.findViewById(R.id.edtvalortitulo);
            vdescripcion = (EditText) dialog.findViewById(R.id.edtvalordescripcion);
            acpetar = (Button) dialog.findViewById(R.id.btnacep);
            cancelar = (Button) dialog.findViewById(R.id.btncancel);
            cancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                }
            });
            acpetar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progreso=new ProgressDialog(itemView.getContext());
                    progreso.setMessage("Cargando...");
                    progreso.show();
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(view.getContext());
                    builder.setMessage("¿Está seguro que quiere editar este anuncio?");
                    builder.setTitle("Mensaje de confirmación");
                    builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            EditMisAnuncios ();
                            dialog.cancel();
                            Mis_AnunciosFrag vistafragment= new Mis_AnunciosFrag();
                            Bundle args = new Bundle();
                            args.putString("idpersona", valorid1);
                            vistafragment.setArguments(args);
                            FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.contenedor, vistafragment, null);
                            transaction.commit();
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
            dialog.show();
            dialog.setCanceledOnTouchOutside(true);

        }

        private void listamisanuciosV1() {
            progreso=new ProgressDialog(itemView.getContext());
           progreso.setMessage("Cargando...");
           progreso.show();
            String ip = itemView.getContext().getString(R.string.ip);
            String urlServices2 = ip + "/dbremota/WsListMisanunciosv1.php?idanuncio="+ id1;
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
                        } else {
                            for (int i = 0; i < json.length(); i++) {
                                oAnuncios = new Anuncios();
                                JSONObject jsonObject = null;
                                jsonObject = json.getJSONObject(i);
                                oAnuncios.setIdAnuncio(jsonObject.optInt("idAnuncio"));
                                oAnuncios.setIdPersona(jsonObject.optInt("idPersona"));
                                oAnuncios.setTitulo(jsonObject.optString("descripcion"));
                                oAnuncios.setDescripcion(jsonObject.optString("titulo"));

                                listaAnuncios1 = new ArrayList<>();
                                listaAnuncios1.add(oAnuncios);

                                vtitulo.setText(listaAnuncios1.get(i).getTitulo().toString());
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

        private void EditMisAnuncios () {
            String ip=itemView.getContext().getString(R.string.ip);
            String urlService2=ip+"/dbremota/WsEditMisanuncios.php?titulo="+vtitulo.getText().toString()+
                    "&descripcion="+vdescripcion.getText().toString()+
                    "&idAnuncio="+id1;
            urlService2=urlService2.replace(" ","%20");
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
                            StyleableToast.makeText(itemView.getContext(),"Se modificaron los datos",R.style.exito_toast).show();
                            progreso.hide();

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

    public void delete(String url) {
        url = url.replace(" ", "%20");
        RequestQueue requestQueue3 = Volley.newRequestQueue(itemView.getContext());
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray json = response.optJSONArray("resultado");
                    JSONObject jsonObjectTest = null;
                    jsonObjectTest = json.getJSONObject(0);
                    if (jsonObjectTest.optString("valor").trim().equals("1")) {
                        StyleableToast.makeText(itemView.getContext(), "Se eliminó el anuncio", R.style.exito_toast).show();

                        Mis_AnunciosFrag vistafragment= new Mis_AnunciosFrag();
                        Bundle args = new Bundle();
                        args.putString("idpersona", valorid1);
                        vistafragment.setArguments(args);
                        FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.contenedor, vistafragment, null);
                        transaction.commit();

                    } else {
                        StyleableToast.makeText(itemView.getContext(), "Error al eliminar", R.style.exampletoast).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    StyleableToast.makeText(itemView.getContext(), "Error del json: " + e, R.style.exampletoast).show();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
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


    }
}
