package ap1.leandro.cahuana.repository;

import ap1.leandro.cahuana.model.WhatsApp;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface WhatsAppRepository extends ReactiveMongoRepository<WhatsApp, String>{
    
}
