package ap1.leandro.cahuana.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ap1.leandro.cahuana.model.WhatsApp;

public interface WhatsAppService {

    Flux<WhatsApp> listarTodos(Boolean active);

    Mono<WhatsApp> validarNumero(String numero);

    Mono<WhatsApp> obtenerPorId(String id);

    Mono<WhatsApp> actualizarNumero(String id);

    Mono<WhatsApp> eliminar(String id);

    Mono<WhatsApp> restaurar(String id);
}