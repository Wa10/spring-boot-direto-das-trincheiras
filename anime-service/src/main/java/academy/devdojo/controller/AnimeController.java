package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import academy.devdojo.mapper.AnimeMapper;
import academy.devdojo.request.AnimePostRequest;
import academy.devdojo.request.AnimePutRequest;
import academy.devdojo.response.AnimeGetResponse;
import academy.devdojo.response.AnimePostResponse;
import academy.devdojo.service.AnimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/animes")
@Slf4j
public class AnimeController {
    private static final AnimeMapper ANIME_MAPPER = AnimeMapper.INSTANCE;
    private AnimeService animeService;

    public AnimeController() {
        this.animeService = new AnimeService();
    }

    @GetMapping("")
    public ResponseEntity<List<AnimeGetResponse>> listAllAnimes(@RequestParam(required = false) String name) {
        log.debug("Request to list all animes, param name '{}'", name);
        var animes = animeService.findAll(name);

        var  animeGetResponseList =ANIME_MAPPER.toAnimesGetResponseList(animes);

        return ResponseEntity.ok(animeGetResponseList);
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimeGetResponse> getAnimePathVariable(@PathVariable Long id) {
        log.debug("Request to find anime by id: {}", id);

        var anime = animeService.findById(id);

        var animeGetResponse = ANIME_MAPPER.toAnimeGetResponse(anime);

        return ResponseEntity.ok(animeGetResponse);
    }

    @PostMapping()
    public ResponseEntity<AnimePostResponse> createAnime(@RequestBody AnimePostRequest animePostRequest) {
        log.debug("Request to create anime: {}", animePostRequest);
        var anime = ANIME_MAPPER.toAnime(animePostRequest);

        var animeSaved = animeService.save(anime);

        var animeGetResponse = ANIME_MAPPER.toAnimePostResponse(animeSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(animeGetResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAnimePathVariable(@PathVariable Long id) {
        log.debug("Request to delete anime by id: {}", id);
        animeService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("")
    public ResponseEntity<Void> updateAnime(@RequestBody AnimePutRequest animePostRequest) {
        log.debug("Request to update anime: {}", animePostRequest);
        Anime anime = ANIME_MAPPER.toAnime(animePostRequest);

        animeService.update(anime);

        return ResponseEntity.noContent().build();
    }
}