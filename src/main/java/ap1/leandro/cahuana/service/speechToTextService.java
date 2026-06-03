package ap1.leandro.cahuana.service;

import ap1.leandro.cahuana.model.speechToText;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface speechToTextService {

    Flux<speechToText> listarTodos(Boolean active);

    Mono<speechToText> transcribir(String url, String lang);

    Mono<speechToText> buscarPorId(String id);

    Mono<speechToText> actualizar(String id, String nuevaUrl, String nuevoLang);

    Mono<speechToText> eliminar(String id);

    Mono<speechToText> restaurar(String id);
    
}
