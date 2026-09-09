package academy.devdojo.repository;

import academy.devdojo.domain.Anime;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnimeData {
    private final List<Anime> animes = new ArrayList<>();

    {
        animes.add(Anime.builder().id(1L).name("DBZ").build());
        animes.add(Anime.builder().id(2L).name("Attack On Titan").build());
        animes.add(Anime.builder().id(3L).name("Naruto").build());
        animes.add(Anime.builder().id(4L).name("One Piece").build());
        animes.add(Anime.builder().id(5L).name("Jujutsu Kaisen").build());
        animes.add(Anime.builder().id(6L).name("Demon Slayer").build());
    }

    public List<Anime> getAnimes() {
        return animes;
    }
}
