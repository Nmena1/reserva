package com.example.fernando.recervascancha;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mario on 13/05/2017.
 */

public class Conexion extends SQLiteOpenHelper {
    public Conexion(Context context) {
        super(context, "reservasCetu", null, 1);
    }

    public static final String tabla_canchas = "canchas";
    public static final String id_cancha = "id_cancha";
    public static final String nombre_cancha = "nombre";
    public static final String estado_cancha = "estado";
    public static final String precio_cancha = "precio_hora";

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL;
        SQL = "create table usuarios (id_usuario integer primary key autoincrement, usuario text unique not null, clave text not null, tipo integer not null," +
                "nombres text not null, apellidos text not null, telefono text not null, correo text not null,estado text)";
        db.execSQL(SQL);

        SQL = "insert into usuarios(usuario,clave,tipo,nombres,apellidos,telefono,correo,estado) values('SA','CETU2017'," +
                "1,'Super Administrador','Super Administrador','7777-7777','cetu@mail.utec.edu.sv','ACTIVO')";
        db.execSQL(SQL);

        SQL = "create table tarifas (id_tarifa integer primary key AUTOINCREMENT, cuota real not null)";
        db.execSQL(SQL);

        SQL = "create table estados (id_estado integer primary key AUTOINCREMENT, estado text not null)";
        db.execSQL(SQL);

        db.execSQL("create table "+ tabla_canchas +" ("+
                id_cancha +" integer primary key AUTOINCREMENT, "+
                nombre_cancha +"text not null, "+
                estado_cancha +" text not null, "+
                precio_cancha +" text not null)");

        SQL = "create table tiempo_reservas (id_tiempo integer primary key AUTOINCREMENT, horas text)";
        db.execSQL(SQL);

        SQL = "insert into tiempo_reservas(horas) values('9-10')";
        db.execSQL(SQL);
        SQL = "insert into tiempo_reservas(horas) values('10-11')";
        db.execSQL(SQL);
        SQL = "insert into tiempo_reservas(horas) values('11-12')";
        db.execSQL(SQL);
        SQL = "insert into tiempo_reservas(horas) values('12-1')";
        db.execSQL(SQL);
        SQL = "insert into tiempo_reservas(horas) values('1-2')";
        db.execSQL(SQL);
        SQL = "insert into tiempo_reservas(horas) values('2-3')";
        db.execSQL(SQL);
        SQL = "insert into tiempo_reservas(horas) values('3-4')";
        db.execSQL(SQL);
        SQL = "insert into tiempo_reservas(horas) values('4-5')";
        db.execSQL(SQL);
        SQL = "insert into tiempo_reservas(horas) values('5-6')";
        db.execSQL(SQL);
        SQL = "insert into tiempo_reservas(horas) values('6-7')";
        db.execSQL(SQL);

        /*SQL = "create table clientes (id_cliente integer primary key AUTOINCREMENT, nombres text not null, apellidos text not null, telefono text not null," +
                " correo text not null, comentario text not null)";
        db.execSQL(SQL);*/

        SQL = "create table reservas (id_reserva integer primary key AUTOINCREMENT, id_usuario integer,cancha text ,fecha text,id_tiempo interger,estado text , FOREIGN KEY (id_usuario) " +
                "REFERENCES tiempo_reservas(id_tiempo),FOREIGN KEY (id_tiempo) REFERENCES usuarios(id_tiempo))";
        db.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void AgregarCancha(String nombre, String estado, String precio){
        ContentValues valores = new ContentValues();
        valores.put(nombre_cancha, nombre);
        valores.put(estado_cancha, estado);
        valores.put(precio_cancha, precio);
        this.getWritableDatabase().insert(tabla_canchas,null,valores);
    }

    public Cursor getCanchas(){
        String columnas[]={id_cancha,nombre_cancha,estado_cancha,precio_cancha};
        Cursor c = this.getReadableDatabase().query(tabla_canchas,columnas,null,null,null,null,null);
        return c;
    }

    public void EliminarCancha(String nombre){
        this.getWritableDatabase().delete(tabla_canchas,nombre_cancha+"=?", new String[]{nombre});
    }
}
