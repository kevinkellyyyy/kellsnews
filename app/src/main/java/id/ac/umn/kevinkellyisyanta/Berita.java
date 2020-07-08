package id.ac.umn.kevinkellyisyanta;

public class Berita {
    private String judul;
    private String deskripsi;
    private String penulis;
    private String url;
    private String urlgambar;
    private String rilis;
    private String content;

    public Berita(String judul, String penulis, String url, String urlgambar, String rilis, String content) {
        this.judul = judul;
        this.penulis = penulis;
        this.url = url;
        this.urlgambar = urlgambar;
        this.rilis = rilis;
        this.content = content;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlgambar() {
        return urlgambar;
    }

    public void setUrlgambar(String urlgambar) {
        this.urlgambar = urlgambar;
    }

    public String getRilis() {
        return rilis;
    }

    public void setRilis(String rilis) {
        this.rilis = rilis;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
