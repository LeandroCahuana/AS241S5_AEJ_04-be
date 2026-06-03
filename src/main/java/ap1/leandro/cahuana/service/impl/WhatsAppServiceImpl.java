package ap1.leandro.cahuana.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
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
    public Flux<WhatsApp> listarTodos(Boolean active) {
        if (active != null) {
            return repository.findAllByActive(active);
        }
        return repository.findAll();
    }

    private Mono<Boolean> consultarApi(String numeroLimpio) {
        return webClient.post()
                .uri(uri)
                .header("x-rapidapi-host", host)
                .header("x-rapidapi-key", apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(new WhatsAppRequest(numeroLimpio))
                .retrieve()
                .bodyToMono(WhatsAppResponse.class)
                .map(response -> "valid".equalsIgnoreCase(response.getStatus()));
    }

    @Override
    public Mono<WhatsApp> validarNumero(String numero) {
        String numeroLimpio = numero.replaceAll("\\s+", "");

        return consultarApi(numeroLimpio)
                .flatMap(esValido -> {
                    WhatsApp w = new WhatsApp();
                    w.setPhoneNumber(numeroLimpio);
                    w.setValid(esValido);
                    w.setCountry("N/A");
                    w.setActive(true);
                    return repository.save(w);
                });
    }

    @Override
    public Mono<WhatsApp> obtenerPorId(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(
                        new RuntimeException("Registro no encontrado con id: " + id)));
    }

    @Override
    public Mono<WhatsApp> actualizarNumero(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(
                        new RuntimeException("Registro no encontrado con id: " + id)))
                .flatMap(existente -> consultarApi(existente.getPhoneNumber())
                        .flatMap(esValido -> {
                            existente.setValid(esValido);
                            return repository.save(existente);
                        }));
    }

    @Override
    public Mono<WhatsApp> eliminar(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(
                        new RuntimeException("Registro no encontrado con id: " + id)))
                .flatMap(existente -> {
                    existente.setActive(false);
                    return repository.save(existente);
                });
    }

    @Override
    public Mono<WhatsApp> restaurar(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(
                        new RuntimeException("Registro no encontrado con id: " + id)))
                .flatMap(existente -> {
                    existente.setActive(true);
                    return repository.save(existente);
                });
    }

}