package com.example.app.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.app.Exceptions.PokemonNotFoundException;
import com.example.app.domain.Pokemon;

import jakarta.validation.Valid;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    public Optional<Pokemon> findByNombre(String nombre);

    public Optional<Pokemon> findByNumDex(Long numDex);

    default Pokemon guardar(Pokemon pokemon) {
        return this.save(pokemon);
    }

    default void guardarTodos(@Valid List<Pokemon> listaPokemon) {
        saveAll(listaPokemon);
    }

    default Optional<Pokemon> buscarPorNumDex(Long numDex) {
        return findByNumDex(numDex);
    }

    default Optional<Pokemon> buscarPorNombre(String nombre) {
        return findByNombre(nombre);
    }

    default List<Pokemon> obtenerTodos() {
        return this.findAll();
    }

    default Pokemon borrarPorNumDex(Long numDex) {
        Optional<Pokemon> borrado = this.buscarPorNumDex(numDex);
        if(borrado.isPresent()) {
            this.deleteById(numDex);
            return borrado.get();
        }throw new PokemonNotFoundException(numDex);
    }
    
}