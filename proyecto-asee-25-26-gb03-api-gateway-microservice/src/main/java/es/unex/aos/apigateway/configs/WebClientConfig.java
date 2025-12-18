package es.unex.aos.apigateway.configs;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced   // Esto permite que 'lb://AUTH-SERVICE' se resuelva vía Eureka
                    // metido para darle mas robustez, ya que tampoco se donde se ejecutará
                    // o incluso si se replicará
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}

