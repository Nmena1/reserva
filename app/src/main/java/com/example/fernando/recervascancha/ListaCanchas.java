package com.example.fernando.recervascancha;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListaCanchas extends Activity {

    ListView Lista;
    Conexion db;
    List<String> item = null;
    Button Agregar;
    //SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_canchas);

        Lista = (ListView)findViewById(R.id.listView_Lista);
        VerCanchas();
        Agregar=(Button)findViewById(R.id.btnNuevaCancha);

        Agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListaCanchas.this,AgregarCancha.class);
                startActivity(i);
            }
        });

        Lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String p = String.valueOf(position);
                Toast.makeText(getApplicationContext(),p,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),EditCancha.class);
                startActivity(intent);
            }
        });

    }
    public void VerCanchas(){
        db = new Conexion(this);
        Cursor c = db.getCanchas();
        item = new ArrayList<String>();
        String n="",e="",p="";
        if (c.moveToFirst()){
            do {
                n = c.getString(1);
                e = c.getString(2);
                p = c.getString(3);
                item.add("Cancha: "+n+" Estado: "+e+" Precio :$"+p);
            }while (c.moveToNext());
        }
        /*String [] from = new String[]{db.nombre_cancha,db.estado_cancha};
        int [] to = new int[]{android.R.id.text1,android.R.id.text2};
        c = db.getCanchas();*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,item); //SimpleCursorAdapter(this,android.R.layout.two_line_list_item,c,from,to,0);
        Lista.setAdapter(adapter);
    }

    public void BuscarCanchas(){
        db = new Conexion(this);
        Cursor c = db.getCanchas();
        String n="";
        if (c.moveToFirst()){
            do {
                n = c.getString(0);

            }while (c.moveToNext());
        }

    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_AgregarDatos){
            Intent i = new Intent(ListaCanchas.this,AgregarCancha.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }*/

}