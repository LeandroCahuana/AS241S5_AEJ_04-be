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

    @Value("${rapidapi.key}")
    private String apiKey;

    public WhatsAppServiceImpl(WebClient webClient, WhatsAppRepository repository) {
        this.webClient = webClient;
        this.repository = repository;
    }

    @Override
public Mono<WhatsApp> validarNumero(String numero) {

    String numeroLimpio = numero.replaceAll("\\s+", "");

    return webClient.post()
            .uri("https://whatsapp-number-validator3.p.rapidapi.com/WhatsappNumberHasItWithToken")
            .header("x-rapidapi-host", "whatsapp-number-validator3.p.rapidapi.com")
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