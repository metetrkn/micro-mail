package se.hitract.model.enums;

public enum PRODUCT_TYPE {

	TICKET("Biljett", "Ticket"),
	TICKET_VIP("VIP-inträde", "VIP access"),
	PRODUCT("Produkt","Product"),
	MEMBERSHIP("Medlemskap","Membership"),
	HIT_DEAL("hitDeal","hitDeal"),
	HIT_DEAL_HITCLUB("hitDeal by hitClub","hitDeal by hitClub"),
	HIT_AD_SALE("hitAd (säljes)","hitAd (sale)"),
	HIT_AD_BUY("hitAd (köpes)","hitAd (buy)");
	
	PRODUCT_TYPE(String textSv, String textEn) {
		this.textSv = textSv;
		this.textEn = textEn;
	}

	private String textSv;
	
	private String textEn;

	public String getTextSv() {
		return textSv;
	}

	public void setTextSv(String textSv) {
		this.textSv = textSv;
	}

	public String getTextEn() {
		return textEn;
	}

	public void setTextEn(String textEn) {
		this.textEn = textEn;
	}
	
}
