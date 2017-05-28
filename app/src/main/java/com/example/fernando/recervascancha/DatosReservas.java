package com.example.fernando.recervascancha;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Fernando on 14/05/2017.
 */
public class DatosReservas {

    private int idUsuario;
    private String cancha;
    private String fecha;
    private String horas;
    private String sql;
    private int idHoras;
    private ArrayList usuarios = new ArrayList();
    private ArrayList aHoras = new ArrayList();

    private ArrayList arrayid = new ArrayList();
    private ArrayList arrayUsuario = new ArrayList();
    private ArrayList arrayCancha = new ArrayList();
    private ArrayList arrayFecha = new ArrayList();
    private ArrayList arrayHorario = new ArrayList();
    private ArrayList arrayEstado = new ArrayList();

    String[] canchas = {"Gambeta Navara","COOPEFA","Gambeta los Proceres"};

    public String[] getCanchas() {
        return canchas;
    }

    public void setCanchas(String[] canchas) {
        this.canchas = canchas;
    }

    public ArrayList getArrayEstado() {
        return arrayEstado;
    }

    public void setArrayEstado(ArrayList arrayEstado) {
        this.arrayEstado = arrayEstado;
    }

    public ArrayList getArrayid() {
        return arrayid;
    }

    public ArrayList getArrayUsuario() {
        return arrayUsuario;
    }

    public ArrayList getArrayCancha() {
        return arrayCancha;
    }

    public ArrayList getArrayFecha() {
        return arrayFecha;
    }

    public ArrayList getArrayHorario() {
        return arrayHorario;
    }

    public void setArrayid(ArrayList arrayid) {
        this.arrayid = arrayid;
    }

    public void setArrayUsuario(ArrayList arrayUsuario) {
        this.arrayUsuario = arrayUsuario;
    }

    public void setArrayCancha(ArrayList arrayCancha) {
        this.arrayCancha = arrayCancha;
    }

    public void setArrayFecha(ArrayList arrayFecha) {
        this.arrayFecha = arrayFecha;
    }

    public void setArrayHorario(ArrayList arrayHorario) {
        this.arrayHorario = arrayHorario;
    }

    public DatosReservas(){
        aHoras.add("No Hay Horarios");

    }



    public int getIdHoras() {
        return idHoras;
    }

    public void setIdHoras(int idHoras) {
        this.idHoras = idHoras;
    }

    public ArrayList getaHoras() {
        return aHoras;
    }

    public void setaHoras(ArrayList aHoras) {
        this.aHoras = aHoras;
    }


