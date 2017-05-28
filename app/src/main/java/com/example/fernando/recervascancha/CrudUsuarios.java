package com.example.fernando.recervascancha;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class CrudUsuarios extends AppCompatActivity {

    TextView tvTipo , tvEstado;
    EditText edtUsuario, edtClave, edtNombres, edtApellidos, edtTelefono, edtCorreo, edtComentarios;
    RadioButton rdbAdministrador, rdbCliente , rdbActivo, rdbInactivo;
    Button btnBuscar, btnCrear, btnActualizar, btnRegresar;
    int tipoLogeado, accion;
    String usuarioLogeado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_usuarios);

        Bundle datos = getIntent().getExtras();

        if (datos!=null) {
            usuarioLogeado = datos.getString("usuario");
            tipoLogeado = datos.getInt("tipo");
        }else {
            tipoLogeado = 0;
        }

        edtUsuario = (EditText)findViewById(R.id.edtUsuario);
        edtClave = (EditText)findViewById(R.id.edtClave);
        edtNombres = (EditText)findViewById(R.id.edtNombres);
        edtApellidos = (EditText)findViewById(R.id.edtApellidos);
        edtTelefono = (EditText)findViewById(R.id.edtTelefono);
        edtCorreo = (EditText)findViewById(R.id.edtCorreo);
        //edtComentarios = (EditText)findViewById(R.id.edtComentarios);
        rdbAdministrador = (RadioButton)findViewById(R.id.rdbAdministrador);
        rdbCliente = (RadioButton)findViewById(R.id.rdbCliente);
        rdbActivo= (RadioButton)findViewById(R.id.rdbActivo);
        rdbInactivo = (RadioButton)findViewById(R.id.rdbInactivo);
        btnBuscar = (Button)findViewById(R.id.btnBuscar);
        btnCrear = (Button)findViewById(R.id.btnCrear);
        btnActualizar = (Button)findViewById(R.id.btnActualizar);
        tvTipo = (TextView)findViewById(R.id.tvTipo);
        tvEstado = (TextView)findViewById(R.id.tvEstado);

        // btnRegresar = (Button)findViewById(R.id.btnRegresar);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        if (tipoLogeado == 0){
            tvTipo.setVisibility(View.INVISIBLE);
            btnActualizar.setVisibility(View.INVISIBLE);
            btnBuscar.setVisibility(View.INVISIBLE);
            rdbCliente.setVisibility(View.INVISIBLE);
            rdbAdministrador.setVisibility(View.INVISIBLE);
            rdbActivo.setVisibility(View.INVISIBLE);
            rdbInactivo.setVisibility(View.INVISIBLE);
            tvEstado.setVisibility(View.INVISIBLE);
        }
        else if(tipoLogeado == 2){
            btnBuscar.setVisibility(View.GONE);
            btnCrear.setVisibility(View.GONE);
            edtUsuario.setEnabled(false);
            rdbAdministrador.setVisibility(View.INVISIBLE);
            rdbCliente.setVisibility(View.INVISIBLE);
            rdbActivo.setVisibility(View.INVISIBLE);
            rdbInactivo.setVisibility(View.INVISIBLE);
            tvEstado.setVisibility(View.INVISIBLE);
            rdbActivo.setVisibility(View.INVISIBLE);
            rdbInactivo.setVisibility(View.INVISIBLE);
            tvTipo.setVisibility(View.INVISIBLE);

            //miBotonB.setVisibility(View.VISIBLE);
            //Toast.makeText(getApplicationContext(),"ADMINISTRADOR",Toast.LENGTH_LONG).show();
            SqlReservas objSqlR = new SqlReservas();
            objSqlR.buscarUsuario(usuarioLogeado,getApplicationContext());
            int encontrado = objSqlR.getId_usuario();
            edtUsuario.setText(objSqlR.getUsuario());
            edtClave.setText(objSqlR.getClave());

            if(objSqlR.getTipo() == 1){
                rdbAdministrador.setChecked(true);
                rdbCliente.setChecked(false);
            }else{
                rdbAdministrador.setChecked(false);
                rdbCliente.setChecked(true);
            }

            if(objSqlR.getEstado().equals("ACTIVO")){
                rdbActivo.setChecked(true);
                rdbInactivo.setChecked(false);
            }else{
                rdbActivo.setChecked(false);
                rdbInactivo.setChecked(true);
            }


            edtNombres.setText(objSqlR.getNombres());
            edtApellidos.setText(objSqlR.getApellidos());
            edtTelefono.setText(objSqlR.getTelefono());
            edtCorreo.setText(objSqlR.getCorreo());
        }else if (tipoLogeado==3){
            //miBotonB.setVisibility(View.VISIBLE);
            //Toast.makeText(getApplicationContext(),"ADMINISTRADOR",Toast.LENGTH_LONG).show();
            SqlReservas objSqlR = new SqlReservas();
            objSqlR.buscarUsuario(usuarioLogeado,getApplicationContext());
            int encontrado = objSqlR.getId_usuario();

            if(objSqlR.getTipo() == 1){
                rdbAdministrador.setChecked(true);
                rdbCliente.setChecked(false);
            }else{
                rdbAdministrador.setChecked(false);
                rdbCliente.setChecked(true);
            }

            edtUsuario.setText(objSqlR.getUsuario());
            edtClave.setText(objSqlR.getClave());

            if(objSqlR.getEstado().equals("ACTIVO")){
                rdbActivo.setChecked(true);
                rdbInactivo.setChecked(false);
            }else{
                rdbActivo.setChecked(false);
                rdbInactivo.setChecked(true);
            }
            edtNombres.setText(objSqlR.getNombres());
            edtApellidos.setText(objSqlR.getApellidos());
            edtTelefono.setText(objSqlR.getTelefono());
            edtCorreo.setText(objSqlR.getCorreo());


        }

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = edtUsuario.getText().toString().toUpperCase().trim();
                if(TextUtils.isEmpty(usuario)){
                    edtUsuario.setError("Digite un usuario");
                    edtUsuario.requestFocus();
                }else{
                    SqlReservas objSqlR = new SqlReservas();
                    objSqlR.buscarUsuario(usuario,getApplicationContext());
                    int encontrado = objSqlR.getId_usuario();
                    if(encontrado == -1){
                        Toast.makeText(getApplicationContext(),"No existe el usuario",Toast.LENGTH_SHORT).show();
                    }else{
                        edtUsuario.setText(objSqlR.getUsuario());
                        edtClave.setText(objSqlR.getClave());
                        if(objSqlR.getTipo() == 1){
                            rdbAdministrador.setChecked(true);
                            rdbCliente.setChecked(false);
                        }else{
                            rdbAdministrador.setChecked(false);
                            rdbCliente.setChecked(true);
                        }

                        edtNombres.setText(objSqlR.getNombres());
                        edtApellidos.setText(objSqlR.getApellidos());
                        edtTelefono.setText(objSqlR.getTelefono());
                        edtCorreo.setText(objSqlR.getCorreo());

                        if(objSqlR.getEstado().equals("ACTIVO")){
                            rdbActivo.setChecked(true);
                            rdbInactivo.setChecked(false);
                        }else{
                            rdbActivo.setChecked(false);
                            rdbInactivo.setChecked(true);
                        }

                    }
                }
            }
        });


        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = edtUsuario.getText().toString().toUpperCase().trim();
                String clave = edtClave.getText().toString().trim();
                int tipo = rdbAdministrador.isChecked() ? 1 : 2;
                String nombres = edtNombres.getText().toString().toUpperCase().trim();
                String apellidos = edtApellidos.getText().toString().toUpperCase().trim();
                String telefono = edtTelefono.getText().toString().toUpperCase().trim();
                String correo = edtCorreo.getText().toString().trim();
                String estado = "ACTIVO" ;
                //String comentarios = edtComentarios.getText().toString().trim();
                int accion = 1;

                if(TextUtils.isEmpty(usuario)){
                    edtUsuario.setError("Digite un usuario");
                    edtUsuario.requestFocus();
                }else if(TextUtils.isEmpty(clave)){
                    edtClave.setError("Digite una clave,");
                    edtClave.requestFocus();
                }else if(TextUtils.isEmpty(nombres)){
                    edtNombres.setError("Digite los nombres,");
                    edtNombres.requestFocus();
                }else if(TextUtils.isEmpty(apellidos)){
                    edtApellidos.setError("Digite los apellidos,");
                    edtApellidos.requestFocus();
                }else if(TextUtils.isEmpty(telefono)){
                    edtTelefono.setError("Digite un telefono,");
                    edtTelefono.requestFocus();
                }else if(TextUtils.isEmpty(correo)) {
                    edtCorreo.setError("Digite un correo.");
                    edtCorreo.requestFocus();
                }else if (!edtCorreo.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+")){
                    edtCorreo.setError("Correo no valido");
                    edtCorreo.requestFocus();
                }
                else{
                    //Consulta sql
                    /* VALIDAR SI EL USUARIO YA ESTA CREADO O NO*/
                    SqlReservas objSqlR = new SqlReservas();
                    objSqlR.buscarUsuario(usuario,getApplicationContext());
                    int encontrado = objSqlR.getId_usuario();
                    if(encontrado >= 1){
                        Toast.makeText(getApplicationContext(),"El usuario ya existe.",Toast.LENGTH_LONG).show();
                    }else{

                        objSqlR.insertarEditar(usuario,clave,tipo,nombres,apellidos,telefono,correo,estado,accion,getApplicationContext());
                        objSqlR.buscarUsuario(usuario,getApplicationContext());

                        /*if(encontrado == -1){
                            Toast.makeText(getApplicationContext(),"El usuario no se ha creado.",Toast.LENGTH_LONG).show();
                        }
                        else{*/
                        if (tipoLogeado==0){
                            finish();
                        }

                            edtUsuario.setText(objSqlR.getUsuario());
                            edtClave.setText(objSqlR.getClave());
                            if(objSqlR.getTipo() == 1){
                                rdbAdministrador.setChecked(true);
                                rdbCliente.setChecked(false);
                            }else{
                                rdbAdministrador.setChecked(false);
                                rdbCliente.setChecked(true);
                            }

                        if(objSqlR.getEstado().equals("ACTIVO")){
                            rdbActivo.setChecked(true);
                            rdbInactivo.setChecked(false);
                            }else{
                            rdbActivo.setChecked(false);
                            rdbInactivo.setChecked(true);
                            }

                            edtNombres.setText(objSqlR.getNombres());
                            edtApellidos.setText(objSqlR.getApellidos());
                            edtTelefono.setText(objSqlR.getTelefono());
                            edtCorreo.setText(objSqlR.getCorreo());
                            //edtComentarios.setText(objSqlR.getComentarios());
                            Toast.makeText(getApplicationContext(),"Usuario creado con exito!!!.",Toast.LENGTH_SHORT).show();
                        //}
                    }
                }
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = edtUsuario.getText().toString().toUpperCase().trim();
                String clave = edtClave.getText().toString().trim();
                int tipo;
                String nombres = edtNombres.getText().toString().toUpperCase().trim();
                String apellidos = edtApellidos.getText().toString().toUpperCase().trim();
                String telefono = edtTelefono.getText().toString().toUpperCase().trim();
                String correo = edtCorreo.getText().toString().trim();
                String estado;
                //String comentarios = edtComentarios.getText().toString().trim();
                int accion = 2;


                if (TextUtils.isEmpty(usuario)) {
                    edtUsuario.setError("Digite un usuario");
                    edtUsuario.requestFocus();
                } else if (TextUtils.isEmpty(clave)) {
                    edtClave.setError("Digite una clave,");
                    edtClave.requestFocus();
                } else if (TextUtils.isEmpty(nombres)) {
                    edtNombres.setError("Digite los nombres,");
                    edtNombres.requestFocus();
                } else if (TextUtils.isEmpty(apellidos)) {
                    edtApellidos.setError("Digite los apellidos,");
                    edtApellidos.requestFocus();
                } else if (TextUtils.isEmpty(telefono)) {
                    edtTelefono.setError("Digite un telefono,");
                    edtTelefono.requestFocus();
                } else if (TextUtils.isEmpty(correo)) {
                    edtCorreo.setError("Digite un correo.");
                    edtCorreo.requestFocus();
                } else if (!edtCorreo.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+")){
                    edtCorreo.setError("Correo no valido");
                edtCorreo.requestFocus();
            }   else{
                    //Consulta sql
                    SqlReservas objSqlR = new SqlReservas();
                    objSqlR.buscarUsuario(usuario,getApplicationContext());
                    int encontrado = objSqlR.getId_usuario();
                    if(encontrado == -1){
                        Toast.makeText(getApplicationContext(),"Usuario no existe.",Toast.LENGTH_LONG).show();
                    }else{

                        if (rdbAdministrador.isChecked()){
                            tipo=1;
                        }else {
                            tipo=2;
                        }

                        if (rdbActivo.isChecked()){
                            estado="ACTIVO";
                        }else {
                            estado="INACTIVO";
                        }

                        objSqlR.insertarEditar(usuario,clave,tipo,nombres,apellidos,telefono,correo,estado,accion,getApplicationContext());
                        edtUsuario.setText(objSqlR.getUsuario());
                        edtClave.setText(objSqlR.getClave());

                        if(objSqlR.getTipo() == 1){
                            rdbAdministrador.setChecked(true);
                            rdbCliente.setChecked(false);
                        }else{
                            rdbAdministrador.setChecked(false);
                            rdbCliente.setChecked(true);
                        }

                        if(objSqlR.getEstado().equals("ACTIVO")){
                            rdbActivo.setChecked(true);
                            rdbInactivo.setChecked(false);
                        }else{
                            rdbActivo.setChecked(false);
                            rdbInactivo.setChecked(true);
                        }

                        edtNombres.setText(objSqlR.getNombres());
                        edtApellidos.setText(objSqlR.getApellidos());
                        edtTelefono.setText(objSqlR.getTelefono());
                        edtCorreo.setText(objSqlR.getCorreo());
                        //edtComentarios.setText(objSqlR.getComentarios());
                        Toast.makeText(getApplicationContext(),"Usuario actualizado con exito!!!.",Toast.LENGTH_LONG).show();
                    }
                }
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

    public  void cancelar(View v){
        Intent objRetorno = new Intent();
        setResult(RESULT_CANCELED,objRetorno);
        finish();
    }
}
