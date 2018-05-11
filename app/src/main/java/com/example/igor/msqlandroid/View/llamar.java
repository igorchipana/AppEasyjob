package com.example.igor.msqlandroid.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.igor.msqlandroid.Adapter.AnunciosAdapter;
import com.example.igor.msqlandroid.R;

public class llamar extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    private TextView numero;
    private Button llamada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anuncios_list);
        numero = findViewById(R.id.txttelcorreo1);
        llamada = findViewById(R.id.btncomentarcall);
        
        llamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                marcarnum();
            }
        });


    }

    private void marcarnum() {
        String numerotel = numero.getText().toString();
        if (numerotel.trim().length()>0){

            if (ContextCompat.checkSelfPermission(llamar.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(llamar.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);

            }else {
                String dial = "llamando:"+numerotel;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
            
        }else
        {
            Toast.makeText(llamar.this, "Este número no está disponible", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                marcarnum();
            }else
            {
                Toast.makeText(this, "Permiso Denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
