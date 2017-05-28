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
public class AdaptadorListaReserva extends ArrayAdapter {

    private Context contexto;
    private  Cursor cursor;
    private ArrayList id = new ArrayList();
    private ArrayList usuario = new ArrayList();
    private ArrayList cancha = new ArrayList();
    private ArrayList fecha = new ArrayList();
    private ArrayList horario = new ArrayList();
    private ArrayList estado = new ArrayList();
    View filav;

    public AdaptadorListaReserva(Context context, ArrayList id, ArrayList usuario, ArrayList cancha,
                                 ArrayList fecha,ArrayList horario,ArrayList estado) {
        super(context,R.layout.row, id);
        this.contexto = context;
        this.id = id;
        this.usuario = usuario;
        this.cancha = cancha;
        this.fecha = fecha;
        this.horario = horario;
        this.estado = estado;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflar = (LayoutInflater)contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        filav=inflar.inflate(R.layout.adapter_reservas,viewGroup,false);
       // TextView tvId = (TextView)filav.findViewById(R.id.tvId);
        TextView tvId = (TextView)filav.findViewById(R.id.tvReservaAr);
        TextView tvUsuario = (TextView)filav.findViewById(R.id.tvUsuAr);
        TextView tvCancha = (TextView)filav.findViewById(R.id.tvCanchaAr);
        TextView tvFecha = (TextView)filav.findViewById(R.id.tvFechaAr);
        TextView tvHorario = (TextView)filav.findViewById(R.id.tvHorarioAr);
        TextView tvEstado = (TextView)filav.findViewById(R.id.tvEstadoAr);


        tvId.setText(""+id.get(i));
        tvUsuario.setText(""+usuario.get(i));
        tvCancha.setText(""+cancha.get(i));
        tvFecha.setText(""+fecha.get(i));
        tvHorario.setText(""+horario.get(i));
        tvEstado.setText(""+estado.get(i));
        return filav;
    }
}
