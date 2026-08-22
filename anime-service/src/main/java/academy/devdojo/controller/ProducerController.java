package academy.devdojo.controller;

import academy.devdojo.domain.Producer;
import academy.devdojo.mapper.ProducerMapper;
import academy.devdojo.request.ProducerPostRequest;
import academy.devdojo.request.ProducerPutRequest;
import academy.devdojo.response.ProducerGetResponse;
import academy.devdojo.response.ProducerPostResponse;
import academy.devdojo.service.ProducerService;
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
    private final ProducerService producerService;

    public ProducerController() {
        this.producerService = new ProducerService();
    }
    @GetMapping("")
    public ResponseEntity<List<ProducerGetResponse>> listAll(@RequestParam(required = false) String name) {
        log.debug("Request to list all producers, param name '{}'", name);
        var producers = producerService.findAll(name);

        var producerGetResponseList = PRODUCER_MAPPER.toProducerGetResponseList(producers);

        return ResponseEntity.ok(producerGetResponseList);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProducerGetResponse> getProducerPathVariable(@PathVariable Long id) {
        log.debug("Request to get producer by id: {}", id);
        var producer = producerService.findById(id);

        var producerGetResponse = PRODUCER_MAPPER.toProducerGetResponse(producer);

        return ResponseEntity.ok(producerGetResponse);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "x-api-key")
    public ResponseEntity<ProducerPostResponse> createAnime(@RequestBody ProducerPostRequest producer, @RequestHeader HttpHeaders headers) {
        log.info("{}", headers);
        var newProducer = PRODUCER_MAPPER.toProducer(producer);

        var producerSaved =producerService.save(newProducer);

        ProducerPostResponse producerGetResponse = PRODUCER_MAPPER.toProducerPostResponse(producerSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(producerGetResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAnime(@PathVariable Long id) {
        log.debug("Request to delete anime by id: {}", id);
        producerService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping()
    public ResponseEntity<ProducerGetResponse> updateProducer(@RequestBody ProducerPutRequest request) {
        log.debug("Request to update producer by id: {}", request);
        Producer producer = PRODUCER_MAPPER.toProducer(request);

        producerService.update(producer);

        return ResponseEntity.noContent().build();
    }
}
