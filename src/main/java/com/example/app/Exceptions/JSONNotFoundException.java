package com.example.app.Exceptions;


public class JSONNotFoundException extends RuntimeException{
    /**
     * Indica que el archivo json se movio
     */
    public JSONNotFoundException() {
        super("No existe el JSON en mi ruta." );
    }
}