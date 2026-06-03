package ap1.leandro.cahuana.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

import java.time.LocalDateTime;
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
    private boolean active = true;

    @CreatedDate
    private LocalDateTime createdAt;
 
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Data
    public static class ChunkData {
        private double offset;
        private double duration;
        private String lang;
        private String text;
    }
}