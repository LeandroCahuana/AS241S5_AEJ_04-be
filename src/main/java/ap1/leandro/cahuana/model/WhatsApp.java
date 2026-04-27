package ap1.leandro.cahuana.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "VerificationWhatsApp")
public class WhatsApp {

    @Id
    private String id;
    private String phoneNumber;
    private boolean valid;
    private String country;
}