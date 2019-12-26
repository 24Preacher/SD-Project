package Servidor;

import java.util.Objects;

public class Utilizador {
    private String username;
    private String password;
    private MensagemBuffer msg;

    public Utilizador(String username, String password) {
        this.username = username;
        this.password = password;
        this.msg = new MensagemBuffer();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public MensagemBuffer getMsg() {
        return msg;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMsg(MensagemBuffer msg) {
        this.msg = msg;
    }

    public boolean autenticar(String password) {
        return this.password.equals(password);
    }
}
