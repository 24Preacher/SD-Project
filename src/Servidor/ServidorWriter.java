package Servidor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServidorWriter implements Runnable{
    private MensagemBuffer msg;
    private BufferedWriter output;

    ServidorWriter(MensagemBuffer msg, Socket cl) throws IOException {
        this.msg = msg;
        this.output = new BufferedWriter(new OutputStreamWriter(cl.getOutputStream()));
    }

    public void run() {
        while (true) {
            try {
                String r = msg.read();
                output.write(r);
                output.newLine();
                output.flush();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
