/*
 * Copyright (c) 2018.  Diego Urrutia Astorga <durrutia@ucn.cl>
 * This work is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 * http://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package cl.ucn.disc.dsm.directorio.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cl.ucn.disc.dsm.directorio.R;
import cl.ucn.disc.dsm.directorio.models.Persona;
import lombok.extern.slf4j.Slf4j;


/**
 * Adaptador de Personas
 */
@Slf4j
public final class ListAdapter extends ArrayAdapter<Persona> {

    /**
     * Contexto actual
     */
    Context context;

    /**
     * ID del layout que contiene una View para usar cuando se instancian las vistas
     */
    int layoutResourceId;

    /**
     * Lista de personas a representar en la listView
     */
    List<Persona> personas = null;

    /**
     * Constructor del ListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public ListAdapter(Context context, int resource, List<Persona> objects) {
        super(context, resource, objects);

        this.layoutResourceId = resource;
        this.context= context;
        this.personas=objects;
    }

    /**
     *Obtiene una vista que muestra los datos en la posición especificada en el conjunto de datos
     * @param position La posición del elemento dentro del conjunto de datos del adaptador del elemento cuya view deseamos
     * @param convertView La antigua View a reutilizar, si es posible
     * @param parent El padre a quien esta View eventualmente se vinculara
     * @return Una View correspondiente a los datos en la posición especificada
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Contenedor
        ViewHolder holder;

        // Si la fila es null, la inflo
        if (convertView == null) {

            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);

            // Instancio y almaceno
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {

            // Obtengo el holder desde el view.
            holder = (ViewHolder) convertView.getTag();
        }

        // Persona a ser mostrada
        final Persona persona = this.personas.get(position);

        // Nombre
        holder.nombre.setText(persona.getNombre());

        // Cargo
        //holder.cargo.setText(persona.getCargo());

        //Unidad
        //holder.unidad.setText(persona.getUnidad());

        // Numero de telefono
        //holder.telefono.setText(persona.getTelefono());

        // Correo electronico
        //holder.email.setText(persona.getEmail());

        // Oficina
        //holder.oficina.setText(persona.getOficina());

        return convertView;
    }

    /**
     * Clase interna
     */
    private static class ViewHolder {

        TextView nombre;

        TextView cargo;

        TextView unidad;

        TextView telefono;

        TextView email;

        TextView oficina;

        ViewHolder(View view) {
            nombre = view.findViewById(R.id.rp_tv_nombre);
            //cargo = view.findViewById(R.id.rp_tv_cargo);
            //unidad = view.findViewById(R.id.rp_tv_unidad);
            //telefono = view.findViewById(R.id.rp_tv_telefono);
            //email = view.findViewById(R.id.rp_tv_email);
            //oficina = view.findViewById(R.id.rp_tv_oficina);

        }

    }
}