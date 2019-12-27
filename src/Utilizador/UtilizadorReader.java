package Utilizador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class UtilizadorReader implements Runnable {

    private BufferedReader in;
    private Socket socket;
    private Menu menu;

    public UtilizadorReader(Menu menu, Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.menu = menu;
    }

    public void run() {
        try {
            String comando;
            while ((comando = in.readLine()) != null) {
                parse(comando);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void parse(String comando) {
        String[] p = comando.split(" ", 2);
        switch (p[0].toLowerCase()) {
            case "autenticado":
                menu.setOpcao(1);
                menu.apresentarMenu();
                break;
            case "registado":
                menu.setOpcao(0);
                menu.apresentarMenu();
                break;
            case "terminada":
                menu.setOpcao(0);
                menu.apresentarMenu();
                break;
            case "uploaded":
                menu.setOpcao(1);
                menu.apresentarMenu();

                System.out.println("passei no upload");
                break;
            case "visto":

                menu.setOpcao(1);
                menu.apresentarMenu();

                System.out.println("passei no visto");
                break;
            default:
                System.out.println(comando);
                menu.apresentarMenu();

                System.out.println("passei no default");
        }
    }
}

