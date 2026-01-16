package se.hitract.model.enums;

public enum INTEGRATION_CATEGORY {

    COMMUNICATION("Kommunikation"),
    ACCOUNTING("Bokföring");

    private String displayName;

    INTEGRATION_CATEGORY(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
