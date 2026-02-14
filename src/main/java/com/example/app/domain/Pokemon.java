package com.example.app.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Pokemon {
    @Id
    @NotNull
    private Long numDex;
    
    @NotNull
    private String nombre;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<String> tipos;
    
    private ArrayList<String> descripcionesPokedex;
    private String imagen;
    
    private List<Double> debilidades;
    private String urlGif;
    
    private int generacionInicial;


    public Pokemon(Long numDex, String nombre, List<String> tipos, List<String> descripciones, String imagen, int GI, String urlGif){
        this.numDex = numDex;
        this.nombre = nombre;
        this.tipos = new ArrayList<String>(tipos);
        this.descripcionesPokedex = new ArrayList<>(descripciones);
        this.imagen = imagen;
        this.generacionInicial = GI;
        this.urlGif = urlGif;
    }
    public Pokemon(Long numDex){
        this.setNumDex(numDex);
        this.descripcionesPokedex=new ArrayList<String>();
        this.setTipos(new ArrayList<String>());
    }
}
