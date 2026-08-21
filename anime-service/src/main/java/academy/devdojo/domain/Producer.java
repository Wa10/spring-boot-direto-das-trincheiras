package academy.devdojo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Producer {

    private Long id;
    private String name;
    private static final List<Producer> PRODUCERS = new ArrayList<>();

    static {
        PRODUCERS.add(new Producer(1L, "Mappa"));
        PRODUCERS.add(new Producer(2L, "Madhouse"));
    }

    public static List<Producer> listAllProducers() {
        return PRODUCERS;
    }

}
