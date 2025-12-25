package ar.edu.huergo.gorodriguez.detectivesoft.dto.security;

public class MensajeDto {
    private String mensaje;

    public MensajeDto() {}

    public MensajeDto(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
