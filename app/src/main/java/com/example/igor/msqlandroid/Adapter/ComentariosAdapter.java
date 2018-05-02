package com.example.igor.msqlandroid.Adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.igor.msqlandroid.Entidades.Anuncios;
import com.example.igor.msqlandroid.R;

import java.util.List;

/**
 * Created by Igor on 21/04/2018.
 */

public class ComentariosAdapter extends RecyclerView.Adapter<ComentariosAdapter.ComentariosHolder> {
    List<Anuncios> listanuncio;
    public ComentariosAdapter(List<Anuncios> listanuncio) {
        this.listanuncio = listanuncio;
    }



    @Override
    public ComentariosAdapter.ComentariosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista=LayoutInflater.from(parent.getContext()).inflate(R.layout.comentarios_list,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new ComentariosHolder(vista);
    }

    @Override
    public void onBindViewHolder(ComentariosAdapter.ComentariosHolder holder, int position) {
        holder.tnombre.setText(listanuncio.get(position).getNombres().toString()+"   "+listanuncio.get(position).getApellidos().toString());
        holder.tcomentario.setText(listanuncio.get(position).getComentario().toString());
        holder.tfecha.setText(listanuncio.get(position).getFecha().toString());
    }

    @Override
    public int getItemCount() {
        return listanuncio.size();
    }

    public class ComentariosHolder extends RecyclerView.ViewHolder{
            TextView tcomentario,tnombre,tfecha;

        public ComentariosHolder(View itemView){
            super(itemView);
            tnombre = (TextView) itemView.findViewById(R.id.vvdatos);
            tcomentario = (TextView) itemView.findViewById(R.id.vvcomentario);
            tcomentario.setMovementMethod (LinkMovementMethod.getInstance());
            tfecha = (TextView) itemView.findViewById(R.id.vvfecha);
        }
    }
}
