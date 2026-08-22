package academy.devdojo.controller;

import academy.devdojo.domain.Producer;
import academy.devdojo.mapper.ProducerMapper;
import academy.devdojo.request.ProducerPostRequest;
import academy.devdojo.request.ProducerPutRequest;
import academy.devdojo.response.ProducerGetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController()
@RequestMapping("/v1/producers")
@Slf4j
public class ProducerController {
    private static final ProducerMapper PRODUCER_MAPPER = ProducerMapper.INSTANCE;
    @GetMapping("")
    public List<ProducerGetResponse> listAll(@RequestParam(required = false) String name) {
        var producers = PRODUCER_MAPPER.toProducerGetResponseList(Producer.listAllProducers());
        if (name == null) return producers;

        return producers.stream().filter(anime -> anime.getName().equals(name)).toList();
    }

    @GetMapping("{id}")
    public ResponseEntity<ProducerGetResponse> getProducerPathVariable(@PathVariable Long id) {
        var response = Producer.listAllProducers().stream()
                .filter(producer -> producer.getId().equals(id))
                .findFirst()
                .map(PRODUCER_MAPPER::toProducerGetResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found"));
        return ResponseEntity.ok(response);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "x-api-key")
    public ResponseEntity<ProducerGetResponse> createAnime(@RequestBody ProducerPostRequest producer,  @RequestHeader HttpHeaders headers) {
        log.info("{}", headers);

        var newProducer = PRODUCER_MAPPER.toProducer(producer);
        var responseProducer = PRODUCER_MAPPER.toProducerGetResponse(newProducer);

        Producer.listAllProducers().add(newProducer);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseProducer);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAnime(@PathVariable Long id) {
        log.debug("Request to delete anime by id: {}", id);
        var producers = Producer.listAllProducers();
        boolean removed = producers.removeIf(producer -> producer.getId().equals(id));

        if (!removed) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found");
        }

        return ResponseEntity.noContent().build();
    }

    @PutMapping()
    public ResponseEntity<ProducerGetResponse> updateProducer(@RequestBody ProducerPutRequest request) {
        log.debug("Request to update producer by id: {}", request);

        var producers = Producer.listAllProducers();
        var producerToUpdate = producers.stream()
                .filter(p -> p.getId().equals(request.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found"));

        var producer = PRODUCER_MAPPER.toProducer(request, producerToUpdate.getCreatedAt());
        producers.remove(producerToUpdate);
        producers.add(producer);

        return ResponseEntity.noContent().build();
    }
}
