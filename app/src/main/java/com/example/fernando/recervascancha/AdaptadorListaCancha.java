package com.example.fernando.recervascancha;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Fernando on 21/05/2017.
 */
public class AdaptadorListaCancha extends ArrayAdapter {

    private Context contexto;
    private  Cursor cursor;
    private ArrayList id = new ArrayList();
    private ArrayList nombre = new ArrayList();
    private ArrayList estado = new ArrayList();
    private ArrayList precio = new ArrayList();
    View filav;

    public AdaptadorListaCancha(Context context, ArrayList id, ArrayList nombre, ArrayList estado, ArrayList precio) {
        super(context,R.layout.rowcancha,id);
        this.contexto = context;
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
        this.precio = precio;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflar = (LayoutInflater)contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        filav=inflar.inflate(R.layout.rowcancha,viewGroup,false);
       // TextView tvId = (TextView)filav.findViewById(R.id.tvId);
        TextView tvUsuario = (TextView)filav.findViewById(R.id.tvUsuarioC);
        TextView tvNombre = (TextView)filav.findViewById(R.id.tvEstadoC);
        TextView tvApellido = (TextView)filav.findViewById(R.id.tvPrecioC);
        //tvId.setText(""+id.get(i));
          tvUsuario.setText(""+nombre.get(i));
        tvNombre.setText(""+estado.get(i));
        tvApellido.setText(""+precio.get(i));
        return filav;
    }
}
