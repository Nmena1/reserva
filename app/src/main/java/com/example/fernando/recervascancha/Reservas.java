package com.example.fernando.recervascancha;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Reservas extends AppCompatActivity {

    private String[] horas = {"10-11","11-12","12-13","13-14","14-15","16-17","17-18"};

    private Spinner spUsuarios, spHoras, spCancha;
    private TextView tvFecha;
    private DatosReservas objRes= new DatosReservas();
    private Button btnDialogo,btnGuardar,btnReservas;
    int año,mes,dia;
    static final int DIALOG_ID = 0;

    private String usuario , idCancha;
    int tipo,operacion,idReserva;
   //fecha = "0";
    //String hora="";
    static  DatePickerDialog.OnDateSetListener listenerFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);

        final DatosReservas objRes= new DatosReservas();
        Bundle datos = getIntent().getExtras();

        if (datos!=null){
            usuario = datos.getString("usuario");
            tipo = datos.getInt("tipo");
            operacion =datos.getInt("operacion");
            if (operacion==1){
                idReserva = datos.getInt("idReserva");
                idCancha = datos.getString("idCancha");
            }
        }


        spUsuarios = (Spinner)findViewById(R.id.spUsuarios);
        spHoras = (Spinner)findViewById(R.id.spHoras);
        spCancha = (Spinner)findViewById(R.id.spCancha);
        btnDialogo = (Button)findViewById(R.id.btnDiaglogo);
        btnGuardar = (Button)findViewById(R.id.btnGuardarRe);
       // btnReservas = (Button)findViewById(R.id.btnReservas);
        tvFecha = (TextView) findViewById(R.id.tvFecha);
        btnGuardar = (Button)findViewById(R.id.btnGuardarRe);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if (tipo==1){
            objRes.usuariosFill(getApplicationContext());
        }else {
            objRes.usuariosFill(getApplicationContext(),usuario);
        }

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),R.layout.spinner,objRes.getUsuarios());
        //adapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        spUsuarios.setAdapter(adapter);

        adapter = new ArrayAdapter(getApplicationContext(),R.layout.spinner,objRes.getaHoras());
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spHoras.setAdapter(adapter);

        adapter = new ArrayAdapter(getApplicationContext(),R.layout.spinner,objRes.canchas);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCancha.setAdapter(adapter);

        reiniciarCalendario();

        listenerFecha = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                if (año <= i ){
                    if (mes <= i1){
                        if (dia <= i2){
                            año = i;
                            mes = i1+1;
                            dia = i2;
                            tvFecha.setText(dia+"/"+mes+"/"+año);
                            String fechaA = tvFecha.getText().toString().trim();
                            objRes.horasFill(getApplication(),fechaA);

                            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),R.layout.spinner,objRes.getaHoras());
                            spHoras.setAdapter(adapter);
                            Toast.makeText(getApplicationContext(),"Fecha registrada",Toast.LENGTH_SHORT).show();
                        }
                           else{
                                reiniciarCalendario();
                            datePicker.updateDate(año,mes,dia);
                            Toast.makeText(getApplicationContext(),"Fecha invalida",Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        reiniciarCalendario();
                        datePicker.updateDate(año,mes,dia);
                        Toast.makeText(getApplicationContext(),"Fecha invalida",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    reiniciarCalendario();
                    datePicker.updateDate(año,mes,dia);
                    Toast.makeText(getApplicationContext(),"Fecha invalida",Toast.LENGTH_LONG).show();
                }
            }
        };

        btnDialogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_ID);

            }
        });



        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fecha = tvFecha.getText().toString();
                String usuario = spUsuarios.getSelectedItem().toString().trim();
                String cancha = spCancha.getSelectedItem().toString().trim();
                String hora = spHoras.getSelectedItem().toString().trim();


                if (fecha.isEmpty()) {
                    tvFecha.setError("Se necesita ingresar fecha");
                    tvFecha.requestFocus();
                }else if (hora.equals("No Hay Horarios")|| hora.equals("Seleccione Un Horario")){
                        Toast.makeText(getApplicationContext(),"se necesita Seleccionar Horario" , Toast.LENGTH_LONG).show();
                    } else {

                    SqlReservas objusu = new SqlReservas();
                    objusu.buscarUsuario(usuario,getApplication());
                    objRes.buscarHora(hora,getApplication());
                    //Toast.makeText(getApplicationContext(),"Hoarario Seleccionado"+hora , Toast.LENGTH_LONG).show();
                    if (objRes.getIdHoras()==-1){
                        Toast.makeText(getApplicationContext(),"Hoarario No Existe" , Toast.LENGTH_LONG).show();

                    }else if (objusu.getId_usuario()!=-1){
                    objRes.insertar(objusu.getId_usuario(),cancha,fecha,objRes.getIdHoras(),getApplicationContext());
                    Toast.makeText(getApplicationContext(),"Se Realizo La Reserva" , Toast.LENGTH_LONG).show();
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(),"No Se Realizo" , Toast.LENGTH_LONG).show();
                    }

                }

            }
        });



    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id){
            case 0:
                return new DatePickerDialog(this,listenerFecha,año,mes,dia);

        }
    return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);

    }

    private void reiniciarCalendario(){
        Calendar calendario = Calendar.getInstance();
        año =calendario.get(Calendar.YEAR);
        mes =calendario.get(Calendar.MONTH);
        dia = calendario.get(Calendar.DAY_OF_MONTH);

    }
}
