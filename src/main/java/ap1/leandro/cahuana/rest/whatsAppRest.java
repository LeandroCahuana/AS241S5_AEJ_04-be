package ap1.leandro.cahuana.rest;

import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
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

    @GetMapping("/registros")
    public Flux<WhatsApp> listar(@RequestParam(required = false) Boolean active) {
        return service.listarTodos(active);
    }

    @GetMapping("/registros/{id}")
    public Mono<WhatsApp> obtenerPorId(@PathVariable String id) {
        return service.obtenerPorId(id);
    }

    @PostMapping("/validar")
    public Mono<WhatsApp> validar(@RequestParam String numero) {
        return service.validarNumero(numero);
    }

    @PutMapping("/registros/revalidar/{id}")
    public Mono<WhatsApp> actualizar(@PathVariable String id) {
        return service.actualizarNumero(id);
    }

    @DeleteMapping("/registros/delete/{id}")
    public Mono<WhatsApp> eliminar(@PathVariable String id) {
        return service.eliminar(id);
    }

    @PatchMapping("/registros/restaurar/{id}")
    public Mono<WhatsApp> restaurar(@PathVariable String id) {
        return service.restaurar(id);
    }

}