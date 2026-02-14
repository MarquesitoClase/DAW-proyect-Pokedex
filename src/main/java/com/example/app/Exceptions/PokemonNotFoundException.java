package com.example.app.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PokemonNotFoundException extends RuntimeException{
    /**
     * Busca a un pokemon según el numero de su dex
     * @param id Numero a buscar entre los nº dex los pokemon
     */
    public PokemonNotFoundException(Long id) {
        super("No existe en mi BD un pokemon con ese número("+id+ ") de la pokedex." );
    }
    public PokemonNotFoundException(){
        super("El json no contiene datos.");
    }
    /**
 * No se encuentra el pokemon con el nombre buscado
 * @param nombre nombre a buscar entre los pokemon
 */
public PokemonNotFoundException(String nombre) {
    super("No existe enn mi BD un pokemon con ese nombre("+nombre );
}
/**
 * Hay 2 mensajes por si quiere mandarse un mensaje propio que no se confunda
 * con el de comprobar nombre sólo.
 * @param mensaje mensaje real
 * @param mensaje2 para diferenciar con el pokemonNotFound(nombre) sólo
 */
    public PokemonNotFoundException(String mensaje, String mensaje2) {
        super(mensaje);
    }
}
