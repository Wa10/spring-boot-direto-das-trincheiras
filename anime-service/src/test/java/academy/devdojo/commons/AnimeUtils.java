package academy.devdojo.commons;

import academy.devdojo.domain.Anime;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnimeUtils {

    public List<Anime> newAnimeList(){
        var naruto = Anime.builder().id(1L).name("Naruto").build();
        var attackOnTitan = Anime.builder().id(2L).name("Attack on Titan").build();
        var mob = Anime.builder().id(3L).name("Mob Psycho 100").build();
        var bleach = Anime.builder().id(4L).name("Bleach").build();

        return new ArrayList<>(List.of(naruto, attackOnTitan, mob, bleach));
    }

    public Anime newAnimeToSave(){
        return Anime.builder().id(99L).name("AOT").build();
    }
}
