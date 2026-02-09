package se.hitract.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.hitract.model.enums.EntityType;

@SuppressWarnings("serial")
public class HitClubSmallPushDTO implements java.io.Serializable {

	private Long hitClubId;

	private String hitClubName;
	
	private String about;
    
	private ImageDTO logo;
	
	private ImageDTO headerLogo;
	
	@JsonIgnore
	private String hitClubMailName;
	
	private boolean ladokVerification;
	
	private String stripeAccountId;
	
	public String getCommonHitClubName() {
		return this.hitClubMailName;
	}
	
	@JsonProperty
	public EntityType getTargetEntityType() {
		return EntityType.HIT_CLUB;
	}

	public Long getHitClubId() {
		return hitClubId;
	}

	public void setHitClubId(Long hitClubId) {
		this.hitClubId = hitClubId;
	}

	public String getHitClubName() {
		return hitClubName;
	}

	public void setHitClubName(String hitClubName) {
		this.hitClubName = hitClubName;
	}

	public ImageDTO getLogo() {
		return logo;
	}

	public void setLogo(ImageDTO logo) {
		this.logo = logo;
	}

	public ImageDTO getHeaderLogo() {
		return headerLogo;
	}

	public void setHeaderLogo(ImageDTO headerLogo) {
		this.headerLogo = headerLogo;
	}

	public String getHitClubMailName() {
		return hitClubMailName;
	}

	public void setHitClubMailName(String hitClubMailName) {
		this.hitClubMailName = hitClubMailName;
	}

	public boolean isLadokVerification() {
		return ladokVerification;
	}

	public void setLadokVerification(boolean ladokVerification) {
		this.ladokVerification = ladokVerification;
	}
	
	public String getCurrentStripeAccountId() {
		return stripeAccountId;
	}
	
	public String getStripeAccountId() {
		return stripeAccountId;
	}

	public void setStripeAccountId(String stripeAccountId) {
		this.stripeAccountId = stripeAccountId;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

}
