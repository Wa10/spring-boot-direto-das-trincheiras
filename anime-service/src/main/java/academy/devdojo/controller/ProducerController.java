package academy.devdojo.controller;

import academy.devdojo.domain.Producer;
import academy.devdojo.mapper.ProducerMapper;
import academy.devdojo.request.ProducerPostRequest;
import academy.devdojo.response.ProducerGetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController()
@RequestMapping("/v1/producers")
@Slf4j
public class ProducerController {
    private static final ProducerMapper PRODUCER_MAPPER = ProducerMapper.INSTANCE;
    @GetMapping("")
    public List<Producer> listAll(@RequestParam(required = false) String name) {
        var producers = Producer.listAllProducers();
        if (name == null) return producers;

        return producers.stream().filter(anime -> anime.getName().equals(name)).toList();
    }

    @GetMapping("{id}")
    public ResponseEntity<Producer> getProducerPathVariable(@PathVariable Long id) {
        return ResponseEntity.ok(Producer.listAllProducers().stream().filter(producer -> producer.getId().equals(id)).findFirst().orElse(null));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "x-api-key")
    public ResponseEntity<ProducerGetResponse> createAnime(@RequestBody ProducerPostRequest producer,  @RequestHeader HttpHeaders headers) {
        log.info("{}", headers);

        var newProducer = PRODUCER_MAPPER.toProducer(producer);
        var responseProducer = PRODUCER_MAPPER.toProducerGetResponse(newProducer);

        Producer.listAllProducers().add(newProducer);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseProducer);
    }
}
