package Servidor;

/**
 * Classe Musica.
 * @author Grupo 12
 */

public class Musica {
    /**Identificar da música*/
    private int id;
    /**Array de bytes da música*/
    private String titulo;
    /**Artista da música*/
    private String artista;
    /**Album da música*/
    private String album;
    /**Genero da música*/
    private int genero;
    /**Número de downloads da música*/
    private int nDownloads;

    /**
     * Cosntrutor parametrizado da Classe Música
     * @param id Identificar da música
     * @param titulo Titulo da música
     * @param artista Artista da música
     * @param album Album da música
     * @param genero Genero da música
     * @param nDownloads Número de downloads da música
     */
    public Musica(int id, String titulo, String artista, String album, int genero, int nDownloads) {
        this.id = id;
        this.titulo = titulo;
        this.artista = artista;
        this.album = album;
        this.genero = genero;
        this.nDownloads = nDownloads;
    }

    /**
     * Construtor não parametrizado da Classe Música
     */
    public Musica(){
        this.id = 0;
        this.titulo = "";
        this.artista = "";
        this.album = "";
        this.genero = 0;
        this.nDownloads = 0;
    }

    /**
     * Devolve o Identificador da música
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Devolve o Titulo da música
     * @return titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Devolve o Artista da música
     * @return artista
     */
    public String getArtista() {
        return artista;
    }

    /**
     * Devolve o Album da música
     * @return album
     */
    public String getAlbum() {
        return album;
    }

    /**
     * Devolve o Genero da música
     * @return genero
     */
    public int getGenero() {
        return genero;
    }

    /**
     * Devolve o número de Downloads da música
     * @return nDownloads
     */
    public int getnDownloads() {
        return nDownloads;
    }

    /**
     * Atualiza o Identificador da música
     * @param id Novo Identificador
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Atualiza o Titulo da música
     * @param titulo Novo Titulo
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Atualiza o Artista da Música
     * @param artista Novo Artista
     */
    public void setArtista(String artista) {
        this.artista = artista;
    }

    /**
     * Atualiza o Album da música
     * @param album Novo Album
     */
    public void setAlbum(String album) {
        this.album = album;
    }

    /**
     * Atualiza o Genero da música
     * @param genero Novo Genero
     */
    public void setGenero(int genero) {
        this.genero = genero;
    }

    /**
     * Atualiza o numero de Download da música
     * @param nDownloads Novo numero de Downloads
     */
    public void setnDownloads(int nDownloads) {
        this.nDownloads = nDownloads;
    }

    /**
     * Incrementa 1 ao número de Downloads da música
     */
    public void nDownloadsInc(){
        setnDownloads((this.nDownloads+1));
    }

}