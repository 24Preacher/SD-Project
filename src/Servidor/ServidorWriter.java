package Servidor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Classe da thread ServidorWriter.
 * @author Grupo 12
 */
public class ServidorWriter implements Runnable{
    /**Buffer de Mensagens*/
    private MensagemBuffer msg;
    /**Socket*/
    private BufferedWriter output;

    /**
     * Construtor Parametrizado da Classe ServidorWriter
     * @param msg Buffer de Mensagens
     * @param cl Socket
     * @throws IOException
     */
    ServidorWriter(MensagemBuffer msg, Socket cl) throws IOException {
        this.msg = msg;
        this.output = new BufferedWriter(new OutputStreamWriter(cl.getOutputStream()));
    }

    /**
     * Metodo para executar a Thread ServidorWriter
     */
    public void run() {
        while (true) {
            try {
                String r = msg.read();
                this.output.write(r);
                this.output.newLine();
                this.output.flush();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
