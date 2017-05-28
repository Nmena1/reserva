package com.example.fernando.recervascancha;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListadoUsuarios extends AppCompatActivity {

    ListView lv;
    ArrayAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_usuarios);

        lv = (ListView)findViewById(R.id.listView);
        adaptador= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        lv.setAdapter(adaptador);

        SqlReservas objSqlR = new SqlReservas();
        objSqlR.listado(getApplicationContext());

        llenar();
        /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int posicion, long l) {
                Intent f = new Intent(getApplicationContext(), Modificar.class);
                startActivity(f);
            }
        });*/

    }

    public void llenar() {
        Conexion objCon = new Conexion(getApplicationContext());
        SQLiteDatabase miBase = objCon.getWritableDatabase();
        String sql = "SELECT * FROM usuarios";
        Cursor datos = miBase.rawQuery(sql, null);
        String codigo = "", usuario = "", tipo = "";
        if (datos.moveToFirst()) {
            do {
                codigo = datos.getString(0);
                usuario = datos.getString(1);
                tipo = datos.getString(3);

                adaptador.add(codigo + "          " + usuario + "          " + tipo);

            } while (datos.moveToNext());
        }
    }
}
