package academy.devdojo.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/heroes")
public class HeroController {
    private static final List<String> Heroes = List.of(
            "Guts",
            "Zoro",
            "Kakashi",
            "Sasuke",
            "Naruto"
    );

    @GetMapping()
    public List<String> listAllHeroes(){
        return Heroes;
    }

    @GetMapping("filter")
    public List<String> listAllHeroesParam(@RequestParam(defaultValue = "") String name){
        return Heroes.stream().filter(hero -> hero.equalsIgnoreCase(name)).toList();
    }

    @GetMapping("filterList")
    public List<String> listAllHeroesParamList(@RequestParam(defaultValue = "") List<String> names){
        return Heroes.stream().filter(names::contains).toList();
    }

}
