package academy.devdojo.service;

import academy.devdojo.domain.Producer;
import academy.devdojo.repository.ProducerHardCodeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static java.util.Collections.singletonList;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerServiceTest {

    @InjectMocks
    private ProducerService service;
    @Mock
    private ProducerHardCodeRepository repository;
    private List<Producer> producerList;

    @BeforeEach
    void setUp() {
        var mappa = Producer.builder().id(1L).name("Mappa").createdAt(LocalDateTime.now()).build();
        var madhouse = Producer.builder().id(2L).name("Madhouse").createdAt(LocalDateTime.now()).build();

        producerList = new ArrayList<>(List.of(mappa, madhouse));
    }

    @Test
    @DisplayName("findAll returns a list with all producers when argument is null")
    @Order(1)
    void findAll_ReturnsAllProducers_WhenArgumentIsNull() {
        when(repository.findAll()).thenReturn(producerList);

        var producers = service.findAll(null);
        Assertions.assertThat(producers).isNotNull().hasSameElementsAs(producerList);
    }

    @Test
    @DisplayName("findAll returns list with found object when name exists")
    @Order(2)
    void findByName_ReturnsFoundProducerInList_WhenNameIsFound() {
        var producer = producerList.getFirst();
        var expectedProducersFound = singletonList(producer);

        when(repository.findByName(producer.getName())).thenReturn(expectedProducersFound);

        var producers = service.findAll(producer.getName());
        Assertions.assertThat(producers).isNotNull().hasSameElementsAs(expectedProducersFound);

        var producersFound = service.findAll(producer.getName());
        Assertions.assertThat(producersFound).containsAll(expectedProducersFound);
    }

    @Test
    @DisplayName("findAll returns empty list when name is not found")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenNameIsNotFound() {
        var name = "Invalid Name";
        when(repository.findByName(name)).thenReturn(List.of());

        var producers = service.findAll(name);
        Assertions.assertThat(producers).isNotNull().isEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("findById returns a producer with given id")
    void findById_ReturnsProducerById_WhenSuccessful() {
        var expectedProducer = producerList.getFirst();

        when(repository.findById(expectedProducer.getId())).thenReturn(Optional.of(expectedProducer));

        var producers = service.findById(expectedProducer.getId());

        Assertions.assertThat(producers).isEqualTo(expectedProducer);
    }

    @Test
    @Order(5)
    @DisplayName("findById throws ResponseStatusException when producer is not found")
    void findById_ThrowsResponseStatusException_WhenProducerIsNotFound() {
        var expectedProducer = producerList.getFirst();

        when(repository.findById(expectedProducer.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.findById(expectedProducer.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @Order(6)
    @DisplayName("save creates a producer")
    void save_CreatesProducer_WhenSuccessful() {
        var producerToSave = Producer.builder().id(99L).name("Ghibli").createdAt(LocalDateTime.now()).build();

        when(repository.save(producerToSave)).thenReturn(producerToSave);

        var savedProducer = service.save(producerToSave);

        Assertions.assertThat(savedProducer).isEqualTo(producerToSave).hasNoNullFieldsOrProperties();
    }

    @Test
    @Order(7)
    @DisplayName("delete removes a producer")
    void delete_RemovesProducer_WhenSuccessful() {
        var expectedProducerToDelete = producerList.getFirst();

        when(repository.findById(expectedProducerToDelete.getId())).thenReturn(Optional.of(expectedProducerToDelete));
        doNothing().when(repository).delete(expectedProducerToDelete);

        service.delete(expectedProducerToDelete.getId());

        Assertions.assertThatNoException().isThrownBy(() -> service.findById(expectedProducerToDelete.getId()));
    }

    @Test
    @Order(8)
    @DisplayName("delete throws ResponseStatusException when producer is not found")
    void delete_ThrowsResponseStatusException_WhenProducerIsNotFound() {
        var expectedProducerToDelete = producerList.getFirst();

        when(repository.findById(expectedProducerToDelete.getId())).thenReturn(Optional.empty());


        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(expectedProducerToDelete.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @Order(9)
    @DisplayName("update a producer")
    void update_UpdateProducer_WhenSuccessful() {
        var producerToUpdate = producerList.getFirst();
        producerToUpdate.setName("Ufotable");

        when(repository.findById(producerToUpdate.getId())).thenReturn(Optional.of(producerToUpdate));
        doNothing().when(repository).update(producerToUpdate);

        service.update(producerToUpdate);

        Assertions.assertThatNoException().isThrownBy(() -> service.update(producerToUpdate));
    }

    @Test
    @Order(9)
    @DisplayName("update throws ResponseStatusException when producer is not found")
    void update_ThrowsResponseStatusException_WhenProducerIsNotFound() {
        var producerToUpdate = producerList.getFirst();

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(producerToUpdate.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }
}