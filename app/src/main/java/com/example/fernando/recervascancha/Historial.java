package com.example.fernando.recervascancha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

public class Historial extends AppCompatActivity {

    ListView lstHistorial;
    String idCancha ,usuario;
    int tipo;
    DatosReservas objReserva = new DatosReservas();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        Bundle datos = getIntent().getExtras();

        usuario = datos.getString("usuario");
        tipo = datos.getInt("tipo");

        lstHistorial = (ListView)findViewById(R.id.lstHistorial);

        if (tipo==1) {
            objReserva.historial(getApplicationContext());
        }else {
            objReserva.historialUsuario(getApplicationContext(),usuario);
        }

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        AdaptadorListaReserva adapter = new AdaptadorListaReserva(getApplicationContext(),objReserva.getArrayid()
                ,objReserva.getArrayUsuario(),objReserva.getArrayCancha(),objReserva.getArrayFecha(),objReserva.getArrayHorario(),objReserva.getArrayEstado());
        lstHistorial.setAdapter(adapter);


        registerForContextMenu(lstHistorial);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);

    }
}
