package academy.devdojo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<String>> listAllHeroes() {
        return ResponseEntity.ok(Heroes);
    }

    @GetMapping("filter")
    public ResponseEntity<List<String>> listAllHeroesParam(@RequestParam(defaultValue = "") String name) {
        var heroesFiltered = Heroes.stream()
                .filter(hero -> hero.equalsIgnoreCase(name))
                .toList();

        return ResponseEntity.ok(heroesFiltered);
    }

    @GetMapping("filterList")
    public ResponseEntity<List<String>> listAllHeroesParamList(@RequestParam(defaultValue = "") List<String> names) {
        var heroesFiltered = Heroes.stream()
                .filter(names::contains)
                .toList();

        return ResponseEntity.ok(heroesFiltered);
    }

    @GetMapping("{name}")
    public ResponseEntity<String> findByName(@PathVariable String name) {
        return Heroes.stream()
                .filter(hero -> hero.equalsIgnoreCase(name))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}