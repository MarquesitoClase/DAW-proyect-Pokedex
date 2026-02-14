package com.example.app.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.Exceptions.JSONNotFoundException;
import com.example.app.Exceptions.PokemonNotFoundException;
import com.example.app.data.PokemonRepository;
import com.example.app.domain.Pokemon;
import com.example.app.domain.Tipo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;

@Service
public class PokemonService {
    private static final Logger logger = Logger.getLogger(PokemonService.class.getName());
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    PokemonRepository pokemonRepository;

    public void initData() {
        try (InputStream inputStream = getClass().getResourceAsStream("/static/json/pokemon.json")) {

            if (inputStream == null) {
                throw new JSONNotFoundException();
            }
            List<Pokemon> pokedex = objectMapper.readValue(inputStream, new TypeReference<List<Pokemon>>() {
            });
            pokemonRepository.guardarTodos(pokedex);

            logger.info("Se han cargado los Pokemon en el repositorio");

        } catch (IOException e) {
            logger.severe("Error al cargar los datos de Pokémon" + e);
            throw new RuntimeException("Error al cargar los datos de Pokémon", e);
        }
    }

    /**
     * @throws PokemonNotFoundException si no existe pokemon con ese id en los datos
     *                                  del JSON
     * @param id
     * @return el pokemon si existe en el repo.
     * @throws PokemonNotFoundException
     */
    public Optional<Pokemon> obtenerPokemonPorId(Long id) throws PokemonNotFoundException {
        logger.info("Pase por obtenerPokemonPorId con id " + id);
        Optional<Pokemon> buscado = pokemonRepository.buscarPorNumDex(id);

        if (!buscado.isPresent())
            throw new PokemonNotFoundException(id);
        else

            logger.info(buscado.toString());
        return buscado;
    }

    public List<Pokemon> obtenerTodos() {
        List<Pokemon> lista = pokemonRepository.obtenerTodos();
        return lista;
    }

    public void Editar(@Valid Pokemon pokemonEditado) {
        Optional<Pokemon> original = pokemonRepository.buscarPorNumDex(pokemonEditado.getNumDex());
        if (original.isPresent()) {
            Pokemon pokemonActualizado = original.get();

            pokemonActualizado.setNombre(pokemonEditado.getNombre());
            pokemonActualizado.setImagen(pokemonEditado.getImagen());
            pokemonActualizado.setGeneracionInicial(pokemonEditado.getGeneracionInicial());
            pokemonActualizado.setDescripcionesPokedex(pokemonEditado.getDescripcionesPokedex());

            if (pokemonEditado.getTipos() != null && !pokemonEditado.getTipos().isEmpty()) {
                pokemonActualizado.setTipos(pokemonEditado.getTipos());
            }

            this.calcularDebilidades(pokemonActualizado);

            pokemonRepository.save(pokemonActualizado);
        } else {
            throw new PokemonNotFoundException("El Pokémon con numDex " + pokemonEditado.getNumDex() + " no existe.");
        }
    }

    public Boolean eliminar(Long pokemonNumDex) throws PokemonNotFoundException {
        if (pokemonRepository.borrarPorNumDex(pokemonNumDex) != null) {
            return true;
        } else {
            throw new PokemonNotFoundException("El Pokémon con numDex " + pokemonNumDex + " no existe.");
        }
    }

    public static List<String> obtenerTipos() {
        return Arrays.stream(Tipo.values())
                .map(tipo -> tipo.name().substring(0, 1) + tipo.name().substring(1).toLowerCase())
                .collect(Collectors.toList());
    }

