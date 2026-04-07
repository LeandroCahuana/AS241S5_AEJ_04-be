package ap1.leandro.cahuana.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.util.List;

@Data
@Document(collection = "SpeechToText")
public class speechToText {
    
    @Id
    private String id;
    private String url;
    private String lang;
    private String text;
    private List<ChunkData> chunks;

    @Data
    public static class ChunkData {
        private double offset;
        private double duration;
        private String lang;
        private String text;
    }
}