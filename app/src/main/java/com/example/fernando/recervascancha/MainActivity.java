package com.example.fernando.recervascancha;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edtUsuario, edtClave;
    Button btnIngresar, btnRegistro;
    int tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Conexion objCon = new Conexion(getApplicationContext());
        SQLiteDatabase base = objCon.getWritableDatabase();

        edtUsuario = (EditText)findViewById(R.id.edtUsuario);
        edtClave = (EditText)findViewById(R.id.edtClave);
        btnIngresar = (Button)findViewById(R.id.btnIngresar);
        btnRegistro =(Button)findViewById(R.id.btnRegistro);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = edtUsuario.getText().toString().toUpperCase().trim();
                String clave = edtClave.getText().toString().trim();

                if(TextUtils.isEmpty(usuario)){
                    edtUsuario.setError("Digite Usuario.");
                    edtUsuario.requestFocus();
                }else if(TextUtils.isEmpty(clave)){
                    edtClave.setError("Digite Clave.");
                    edtClave.requestFocus();
                }else{
                    SqlReservas objSqlR = new SqlReservas();
                    objSqlR.login(usuario,clave,getApplicationContext());
                    int encontrado = objSqlR.getId_usuario();
                    if(encontrado == -1){
                        Toast.makeText(getApplicationContext(),"Usuario O Clave Incorrecto "+usuario+" "+clave,Toast.LENGTH_SHORT).show();
                    }
                    else{
                        tipo = objSqlR.getTipo();
                        if(tipo == 1){
                            //Intent objMenu = new Intent(getApplicationContext(),MenuAdministrador.class);
                            Intent objMenu = new Intent(getApplicationContext(),MainActivityAdministrador.class);
                            objMenu.putExtra("usuario",usuario);
                            objMenu.putExtra("tipo",tipo);
                            startActivity(objMenu);
                        }else{
                            Intent objMenu = new Intent(getApplicationContext(),MenuCliente1.class);
                            objMenu.putExtra("usuario",usuario);
                            objMenu.putExtra("tipo",tipo);
                            startActivity(objMenu);
                        }
                    }
                }
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent objRegistro = new Intent(getApplication(),CrudUsuarios.class);
                startActivity(objRegistro);

            }
        });
    }
}
