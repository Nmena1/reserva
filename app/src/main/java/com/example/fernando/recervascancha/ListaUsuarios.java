package com.example.fernando.recervascancha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class ListaUsuarios extends AppCompatActivity {

    ListView lstUsuarios;
    private String sql = "";
    private AdaptadorListaUsu adapter;
  //  ArrayList nombre = new ArrayList();
   // Button btnAtras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);
        SqlReservas objR = new SqlReservas();
    /*    nombre.add("fernando");
        nombre.add("otro");
*/
        lstUsuarios = (ListView)findViewById(R.id.lstUsuarios);
       // btnAtras = (Button)findViewById(R.id.btnAtras);



        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        objR.listado(getApplicationContext());

        adapter = new AdaptadorListaUsu(getApplicationContext(),objR.getArrayid()
                ,objR.getArrayUsuario(),objR.getArrayNombre(),objR.getArrayApellido());

        lstUsuarios.setAdapter(adapter);
        //listado();

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        lstUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tvUsuario = (TextView)view.findViewById(R.id.tvUsuarioC);
                String strUsuario = tvUsuario.getText().toString();
                Intent objIntent = new Intent(getApplicationContext(),CrudUsuarios.class);
                objIntent.putExtra("usuario",strUsuario);
                objIntent.putExtra("tipo",3);
                objIntent.putExtra("accion",1);
                startActivity(objIntent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            finish();
            overridePendingTransition(R.anim.right_out,R.anim.right_in);
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_out,R.anim.right_in);
    }

/*
    public void listado(){
        SqlReservas objR = new SqlReservas();
        Cursor objC= objR.listado(getApplication());
        if (objC!=null){
            while (objC.moveToNext()){
                adapter.add("Id:"+objC.getString(0)+" Usuario:"+objC.getString(1)+" Tipo :"+objC.getString(2));
            }

        }else {
            adapter.add("no hay datos");
        }
        objC.close();


    }

*/
}
