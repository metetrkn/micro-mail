package se.hitract.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import se.hitract.model.enums.AspectRatio;


@SuppressWarnings("serial")
public class ImageDTO  implements java.io.Serializable {

    private Long imageId;
    
    @JsonIgnore
    private String baseUrl;
    
    private String thumbnail;

    private String small;

    private String medium;
    
    private String large;

	private double aspectRatio;

	private AspectRatio aspectRatioType;

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getThumbnail() {
		return (baseUrl == null ? "" : baseUrl) + thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getSmall() {
		return (baseUrl == null ? "" : baseUrl) + small;
	}

	public void setSmall(String small) {
		this.small = small;
	}

	public String getMedium() {
		return (baseUrl == null ? "" : baseUrl) + medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}
		
	public String getLarge() {
		return (baseUrl == null ? "" : baseUrl) + large;
	}

	public void setLarge(String large) {
		this.large = large;
	}

	public String getDefault() {
		return getMedium();
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
}
