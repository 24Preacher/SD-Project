package Servidor;

import java.util.ArrayList;

/**
 * Classe dos buffers de Mensagens.
 * @author Grupo 12
 */
public class MensagemBuffer {
    /**Lista de Mensagens no buffer*/
    private ArrayList<String> mensagens;
    /** Indice do buffer*/
    private int index;

    /**Construtor não parametrizado da classe MensagemBuffer*/
    public MensagemBuffer() {
        mensagens = new ArrayList<>();
        index = 0;
    }

    /**
     * Escreve uma mensagem no buffer
     * @param message Mensagem a escrever
     */
    synchronized public void write(String message) {
        mensagens.add(message);
        notifyAll();
    }

    /**
     * Lê a ultima mensagem escrita no Buffer
     * @return message
     * @throws InterruptedException
     */

    synchronized public String read() throws InterruptedException {
        while(isEmpty())
            wait();

        String message = mensagens.get(index);
        index += 1;

        return message;
    }

    /**
     * Verifica se o Buffer está vazio
     * @return Se o buffer está vazio ou não
     */
   synchronized public boolean isEmpty() {
        return mensagens.size() == index;
    }
}
