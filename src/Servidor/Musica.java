package Servidor;

import javax.sound.sampled.AudioInputStream;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Musica {

    private int id;
    private byte[] song;
    private String titulo;
    private String artista;
    private String album;
    private int genero; //0 - Variavel / 1 - Pop / 2 - Rock / 3 - Rap / 4 -Trap .......
    private int nDownloads;

    public Musica(int id, byte[] song, String titulo, String artista, String album, int genero, int nDownloads) {
        this.id = id;
        this.song = song;
        this.titulo = titulo;
        this.artista = artista;
        this.album = album;
        this.genero = genero;
        this.nDownloads = nDownloads;
    }

    public Musica(){
        this.id = 0;
        this.song = new byte[1];
        this.titulo = "";
        this.artista = "";
        this.album = "";
        this.genero = 0;
        this.nDownloads = 0;
    }


    public int getId() {
        return id;
    }

    public byte[] getSong() {
        return song;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getArtista() {
        return artista;
    }

    public String getAlbum() {
        return album;
    }

    public int getGenero() {
        return genero;
    }

    public int getnDownloads() {
        return nDownloads;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSong(byte[] song) {
        this.song = song;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setGenero(int genero) {
        this.genero = genero;
    }

    public void setnDownloads(int nDownloads) {
        this.nDownloads = nDownloads;
    }

    public void nDownloadsInc() {
        setnDownloads((this.nDownloads + 1));
    }

}

