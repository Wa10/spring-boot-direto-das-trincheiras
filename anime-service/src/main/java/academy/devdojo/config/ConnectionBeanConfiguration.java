package academy.devdojo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ConnectionBeanConfiguration {
    private final ConnectionConfigurationProperties connectionConfiguration;

    @Bean
    public Connection connectionMysql(){
        return new Connection(
                connectionConfiguration.url(),
                connectionConfiguration.username(),
                connectionConfiguration.password());
    }

//    @Bean(name = "connectionMongoDB")
//    public Connection connectionMongo(){
//        return new Connection("localhost", "devdojoMongo", "goku");
//    }

}
