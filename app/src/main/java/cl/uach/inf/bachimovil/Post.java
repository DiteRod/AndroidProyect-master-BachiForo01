package cl.uach.inf.bachimovil;
//lola
public class Post {
    private String id;
    private String titulo;
    private String descripcion;
    private String taqs;

    public Post(String id,String titulo,String descripcion,String taqs) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.taqs= taqs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTaqs() {
        return taqs;
    }

    public void setTaqs(String taqs) {
        this.taqs = taqs;
    }
}

