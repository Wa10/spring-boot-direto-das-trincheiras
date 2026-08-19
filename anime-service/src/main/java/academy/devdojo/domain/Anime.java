package academy.devdojo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Anime {
    private Long id;
    private String name;

    private static final List<Anime> ANIMES = new ArrayList<>();

    static {
        ANIMES.add(new Anime(1L, "DBZ"));
        ANIMES.add(new Anime(2L, "Attack On Titan"));
    }

    public static List<Anime> listAllAnimes() {
        return ANIMES;
    }

}
