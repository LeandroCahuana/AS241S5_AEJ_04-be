package ap1.leandro.cahuana.repository;

import ap1.leandro.cahuana.model.WhatsApp;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface WhatsAppRepository extends ReactiveMongoRepository<WhatsApp, String>{

    Flux<WhatsApp> findAllByActive(boolean active);

    Mono<WhatsApp> findByPhoneNumber(String phoneNumber);
    
}
