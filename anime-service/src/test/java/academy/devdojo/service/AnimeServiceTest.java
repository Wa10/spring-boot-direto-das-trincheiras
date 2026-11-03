package academy.devdojo.service;

import academy.devdojo.domain.Anime;
import academy.devdojo.repository.AnimeHardCodeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AnimeServiceTest {

    @InjectMocks
    private AnimeService service;
    @Mock
    private AnimeHardCodeRepository repository;
    private List<Anime> animeList;

    @BeforeEach
    void setUp() {
        var naruto = Anime.builder().id(1L).name("Naruto").build();
        var attackOnTitan = Anime.builder().id(2L).name("Attack on Titan").build();
        var mob = Anime.builder().id(3L).name("Mob Psycho 100").build();
        var bleach = Anime.builder().id(4L).name("Bleach").build();

        animeList = new ArrayList<>(List.of(naruto, attackOnTitan, mob, bleach));
    }

    @Test
    @DisplayName("findAll returns a list with all animes when argument is null")
    @Order(1)
    void findAll_ReturnsAllAnimes_WhenArgumentIsNull() {
        when(repository.findAll()).thenReturn(animeList);

        var animes = service.findAll(null);
        Assertions.assertThat(animes).isNotNull().hasSameElementsAs(animeList);
    }

    @Test
    @DisplayName("findAll returns list with found object when name exists")
    @Order(2)
    void findByName_ReturnsFoundAnimeInList_WhenNameIsFound() {
        var anime = animeList.getFirst();
        var expectedAnimesFound = singletonList(anime);

        when(repository.findByName(anime.getName())).thenReturn(expectedAnimesFound);

        var animes = service.findAll(anime.getName());
        Assertions.assertThat(animes).isNotNull().hasSameElementsAs(expectedAnimesFound);

        var animesFound = service.findAll(anime.getName());
        Assertions.assertThat(animesFound).containsAll(expectedAnimesFound);
    }

    @Test
    @DisplayName("findAll returns empty list when name is not found")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenNameIsNotFound() {
        var name = "Invalid Name";
        when(repository.findByName(name)).thenReturn(List.of());

        var animes = service.findAll(name);
        Assertions.assertThat(animes).isNotNull().isEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("findById returns a anime with given id")
    void findById_ReturnsAnimeById_WhenSuccessful() {
        var expectedAnime = animeList.getFirst();

        when(repository.findById(expectedAnime.getId())).thenReturn(Optional.of(expectedAnime));

        var animes = service.findById(expectedAnime.getId());

        Assertions.assertThat(animes).isEqualTo(expectedAnime);
    }

    @Test
    @Order(5)
    @DisplayName("findById throws ResponseStatusException when anime is not found")
    void findById_ThrowsResponseStatusException_WhenAnimeIsNotFound() {
        var expectedAnime = animeList.getFirst();

        when(repository.findById(expectedAnime.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.findById(expectedAnime.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @Order(6)
    @DisplayName("save creates a anime")
    void save_CreatesAnime_WhenSuccessful() {
        var animeToSave = Anime.builder().id(5L).name("Boku No Hero").build();

        when(repository.save(animeToSave)).thenReturn(animeToSave);

        var savedAnime = service.save(animeToSave);

        Assertions.assertThat(savedAnime).isEqualTo(animeToSave).hasNoNullFieldsOrProperties();
    }

    @Test
    @Order(7)
    @DisplayName("delete removes a anime")
    void delete_RemovesAnime_WhenSuccessful() {
        var expectedAnimeToDelete = animeList.getFirst();

        when(repository.findById(expectedAnimeToDelete.getId())).thenReturn(Optional.of(expectedAnimeToDelete));
        doNothing().when(repository).delete(expectedAnimeToDelete);

        service.delete(expectedAnimeToDelete.getId());

        Assertions.assertThatNoException().isThrownBy(() -> service.findById(expectedAnimeToDelete.getId()));
    }

    @Test
    @Order(8)
    @DisplayName("delete throws ResponseStatusException when anime is not found")
    void delete_ThrowsResponseStatusException_WhenAnimeIsNotFound() {
        var expectedAnimeToDelete = animeList.getFirst();

        when(repository.findById(expectedAnimeToDelete.getId())).thenReturn(Optional.empty());


        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(expectedAnimeToDelete.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @Order(9)
    @DisplayName("update an anime")
    void update_UpdateAnime_WhenSuccessful() {
        var animeToUpdate = animeList.getFirst();
        animeToUpdate.setName("Ufotable");

        when(repository.findById(animeToUpdate.getId())).thenReturn(Optional.of(animeToUpdate));
        doNothing().when(repository).update(animeToUpdate);

        service.update(animeToUpdate);

        Assertions.assertThatNoException()
                .isThrownBy(() -> service.update(animeToUpdate));
    }

    @Test
    @Order(10)
    @DisplayName("update throws ResponseStatusException when anime is not found")
    void update_ThrowsResponseStatusException_WhenAnimeIsNotFound() {
        var animeToUpdate = animeList.getFirst();

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(animeToUpdate.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }
}
