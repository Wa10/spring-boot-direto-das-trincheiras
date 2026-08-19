package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RestController()
@RequestMapping("/v1/animes")
public class AnimeController {

    @GetMapping("")
    public List<Anime> listAllAnimes(@RequestParam(required = false) String name) {
        var animes = Anime.listAllAnimes();
        if (name == null) return animes;

        return animes.stream().filter(anime -> anime.getName().equals(name)).toList();
    }

    @GetMapping("{id}")
    public Anime getAnimePathVariable(@PathVariable Long id) {
        return Anime.listAllAnimes().stream().filter(anime -> anime.getId().equals(id)).findFirst().orElse(null);
    }

    @PostMapping()
    public Anime createAnime(@RequestBody Anime anime) {
        anime.setId(ThreadLocalRandom.current().nextLong(1, 100000));
        Anime.listAllAnimes().add(anime);
        return anime;
    }
}
