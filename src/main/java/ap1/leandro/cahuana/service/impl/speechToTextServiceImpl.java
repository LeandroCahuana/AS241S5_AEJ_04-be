package ap1.leandro.cahuana.service.impl;

import ap1.leandro.cahuana.dto.ApiResponse;
import ap1.leandro.cahuana.model.speechToText;
import ap1.leandro.cahuana.model.speechToText.ChunkData;
import ap1.leandro.cahuana.service.speechToTextService;
import ap1.leandro.cahuana.repository.speechToTextRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class speechToTextServiceImpl implements speechToTextService {

    private final speechToTextRepository repository;

    private final WebClient webClient;

    @Value("${rapidapi.speech.key}")
    private String apiKey;

    @Value("${rapidapi.speech.host}")
    private String host;

    public speechToTextServiceImpl(WebClient webClient, speechToTextRepository repository) {
        this.webClient = webClient;
        this.repository = repository;
    }

    private Mono<ApiResponse> consultarApi(String url, String lang) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host(host)
                        .path("/transcribe")
                        .queryParam("url", url)
                        .queryParam("lang", lang)
                        .queryParam("task", "transcribe")
                        .build())
                .header("x-rapidapi-host", host)
                .header("x-rapidapi-key", apiKey)
                .retrieve()
                .bodyToMono(ApiResponse.class);
    }

    private speechToText mapearRespuesta(speechToText entidad, String url, String lang, ApiResponse response) {
        entidad.setUrl(url);
        entidad.setLang(lang);
        entidad.setText(response.getText());

        List<ChunkData> chunks = response.getChunks().stream().map(c -> {
            ChunkData cd = new ChunkData();
            cd.setOffset(c.getOffset());
            cd.setDuration(c.getDuration());
            cd.setLang(c.getLang());
            cd.setText(c.getText());
            return cd;
        }).toList();

        entidad.setChunks(chunks);
        return entidad;
    }

    @Override
    public Mono<speechToText> transcribir(String url, String lang) {
        return consultarApi(url, lang)
                .map(response -> {
                    speechToText t = new speechToText();
                    t.setActive(true);
                    return mapearRespuesta(t, url, lang, response);
                })
                .flatMap(repository::save);
    }

    @Override
    public Flux<speechToText> listarTodos(Boolean active) {
        if (active != null) {
            return repository.findAllByActive(active);
        }
        return repository.findAll();
    }

    @Override
    public Mono<speechToText> buscarPorId(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(
                        new RuntimeException("Transcripción no encontrada con id: " + id)));
    }

    @Override
    public Mono<speechToText> actualizar(String id, String nuevaUrl, String nuevoLang) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(
                        new RuntimeException("Transcripción no encontrada con id: " + id)))
                .flatMap(existente -> {
                    // Si no se manda un nuevo lang, conservar el que ya tenía
                    String langFinal = (nuevoLang != null && !nuevoLang.isBlank())
                            ? nuevoLang
                            : existente.getLang();

                    return consultarApi(nuevaUrl, langFinal)
                            .map(response -> mapearRespuesta(existente, nuevaUrl, langFinal, response))
                            .flatMap(repository::save);
                });
    }

    @Override
    public Mono<speechToText> eliminar(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(
                        new RuntimeException("Transcripción no encontrada con id: " + id)))
                .flatMap(existente -> {
                    existente.setActive(false);
                    return repository.save(existente);
                });
    }

    @Override
    public Mono<speechToText> restaurar(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(
                        new RuntimeException("Transcripción no encontrada con id: " + id)))
                .flatMap(existente -> {
                    existente.setActive(true);
                    return repository.save(existente);
                });
    }
}
