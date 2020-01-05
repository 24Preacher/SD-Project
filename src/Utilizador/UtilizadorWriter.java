package Utilizador;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.FileHandler;

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
                break;
            case 2 :
                if (opcao == 5) {
                    verMusicasTitulo();
                }
                if (opcao == 6) {
                    verMusicasArtista();
                }
                if (opcao == 7) {
                    verMusicasAlbum();
                }
                if (opcao == 8) {
                    verMusicasGenero();
                }
                if (opcao == 0) {
                    voltarInicio();
                }
                break;


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

    public void enviaMusica(String path) throws IOException {
        DataOutputStream dos = new DataOutputStream(this.socket.getOutputStream());
        FileInputStream fis = new FileInputStream(path);
        byte[] buffer = new byte[4096];
        int read;

        while ((read = fis.read(buffer)) > 0) {
            dos.write(buffer,0,read);
        }
    }

    private void downloadMusica() throws IOException{
        String id = menu.lerString("Inserir id da musica a transferir:");
        String q = String.join(" ","download", id);

        out.write(q);
        out.newLine();
        out.flush();
    }

    private void uploadMusica() throws IOException{
        String nomefich = menu.lerString("Ficheiro:");
        String path = "/home/flash_12/Desktop/SD_1920/SD-Project-master/src/Upload/" + nomefich;
        String titulo = menu.lerString("Titulo:");
        String artista = menu.lerString("Artista:");
        String album = menu.lerString("Album:");
        String genero = menu.lerString("0 - Variavel\n1 - Pop\n2 - Rock\n3 - Rap\n4 - Trap\n Escolher opção: ");
        File file = new File(path);

        String q = String.join(" ", "upload", titulo, artista, album, genero, Long.toString(file.length()));

        out.write(q);
        out.newLine();
        out.flush();

        enviaMusica(path);
    }

    private void verMusicas() throws IOException{
        out.write("ver");
        out.newLine();
        out.flush();
    }

    private void verMusicasTitulo() throws IOException{
        String titulo = menu.lerString("Qual o Titulo da música?");
        String q = String.join(" ", "titulo", titulo);
        out.write(q);
        out.newLine();
        out.flush();
    }

    private void verMusicasArtista() throws IOException{
        String artista = menu.lerString("Qual o Artista da música?");
        String q = String.join(" ", "artista", artista);
        out.write(q);
        out.newLine();
        out.flush();
    }

    private void verMusicasAlbum() throws IOException{
          String album = menu.lerString("Qual o Album da música?");
          String q = String.join(" ", "album", album);
          out.write(q);
          out.newLine();
          out.flush();
      }

    private void verMusicasGenero() throws IOException{
        String genero = menu.lerString("Qual o Genero da música?\n 0 - Variavel\n1 - Pop\n2 - Rock\n3 - Rap\n4 - Trap\n Escolher opção: ");
        String q = String.join(" ", "genero", genero);
        out.write(q);
        out.newLine();
        out.flush();
    }

    private void voltarInicio() throws IOException{
        out.write("voltar");
        out.newLine();
        out.flush();
    }

}
