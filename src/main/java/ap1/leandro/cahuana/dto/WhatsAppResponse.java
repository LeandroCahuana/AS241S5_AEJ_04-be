package ap1.leandro.cahuana.dto;

import lombok.Data;

@Data
public class WhatsAppResponse {
    private String status;

    public String getStatus() {
    return status;
}
}