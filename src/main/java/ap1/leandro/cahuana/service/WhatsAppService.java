package ap1.leandro.cahuana.service;

import reactor.core.publisher.Mono;
import ap1.leandro.cahuana.model.WhatsApp;

public interface WhatsAppService {
    Mono<WhatsApp> validarNumero(String numero);
}