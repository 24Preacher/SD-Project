package Servidor;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cloud {

    private Map<String, Utilizador> users;
    private Map<Integer, Musica> musicas;
    private Lock usersLock;
    private Lock musicasLock;

    public Cloud() {
        this.users = new HashMap<>();
        this.musicas = new HashMap<>();
        this.usersLock = new ReentrantLock();
        this.musicasLock = new ReentrantLock();
    }

    public void registar(String username, String password) throws IOException {
        usersLock.lock();
        try {
            if (this.users.containsKey(username))
                throw new IOException("Username existente");
            else this.users.put(username, new Utilizador(username, password));
        } finally {
            usersLock.unlock();
        }
    }

    public Utilizador iniciarSessao(String username, String password, MensagemBuffer msg) throws IOException {
        Utilizador u;
        this.usersLock.lock();
        try {
            u = this.users.get(username);
            if (u == null || !u.autenticar(password)) throw new IOException("Dados incorretos");
            else u.setMsg(msg);
        } finally {
            usersLock.unlock();
        }

        return u;
    }

    public byte[] conversor(String path) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));

        int read;
        byte[] buff = new byte[1024];
        while ((read = in.read(buff)) > 0)
        {
            out.write(buff, 0, read);
        }
        out.flush();
        byte[] audioBytes = out.toByteArray();
        in.close();
        out.close();
        return audioBytes;
    }

    public void uploadMusica(String path, String titulo, String artista, String album, String genero) throws IOException {
        this.musicasLock.lock();
        try {
            int id = this.musicas.size();
            System.out.println(id);
            byte[] bytes = conversor(path);
            this.musicas.put(id, new Musica(id, bytes, titulo, artista, album, Integer.parseInt(genero), 0));
        } finally {
            this.musicasLock.unlock();
        }
    }

    public List<Musica> verMusicas() {
        List<Musica> songs = new ArrayList<>();
        for (Musica m : this.musicas.values())
                    songs.add(m);
        return songs;
    }
}
