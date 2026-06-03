package ap1.leandro.cahuana.dto;
import java.util.List;
import lombok.Data;

@Data
public class ApiResponse {
    private List<Chunk> chunks;
    private String text;

    @Data
    public static class Chunk {
        private double offset;
        private double duration;
        private String lang;
        private String text;
}
}