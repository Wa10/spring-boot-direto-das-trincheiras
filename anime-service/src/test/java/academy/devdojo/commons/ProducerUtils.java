package academy.devdojo.commons;

import academy.devdojo.domain.Producer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProducerUtils {

    public List<Producer> newProducerList(){
        var dateTime = "2026-11-07T14:27:19.133556";
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        var localDateTime = LocalDateTime.parse(dateTime, formatter);

        var mappa = Producer.builder().id(1L).name("Mappa").createdAt(localDateTime).build();
        var madHouse = Producer.builder().id(2L).name("Madhouse").createdAt(localDateTime).build();
        return new ArrayList<>(List.of(mappa, madHouse));
    }

    public Producer newProducerToSave(){
        return Producer.builder().id(99L).name("MAPPA").createdAt(LocalDateTime.now()).build();
    }
}
