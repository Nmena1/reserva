package com.example.fernando.recervascancha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class EditCancha extends AppCompatActivity {

    EditText edtNombre, edtPrecio;
    Switch swtch;
    ImageButton Editar, Eliminar;
    public String nuevoNombre="", nuevoEstado="", nuevoPrecio;
    Conexion bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cancha);

        edtNombre= (EditText) findViewById(R.id.editText_Nombre);
        edtPrecio= (EditText) findViewById(R.id.editText_Precio);
        Editar= (ImageButton) findViewById(R.id.imgEditar);
        Eliminar= (ImageButton) findViewById(R.id.imgEliminar);
        swtch = (Switch)findViewById(R.id.switch1);

        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("nCancha")!= null)
        {
            String nCancha = bundle.getString("nCancha");
            String eCancha = bundle.getString("eCancha");
            String pCancha = bundle.getString("pCancha");
            final int accion = 1;

            edtNombre.setText(nCancha);
            edtPrecio.setText(pCancha);
            if(eCancha.equals("Disponible")){
                swtch.setChecked(true);
            }
            else if(eCancha.equals("Reservado")){
                swtch.setChecked(false);
            }
            else {
                swtch.setChecked(true);
            }

            swtch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(swtch.isChecked()){
                        nuevoEstado = "Disponible";
                    }
                    if(!swtch.isChecked()){
                        nuevoEstado = "Reservado";
                    }
                }
            });

            Editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nuevoNombre = edtNombre.getText().toString();
                    if(!nuevoNombre.isEmpty()){
                        nuevoPrecio = edtPrecio.getText().toString();
                        if(!nuevoPrecio.isEmpty()){
                            SqlReservas objSqlR = new SqlReservas();
                            objSqlR.buscarCancha(nuevoNombre,getApplicationContext());
                            int encontrado = objSqlR.getId_cancha();
                            if(encontrado == -1){
                                Toast.makeText(getApplicationContext(),"Cancha no existe.",Toast.LENGTH_SHORT).show();
                            }else{
                                objSqlR.EditarCancha(nuevoNombre,nuevoEstado,nuevoPrecio,accion,getApplicationContext());
                                edtNombre.setText(objSqlR.getNombre_cancha());
                                edtPrecio.setText(objSqlR.getPrecio_cancha());
                               if(objSqlR.getEstado_cancha().equals("Disponible")){
                                    swtch.setChecked(true);
                                }
                                else{
                                    swtch.setChecked(false);
                                }
                                Intent intent = new Intent(EditCancha.this,ListaCanchas.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(),"Excelente",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Ingrese precio nuevo",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Ingrese nombre nuevo",Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }
    }
}
