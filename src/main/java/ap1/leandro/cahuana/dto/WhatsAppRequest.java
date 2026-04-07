package ap1.leandro.cahuana.dto;

public class WhatsAppRequest {
    private String phone_number;

    public WhatsAppRequest(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}