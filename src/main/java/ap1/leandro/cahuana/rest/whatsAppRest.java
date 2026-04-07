package ap1.leandro.cahuana.rest;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ap1.leandro.cahuana.model.WhatsApp;
import ap1.leandro.cahuana.service.WhatsAppService;

@RestController
@RequestMapping("/api2")
public class WhatsAppRest {

    private final WhatsAppService service;

    public WhatsAppRest(WhatsAppService service) {
        this.service = service;
    }

    @PostMapping("/validar")
    public Mono<WhatsApp> validar(@RequestParam String numero) {
        return service.validarNumero(numero);
    }
}