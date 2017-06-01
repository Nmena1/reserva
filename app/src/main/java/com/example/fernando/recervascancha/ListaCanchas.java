package com.example.fernando.recervascancha;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//public class ListaCanchas extends Activity {
public class ListaCanchas extends AppCompatActivity {

    ListView Lista;
    Conexion db;
    List<String> item = null;
    Button Agregar;
    ArrayAdapter<String> adapter;
    //SimpleCursorAdapter adapter;
    SqlReservas objsql = new SqlReservas();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_canchas);

        Lista = (ListView)findViewById(R.id.listView_Lista);
        //adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);

       objsql.listadoCanchas(getApplicationContext());
        adapter = new AdaptadorListaCancha(getApplicationContext(),
                objsql.getArrayidcancha(),
                objsql.getArraynombrecancha(),
                objsql.getArrayestadocancha(),
                objsql.getArraypreciocancha());

        Lista.setAdapter(adapter);
        //llenarCanchas();

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
                TextView txv = (TextView)view.findViewById(R.id.tvUsuarioC);
                TextView txv2 = (TextView)view.findViewById(R.id.tvEstadoC);
                TextView txv3 = (TextView)view.findViewById(R.id.tvPrecioC);

                Intent intent = new Intent(getApplicationContext(),EditCancha.class);
                String nomCancha=txv.getText().toString();
                String estCancha=txv2.getText().toString();
                String preCancha=txv3.getText().toString();
                //Toast.makeText(getApplicationContext(),nomCancha,Toast.LENGTH_SHORT).show();

                intent.putExtra("nCancha",nomCancha);
                intent.putExtra("eCancha",estCancha);
                intent.putExtra("pCancha",preCancha);
                startActivity(intent);
            }
        });
    }

    public void llenarCanchas() {
        Conexion objCon = new Conexion(getApplicationContext());
        SQLiteDatabase miBase = objCon.getWritableDatabase();
        String sql = "SELECT * FROM canchas";
        Cursor datos = miBase.rawQuery(sql, null);
        String codigo = "", usuario = "", tipo = "";
        if (datos.moveToFirst()) {
            do {
                codigo = datos.getString(1);
                usuario = datos.getString(2);
                tipo = datos.getString(3);

                //adapter
                adapter.add(codigo + " " + usuario + " " + tipo);
            } while (datos.moveToNext());
        }
    }

    public void getCanchas(){
        db = new Conexion(this);
        Cursor cursor = db.getCanchas();
        item = new ArrayList<String>();
        String n="",e="",p="";
        int i;
        if (cursor.moveToFirst()){
            do {
                i = cursor.getInt(0);
                //n = cursor.getString(1);
                e = cursor.getString(2);
                p = cursor.getString(3);
                item.add("Cancha: "+i+" Estado: "+e+" Precio :$"+p);
            }while (cursor.moveToNext());
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

    /*@Override
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