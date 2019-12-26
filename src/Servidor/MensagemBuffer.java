package Servidor;

import java.util.ArrayList;

public class MensagemBuffer {

    private ArrayList<String> mensagens;
    private int index;

    public MensagemBuffer() {
        mensagens = new ArrayList<>();
        index = 0;
    }

    synchronized public void write(String message) {
        mensagens.add(message);
        notifyAll();
    }

    synchronized public String read() throws InterruptedException {
        while(isEmpty())
            wait();

        String message = mensagens.get(index);
        index += 1;

        return message;
    }

   synchronized public boolean isEmpty() {
        return mensagens.size() == index;
    }
}
