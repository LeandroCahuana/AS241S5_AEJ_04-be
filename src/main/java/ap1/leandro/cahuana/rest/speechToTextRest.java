package ap1.leandro.cahuana.rest;

import org.springframework.web.bind.annotation.*;

import ap1.leandro.cahuana.model.speechToText;
import ap1.leandro.cahuana.service.speechToTextService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api1")
public class speechToTextRest {
    
    private final speechToTextService service;

    public speechToTextRest(speechToTextService service) {
        this.service = service;
    }

    @GetMapping("/transcribir")
    public Mono<speechToText> transcribir(@RequestParam String url, String lang) {
        return service.transcribir(url, lang);
    }

    @GetMapping("/transcripcion/{id}")
    public Mono<speechToText> obtenerPorId(@PathVariable String id) {
        return service.buscarPorId(id);
    }

}
