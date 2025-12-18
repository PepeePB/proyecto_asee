package es.unex.aos.apigateway.filters;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.List;

@Component
public class AuthTokenFilter implements GlobalFilter, Ordered {

    private final WebClient webClient;

    public AuthTokenFilter(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://musicfly-users").build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        HttpMethod method = exchange.getRequest().getMethod();

        if (HttpMethod.OPTIONS.equals(method) || path.startsWith("/access")) {
            return chain.filter(exchange); // dejar pasar /access/** sin filtrar
        }

        // Obtener token de header o cookie
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (token == null || token.isEmpty()) {
            List<HttpCookie> cookies = exchange.getRequest().getCookies().get("token");
            if (cookies != null && !cookies.isEmpty()) {
                token = cookies.get(0).getValue();
            }
        }

        if (token == null || token.isEmpty()) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Validar token con microservicio auth
        String finalToken = token;
        return webClient.post()
                .uri("/api/verified")
                .cookie("token", (token.contains("Bearer")?token.split(" ")[1]:token))
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return chain.filter(exchange); // Token vÃ¡lido
                    } else {
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    }
                })
                .onErrorResume(err -> {
                    System.err.println("EL TOKEN ERRONEO: "+ finalToken);
                    err.printStackTrace();
                    exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                    return exchange.getResponse().setComplete();
                });

    }

    @Override
    public int getOrder() {
        return -1; // Ejecutar antes que otros filtros
    }
}