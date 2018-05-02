package com.example.igor.msqlandroid.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;
import  android.support.v4.app.FragmentTransaction;

import com.example.igor.msqlandroid.FregistroAnuncio;
import com.example.igor.msqlandroid.R;
import com.example.igor.msqlandroid.View.ExampleComboBox;
import com.example.igor.msqlandroid.View.MainActivity;

public class MainActivity2 extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {
   // Bundle extras=getIntent().getExtras();
    String vPERSONAID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        View header = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);
        try{
            Bundle extras=getIntent().getExtras();
            vPERSONAID=extras.getString("PERSONAID");
            String vNOMBRES=extras.getString("NOMBRES");
            String vAPELLIDOS=extras.getString("APELLIDOS");

            ((TextView) header.findViewById(R.id.bienvenidos)).setText("Bienvenido:");
            ((TextView) header.findViewById(R.id.cargardatos)).setText(vNOMBRES+" "+vAPELLIDOS);



        }catch (Exception e){
            e.printStackTrace();
        }
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        //fragmentManager.beginTransaction().replace(R.id.contenedor,new ListaAnuncios()).commit();
        fragmentManager.beginTransaction().replace(R.id.contenedor,new ExampleComboBox()).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        FregistroAnuncio vistafragment= new FregistroAnuncio();
        Bundle args = new Bundle();
        args.putString("idpersona", vPERSONAID);
        vistafragment.setArguments(args);


        PerfilUss vistafragment1= new PerfilUss();
        Bundle args1 = new Bundle();
        args1.putString("idpersona", vPERSONAID);
        vistafragment1.setArguments(args1);

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_listaanuncios) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new ExampleComboBox()).commit();
        } else if (id == R.id.nav_anuncios) {
            FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
            fragTransaction.replace(R.id.contenedor, vistafragment, null);
            fragTransaction.commit();
        }
        else if (id == R.id.nav_ubicaranuncios) {

            //fragmentManager.beginTransaction().replace(R.id.contenedor,new Anuncios_Maps()).commit();

            Intent intentfff = new Intent(this, MapsAnuncios.class);
             startActivityForResult(intentfff, 12345);
          //  Intent intentfff = new Intent(this, Anuncios_Maps.class);
            //startActivityForResult(intentfff, 12345);



        }else if (id == R.id.nav_perfil) {
            FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
            fragTransaction.replace(R.id.contenedor,vistafragment1,null);
            fragTransaction.commit();

        }  else if (id == R.id.nav_share1) {

        } else if (id == R.id.nav_salir) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Seguro que quiere cerrar sesión?");
            builder.setTitle("Mensaje de confirmación");
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent in = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(in);
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog dialog=builder.create();
            dialog.show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }



}
