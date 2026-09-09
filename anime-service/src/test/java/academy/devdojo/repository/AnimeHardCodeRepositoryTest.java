package academy.devdojo.repository;

import academy.devdojo.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimeHardCodeRepositoryTest {

    @InjectMocks
    private AnimeHardCodeRepository repository;

    @Mock
    private AnimeData animeData;

    private final List<Anime> animeList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        animeList.add(Anime.builder().id(1L).name("Naruto").build());
        animeList.add(Anime.builder().id(2L).name("Attack on Titan").build());
        animeList.add(Anime.builder().id(3L).name("Mob Psycho 100").build());
        animeList.add(Anime.builder().id(4L).name("Bleach").build());
    }

    @Test
    @Order(1)
    @DisplayName("findAll returns a list with all animes")
    void findAll_RerturnsAllAnimes_WhenSuccessful() {
        when(animeData.getAnimes()).thenReturn(animeList);

        var animes = repository.findAll();
        Assertions.assertThat(animes).hasSize(animes.size());
    }

    @Test
    @Order(2)
    @DisplayName("findById returns am anime with given id")
    void findAll_ReturnsAnimeById_WhenSuccessful() {
        when(animeData.getAnimes()).thenReturn(animeList);

        var expectedAnime = animeList.getFirst();
        var animes = repository.findById(expectedAnime.getId());
        Assertions.assertThat(animes).isPresent().contains(expectedAnime);
    }

    @Test
    @Order(3)
    @DisplayName("findByName returns empty list when name is null")
    void findByName_ReturnsEmptyList_WhenNameIsNull(){
        when(animeData.getAnimes()).thenReturn(animeList);

        var animes = repository.findByName(null);
        Assertions.assertThat(animes).isNotNull().isEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("findByName returns list with found object when name exists")
    void findByName_ReturnsFoundAnimeInList_WhenNameExists(){
        when(animeData.getAnimes()).thenReturn(animeList);
        var expectedAnime = animeList.getFirst();

        var animes = repository.findByName(expectedAnime.getName());

        Assertions.assertThat(animes).contains(expectedAnime);
    }

    @Test
    @Order(5)
    @DisplayName("save creates am anime")
    void save_CreatesAnimer_WhenSuccessful() {
        when(animeData.getAnimes()).thenReturn(animeList);

        var animeToSave = Anime.builder().id(99L).name("My Hero Academic").build();
        var anime = repository.save(animeToSave);

        Assertions.assertThat(anime).isEqualTo(animeToSave).hasNoNullFieldsOrProperties();

        var producerSaveOptional = repository.findById(anime.getId());
        Assertions.assertThat(producerSaveOptional).isPresent().contains(animeToSave);
    }

    @Test
    @Order(6)
    @DisplayName("delete removes am anime")
    void delete_RemovesAnime_WhenSuccessful() {
        when(animeData.getAnimes()).thenReturn(animeList);

        var producerToDelete = animeList.getFirst();
        repository.delete(producerToDelete);

        var animes = repository.findAll();

        Assertions.assertThat(animes).isNotEmpty().doesNotContain(producerToDelete);
    }

    @Test
    @Order(7)
    @DisplayName("update an anime")
    void update_UpdateAnime_WhenSuccessful() {
        when(animeData.getAnimes()).thenReturn(animeList);
        var animeToUpdate = animeList.getFirst();
        animeToUpdate.setName("Trigun");

        repository.update(animeToUpdate);

        Assertions.assertThat(this.animeList).contains(animeToUpdate);

        var animeUpdateOptional = repository.findById(animeToUpdate.getId());
        Assertions.assertThat(animeUpdateOptional).isPresent();
        Assertions.assertThat(animeUpdateOptional.get().getName()).isEqualTo(animeToUpdate.getName());
    }
}