package com.example.app.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.domain.Pokemon;
import com.example.app.services.PokemonService;

@RestController
@RequestMapping("/api")
public class PokemonRestController {
    @Autowired
    PokemonService pokemonService;

    @GetMapping("/pokemons")
    public List<Pokemon> obtenerTodos() {
        return pokemonService.obtenerTodos();
    }

}