    public void calcularDebilidades(Pokemon pokemon) {
        pokemon.setDebilidades(new ArrayList<>(Collections.nCopies(18, 1.0)));
        for (String tipo : pokemon.getTipos()) {
            switch (tipo.toUpperCase()) {
                case "NORMAL": {
                    pokemon.getDebilidades().set(6, pokemon.getDebilidades().get(6) * 2); // Lucha
                    pokemon.getDebilidades().set(11, pokemon.getDebilidades().get(11) * 0); // Fantasma
                    break;
                }
                case "FUEGO": {
                    pokemon.getDebilidades().set(1, pokemon.getDebilidades().get(1) * 0.5); // Fuego
                    pokemon.getDebilidades().set(2, pokemon.getDebilidades().get(2) * 2); // Agua
                    pokemon.getDebilidades().set(3, pokemon.getDebilidades().get(3) * 0.5); // Planta
                    pokemon.getDebilidades().set(5, pokemon.getDebilidades().get(5) * 0.5); // Hielo
                    pokemon.getDebilidades().set(8, pokemon.getDebilidades().get(8) * 2); // Tierra
                    pokemon.getDebilidades().set(9, pokemon.getDebilidades().get(9) * 2); // Roca
                    pokemon.getDebilidades().set(10, pokemon.getDebilidades().get(10) * 0.5); // Bicho
                    pokemon.getDebilidades().set(12, pokemon.getDebilidades().get(12) * 0.5); // Acero
                    pokemon.getDebilidades().set(15, pokemon.getDebilidades().get(15) * 0.5); // Hada
                    break;
                }

                case "AGUA": {
                    pokemon.getDebilidades().set(1, pokemon.getDebilidades().get(1) * 0.5); // Fuego
                    pokemon.getDebilidades().set(2, pokemon.getDebilidades().get(2) * 0.5); // Agua
                    pokemon.getDebilidades().set(3, pokemon.getDebilidades().get(3) * 2); // Planta
                    pokemon.getDebilidades().set(4, pokemon.getDebilidades().get(4) * 2); // Eléctrico
                    pokemon.getDebilidades().set(5, pokemon.getDebilidades().get(5) * 0.5); // Hielo
                    pokemon.getDebilidades().set(12, pokemon.getDebilidades().get(12) * 0.5); // Acero
                    break;
                }
                case "ELECTRICO": {
                    pokemon.getDebilidades().set(4, pokemon.getDebilidades().get(4) * 0.5);/// Electrico
                    pokemon.getDebilidades().set(8, pokemon.getDebilidades().get(8) * 2.0);// Tierra
                    pokemon.getDebilidades().set(12, pokemon.getDebilidades().get(12) * 0.5);// Acero
                    pokemon.getDebilidades().set(17, pokemon.getDebilidades().get(17) * 0.5);// Volador
                    break;
                }
                case "HIELO": {
                    pokemon.getDebilidades().set(1, pokemon.getDebilidades().get(1) * 2.0);// fuego
                    pokemon.getDebilidades().set(5, pokemon.getDebilidades().get(5) * 0.5);// hielo
                    pokemon.getDebilidades().set(6, pokemon.getDebilidades().get(6) * 2.0);// lucha
                    pokemon.getDebilidades().set(9, pokemon.getDebilidades().get(9) * 2.0);// roca
                    pokemon.getDebilidades().set(12, pokemon.getDebilidades().get(12) * 2.0);// acero
                    break;
                }

                case "LUCHA": {
                    pokemon.getDebilidades().set(16, pokemon.getDebilidades().get(16) * 2.0); // Volador
                    pokemon.getDebilidades().set(15, pokemon.getDebilidades().get(15) * 2.0); // Psíquico
                    pokemon.getDebilidades().set(11, pokemon.getDebilidades().get(10) * 0.5); // Bicho
                    pokemon.getDebilidades().set(9, pokemon.getDebilidades().get(9) * 0.5); // Roca
                    pokemon.getDebilidades().set(14, pokemon.getDebilidades().get(14) * 0.5); // Siniestro
                    pokemon.getDebilidades().set(15, pokemon.getDebilidades().get(15) * 2.0);// Hada
                    break;
                }
                case "VENENO": {
                    pokemon.getDebilidades().set(3, pokemon.getDebilidades().get(3) * 0.5);// planta
                    pokemon.getDebilidades().set(7, pokemon.getDebilidades().get(7) * 0.5);// Veneno
                    pokemon.getDebilidades().set(8, pokemon.getDebilidades().get(8) * 2.0);// Tierra
                    pokemon.getDebilidades().set(10, pokemon.getDebilidades().get(10) * 0.5);// Bicho
                    pokemon.getDebilidades().set(15, pokemon.getDebilidades().get(15) * 0.5);// Hada
                    pokemon.getDebilidades().set(16, pokemon.getDebilidades().get(16) * 2.0);// Psiquico
                    break;
                }
                case "TIERRA": {
                    pokemon.getDebilidades().set(2, pokemon.getDebilidades().get(2) * 2);/// Agua
                    pokemon.getDebilidades().set(3, pokemon.getDebilidades().get(3) * 2.0);// Planta
                    pokemon.getDebilidades().set(4, pokemon.getDebilidades().get(4) * 0 - 0);// Eléctrico
                    pokemon.getDebilidades().set(3, pokemon.getDebilidades().get(3) * 2.0);// Planta
                    pokemon.getDebilidades().set(5, pokemon.getDebilidades().get(5) * 2.0);// Hielo
                    pokemon.getDebilidades().set(7, pokemon.getDebilidades().get(7) * 0.5);// Veneno
                    pokemon.getDebilidades().set(9, pokemon.getDebilidades().get(9) * 0.5);// Hielo
                    break;
                }
                case "ROCA": {
                    pokemon.getDebilidades().set(2, pokemon.getDebilidades().get(2) * 2.0);/// Agua
                    pokemon.getDebilidades().set(12, pokemon.getDebilidades().get(12) * 2.0);// Acero
                    pokemon.getDebilidades().set(1, pokemon.getDebilidades().get(1) * 0.5);// Fuego
                    pokemon.getDebilidades().set(6, pokemon.getDebilidades().get(6) * 2.0);// Lucha
                    pokemon.getDebilidades().set(0, pokemon.getDebilidades().get(0) * 0.5);// Normal
                    pokemon.getDebilidades().set(3, pokemon.getDebilidades().get(3) * 2.0);// Planta
                    break;
                }
                case "BICHO": {
                    pokemon.getDebilidades().set(1, pokemon.getDebilidades().get(1) * 2); // Fuego
                    pokemon.getDebilidades().set(3, pokemon.getDebilidades().get(3) * 0.5);// Planta
                    pokemon.getDebilidades().set(6, pokemon.getDebilidades().get(6) * 0.5);// Lucha
                    pokemon.getDebilidades().set(8, pokemon.getDebilidades().get(8) * 0.5);// Tierra
                    pokemon.getDebilidades().set(9, pokemon.getDebilidades().get(9) * 2.0);// Roca
                    pokemon.getDebilidades().set(17, pokemon.getDebilidades().get(17) * 2.0);// Volador
                    break;
                }

                case "FANTASMA": {
                    pokemon.getDebilidades().set(0, pokemon.getDebilidades().get(0) * 0.0); // Normal
                    pokemon.getDebilidades().set(6, pokemon.getDebilidades().get(6) * 0.0); // Lucha
                    pokemon.getDebilidades().set(7, pokemon.getDebilidades().get(7) * 0.5); // Veneno
                    pokemon.getDebilidades().set(10, pokemon.getDebilidades().get(10) * 0.5); // Bicho
                    pokemon.getDebilidades().set(11, pokemon.getDebilidades().get(11) * 2.0); // Fantasma
                    pokemon.getDebilidades().set(14, pokemon.getDebilidades().get(14) * 2.0); // Siniestro
                    break;
                }

                case "ACERO": {
                    pokemon.getDebilidades().set(0, pokemon.getDebilidades().get(0) * 0.5); // Normal
                    pokemon.getDebilidades().set(1, pokemon.getDebilidades().get(1) * 2.0); // Fuego
                    pokemon.getDebilidades().set(3, pokemon.getDebilidades().get(3) * 0.5); // Planta
                    pokemon.getDebilidades().set(5, pokemon.getDebilidades().get(5) * 0.5); // Hielo
                    pokemon.getDebilidades().set(6, pokemon.getDebilidades().get(6) * 2.0); // Lucha
                    pokemon.getDebilidades().set(7, pokemon.getDebilidades().get(7) * 0.0); // Veneno
                    pokemon.getDebilidades().set(8, pokemon.getDebilidades().get(8) * 2.0); // Tierra
                    pokemon.getDebilidades().set(9, pokemon.getDebilidades().get(9) * 050); // Roca
                    pokemon.getDebilidades().set(10, pokemon.getDebilidades().get(10) * 0.5); // Bicho
                    pokemon.getDebilidades().set(12, pokemon.getDebilidades().get(12) * 0.5); // Acero
                    pokemon.getDebilidades().set(13, pokemon.getDebilidades().get(13) * 0.5); // Dragón
                    pokemon.getDebilidades().set(15, pokemon.getDebilidades().get(15) * 2.0); // Hada
                    pokemon.getDebilidades().set(16, pokemon.getDebilidades().get(16) * 0.5); // Psíquico
                    pokemon.getDebilidades().set(17, pokemon.getDebilidades().get(17) * 0.5); // Volador
                    break;
                }

                case "DRAGON": {
                    pokemon.getDebilidades().set(2, pokemon.getDebilidades().get(2) * 0.5); // Agua
                    pokemon.getDebilidades().set(3, pokemon.getDebilidades().get(3) * 0.5); // Planta
                    pokemon.getDebilidades().set(4, pokemon.getDebilidades().get(4) * 0.5); // Eléctrico
                    pokemon.getDebilidades().set(5, pokemon.getDebilidades().get(5) * 2.0); // Hielo
                    pokemon.getDebilidades().set(13, pokemon.getDebilidades().get(13) * 2.0); // Dragón
                    pokemon.getDebilidades().set(15, pokemon.getDebilidades().get(15) * 2.0); // Hada
                    break;
                }

                case "SINIESTRO": {
                    pokemon.getDebilidades().set(6, pokemon.getDebilidades().get(6) * 2.0); // Lucha
                    pokemon.getDebilidades().set(10, pokemon.getDebilidades().get(10) * 2.0); // Bicho
                    pokemon.getDebilidades().set(11, pokemon.getDebilidades().get(11) * 0.5); // Fantasma
                    pokemon.getDebilidades().set(14, pokemon.getDebilidades().get(14) * 0.5); // Siniestro
                    pokemon.getDebilidades().set(15, pokemon.getDebilidades().get(15) * 2.0); // Hada
                    pokemon.getDebilidades().set(16, pokemon.getDebilidades().get(16) * 0.0); // Psíquico
                    break;
                }
                case "HADA": {
                    pokemon.getDebilidades().set(6, pokemon.getDebilidades().get(6) * 0.5); // Lucha
                    pokemon.getDebilidades().set(7, pokemon.getDebilidades().get(7) * 2.0); // Veneno
                    pokemon.getDebilidades().set(10, pokemon.getDebilidades().get(100) * 0.5); // Bicho
                    pokemon.getDebilidades().set(12, pokemon.getDebilidades().get(12) * 2.0); // Acero
                    pokemon.getDebilidades().set(13, pokemon.getDebilidades().get(13) * 0.0); // Dragón
                    pokemon.getDebilidades().set(14, pokemon.getDebilidades().get(14) * 0.5); // Siniestro
                    pokemon.getDebilidades().set(15, pokemon.getDebilidades().get(15) * 0.5); // Hada
                    break;
                }

                case "PSÍQUICO": {
                    pokemon.getDebilidades().set(6, pokemon.getDebilidades().get(6) * 0.5); // Lucha
                    pokemon.getDebilidades().set(10, pokemon.getDebilidades().get(10) * 2.0); // Bicho
                    pokemon.getDebilidades().set(11, pokemon.getDebilidades().get(11) * 2.0); // Fantasma
                    pokemon.getDebilidades().set(14, pokemon.getDebilidades().get(14) * 2.0); // Siniestro
                    pokemon.getDebilidades().set(16, pokemon.getDebilidades().get(16) * 0.5); // Psíquico
                    break;
                }

                case "VOLADOR": {
                    pokemon.getDebilidades().set(3, pokemon.getDebilidades().get(3) * 0.5); // Planta
                    pokemon.getDebilidades().set(4, pokemon.getDebilidades().get(4) * 2.0); // Eléctrico
                    pokemon.getDebilidades().set(5, pokemon.getDebilidades().get(5) * 2.0); // Hielo
                    pokemon.getDebilidades().set(7, pokemon.getDebilidades().get(7) * 0.5); // Veneno
                    pokemon.getDebilidades().set(8, pokemon.getDebilidades().get(8) * 0.0); // Tierra
                    pokemon.getDebilidades().set(9, pokemon.getDebilidades().get(9) * 2.0); // Roca
                    pokemon.getDebilidades().set(10, pokemon.getDebilidades().get(10) * 0.5); // Bicho
                    break;
                }

                case "PLANTA": {
                    pokemon.getDebilidades().set(1, pokemon.getDebilidades().get(1) * 2.0); // Fuego
                    pokemon.getDebilidades().set(2, pokemon.getDebilidades().get(2) * 0.5); // Agua
                    pokemon.getDebilidades().set(3, pokemon.getDebilidades().get(3) * 0.5); // Planta
                    pokemon.getDebilidades().set(4, pokemon.getDebilidades().get(4) * 0.5); // Electrico
                    pokemon.getDebilidades().set(5, pokemon.getDebilidades().get(5) * 2.0); // Hielo
                    pokemon.getDebilidades().set(7, pokemon.getDebilidades().get(7) * 2.0); // Veneno
                    pokemon.getDebilidades().set(8, pokemon.getDebilidades().get(8) * 0.5); // Roca
                    pokemon.getDebilidades().set(10, pokemon.getDebilidades().get(10) * 2.0);// Bicho
                    pokemon.getDebilidades().set(17, pokemon.getDebilidades().get(17) * 2.0); // Volador
                }
            }
        }
    }
}