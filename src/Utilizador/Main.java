package Utilizador;

import Utilizador.Menu;
import Utilizador.UtilizadorReader;
import Utilizador.UtilizadorWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        Socket socket = null;
        try {

            socket = new Socket("127.0.0.1", 9999);
            Menu menu =  new Menu();
            UtilizadorWriter uw = new UtilizadorWriter(menu,socket);
            UtilizadorReader ur = new UtilizadorReader(menu,socket);
            Thread writer = new Thread(uw);
            Thread reader = new Thread(ur);
            reader.start();
            writer.start();
        } catch(IOException e){System.out.println(e.getMessage());}
    }
}
