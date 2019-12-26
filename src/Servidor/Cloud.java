package Servidor;

import java.io.IOException;
import java.util.HashMap;
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
            if (users.containsKey(username))
                throw new IOException("Username existente");
            else users.put(username, new Utilizador(username, password));
        } finally {
            usersLock.unlock();
        }
    }

    public Utilizador iniciarSessao(String username, String password, MensagemBuffer msg) throws IOException {
        Utilizador u;
        usersLock.lock();
        try {
            u = users.get(username);
            if (u == null || !u.autenticar(password)) throw new IOException("Dados incorretos");
            else u.setMsg(msg);
        } finally {
            usersLock.unlock();
        }

        return u;
    }

    public void uploadMusica(String path, String titulo, String artista, String album, String genero){
        musicasLock.lock();
        try {
            int id = this.musicas.size();
            musicas.put(id, new Musica(id, bytes, titulo, artista, album, Integer.parseInt(genero), 0));
        } finally {
            musicasLock.unlock();
        }
    }
}
