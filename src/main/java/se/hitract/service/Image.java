package se.hitract.service;


import jakarta.persistence.*;
import se.hitract.model.CommonEntity;
import se.hitract.model.enums.AspectRatio;

@SuppressWarnings("serial")
@Entity
public class Image extends CommonEntity implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    private String baseUrl;

    private String thumbnail;

    private String small;

    private String medium;

    private String large;

    private String original;

    private double aspectRatio;

    @Enumerated(EnumType.STRING)
    private AspectRatio aspectRatioType;

    private Integer width;

    private Integer height;

    public Image(Long imageId) {
        this.imageId = imageId;
    }

    public Image() {
    }

    @Override
    public Long getId() {
        return imageId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public double getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public AspectRatio getAspectRatioType() {
        return aspectRatioType;
    }

    public void setAspectRatioType(AspectRatio aspectRatioType) {
        this.aspectRatioType = aspectRatioType;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * Calculates and sets both the numeric aspect ratio and the aspect ratio type
     * based on the provided width and height dimensions.
     *
     * @param width The width of the image in pixels
     * @param height The height of the image in pixels
     */
    public void calculateAndSetAspectRatio(int width, int height) {
        this.width = width;
        this.height = height;
        this.aspectRatio = AspectRatio.calculateRatio(width, height);
        this.aspectRatioType = AspectRatio.fromDimensions(width, height);
    }

    /**
     * Updates the aspect ratio type based on the current numeric aspect ratio.
     * Useful when aspectRatio is set directly without dimensions.
     */
    public void updateAspectRatioType() {
        if (this.aspectRatio > 0) {
            this.aspectRatioType = AspectRatio.fromRatio(this.aspectRatio);
        }
    }

    /**
     * Checks if the image has landscape orientation.
     */
    public boolean isLandscape() {
        return aspectRatioType != null && aspectRatioType.isLandscape();
    }

    /**
     * Checks if the image has portrait orientation.
     */
    public boolean isPortrait() {
        return aspectRatioType != null && aspectRatioType.isPortrait();
    }

    /**
     * Checks if the image is square.
     */
    public boolean isSquare() {
        return aspectRatioType != null && aspectRatioType.isSquare();
    }
}
