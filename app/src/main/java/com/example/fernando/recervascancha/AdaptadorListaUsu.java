package com.example.fernando.recervascancha;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Fernando on 21/05/2017.
 */
public class AdaptadorListaUsu extends ArrayAdapter {

    private Context contexto;
    private  Cursor cursor;
    private ArrayList id = new ArrayList();
    private ArrayList usuario = new ArrayList();
    private ArrayList nombre = new ArrayList();
    private ArrayList apellido = new ArrayList();
    View filav;

    public AdaptadorListaUsu(Context context,ArrayList id,ArrayList usuario,ArrayList nombres,ArrayList apellido) {
        super(context,R.layout.row,usuario);
        this.contexto = context;
        this.id = id;
        this.usuario = usuario;
        this.nombre = nombres;
        this.apellido = apellido;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflar = (LayoutInflater)contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        filav=inflar.inflate(R.layout.row,viewGroup,false);
       // TextView tvId = (TextView)filav.findViewById(R.id.tvId);
        TextView tvUsuario = (TextView)filav.findViewById(R.id.tvUsuario);
        TextView tvNombre = (TextView)filav.findViewById(R.id.tvNombre);
        TextView tvApellido = (TextView)filav.findViewById(R.id.tvApellido);
        //tvId.setText(""+id.get(i));
          tvUsuario.setText(""+usuario.get(i));
        tvNombre.setText(""+nombre.get(i));
        tvApellido.setText(""+apellido.get(i));
        return filav;
    }
}
