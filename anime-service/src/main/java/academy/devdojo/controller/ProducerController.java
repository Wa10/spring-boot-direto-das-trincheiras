package academy.devdojo.controller;

import academy.devdojo.domain.Producer;
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

        Producer newProducer = Producer.builder()
                .id(ThreadLocalRandom.current().nextLong(1, 100000))
                .name(producer.getName())
                .createdAt(LocalDateTime.now())
                .build();

        Producer.listAllProducers().add(newProducer);

        ProducerGetResponse responseProducer = ProducerGetResponse.builder()
                .id(newProducer.getId())
                .name(newProducer.getName())
                .createdAt(newProducer.getCreatedAt())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseProducer);
    }
}
