package com.example.fernando.recervascancha;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MenuCliente1 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String usuario;
    int tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cliente1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle datos = getIntent().getExtras();
        usuario = datos.getString("usuario");
        tipo = datos.getInt("tipo");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cliente1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_manteUsuarios) {
            Intent objCrudUsuarios1 = new Intent(getApplicationContext(),CrudUsuarios.class);
            objCrudUsuarios1.putExtra("usuario",usuario);
            objCrudUsuarios1.putExtra("tipo",tipo);
            startActivity(objCrudUsuarios1);
            overridePendingTransition(R.anim.right_out,R.anim.right_in);

        } else if (id == R.id.nav_realizarReserva) {
            Intent objListaUsuarios = new Intent(getApplicationContext(),Reservas.class);
            objListaUsuarios.putExtra("usuario",usuario);
            objListaUsuarios.putExtra("tipo",tipo);
            objListaUsuarios.putExtra("operacion",0);//creacion
            startActivity(objListaUsuarios);
            overridePendingTransition(R.anim.right_out,R.anim.right_in);

        } else if (id == R.id.nav_ListadoReservas1) {
            Intent objListaUsuarios = new Intent(getApplicationContext(),ListaReservas.class);
            objListaUsuarios.putExtra("usuario",usuario);
            objListaUsuarios.putExtra("tipo",tipo);
            startActivity(objListaUsuarios);
            overridePendingTransition(R.anim.right_out,R.anim.right_in);

        } else if (id == R.id.nav_HistorialReserva) {
            Intent objListaUsuarios = new Intent(getApplicationContext(),Historial.class);
            objListaUsuarios.putExtra("usuario",usuario);
            objListaUsuarios.putExtra("tipo",tipo);
            startActivity(objListaUsuarios);
            overridePendingTransition(R.anim.right_out,R.anim.right_in);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
