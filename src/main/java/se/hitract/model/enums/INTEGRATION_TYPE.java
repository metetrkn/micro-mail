package se.hitract.model.enums;

public enum INTEGRATION_TYPE {

    FORTNOX("Fortnox", "Automatiserad bokföring","feature", "https://storage.hitract.se/resources/integration/fortnox.png", true, INTEGRATION_CATEGORY.ACCOUNTING),
    INSTAGRAM("Instagram", "Posta content på ditt instagram","feature","https://storage.hitract.se/resources/integration/instagram.png", true, INTEGRATION_CATEGORY.COMMUNICATION),
    LINKEDIN("LinkedIn", "Posta content på din Linkedin","feature","https://storage.hitract.se/resources/integration/linkedin.jpg", true, INTEGRATION_CATEGORY.COMMUNICATION),
    TIKTOK("TikTok", "Posta content på din TikTok","feature","https://storage.hitract.se/resources/integration/tiktok.png", true, INTEGRATION_CATEGORY.COMMUNICATION),
    SIMPLESIGN("SimpleSign", "Hantera filer från SimpleSign direkt på hitract","feature","https://storage.hitract.se/resources/integration/simplesign.png", false, INTEGRATION_CATEGORY.COMMUNICATION);

    private String displayName;

    private String description;

    private String feature;

    private String logoUrl;

    private boolean userSelectable;

    private INTEGRATION_CATEGORY integrationCategory;

    INTEGRATION_TYPE(String displayName, String description, String feature, String logoUrl, boolean userSelectable, INTEGRATION_CATEGORY integrationCategory) {
        this.displayName = displayName;
        this.description = description;
        this.feature = feature;
        this.logoUrl = logoUrl;
        this.userSelectable = userSelectable;
        this.integrationCategory = integrationCategory;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isUserSelectable() {
        return userSelectable;
    }

    public void setUserSelectable(boolean userSelectable) {
        this.userSelectable = userSelectable;
    }

    public INTEGRATION_CATEGORY getIntegrationCategory() {
        return integrationCategory;
    }

    public void setIntegrationCategory(INTEGRATION_CATEGORY integrationCategory) {
        this.integrationCategory = integrationCategory;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
