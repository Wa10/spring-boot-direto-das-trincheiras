package academy.devdojo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/v1/animes")
public class AnimeController {

    private static final List<String> ANIMES = List.of(
            "Naruto",
            "Dragon Ball Z",
            "One Piece",
            "Bleach",
            "Attack on Titan"
    );

    @GetMapping()
    public List<String> animes(){
        return ANIMES;
    }
}
