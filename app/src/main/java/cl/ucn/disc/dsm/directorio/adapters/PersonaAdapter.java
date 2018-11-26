/*
 * Copyright (c) 2018.  Diego Urrutia Astorga <durrutia@ucn.cl>
 * This work is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 * http://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package cl.ucn.disc.dsm.directorio.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cl.ucn.disc.dsm.directorio.R;
import cl.ucn.disc.dsm.directorio.models.Persona;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Adaptador de Personas
 */
@Slf4j
public final class PersonaAdapter extends BaseAdapter {

    /**
     * Listado de Personas.
     */
    private List<Persona> personas = new ArrayList<>();

    /**
     * Inflater
     */
    private LayoutInflater inflater;


    /**
     * @param context para obtener el inflater.
     */
    public PersonaAdapter(@NonNull final Context context) {

        this.inflater = LayoutInflater.from(context);

    }

    /**
     * Cargar las personas desde el archivo ucn.json
     */
    public void load(@NonNull final Context context) {

        String json;

        try {
            InputStream is = context.getAssets().open("ucn.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");

            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0; i < jsonArray.length(); i++){

                JSONObject object = jsonArray.getJSONObject(i);
                personas.add(Persona.builder()
                        .id(object.getString("id"))
                        .nombre(object.getString("nombre"))
                        .cargo(object.getString("cargo"))
                        .unidad(object.getString("unidad"))
                        .email(object.getString("email"))
                        .telefono(object.getString("telefono"))
                        .oficina(object.getString("oficina"))
                        .build());

                log.debug("Persona: {}", personas.get(i));
            }

        }catch (IOException exception) {
            exception.printStackTrace();

        }catch (JSONException exception){
            exception.printStackTrace();
        }

        // Ordamiento por nombre
        Collections.sort(this.personas, (p1, p2) -> p1.getNombre().compareTo(p2.getNombre()));

    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return this.personas.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Persona getItem(int position) {
        return this.personas.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Contenedor
        ViewHolder holder;

        // Si la fila es null, la inflo
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.activity_main, parent, false);

            // Instancio y almaceno
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {

            // Obtengo el holder desde el view.
            holder = (ViewHolder) convertView.getTag();
        }

        // Persona a ser mostrada
        final Persona persona = this.personas.get(position);

        // Valores
        holder.nombre.setText(persona.getNombre());

        // Cargo
        holder.cargo.setText(persona.getCargo());

        //Unidad
        holder.unidad.setText(persona.getUnidad());

        // Numero de telefono
        //holder.telefono.setText(persona.getTelefono());

        // Correo electronico
        //holder.email.setText(persona.getEmail());

        // Obtengo el md5 desde el map
        //String md5 = md5Email.get(persona.getEmail());

        return convertView;
    }

    /**
     * Clase interna.
     */
    private static class ViewHolder {

        TextView nombre;

        TextView cargo;

        TextView unidad;

        //TextView telefono;

        //TextView email;

        ViewHolder(View view) {
            nombre = view.findViewById(R.id.rp_tv_nombre);
            cargo = view.findViewById(R.id.rp_tv_cargo);
            unidad = view.findViewById(R.id.rp_tv_unidad);
            //telefono = view.findViewById(R.id.rp_tv_telefono);
            //email = view.findViewById(R.id.rp_tv_email);
        }

    }
}