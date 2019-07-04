package com.example.lorena.releasemaps;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

/**
 * Created by Lorena Pérez on 17/06/2019.
 * software developer
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
        final boolean btnBorrar = viewHolder.btnDelete.isClickable();
        final boolean btnEditar = viewHolder.btnUpdate.isClickable();
        //final boolean btnSave = viewHolder.btnGuardar.isClickable();


        if (btnBorrar) {

            final ViewHolder finalViewHolder = viewHolder;
            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final AlertDialog.Builder alert = new AlertDialog.Builder(context);
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
                            int estado = 0;
                            String txtestado = Integer.toString(estado);

                            DeleteUsuario conexion = new DeleteUsuario();
                            conexion.execute(txtestado, cedulaUsu); //se envia cambios a DB

                            context.finish(); // finaliza y me regresa a la activity anterior.
                        }
                    });

                    alt.show(); //dialog

                }
            });


        }
        if (btnEditar) {
            final ViewHolder finalViewHolder1 = viewHolder;
            viewHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalViewHolder1.vsBoton.showNext();
                    finalViewHolder1.vsCedula.showNext();
                    finalViewHolder1.vsNombre.showNext();
                    finalViewHolder1.vsRol.showNext(); // mirar si lo puedo cambiar al list predeterminado


                    final TextView tem = finalViewHolder1.vsNombre.findViewById(R.id.renglon_nombreUsuario);
                    finalViewHolder1.editNombre.setText(tem.getText().toString());
                    tem.setText(finalViewHolder1.editNombre.getText().toString());

                    final TextView tem1 = finalViewHolder1.vsCedula.findViewById(R.id.renglon_cedulaUsuario);
                    finalViewHolder1.editCedula.setText(tem1.getText().toString());
                    tem1.setText(finalViewHolder1.editCedula.getText().toString());

                    final TextView tem2 = finalViewHolder1.vsRol.findViewById(R.id.renglon_rol);
                    finalViewHolder1.editRol.setText(tem2.getText().toString());
                    tem2.setText(finalViewHolder1.editRol.getText().toString());

                    // finalViewHolder1.txt3.setEnabled(true);
                    //finalViewHolder1.txt2.setEnabled(true);
                    //finalViewHolder1.txt4.setEnabled(true);

                    finalViewHolder1.btnGuardar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            tem.setText(finalViewHolder1.editNombre.getText().toString());
                            String nombreUsu = tem.getText().toString();
                            tem1.setText(finalViewHolder1.editCedula.getText().toString());
                            String cedulaUsu = tem1.getText().toString();
                            tem2.setText(finalViewHolder1.editRol.getText().toString());
                            String rolUsu = tem2.getText().toString();

                            UpdateUsuario conexionUp = new UpdateUsuario();
                            conexionUp.execute(nombreUsu, cedulaUsu, rolUsu); //se envia cambios a DB
                            finalViewHolder1.vsNombre.showNext();

                            Toast.makeText(context,"Información Actualizada con exito", Toast.LENGTH_SHORT).show();
                            context.onBackPressed(); // actualiza, pero al actulaizar el num de cedula no lo permite crea
                            // conflicto, porque es el filtro dela consulta :(

                        }
                    });

                }
            });


        }
        /** if (btnSave) {

         final ViewHolder finalViewHolder2 = viewHolder;

         //cambiar esta relacion, debe ser con los campos del edit anterior, no estos directamente, porque son vacios, una opcion es
         //activar el action listener desto del if del edit.
         String cedulaUsu = finalViewHolder2.editCedula.getText().toString();
         String nombreUsu = finalViewHolder2.editNombre.getText().toString();
         String rolUsu = finalViewHolder2.editRol.getText().toString();


         // cambiar el swecher View a las view de nuevo con la info mas reciente y ya

         // hacer conexion y enviar datos actualizados

         }**/

        return convertView;
    }


    class ViewHolder {


        TextView txt2;
        TextView txt3;
        TextView txt4;
        Button btnDelete;
        Button btnUpdate;
        Button btnGuardar;
        ViewSwitcher vsNombre;
        ViewSwitcher vsCedula;
        ViewSwitcher vsRol;
        ViewSwitcher vsBoton;
        EditText editNombre;
        EditText editCedula;
        EditText editRol;


        ViewHolder(View v) {

            txt2 = v.findViewById(R.id.renglon_cedulaUsuario);
            txt3 = v.findViewById(R.id.renglon_nombreUsuario);
            txt4 = v.findViewById(R.id.renglon_rol);
            btnDelete = v.findViewById(R.id.btnEliminar);
            btnUpdate = v.findViewById(R.id.btnEditar);
            btnGuardar = v.findViewById(R.id.btnGuardar);
            vsNombre = v.findViewById(R.id.vsnombre);
            vsCedula = v.findViewById(R.id.vscedula);
            vsRol = v.findViewById(R.id.vsrol);
            vsBoton = v.findViewById(R.id.vsBoton);
            editNombre = v.findViewById(R.id.renglon_editNombre);
            editCedula = v.findViewById(R.id.renglon_editCedula);
            editRol = v.findViewById(R.id.renglon_editRol);

        }

    }


}

