package ap1.leandro.cahuana.repository;

import ap1.leandro.cahuana.model.speechToText;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface speechToTextRepository extends ReactiveMongoRepository<speechToText, String>{
    
}