    public ArrayList getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList usuarios) {
        this.usuarios = usuarios;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getCancha() {
        return cancha;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHoras() {
        return horas;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setCancha(String cancha) {
        this.cancha = cancha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public void usuariosFill(Context context){
        Conexion objConexion = new Conexion(context);
        SQLiteDatabase miBase = objConexion.getWritableDatabase();
        sql="select * from usuarios ";

        Cursor objCursor = miBase.rawQuery(sql,null);

        if (objCursor!=null){
            while(objCursor.moveToNext()){
                usuarios.add(objCursor.getString(1));
            }
        }
        else {
            usuarios.add("no hay Usuarios");
        }
        objCursor.close();
        miBase.close();
        objConexion.close();
    }

    public void usuariosFill(Context context,String usuario){
        Conexion objConexion = new Conexion(context);
        SQLiteDatabase miBase = objConexion.getWritableDatabase();
        sql="select * from usuarios where usuario='"+usuario+"'";

        Cursor objCursor = miBase.rawQuery(sql,null);

        if (objCursor!=null){
            while(objCursor.moveToNext()){
                usuarios.add(objCursor.getString(1));
            }
        }
        else {
            usuarios.add("no hay Usuarios");
        }
        objCursor.close();
        miBase.close();
        objConexion.close();
    }

    public Cursor usuariosFillC(Context context){
        Conexion objConexion = new Conexion(context);
        SQLiteDatabase miBase = objConexion.getWritableDatabase();
        sql="select * from usuarios ";
        Cursor objCursor = miBase.rawQuery(sql,null);
        miBase.close();
        return objCursor;
    }

    public void horasFill(Context context,String fecha){
        aHoras.clear();
        aHoras.add("Seleccione Un Horario");
        Conexion objConexion = new Conexion(context);
        SQLiteDatabase miBase = objConexion.getWritableDatabase();
        sql="select * from tiempo_reservas where id_tiempo NOT IN(select id_tiempo from reservas where estado !='ANULADA' and fecha='"+fecha+"')";

        Cursor objCursor = miBase.rawQuery(sql,null);

        if (objCursor!=null){
            while(objCursor.moveToNext()){
               aHoras.add(objCursor.getString(1));
            }
        }
        else {
            aHoras.add("No Hay Horarios");
        }
        objCursor.close();
        miBase.close();
        objConexion.close();
    }

    public void buscarHora(String pHora, Context contexto){
       setHoras(pHora);
        Conexion objCon = new Conexion(contexto);
        SQLiteDatabase miBase = objCon.getWritableDatabase();

        Cursor datos = miBase.rawQuery("select * from tiempo_reservas where horas ='"+horas+"'",null);
        if(datos.moveToFirst()){
            setIdHoras(datos.getInt(0));
        }
        else{
            setIdHoras(-1);
        }
        //al terminar metodo se deben cerrar las conecciones
        datos.close();
        miBase.close();
        objCon.close();
    }



    public void insertar (int pUsuario, String pCancha, String pFecha, int pIdTiempo, Context context){
        Conexion objConexion = new Conexion(context);
        SQLiteDatabase miBase = objConexion.getWritableDatabase();
        sql="insert into reservas (id_usuario,cancha,fecha,id_tiempo,estado) values ("+pUsuario+",'"+pCancha+"','"+pFecha+"',"+pIdTiempo+",'ACTIVA') ";
        miBase.execSQL(sql);
        miBase.close();
        objConexion.close();
    }

    public void listado(Context contexto){
        Conexion objCn = new Conexion(contexto);
        SQLiteDatabase miBase = objCn.getWritableDatabase();
        limpiarArrays();

        Cursor listado = miBase.rawQuery("select r.id_reserva,usu.usuario,r.cancha,r.fecha,tr.horas,r.estado from reservas r , tiempo_reservas tr, usuarios usu " +
                "where tr.id_tiempo = r.id_tiempo and r.estado ='ACTIVA' and usu.id_usuario = r.id_usuario ",null);
        if (listado!=null) {
            while (listado.moveToNext()) {
                arrayid.add(listado.getString(0));
                arrayUsuario.add(listado.getString(1));
                arrayCancha.add(listado.getString(2));
                arrayFecha.add(listado.getString(3));
                arrayHorario.add(listado.getString(4));
                arrayEstado.add(listado.getString(5));
            }
        }
        miBase.close();
        objCn.close();

    }

    public void listadoRUsuario(Context contexto,String usuario){
        Conexion objCn = new Conexion(contexto);
        SQLiteDatabase miBase = objCn.getWritableDatabase();
        limpiarArrays();
        Cursor listado = miBase.rawQuery("select r.id_reserva,usu.usuario,r.cancha,r.fecha,tr.horas,r.estado from reservas r , tiempo_reservas tr , usuarios usu " +
                "where tr.id_tiempo = r.id_tiempo and r.estado ='ACTIVA' and usu.id_usuario = r.id_usuario and usu.usuario ='"+usuario+"'",null);
        if (listado!=null) {
            while (listado.moveToNext()) {
                arrayid.add(listado.getString(0));
                arrayUsuario.add(listado.getString(1));
                arrayCancha.add(listado.getString(2));
                arrayFecha.add(listado.getString(3));
                arrayHorario.add(listado.getString(4));
                arrayEstado.add(listado.getString(5));
            }
        }
        miBase.close();
        objCn.close();

    }

    public void historial(Context contexto){
        Conexion objCn = new Conexion(contexto);
        SQLiteDatabase miBase = objCn.getWritableDatabase();
        limpiarArrays();
        Cursor listado = miBase.rawQuery("select r.id_reserva,usu.usuario,r.cancha,r.fecha,tr.horas,r.estado from reservas r , tiempo_reservas tr , usuarios usu " +
                "where tr.id_tiempo = r.id_tiempo and r.estado !='ACTIVA' and usu.id_usuario = r.id_usuario order by r.fecha",null);
        if (listado!=null) {
            while (listado.moveToNext()) {
                arrayid.add(listado.getString(0));
                arrayUsuario.add(listado.getString(1));
                arrayCancha.add(listado.getString(2));
                arrayFecha.add(listado.getString(3));
                arrayHorario.add(listado.getString(4));
                arrayEstado.add(listado.getString(5));
            }
        }
        miBase.close();
        objCn.close();

    }

    public void historialUsuario(Context contexto,String usuario){
        Conexion objCn = new Conexion(contexto);
        SQLiteDatabase miBase = objCn.getWritableDatabase();
        limpiarArrays();
        Cursor listado = miBase.rawQuery("select r.id_reserva,usu.usuario,r.cancha,r.fecha,tr.horas,r.estado from reservas r , tiempo_reservas tr , usuarios usu " +
                "where tr.id_tiempo = r.id_tiempo and r.estado !='ACTIVA' and usu.id_usuario = r.id_usuario and usu.usuario ='"+usuario+"' order by r.fecha",null);
        if (listado!=null) {
            while (listado.moveToNext()) {
                arrayid.add(listado.getString(0));
                arrayUsuario.add(listado.getString(1));
                arrayCancha.add(listado.getString(2));
                arrayFecha.add(listado.getString(3));
                arrayHorario.add(listado.getString(4));
                arrayEstado.add(listado.getString(5));
            }
        }
        miBase.close();
        objCn.close();

    }

    public void procesarAnular (int idReserva ,int operacion,Context context){
        Conexion objConexion = new Conexion(context);
        SQLiteDatabase miBase = objConexion.getWritableDatabase();

        if (operacion==0){//Anular
        sql="update reservas set estado = 'ANULADA' where id_reserva ="+idReserva;
        }else {//Finalizar
           sql = "update reservas set estado = 'PAGADA' where id_reserva ="+idReserva;
        }

        miBase.execSQL(sql);
        miBase.close();
        objConexion.close();
    }

    public void limpiarArrays(){
        arrayid.clear();
        arrayUsuario.clear();
        arrayCancha.clear();
        arrayFecha.clear();
        arrayHorario.clear();
        arrayEstado.clear();

    }

    public void AgregarCancha(String nombre_cancha, Context contexto){



        sql = "insert into canchas(estado)" + "values('"+cancha+"')";
        Conexion objConexion = new Conexion(contexto);
        SQLiteDatabase miBase = objConexion.getWritableDatabase();
        miBase.execSQL(sql);

        miBase.close();
        objConexion.close();
    }

}
