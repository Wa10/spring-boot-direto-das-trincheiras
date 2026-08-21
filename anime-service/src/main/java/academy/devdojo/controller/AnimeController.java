package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/v1/animes")
public class AnimeController {

    @GetMapping("")
    public ResponseEntity<List<Anime>> listAllAnimes(@RequestParam(required = false) String name) {
        var animes = Anime.listAllAnimes();

        if (name == null) {
            return ResponseEntity.ok(animes);
        }

        var filteredAnimes = animes.stream()
                .filter(anime -> anime.getName().equals(name))
                .toList();

        return ResponseEntity.ok(filteredAnimes);
    }

    @GetMapping("{id}")
    public ResponseEntity<Anime> getAnimePathVariable(@PathVariable Long id) {
        return Anime.listAllAnimes()
                .stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Anime> createAnime(@RequestBody Anime anime) {
        anime.setId(ThreadLocalRandom.current().nextLong(1, 100000));
        Anime.listAllAnimes().add(anime);

        return ResponseEntity.status(HttpStatus.CREATED).body(anime);
    }
}