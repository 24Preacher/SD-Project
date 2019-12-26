package Servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServidorReader implements Runnable {

    private Utilizador utilizador;
    private BufferedReader in;
    private MensagemBuffer msg;
    private Socket socket;
    private Cloud cloud;

    public ServidorReader(MensagemBuffer msg, Socket socket, Cloud cloud) throws IOException {
        this.msg = msg;
        this.socket = socket;
        this.cloud = cloud;
        this.utilizador = null;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void run() {
        String r;
        while ((r = readLine()) != null) {
            try {
                msg.write(parse(r));
            } catch (IndexOutOfBoundsException e) {
                msg.write("Inadequado");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //   endConnection();
        if (this.utilizador == null) {
            try {
                socket.shutdownInput();
                socket.shutdownOutput();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String readLine() {
        String linha = null;
        try {
            linha = in.readLine();
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

            case "iniciarsessao":
                verificaAutenticacao(false);
                return this.iniciarSessao(ss[1]);

            case "terminarsessao":
                verificaAutenticacao(true);
                return this.terminarSessao();
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
        cloud.registar(s[0], s[1]);
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
}
