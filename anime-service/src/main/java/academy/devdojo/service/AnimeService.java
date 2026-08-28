package academy.devdojo.service;

import academy.devdojo.domain.Anime;
import academy.devdojo.repository.AnimeHardCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeHardCodeRepository animeHardCodeRepository;

    public List<Anime> findAll(String name) {
        return name == null ? animeHardCodeRepository.findAll() : animeHardCodeRepository.findByName(name);
    }

    public Anime findById(Long id) {
        return animeHardCodeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));
    }

    public List<Anime> findByName(String name) {
        return animeHardCodeRepository.findByName(name);
    }

    public Anime save(Anime anime) {
        return animeHardCodeRepository.save(anime);
    }

    public void delete(Long id) {
        Anime anime = findById(id);
        animeHardCodeRepository.delete(anime);
    }

    public void update(Anime animeToUpdate) {
        assertAnimeExists(animeToUpdate.getId());
        animeHardCodeRepository.update(animeToUpdate);
    }

    public void assertAnimeExists(Long id) {
        findById(id);
    }
}
