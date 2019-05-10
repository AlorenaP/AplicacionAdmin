package com.example.lorena.releasemaps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

    /**
     * Created by Domiciano on 01/05/2018.
     */

/*
    PARA USAR ESTE ADAPTADOR DEBE TENER EL ARCHIVO renglon.xml EN LA CARPETA LAYOUT
    DEBE TAMBIÃ‰N USAR
*/
    public class Adaptador extends BaseAdapter {
        ArrayList<Registro> usuarios;
       // FirebaseStorage storage;
        Context context;


        public Adaptador(Context context) {
            this.context = context;
            usuarios = new ArrayList<>();
            //storage = FirebaseStorage.getInstance();


        }

        @Override
        public int getCount() {
            return usuarios.size();
        }

        @Override
        public Object getItem(int position) {
            return usuarios.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        //se ejecuta tantas veces como elementos existan en la lista
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            /**   LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //aprovechamos el cache del ListView
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.renglon, null);
            }

            final TextView tv_nombreChef = convertView.findViewById(R.id.tv_renglon_nombreChef);
            final TextView tv_telefono = convertView.findViewById(R.id.tv_renglon_telefono);
            final TextView tv_correo = convertView.findViewById(R.id.tv_renglon_correo);
            final TextView tv_disponibilidad = convertView.findViewById(R.id.tv_renglon_disponibilidadChef);

            //tv_disponibilidad.setText();

            //StorageReference ref = storage.getReference().child("usuarios");
            //final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("usuarios").child("chef");

          //  myRef.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        myRef.child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                Usuario guardado=snapshot.getValue(Usuario.class);

                                String name=guardado.getNombre();
                                String correo=guardado.getEmail();
                                Log.e("Datos",""+ name+ ""+correo);

                                tv_nombreChef.setText(name);
                                //tv_telefono.setText(usuariosChef.get(position).getTelefono());
                                tv_correo.setText(correo);
                                //Log.e("Datos:", "" + tv_nombreChef.toString() + "" + tv_correo.toString());

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });**/

            /**ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
            Glide.with(context).load(uri).into(renglon_img);

            }
            });**/
            return convertView;
        }


        public void agregarChef(Registro c) {

            usuarios.add(c);
            notifyDataSetChanged();

        }

        /** public void refreshComment(Usuario c) {

         int index = usuariosChef.indexOf(c);
         Usuario viejo= usuariosChef.get(index);
         notifyDataSetChanged();
         }**/

    }
