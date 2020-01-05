package Servidor;

/**
 * Classe Utilizador.
 * @author Grupo 12
 */

public class Utilizador {
    /**Username do Utilizador*/
    private String username;
    /**Password do Utilizador*/
    private String password;
    /**Buffer de Mensagens do Utilizador*/
    private MensagemBuffer msg;

    /**
     * Construtor Parametrizado da classe Utilizador
     * @param username Username do Utilizador
     * @param password Password do Utilizador
     */
    public Utilizador(String username, String password) {
        this.username = username;
        this.password = password;
        this.msg = new MensagemBuffer();
    }

    /**
     * Devolve o Username do Utilizador
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Devolve a Password do Utilizador
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Devolve o Buffer de Mensagens do Utilizador
     * @return msg
     */
    public MensagemBuffer getMsg() {
        return msg;
    }

    /**
     * Atualiza o Username do Utilizador
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Atualiza a Password do Utilizador
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }


    public void writeNotification( String message ) {
        msg.write(message);
    }

    /**
     * Atualiza o Buffer de Mensagens do Utilizador
     * @param msg
     */
    public void setMsg(MensagemBuffer msg) {
        this.msg = msg;
    }

    /**
     * Verifica se a password passada como parametro é igual à do Utilizador
     * @param password
     * @return True se corresponder, False se não corresponder
     */
    public boolean autenticar(String password) {
        return this.password.equals(password);
    }

    public String readNotification() throws InterruptedException {
        return msg.read();
    }
}
