package academy.devdojo.repository;

import academy.devdojo.domain.Producer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerHardCodeRepositoryTest {

    @InjectMocks
    private ProducerHardCodeRepository repository;

    @Mock
    private ProducerData producerData;

    private final List<Producer> producerList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        producerList.add(Producer.builder().id(1L).name("Mappa").createdAt(LocalDateTime.now()).build());
        producerList.add(Producer.builder().id(2L).name("Madhouse").createdAt(LocalDateTime.now()).build());
    }

    @Test
    @Order(1)
    @DisplayName("findAll returns a list with all producers")
    void findAll_RerturnsAllProducers_WhenSuccessful() {
        when(producerData.getProducers()).thenReturn(producerList);

        var producers = repository.findAll();
        Assertions.assertThat(producers).hasSize(producers.size());
    }

    @Test
    @Order(2)
    @DisplayName("findById returns a producer with given id")
    void findAll_ReturnsProducerById_WhenSuccessful() {
        when(producerData.getProducers()).thenReturn(producerList);

        var expectedProducer = producerList.getFirst();
        var producers = repository.findById(expectedProducer.getId());
        Assertions.assertThat(producers).isPresent().contains(expectedProducer);
    }

    @Test
    @Order(3)
    @DisplayName("findByName returns empty list when name is null")
    void findByName_ReturnsEmptyList_WhenNameIsNull(){
        when(producerData.getProducers()).thenReturn(producerList);

        var producers = repository.findByName(null);
        Assertions.assertThat(producers).isNotNull().isEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("findByName returns list with found object when name exists")
    void findByName_ReturnsFoundProducerInList_WhenNameExists(){
        when(producerData.getProducers()).thenReturn(producerList);
        var expectedProducer = producerList.getFirst();

        var producers = repository.findByName(expectedProducer.getName());

        Assertions.assertThat(producers).contains(expectedProducer);
    }

    @Test
    @Order(5)
    @DisplayName("save creates a producer")
    void save_CreatesProducer_WhenSuccessful() {
        when(producerData.getProducers()).thenReturn(producerList);

        var producerToSave = Producer.builder().id(99L).name("Ghibli").createdAt(LocalDateTime.now()).build();
        var producer = repository.save(producerToSave);

        Assertions.assertThat(producer).isEqualTo(producerToSave).hasNoNullFieldsOrProperties();

        var producerSaveOptional = repository.findById(producer.getId());
        Assertions.assertThat(producerSaveOptional).isPresent().contains(producerToSave);
    }

    @Test
    @Order(6)
    @DisplayName("delete removes a producer")
    void delete_RemovesProducer_WhenSuccessful() {
        when(producerData.getProducers()).thenReturn(producerList);

        var producerToDelete = producerList.getFirst();
        repository.delete(producerToDelete);

        var producers = repository.findAll();

        Assertions.assertThat(producers).isNotEmpty().doesNotContain(producerToDelete);
    }

    @Test
    @Order(7)
    @DisplayName("update a producer")
    void update_UpdateProducer_WhenSuccessful() {
        when(producerData.getProducers()).thenReturn(producerList);
        var producerToUpdate = producerList.getFirst();
        producerToUpdate.setName("Ufotable");

        repository.update(producerToUpdate);

        Assertions.assertThat(this.producerList).contains(producerToUpdate);

        var producerUpdateOptional = repository.findById(producerToUpdate.getId());
        Assertions.assertThat(producerUpdateOptional).isPresent();
        Assertions.assertThat(producerUpdateOptional.get().getName()).isEqualTo(producerToUpdate.getName());
    }
}