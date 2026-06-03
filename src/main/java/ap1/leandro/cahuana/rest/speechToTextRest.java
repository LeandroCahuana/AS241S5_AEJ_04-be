package ap1.leandro.cahuana.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ap1.leandro.cahuana.model.speechToText;
import ap1.leandro.cahuana.service.speechToTextService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api1")
public class speechToTextRest {
    
    private final speechToTextService service;

    public speechToTextRest(speechToTextService service) {
        this.service = service;
    }

    @GetMapping("/transcripciones")
    public Flux<speechToText> listar(@RequestParam(required = false) Boolean active) {
        return service.listarTodos(active);
    }

    @GetMapping("/transcripciones/{id}")
    public Mono<speechToText> obtenerPorId(@PathVariable String id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/transcribir")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<speechToText> transcribir(@RequestParam String url, @RequestParam String lang) {
        return service.transcribir(url, lang);
    }

    @PutMapping("/transcripciones/{id}")
    public Mono<speechToText> actualizar(
            @PathVariable String id,
            @RequestParam String url,
            @RequestParam(required = false) String lang) {
        return service.actualizar(id, url, lang);
    }

    @DeleteMapping("/transcripciones/delete/{id}")
    public Mono<speechToText> eliminar(@PathVariable String id) {
        return service.eliminar(id);
    }

    @PatchMapping("/transcripciones/restaurar/{id}")
    public Mono<speechToText> restaurar(@PathVariable String id) {
        return service.restaurar(id);
    }

}
