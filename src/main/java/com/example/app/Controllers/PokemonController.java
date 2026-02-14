package com.example.app.Controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.app.Exceptions.PokemonNotFoundException;
import com.example.app.domain.Pokemon;
import com.example.app.services.PokemonService;

@Controller
public class PokemonController {

    @Autowired
    PokemonService pokemonService;

    public List<String> tipos = PokemonService.obtenerTipos();

    @GetMapping({ "/pokedex", "/todos", "/all" })
    public String verTodos(Model model) {
        model.addAttribute("pokedex", pokemonService.obtenerTodos());
        return "dexView";
    }

    @GetMapping("/pokemonView/{numDex}")
    public String pokemonView(@PathVariable Long numDex, Model model) throws PokemonNotFoundException, IOException {
        try {
            System.out.println("Llegué con id = " + numDex);
            Optional<Pokemon> pokemonOptional = pokemonService.obtenerPokemonPorId(numDex);

            if (pokemonOptional.isPresent()) {
                Pokemon pokemon = pokemonOptional.get();
                model.addAttribute("buscado", pokemon);
                pokemonService.calcularDebilidades(pokemon);
                model.addAttribute("debilidadesPokemon", pokemon.getDebilidades());
                return "pokemonView";
            }
            else{
                throw new PokemonNotFoundException();
            }
        } catch (PokemonNotFoundException e) {
            model.addAttribute("numDex", numDex);
            return "redirect:/pokemonRobado/" + numDex;
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/errorView";
        }
    }

    @GetMapping("/editar/{numDex}")
    public String Editar(@PathVariable Long numDex, Model model) {
        try {
            Optional<Pokemon> buscado = pokemonService.obtenerPokemonPorId(numDex);
            if (buscado.isPresent()) {
                Pokemon pokemon = buscado.get();
                model.addAttribute("buscado", pokemon);
                pokemonService.calcularDebilidades(pokemon);
                model.addAttribute("debilidadesPokemon", pokemon.getDebilidades());
                model.addAttribute("tiposPokemon", tipos);
            } else {
                throw new PokemonNotFoundException();
            }
        } catch (PokemonNotFoundException e) {
            model.addAttribute("NDexFaltante", numDex);
            return "redirect:/pokemonRobado/" + numDex;
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "errorView";
        }
        return "editar";
    }

    @PostMapping("/editar")
    public String EditarPokemon(@ModelAttribute Pokemon pokemon, Model model) {
        model.addAttribute("NDexFaltante", pokemon.getNumDex());
        model.addAttribute("tiposDisponibles", tipos);
        try {
            System.out.println("Antes de pasar por editar mis devilidades son: "+pokemon.getDebilidades());
            pokemonService.Editar(pokemon);
            System.out.println("Despues de pasar por editar mis devilidades son: "+pokemon.getDebilidades());
            model.addAttribute("debilidadesPokemon", pokemon.getDebilidades());
        } catch (Exception e) {
            System.out.println("Error al guardar un Pokémon editado: " + e.getMessage());
            return "errorView";
        }
        pokemonService.calcularDebilidades(pokemon);
        return "redirect:/pokemonView/" + pokemon.getNumDex();
    }


    @GetMapping("/borrar/{numDex}")
    public String borrarPokemon(@PathVariable Long numDex) {
        System.out.println("Entré en borrar");
        Boolean eliminado = pokemonService.eliminar(numDex);

        if (eliminado) {
            System.out.println("El Pokémon fue eliminado");
        } else {
            System.out.println("El Pokémon no se borró");
        }
        return "redirect:/pokedex";
    }

    @GetMapping("/pokemonRobado/{numDex}")
    public String teneisProblemas(@PathVariable String numDex, Model model) {
        Pokemon buscado=new Pokemon(Long.parseLong(numDex));
        model.addAttribute("Pokemon", buscado);
        model.addAttribute("NDexFaltante", numDex);
        return "pokemonRobado";
    }

    @PostMapping("recuperarDatos/{numDex}")
    public String FormularioRecuperarPokemon(@PathVariable String numDex, Model model){
    model.addAttribute("numDex", numDex);
    model.addAttribute("tipos", PokemonService.obtenerTipos());
        return "comisaria";
    }

    @PostMapping("formularioRecuperacion/{numDex}")
    public String recuperarPokemon(@PathVariable Long numDex, @ModelAttribute Pokemon pokemon, Model model){
        try{
            Optional<Pokemon> existe = pokemonService.obtenerPokemonPorId(numDex);
            if(!existe.isPresent()) throw new PokemonNotFoundException();
        }catch(PokemonNotFoundException e){
            pokemonService.Editar(pokemon);
            return "editar";
        }
        catch (Exception e) {
            model.addAttribute("error", "Error al recuperar el Pokémon: " + e.getMessage());
            return "error";
        }
        return "dexView";
    }
}