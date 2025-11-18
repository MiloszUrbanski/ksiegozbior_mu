public class Ksiazka {
    private int id;
    private String tytul;
    private String autor;
    private int rokWydania;


    public Ksiazka(int id, String tytul, String autor, int rokWydania) {
        this.id = id;
        this.tytul = tytul;
        this.autor = autor;
        this.rokWydania = rokWydania;
    }

    public Ksiazka(String tytul, String autor, int rokWydania) {
        this(0, tytul, autor, rokWydania);
    }
    public int getId() {
        return id;
    }
    public String getTytul() {
        return tytul;
    }
    public String getAutor() {
        return autor;
    }
    public int getRokWydania() {
        return rokWydania;
    }
}
