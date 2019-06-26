package com.example.lorena.releasemaps;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Lorena on 17/06/2019.
 */

public class Adaptador extends ArrayAdapter<String> {


    private String[] rol;
    private String[] cedula;
    private String[] nombre;
    private Activity context;


    public Adaptador(Activity context, String[] id, String[] names, String[] rol) {
        super(context, R.layout.renglon, id);
        this.context = context;
        this.cedula = id;
        this.nombre = names;
        this.rol = rol;

    }


    //se ejecuta tantas veces como elementos existan en la lista
    @NonNull
    @Override
    public View getView(final int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;

        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //aprovechamos el cache del ListView
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.renglon, null, true);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txt2.setText(cedula[position]);
        viewHolder.txt3.setText(nombre[position]);
        viewHolder.txt4.setText(rol[position]);
        boolean btnBorrar=viewHolder.btnDelete.isClickable();
        boolean btnEdit=viewHolder.btnUpdate.isClickable();
        if(btnBorrar){

            final ViewHolder finalViewHolder = viewHolder;
            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final AlertDialog.Builder alert=new AlertDialog.Builder(context);
                    View tem = inflater.inflate(R.layout.alert_dialog, null);

                    Button btn_ok = tem.findViewById(R.id.btn_ok);
                    Button btn_cancel = tem.findViewById(R.id.btn_cancel);

                    alert.setView(tem);
                    final AlertDialog alt = alert.create();
                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alt.dismiss();
                        }
                    });

                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String cedulaUsu = finalViewHolder.txt2.getText().toString();
                            int estado =0;
                            String txtestado=Integer.toString(estado);

                            DeleteUsuario conexion = new DeleteUsuario();
                            conexion.execute(txtestado, cedulaUsu);

                            context.finish(); // finaliza y me regresa al contexto anterior.
                        }
                    });
                    alt.show();

                }
            });

        }else if(btnEdit){

            viewHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }


        //final TextView tv_cedulaUsuario = convertView.findViewById(R.id.renglon_cedulaUsuario);
        //final TextView tv_nombreUsuario = convertView.findViewById(R.id.renglon_nombreUsuario);
        //final TextView tv_rolUsuario = convertView.findViewById(R.id.renglon_rol);
        //final Button btn_update= convertView.findViewById(R.id.btnEditar);
        //final Button btn_eliminar=convertView.findViewById(R.id.btnEliminar);

        return convertView;
    }




    class ViewHolder {

        TextView txt2;
        TextView txt3;
        TextView txt4;
        Button btnDelete;
        Button btnUpdate;


        ViewHolder(View v) {

            txt2 = v.findViewById(R.id.renglon_cedulaUsuario);
            txt3 = v.findViewById(R.id.renglon_nombreUsuario);
            txt4 = v.findViewById(R.id.renglon_rol);
            btnDelete = v.findViewById(R.id.btnEliminar);
            btnUpdate = v.findViewById(R.id.btnEditar);
        }

    }


}

