package Utilizador;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class UtilizadorWriter implements Runnable{
    private BufferedWriter out;
    private Socket socket;
    private Menu menu;

    public UtilizadorWriter(Menu menu, Socket socket){
        try{
            this.socket = socket;
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.menu = menu;
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        int op;
        menu.apresentarMenu();
        try {
            while ((op = menu.opcao()) != -1) {
                parse(op);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parse(Integer op) throws IOException {
        switch (menu.getOpcao()) {
            case 0:
                if (op == 1) {
                    iniciarSessao();
                }
                if (op == 2) {
                    registar();
                }
                if (op == 0)
                    System.exit(0);
                break;
        }
    }

    private void iniciarSessao() throws IOException{
        String username = menu.lerString("Username: ");
        String password = menu.lerString("Password: ");
        String q = String.join(" ", "iniciarsessao", username, password);
        out.write(q);
        out.newLine();
        out.flush();
    }

    private void registar() throws IOException{
        String username = menu.lerString("Username: ");
        String password = menu.lerString("Password: ");
        String q = String.join(" ", "registar", username, password);
        out.write(q);
        out.newLine();
        out.flush();
    }
}
