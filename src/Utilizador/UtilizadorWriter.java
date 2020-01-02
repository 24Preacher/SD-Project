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
        int opcao;
        menu.apresentarMenu();
        try {
            while ((opcao = menu.opcao()) != -1) {
                parse(opcao);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parse(Integer opcao) throws IOException {
        switch (menu.getOpcao()) {
            case 0:
                if (opcao == 1) {
                    iniciarSessao();
                }
                if (opcao == 2) {
                    registar();
                }
                if (opcao == 0)
                    System.exit(0);
                break;
            case 1:
                if (opcao == 1){
                    uploadMusica();
                }
                if (opcao == 2){
                    downloadMusica();
                }
                if (opcao == 3){
                    verMusicas();
                }
                if (opcao == 0){
                    terminarSessao();
                }
        }
    }

    private void iniciarSessao() throws IOException{
        String username = menu.lerString("Username: ");
        String password = menu.lerString("Password: ");
        String q = String.join(" ", "iniciar", username, password);
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

    private void terminarSessao() throws IOException {
        out.write("terminar");
        out.newLine();
        out.flush();
    }

    private void downloadMusica() throws IOException{
        String id = menu.lerString("Inserir id da musica a transferir:");
        String q = String.join(" ","download", id);
        out.write(q);
        out.newLine();
        out.flush();
    }

    private  void uploadMusica() throws IOException{


        String nomefich = menu.lerString("Ficheiro:");
        String path = "/home/flash_12/Desktop/SD_1920/SD-Project-master/src/Upload/" + nomefich;
        String titulo = menu.lerString("Titulo:");
        String artista = menu.lerString("Artista:");
        String album = menu.lerString("Album:");
        String genero = menu.lerString("0 - Variavel\n1 - Pop\n2 - Rock\n3 - Rap\n4 - Trap\n Escolher opção: ");
        String q = String.join(" ", "upload", path, titulo, artista, album, genero);
        out.write(q);
        out.newLine();
        out.flush();
    }

    private void verMusicas() throws IOException{
        out.write("ver");
        out.newLine();
        out.flush();
    }
}
