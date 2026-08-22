package academy.devdojo.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Producer {
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private static final List<Producer> PRODUCERS = new ArrayList<>();

    static {
        PRODUCERS.add(Producer.builder().id(1L).name("Mappa").createdAt(LocalDateTime.now()).build());
        PRODUCERS.add(Producer.builder().id(2L).name("Madhouse").createdAt(LocalDateTime.now()).build());
    }

    public static List<Producer> listAllProducers() {
        return PRODUCERS;
    }

}
