package se.hitract.model;

import lombok.Getter;
import lombok.Setter;

public class HitEventMailDTO {

    private String name;
    private String hitClubName;
    private ImageDTO hitClubLogo;
    private ImageDTO hitClubHeaderLogo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHitClubName() {
        return hitClubName;
    }

    public void setHitClubName(String hitClubName) {
        this.hitClubName = hitClubName;
    }

    public ImageDTO getHitClubLogo() {
        return hitClubLogo;
    }

    public void setHitClubLogo(ImageDTO hitClubLogo) {
        this.hitClubLogo = hitClubLogo;
    }

    public ImageDTO getHitClubHeaderLogo() {
        return hitClubHeaderLogo;
    }

    public void setHitClubHeaderLogo(ImageDTO hitClubHeaderLogo) {
        this.hitClubHeaderLogo = hitClubHeaderLogo;
    }
}
