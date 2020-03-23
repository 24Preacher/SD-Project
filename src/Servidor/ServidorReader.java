package Servidor;

import java.io.*;
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
    private String titulo;


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
                this.msg.write("Inadequado");
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

    /**
     * Lê a linha do BufferReader
     *
     * @return Linha lida
     */
    private String lerLinha() {
        String linha = null;
        try {
            linha = this.in.readLine();
            System.out.println(linha);
        } catch (IOException e) {
            System.out.println("Impossível ler mais mensagens");
        }
        return linha;
    }

    /**
     * Faz parse das linhas lidas para controlar as atividades do Servidor
     *
     * @param s String
     * @return String
     * @throws IOException
     */
    private String parse(String s) throws IOException {
        String[] ss = s.split(" ", 2);
        switch (ss[0].toLowerCase()) {
            case "registar":
                verificaAutenticacao(false);
                try {
                    String registado = this.registar(ss[1]);
                    return registado;

                } catch (Exception e) {
                    return "ERRO REGISTO";
                }

            case "iniciar":
                verificaAutenticacao(false);
                try {
                    String iniciado = this.iniciarSessao(ss[1]);
                    return iniciado;

                } catch (Exception e) {
                    return "ERRO DADOS INCORRETOS";
                }

            case "terminar":
                verificaAutenticacao(true);
                return this.terminarSessao();

            case "upload":
                verificaAutenticacao(true);
                return uploadMusica(ss[1]);

            case "ver":
                verificaAutenticacao(true);
                return "visto";

            case "titulo":
                verificaAutenticacao(true);
                return verMusicasTitulo(ss[1]).toString();

            case "artista":
                verificaAutenticacao(true);
                return verMusicasArtista(ss[1]).toString();

            case "album":
                verificaAutenticacao(true);
                return verMusicasAlbum(ss[1]).toString();

            case "genero":
                verificaAutenticacao(true);
                return verMusicasGenero(ss[1]).toString();

            case "voltar":
                return "voltou";

            case "download":
                verificaAutenticacao(true);
                try {
                    String download = this.downloadMusica(ss[1]);

                    return download;

                } catch (Exception e) {
                    return "ERRO MUSICA INEXISTENTE";
                }

            default:
                return "ERRO";
        }
    }

    /**
     * Verifica se um Utilizador está autenticado
     *
     * @param estado Estado da sessão
     * @throws IOException
     */
    private void verificaAutenticacao(Boolean estado) throws IOException {
        if (estado && utilizador == null)
            throw new IOException("Acesso negado");
        if (!estado && utilizador != null)
            throw new IOException("Já existe um utilizador autenticado");
    }

    /**
     * Regista um novo utilizador
     *
     * @param in Linha lida do BufferReader
     * @return String
     * @throws IOException
     */
    private String registar(String in) throws IOException {
        String[] s = in.split(" ");
        if (s.length != 2)
            throw new IOException("Dados incorretos");
        this.cloud.registar(s[0], s[1]);
        return "registado";
    }

    /**
     * Inicia a Sessão no Servidor
     *
     * @param in Linha lida do BufferReader
     * @return String
     * @throws IOException
     */
    private String iniciarSessao(String in) throws IOException {
        String[] s = in.split(" ");
        if (s.length != 2)
            throw new IOException("Dados incorretos");
        this.utilizador = cloud.iniciarSessao(s[0], s[1], msg);
        return "autenticado";
    }

    /**
     * Termina a Sessão de um Utilizador no Servidor
     *
     * @return String
     */
    private String terminarSessao() {
        this.utilizador = null;
        return "terminada";
    }

    /**
     * Faz download a uma música
     *
     * @param in Linha lida do BufferReader
     * @return String
     * @throws IOException
     */
    private String downloadMusica(String in) throws IOException {
        String[] s = in.split(" ");
        if (s.length != 1)
            throw new IOException("Dados incorretos teste");

        int id = Integer.parseInt(s[0]);

        if (!this.cloud.containMusicID(id))
            throw new IOException("A música não existe");

        this.cloud.downloadMusica(id);

        return "downloaded";
    }

    /**
     * Faz upload de uma música
     *
     * @param in Linha lida do BufferReader
     * @return String todo meter isto melhor
     * @throws IOException
     */
    private String uploadMusica(String in) throws IOException {
        String[] s = in.split(" ", 5);
        int id = this.cloud.getMusicasSize();
        String destPath = "/home/preacher/SD-Project/src/Musicas/" + id + ".txt";

        if (s.length != 5)
            throw new IOException("Dados incorretos");

        this.cloud.uploadMusica(s[0], s[1], s[2], s[3]);

        try {
            DataInputStream dis = new DataInputStream(this.socket.getInputStream());
            FileOutputStream fos = new FileOutputStream(destPath);
            byte[] buffer = new byte[Integer.parseInt(s[4])];


            int filesize = Integer.parseInt(s[4]); // Send file size in separate msg
            int read = 0;
            int totalRead = 0;
            int remaining = filesize;
            while ((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
                totalRead += read;
                remaining -= read;
                System.out.println("Read " + totalRead + " bytes.");
                fos.write(buffer, 0, read);
            }
        } catch (Exception e) {
            throw new IOException("Upload failed");
        }

        return "uploaded";
    }

    /**
     * Ve todas as músicas do Servidor
     *
     * @return Lista com as músicas
     */
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
            String s = String.join(" ", Integer.toString(id),
                    titulo, artista, album, Integer.toString(genero), Integer.toString(downloads));
            resultado.add(s);
        }
        return resultado;
    }

    /**
     * Ve todas as músicas com um determinado Titulo
     *
     * @param in Linha lida do BufferReader
     * @return Lista das músicas
     */
    private List<String> verMusicasTitulo(String in) {
        List<Musica> m = cloud.verMusicasTitulo(in);
        List<String> resultado = new ArrayList<>();
        for (Musica songs : m) {
            int id = songs.getId();
            String titulo = songs.getTitulo();
            String artista = songs.getArtista();
            String album = songs.getAlbum();
            int genero = songs.getGenero();
            int downloads = songs.getnDownloads();
            String s = String.join(" ", Integer.toString(id),
                    titulo, artista, album, Integer.toString(genero), Integer.toString(downloads));
            resultado.add(s);
        }
        return resultado;
    }

    /**
     * Ve todas as músicas com um determinado Artista
     *
     * @param in Linha lida do BufferReader
     * @return Lista das músicas
     */
    private List<String> verMusicasArtista(String in) {
        List<Musica> m = cloud.verMusicasArtista(in);
        List<String> resultado = new ArrayList<>();
        for (Musica songs : m) {
            int id = songs.getId();
            String titulo = songs.getTitulo();
            String artista = songs.getArtista();
            String album = songs.getAlbum();
            int genero = songs.getGenero();
            int downloads = songs.getnDownloads();
            String s = String.join(" ", Integer.toString(id),
                    titulo, artista, album, Integer.toString(genero), Integer.toString(downloads));
            resultado.add(s);
        }
        return resultado;
    }

    /**
     * Ve todas as músicas com um determinado Album
     *
     * @param in Linha lida do BufferReader
     * @return Lista das músicas
     */
    private List<String> verMusicasAlbum(String in) {
        List<Musica> m = cloud.verMusicasAlbum(in);
        List<String> resultado = new ArrayList<>();
        for (Musica songs : m) {
            int id = songs.getId();
            String titulo = songs.getTitulo();
            String artista = songs.getArtista();
            String album = songs.getAlbum();
            int genero = songs.getGenero();
            int downloads = songs.getnDownloads();
            String s = String.join(" ", Integer.toString(id),
                    titulo, artista, album, Integer.toString(genero), Integer.toString(downloads));
            resultado.add(s);
        }
        return resultado;
    }

    /**
     * Ve todas as músicas com um determinado Genero
     *
     * @param in Linha lida do BufferReader
     * @return Lista das músicas
     */
    private List<String> verMusicasGenero(String in) {
        List<Musica> m = cloud.verMusicasGenero(in);
        List<String> resultado = new ArrayList<>();
        for (Musica songs : m) {
            int id = songs.getId();
            String titulo = songs.getTitulo();
            String artista = songs.getArtista();
            String album = songs.getAlbum();
            int genero = songs.getGenero();
            int downloads = songs.getnDownloads();
            String s = String.join(" ", Integer.toString(id),
                    titulo, artista, album, Integer.toString(genero), Integer.toString(downloads));
            resultado.add(s);
        }
        return resultado;
    }

}