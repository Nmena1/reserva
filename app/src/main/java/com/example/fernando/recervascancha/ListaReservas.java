package com.example.fernando.recervascancha;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListaReservas extends AppCompatActivity {

    private ListView lstReservas;
    private DatosReservas objReserva = new DatosReservas();
    int idReserva;
    private String idCancha ,usuario;
    int tipo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_reservas);

        Bundle datos = getIntent().getExtras();

        usuario = datos.getString("usuario");
        tipo = datos.getInt("tipo");

        lstReservas = (ListView)findViewById(R.id.lsReserva);


        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }



        //adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        /*AdaptadorListaReserva adapter = new AdaptadorListaReserva(getApplicationContext(),objReserva.getArrayid()
                ,objReserva.getArrayUsuario(),objReserva.getArrayCancha(),objReserva.getArrayFecha(),objReserva.getArrayHorario(),objReserva.getArrayEstado());
        lstReservas.setAdapter(adapter);
        */
        updateData();
        registerForContextMenu(lstReservas);
        //listado();

        /*
       lstReservas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tvIdReserva = (TextView) view.findViewById(R.id.tvReservaAr);
                TextView tvIdCancha = (TextView)view.findViewById(R.id.tvCanchaAr) ;
                idReserva = Integer.parseInt(tvIdReserva.getText().toString().trim());
                 idCancha = tvIdReserva.getText().toString().trim();
            }
        });


    */

    }

    /*
    public void listado(){
        Conexion conexion = new Conexion(getApplicationContext());
        SQLiteDatabase mibase = conexion.getWritableDatabase();
        sql = "select s.usuario,r.cancha,r.fecha,tr.horas from reservas r,usuarios s, tiempo_reservas " +
                "tr where s.id_usuario = r.id_usuario and tr.id_tiempo = r.id_tiempo";
        Cursor objC= mibase.rawQuery(sql,null);

        if (objC!=null){
            while (objC.moveToNext()){
                adapter.add("Usuario :"+objC.getString(0)+" Cancha :"+objC.getString(1)+" Fecha :"+objC.getString(2)+" Horas :"+objC.getString(3));
            }

        }else {
            adapter.add("no hay datos");
        }
        objC.close();
        mibase.close();
        conexion.close();

    }
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (tipo==1){
        getMenuInflater().inflate(R.menu.menu_reservas,menu);
        }else if (tipo==2){
            getMenuInflater().inflate(R.menu.menu_reservas_usu,menu);
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        TextView cancha = (TextView) info.targetView.findViewById(R.id.tvCanchaAr);
        TextView reserva = (TextView) info.targetView.findViewById(R.id.tvReservaAr);
        idReserva = Integer.parseInt(reserva.getText().toString().trim());
        idCancha = cancha.getText().toString().trim();
        //AdaptadorListaReserva adapter;


        switch (item.getItemId()){
            case R.id.finalizar:
                objReserva.procesarAnular(idReserva,1,getApplicationContext());
                Toast.makeText(this,"Se Finalizo La Reserva "+ idReserva, Toast.LENGTH_SHORT).show();
                //objReserva.listado(getApplicationContext());
                updateData();

                return true;

            case R.id.anular:
                objReserva.procesarAnular(idReserva,0,getApplicationContext());
                Toast.makeText(this,"Se Anulo La Reserva " + idReserva, Toast.LENGTH_SHORT).show();
                //objReserva.listado(getApplicationContext());
                updateData();

                return true;

            case R.id.anularUsu:
                objReserva.procesarAnular(idReserva,0,getApplicationContext());
                Toast.makeText(this,"Se Anulo La Reserva " + idReserva, Toast.LENGTH_SHORT).show();

                //objReserva.listadoRUsuario(getApplication(),usuario);
                updateData();
                return true;
        }

        return super.onContextItemSelected(item);
    }

    private void updateData() {
        if (tipo==1){
            objReserva.listado(getApplicationContext());
        }else if (tipo==2){
            objReserva.listadoRUsuario(getApplication(),usuario);
        }

        Toast.makeText(getApplicationContext(),"se ejecuta usuario"+tipo,Toast.LENGTH_LONG).show();
        AdaptadorListaReserva adapter = new AdaptadorListaReserva(getApplicationContext(), objReserva.getArrayid()
                , objReserva.getArrayUsuario(), objReserva.getArrayCancha(), objReserva.getArrayFecha(), objReserva.getArrayHorario(), objReserva.getArrayEstado());
        lstReservas.setAdapter(adapter);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lstReservas.deferNotifyDataSetChanged();
            }
        });
    }




}
