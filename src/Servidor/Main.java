package Servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(9999);
        Cloud serverCloud = new Cloud();
        while(true){
            MensagemBuffer msg = new MensagemBuffer();
            Socket socket = s.accept();
            ServidorReader sr =  new ServidorReader(msg,socket,serverCloud);
            ServidorWriter sw = new ServidorWriter(msg,socket);
            Thread tw = new Thread(sw);
            Thread tr = new Thread(sr);
            tw.start();
            tr.start();
        }
    }
}
