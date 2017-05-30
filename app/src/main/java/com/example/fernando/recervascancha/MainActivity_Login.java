package com.example.fernando.recervascancha;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fernando.recervascancha.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity_Login extends AppCompatActivity {

    EditText edtUsuario, edtClave;
    Button btnIngresar, btnRegistro;
    int tipo;
    Context contexto;
    private ProgressDialog pDialog;
    TextView res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__login);
        Conexion objCon = new Conexion(getApplicationContext());
        SQLiteDatabase base = objCon.getWritableDatabase();

        edtUsuario = (EditText) findViewById(R.id.edtUsuario);
        edtClave = (EditText) findViewById(R.id.edtClave);
        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        btnRegistro = (Button) findViewById(R.id.btnRegistro);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new OperacionSoap().execute(
                        edtUsuario.getText().toString().trim(),
                        edtClave.getText().toString().trim()
                );
            }
        });
    }
    private  class OperacionSoap extends AsyncTask<String, String,String>{

            static final String NAMESPACE = "http://tempuri.org/";
            static final String METHODNAME = "WSConsultasCanchas";
            static final String URL = "http://192.168.0.18:8085/WSConsultasCanchas.asmx";//direccion donde esta nuestro ws
            static final String SOAP_ACTION = NAMESPACE + METHODNAME;

            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(MainActivity_Login.this);
                pDialog.setMessage("Cargando...");
                pDialog.setMax(100);
                pDialog.show();
            }


        @Override
        protected String doInBackground(String... params) {
            String resultado="";
//Aca enviamos los valores para el ws
            SoapObject request = new SoapObject(NAMESPACE,METHODNAME);
            request.addProperty("edtUsuario",params[0]);
            request.addProperty("edtClave",params[1]);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;//esta el true por que nuestro ws esta creado en .NET de no ser asi debe de estar en false
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(URL);//realizamos el envio del request
            Log.i("estess",request.toString());

            try{
                transporte.call(SOAP_ACTION,envelope);
                SoapPrimitive response=(SoapPrimitive)envelope.getResponse();
                resultado=response.toString();
                Log.i("resultado..",resultado.toString());
            }catch (Exception e){

                Toast.makeText(contexto,"Error al conectar con el server",Toast.LENGTH_SHORT).show();
            }
            return resultado;
        }
    }
    //@Override
    private void onPostExecute(String resultado) {
        pDialog.dismiss();
        res.setText(resultado);
    }


    public void consVneder(View view){
        Intent i = new Intent(this,CrudUsuarios.class);
        startActivity(i);
        finish();
    }


/*
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
  -                  SqlReservas objSqlR = new SqlReservas();
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
                            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

                        }else{
                            Intent objMenu = new Intent(getApplicationContext(),MenuCliente1.class);
                            objMenu.putExtra("usuario",usuario);
                            objMenu.putExtra("tipo",tipo);
                            startActivity(objMenu);
                            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
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
                overridePendingTransition(R.anim.left_out,R.anim.left_in);

            }
        });
    }
    */
}
