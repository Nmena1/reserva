package com.example.fernando.recervascancha;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Mario on 14/05/2017.
 */

public class SqlReservas {
    private int id_usuario;
    private String usuario;
    private String clave;
    private int tipo;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String correo;
    private String comentarios;
    private String estado;
    private ArrayList arrayid = new ArrayList();
    private ArrayList arrayUsuario = new ArrayList();
    private ArrayList arrayNombre = new ArrayList();
    private ArrayList arrayApellido = new ArrayList();

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ArrayList getArrayid() {
        return arrayid;
    }

    public ArrayList getArrayUsuario() {
        return arrayUsuario;
    }

    public ArrayList getArrayNombre() {
        return arrayNombre;
    }

    public ArrayList getArrayApellido() {
        return arrayApellido;
    }

    public void setArrayid(ArrayList arrayid) {
        this.arrayid = arrayid;
    }

    public void setArrayUsuario(ArrayList arrayUsuario) {
        this.arrayUsuario = arrayUsuario;
    }

    public void setArrayNombre(ArrayList arrayNombre) {
        this.arrayNombre = arrayNombre;
    }

    public void setArrayApellido(ArrayList arrayApellido) {
        this.arrayApellido = arrayApellido;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public void login(String pUsuario, String pClave, Context contexto){
        setUsuario(pUsuario);
        setClave(pClave);
        Conexion objCon = new Conexion(contexto);
        SQLiteDatabase miBase = objCon.getWritableDatabase();

        Cursor datos = miBase.rawQuery("select * from usuarios where estado = 'ACTIVO' and usuario = '"+usuario+"' and clave = '"+clave+"'",null);
        if(datos.moveToFirst()){
            //En caso encuentra usuario y contraseña asigno a la variable codigo de esta clase
            //el valor devuelto de la cosulta del campo codigo
            setId_usuario(datos.getInt(0));
            setNombres(datos.getString(4).toString());
            setTipo(datos.getInt(3));
        }
        else{
            //En caso no encuentra valores la consulta asigno -1 a la varible codigo de esta clase
            //indicando que no encontro resultado la consulta
            setId_usuario(-1);
            miBase.close();
        }
    }

    public void buscarUsuario(String pUsuario, Context contexto){
        setUsuario(pUsuario);
        Conexion objCon = new Conexion(contexto);
        SQLiteDatabase miBase = objCon.getWritableDatabase();

        Cursor datos = miBase.rawQuery("select * from usuarios where usuario = '"+usuario+"'",null);
        if(datos.moveToFirst()){
            //En caso encuentra usuario asigno a la variable codigo de esta clase
            //el valor devuelto de la cosulta del campo codigo
            setId_usuario(datos.getInt(0));
            /*setUsuario(datos.getString(1).toString());*/
            setClave(datos.getString(2).toString());
            setTipo(datos.getInt(3));
            setNombres(datos.getString(4).toString());
            setApellidos(datos.getString(5).toString());
            setTelefono(datos.getString(6).toString());
            setCorreo(datos.getString(7).toString());
            setEstado(datos.getString(8).toString());
            //setComentarios(datos.getString(8).toString());
        }
        else{
            //En caso no encuentra valores la consulta asigno -1 a la varible codigo de esta clase
            //indicando que no encontro resultado la consulta
            setId_usuario(-1);
        }
        //al terminar metodo se deben cerrar las conecciones
        //datos.close();
        miBase.close();
        objCon.close();
    }

    public void insertarEditar(String pUsuario, String pClave, int pTipo, String pNombres,  String pApellidos, String pTelefono, String pCorreo,String pEstado, int accion, Context contexto){
        setUsuario(pUsuario);
        setClave(pClave);
        setTipo(pTipo);
        setNombres(pNombres);
        setApellidos(pApellidos);
        setTelefono(pTelefono);
        setCorreo(pCorreo);
        setEstado(pEstado);
        String consultaSql;
        if(accion == 1){
            consultaSql = "insert into usuarios(usuario,clave,tipo,nombres,apellidos,telefono,correo,estado) " +
                    "values('"+usuario+"','"+clave+"',"+tipo+",'"+nombres+"','"+apellidos+"','"+telefono+"','"+correo+"','"+estado+"')";
        }
        else{
            consultaSql = "update usuarios set usuario = '"+usuario+"',clave = '"+clave+"', tipo = "+tipo+",nombres = '"+nombres+"', apellidos = '"+apellidos+"', " +
                    "telefono = '"+telefono+"', correo = '"+correo+"', estado ='"+estado+"'  where usuario = '"+usuario+"'";
        }

        Conexion objCon = new Conexion(contexto);
        SQLiteDatabase miBase = objCon.getWritableDatabase();

        miBase.execSQL(consultaSql);
        //al terminar metodo se deben cerrar las conecciones
        //datos.close();
        miBase.close();
        objCon.close();
    }

    /*
    public  void eliminar(String pUsuario, Context contexto){
        setUsuario(pUsuario);
        Conexion objCn = new Conexion(contexto);
        SQLiteDatabase miBase = objCn.getWritableDatabase();

        miBase.execSQL("delete from usuarios where usuario = '"+usuario+"'");
    }*/

    public void listado(Context contexto){
        Conexion objCn = new Conexion(contexto);
        SQLiteDatabase miBase = objCn.getWritableDatabase();

        Cursor listado = miBase.rawQuery("select s.id_usuario, s.usuario,s.nombres,s.apellidos from usuarios s",null);
        if (listado!=null) {
            while (listado.moveToNext()) {
                arrayid.add(listado.getString(0));
                arrayUsuario.add(listado.getString(1));
                arrayNombre.add(listado.getString(2));
                arrayApellido.add(listado.getString(3));
            }
            }
        miBase.close();
        objCn.close();

    }

    public void llenar(){
        //DBSQlite j = new DBSQlite(this,"CanchaUtec",null,1);
        //SQLiteDatabase jj = j.getWritableDatabase();
        //String q ="SELECT * FROM cliente";
        //Cursor r = jj.rawQuery(q,null);
        //String codigo = "",nombre = "",tipo ="" ;
        /*if(r.moveToFirst()){
            do {
                codigo = r.getString(0);
                nombre = r.getString(1);
                tipo =r.getString(2);
                adaptador.add(codigo+"          "+nombre+"          "+tipo);
            }while (r.moveToNext());
        }*/
    }
}
