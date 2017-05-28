package com.example.fernando.recervascancha;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import static com.example.fernando.recervascancha.R.id.edtNombres;
import static com.example.fernando.recervascancha.R.id.nombreCancha;

public class ListaCanchas extends AppCompatActivity {

    ListView lstCanchas;
    Button agregarCancha;
    private ArrayAdapter Adapter_;
    private String sql = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_canchas);

        lstCanchas=(ListView)findViewById(R.id.listCanchas);
        agregarCancha=(Button)findViewById(R.id.btnNuevaCancha);
        agregarCancha=(Button)findViewById(R.id.btnNuevaCancha);

        Adapter_ = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        lstCanchas.setAdapter(Adapter_);
        Canchas();

        agregarCancha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ListaCanchas.this);
                View mVista = getLayoutInflater().inflate(R.layout.dialog_cancha,null);
                final EditText mNombre = (EditText) mVista.findViewById(R.id.nombreCancha);
                final Button mGuardar = (Button) mVista.findViewById(R.id.entrar_boton);

                mGuardar.setOnClickListener(new View.OnClickListener(){
                    String nombreC = mNombre.getText().toString().toUpperCase().trim();

                    @Override
                    public void onClick(View view){
                       if (!mNombre.getText().toString().isEmpty()) {
                           String nombre_cancha = mNombre.getText().toString().toUpperCase().trim();
                           Toast.makeText(getApplicationContext(), nombre_cancha + " guardado.", Toast.LENGTH_LONG).show();

                           DatosReservas objSqlR = new DatosReservas();
                           objSqlR.AgregarCancha(nombre_cancha,getApplicationContext());
                        }
                        else {
                           Toast.makeText(getApplicationContext(), "Ingrese un nombre.", Toast.LENGTH_LONG).show();
                       }
                    }
                });
                mBuilder.setView(mVista);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void Canchas(){
        Conexion conexion = new Conexion(getApplicationContext());
        SQLiteDatabase mibase = conexion.getWritableDatabase();
        sql = "select s.id_cancha, s.nombre, s.id_estado from canchas s";
        Cursor objC= mibase.rawQuery(sql,null);

        if (objC!=null){
            while (objC.moveToNext()){
                Adapter_.add("Id:"+objC.getString(0)+" Nombre:"+objC.getString(1)+" Estado :"+objC.getString(2));
            }
        }else {
            Adapter_.add("no hay datos");
        }
        objC.close();
        mibase.close();
        conexion.close();
    }



}
