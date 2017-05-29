package com.example.fernando.recervascancha;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;

import javax.sql.ConnectionPoolDataSource;

/**
 * Created by Jnn on 27/05/2017.
 */

public class AgregarCancha extends AppCompatActivity {

    EditText nombreC, precioC;
    String nombre_, estado_, precio_;
    Button Guardar;
    Conexion db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insertar_canchas);

        nombreC = (EditText)findViewById(R.id.editText_Nombre);
        precioC = (EditText)findViewById(R.id.editText_Precio);
        Guardar = (Button) findViewById(R.id.button_Guardar);

        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AgregarDatos();
            }
        });


    }
    private void AgregarDatos(){

        nombre_ = nombreC.getText().toString();
        if(!nombre_.isEmpty()) {
            precio_ = precioC.getText().toString();
            if(!precio_.isEmpty()) {
                estado_ = "Disponible";
                db = new Conexion(this);
                db.AgregarCancha(nombre_, estado_, precio_);
                //db.close();
                Intent i = new Intent(AgregarCancha.this, ListaCanchas.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(),"Se guardo con exito",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(),"Ingrese el precio para esta cancha",Toast.LENGTH_LONG).show();
                precioC.requestFocus();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"Ingrese nombre",Toast.LENGTH_SHORT).show();
            nombreC.requestFocus();
        }
    }
}
