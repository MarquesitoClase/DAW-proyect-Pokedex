package com.example.app.data;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.example.app.Exceptions.JSONNotFoundException;
import com.example.app.Exceptions.PokemonNotFoundException;
import com.example.app.domain.Pokemon;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JSONParser {
    private final ObjectMapper objectMapper;
    private final PokemonRepository pokemonRepository;
    
    public JSONParser(PokemonRepository pokemonRepository){
        this.objectMapper = new ObjectMapper();
        this.pokemonRepository = pokemonRepository;
    }

    public void loadPokemonsFromJson() throws IOException, JSONNotFoundException, PokemonNotFoundException {
        File JSONFile = new File("data/pokemon_data.json");

        if (!JSONFile.exists()) {
            throw new JSONNotFoundException();
        }

        List<Pokemon> pokemons = objectMapper.readValue(JSONFile, new TypeReference<List<Pokemon>>(){});

        if(pokemons.isEmpty()) throw new PokemonNotFoundException();

        pokemonRepository.guardarTodos(pokemons);
    }
}