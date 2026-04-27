package ap1.leandro.cahuana.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ap1.leandro.cahuana.dto.WhatsAppRequest;
import ap1.leandro.cahuana.dto.WhatsAppResponse;
import ap1.leandro.cahuana.model.WhatsApp;
import ap1.leandro.cahuana.repository.WhatsAppRepository;
import ap1.leandro.cahuana.service.WhatsAppService;

import org.springframework.beans.factory.annotation.Value;

@Service
public class WhatsAppServiceImpl implements WhatsAppService {

    private final WebClient webClient;
    private final WhatsAppRepository repository;

    @Value("${rapidapi.whatsapp.key}")
    private String apiKey;

    @Value("${rapidapi.whatsapp.uri}")
    private String uri;

    @Value("${rapidapi.whatsapp.host}")
    private String host;

    public WhatsAppServiceImpl(WebClient webClient, WhatsAppRepository repository) {
        this.webClient = webClient;
        this.repository = repository;
    }

    @Override
public Mono<WhatsApp> validarNumero(String numero) {

    String numeroLimpio = numero.replaceAll("\\s+", "");

    return webClient.post()
            .uri(uri)
            .header("x-rapidapi-host", host)
            .header("x-rapidapi-key", apiKey)
            .header("Content-Type", "application/json")
            .bodyValue(new WhatsAppRequest(numeroLimpio))
            .retrieve()
            .bodyToMono(WhatsAppResponse.class)
            .map(response -> {

                WhatsApp w = new WhatsApp();

                w.setPhoneNumber(numeroLimpio);
                w.setValid("valid".equalsIgnoreCase(response.getStatus()));
                w.setCountry("N/A");

                return w;
            })
            .flatMap(repository::save);
}
}