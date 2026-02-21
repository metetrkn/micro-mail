package se.hitract.model;


import jakarta.persistence.*;

@SuppressWarnings("serial")
@Entity
public class Attachment implements java.io.Serializable {


    @Id
    private Long id;

    private String filename;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
