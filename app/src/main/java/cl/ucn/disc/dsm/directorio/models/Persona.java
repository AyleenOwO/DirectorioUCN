package cl.ucn.disc.dsm.directorio.models;

import lombok.Builder;
import lombok.Getter;

/**
 * Clase que representa el contacto de una Persona.
 */
@Builder
public final class Persona {

    /**
     *
     */
    @Getter
    private String id;

    /**
     *
     */
    @Getter
    private String nombre;

    /**
     *
     */
    @Getter
    private String cargo;

    /**
     *
     */
    @Getter
    private String unidad;

    /**
     *
     */
    @Getter
    private String email;

    /**
     *
     */
    @Getter
    private String telefono;

    /**
     *
     */
    @Getter
    private String oficina;
}
