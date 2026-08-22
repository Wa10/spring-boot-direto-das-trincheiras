package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import academy.devdojo.mapper.AnimeMapper;
import academy.devdojo.request.AnimePostRequest;
import academy.devdojo.request.AnimePutRequest;
import academy.devdojo.response.AnimeGetResponse;
import academy.devdojo.response.AnimePostResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/v1/animes")
@Slf4j
public class AnimeController {
    private static final AnimeMapper ANIME_MAPPER = AnimeMapper.INSTANCE;
    @GetMapping("")
    public ResponseEntity<List<AnimeGetResponse>> listAllAnimes(@RequestParam(required = false) String name) {
        log.debug("Request to list all animes, param name '{}'", name);
        var animes = Anime.listAllAnimes();

        var  animeGetResponseList =ANIME_MAPPER.toAnimesGetResponseList(animes);

        if (name == null) {
            return ResponseEntity.ok(animeGetResponseList);
        }

        var animeGetResponses = animeGetResponseList.stream()
                .filter(anime -> anime.getName().equalsIgnoreCase(name))
                .toList();

        return ResponseEntity.ok(animeGetResponses);

    }

    @GetMapping("{id}")
    public ResponseEntity<AnimeGetResponse> getAnimePathVariable(@PathVariable Long id) {
        log.debug("Request to find anime by id: {}", id);

        var animeGetResponse = Anime.listAllAnimes()
                .stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .map(ANIME_MAPPER::toAnimeGetResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));

        return ResponseEntity.ok(animeGetResponse);
    }

    @PostMapping()
    public ResponseEntity<AnimePostResponse> createAnime(@RequestBody AnimePostRequest animePostRequest) {
        log.debug("Request to create anime: {}", animePostRequest);
        var anime = ANIME_MAPPER.toAnime(animePostRequest);
        var animeGetResponse = ANIME_MAPPER.toAnimePostResponse(anime);

        Anime.listAllAnimes().add(anime);

        return ResponseEntity.status(HttpStatus.CREATED).body(animeGetResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAnimePathVariable(@PathVariable Long id) {
        log.debug("Request to delete anime by id: {}", id);
        var animes = Anime.listAllAnimes();
        boolean removed = animes.removeIf(anime -> anime.getId().equals(id));

        if(!removed){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("")
    public ResponseEntity<Void> updateAnime(@RequestBody AnimePutRequest animePostRequest) {
        log.debug("Request to update anime: {}", animePostRequest);
        List<Anime> animes = Anime.listAllAnimes();
        var animeToRemove = Anime.listAllAnimes().stream()
                .filter(anime -> anime.getId().equals(animePostRequest.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));

        var anime = ANIME_MAPPER.toAnime(animePostRequest);
        animes.remove(animeToRemove);
        animes.add(anime);

        return ResponseEntity.noContent().build();
    }
}