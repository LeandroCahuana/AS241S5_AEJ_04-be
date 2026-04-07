package ap1.leandro.cahuana.service;

import ap1.leandro.cahuana.model.speechToText;
import reactor.core.publisher.Mono;

public interface speechToTextService {

    Mono<speechToText> transcribir(String url, String lang);

    Mono<speechToText> buscarPorId(String id);
    
}
