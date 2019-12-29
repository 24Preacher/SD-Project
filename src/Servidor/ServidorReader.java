package Servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServidorReader implements Runnable {

    private Utilizador utilizador;
    private Musica musica;
    private BufferedReader in;
    private MensagemBuffer msg;
    private Socket socket;
    private Cloud cloud;

    public ServidorReader(MensagemBuffer msg, Socket socket, Cloud cloud) throws IOException {
        this.msg = msg;
        this.socket = socket;
        this.cloud = cloud;
        this.utilizador = null;
        this.musica = null;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void run() {
        String r;
        while ((r = lerLinha()) != null) {
            try {
                this.msg.write(parse(r));
            } catch (IndexOutOfBoundsException e) {
                this.msg.write("Inadequado teste");
            } catch (IOException e) {

                e.printStackTrace();
            }
        }

        if (this.utilizador == null) {
            try {
                this.socket.shutdownInput();
                this.socket.shutdownOutput();
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String lerLinha() {
        String linha = null;
        try {
            linha = this.in.readLine();
        } catch (IOException e) {
            System.out.println("Impossível ler mais mensagens");
        }
        return linha;
    }

    private String parse(String s) throws IOException {
        String[] ss = s.split(" ",2);
        switch (ss[0].toLowerCase()){
            case "registar":
                verificaAutenticacao(false);
                return this.registar(ss[1]);

            case "iniciar":
                verificaAutenticacao(false);
                return this.iniciarSessao(ss[1]);

            case "terminar":
                verificaAutenticacao(true);
                return this.terminarSessao();

            case "upload":
                verificaAutenticacao(true);
                return uploadMusica(ss[1]);
            case "ver":
                verificaAutenticacao(true);
                return this.verMusicas().toString();
            case "download":
                verificaAutenticacao(true);
                return this.downloadMusica(ss[1]);
            default:
                return "ERRO";
        }
    }

    private void verificaAutenticacao(Boolean estado) throws IOException {
        if (estado && utilizador == null)
            throw new IOException("Acesso negado");
        if (!estado && utilizador != null)
            throw new IOException("Já existe um utilizador autenticado");
    }

    private String registar(String in) throws IOException {
        String[] s = in.split(" ");
        if (s.length != 2)
            throw new IOException("Dados incorretos");
        this.cloud.registar(s[0], s[1]);
        return "registado";
    }

    private String iniciarSessao(String in) throws IOException {
        String[] s = in.split(" ");
        if (s.length != 2)
            throw new IOException("Dados incorretos");
        this.utilizador = cloud.iniciarSessao(s[0], s[1],msg);
        return "autenticado";
    }

    private String terminarSessao() {
        this.utilizador = null;
        return "terminada";
    }

    private String downloadMusica(String in) throws IOException{
        String[] s = in.split(" ");
        if (s.length != 1)
            throw new IOException("Dados incorretos teste");
        int id = Integer.parseInt(s[0]);
        if (!this.cloud.containMusicID(id))
            throw new IOException("A música não existe");
        this.cloud.downloadMusica(id);
        return "downloaded";
    }

    private String uploadMusica(String in) throws IOException{
        String[] s = in.split(" ");
        if (s.length != 5)
            throw new IOException("Dados incorretos");
       this.cloud.uploadMusica(s[0], s[1], s[2], s[3], s[4]);
        return "uploaded";
    }

    private List<String> verMusicas() {
        List<Musica> m = cloud.verMusicas();
        List<String> resultado = new ArrayList<>();
        for (Musica songs : m) {
            int id = songs.getId();
            String titulo = songs.getTitulo();
            String artista = songs.getArtista();
            String album = songs.getAlbum();
            int genero = songs.getGenero();
            int downloads = songs.getnDownloads();
            String s = String.join(" " ,Integer.toString(id),
                    titulo, artista, album, Integer.toString(genero), Integer.toString(downloads));
            resultado.add(s);
        }
        return resultado;
    }
}
